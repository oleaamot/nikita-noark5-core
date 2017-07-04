var app = angular.module('nikita-fonds', []);


var changeLocation = function ($scope, url, forceReload) {
    $scope = $scope || angular.element(document).scope();
    console.log("fonds.js changelocation to URL" + url);
    if (forceReload || $scope.$$phase) {
        window.location = url;
    }
    else {
        //only use this if you want to replace the history stack
        //$location.path(url).replace();

        //this this if you want to change the URL and add it to the history stack
        $location.path(url);
        $scope.$apply();
    }
};

var fondsController = app.controller('FondsController', ['$scope', '$http',
    function ($scope, $http) {

        $scope.display_footer_note = display_footer_note;
        $scope.token = GetUserToken();
        $scope.show_create_fonds = true;

        $http({
        method: 'GET',
        url: app_url + '/arkivstruktur/arkiv',
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        $scope.fonds = response.data.results;
        console.log("data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    url = app_url + '/arkivstruktur/ny-arkiv';
    $scope.formfields = ['tittel', 'beskrivelse'];
    $http({
        method: 'GET',
        url: url,
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
	for (var key of Object.keys(response.data)) {
	    if ("_links" === key) {
	    } else {
		//console.log(key,response.data[key]);
		$scope[key] = response.data[key];
		$scope.formfields.push(key);
	    }
	}
        //console.log("ny-arkiv data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    // TODO : Add the href to local storage indicating what was clicked
    //
    $scope.seriesSelected = function(href, fondSystemId){
        console.log('series selected link clicked ' + href);
        token = GetUserToken();
        SetLinkToChosenSeries(href);
        SetChosenFonds(fondSystemId);
        window.location = gui_base_url + "/arkivdel.html";
    };

    $scope.send_form = function() {
	token = GetUserToken();
	url = app_url + '/arkivstruktur/ny-arkiv';
	formdata = {};
	for (var key of $scope.formfields) {
	    formdata[key] = $scope[key];
	}
	$http({
	    url: url,
	    method: "POST",
	    headers: {
		'Content-Type': 'application/vnd.noark5-v4+json',
		'Authorization': token,
	    },
	    data: formdata,
	}).then(function(data, status, headers, config) {
            changeLocation($scope, "./arkiv.html", true);
	}, function(data, status, headers, config) {
            alert(data.data);
	});
    };
}]);

var GetUserToken = function(t) {
    return localStorage.getItem("token");
};
