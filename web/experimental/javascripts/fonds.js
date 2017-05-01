
let app = angular.module('nikita-fonds', []);

var SetLinkToChosenSeries = function(t) {
    localStorage.setItem("linkToChosenSeries", t);
    console.log("Setting linkToChosenSeries="+t);
};

var SetChosenFonds = function(fondSystemId) {
    localStorage.setItem("chosenfonds", fondSystemId);
    console.log("Setting fondSystemId="+fondSystemId);
};

var changeLocation = function ($scope, url, forceReload) {
    $scope = $scope || angular.element(document).scope();
    console.log("URL" + url);
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

let fondsController = app.controller('FondsController', ['$scope', '$http', function ($scope, $http) {
    $scope.token = GetUserToken();
    console.log("token="+$scope.token);
    $scope.fonds = "xyz";
    $http({
        method: 'GET',
        url: 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv',
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        $scope.fonds = response.data.results;
        console.log("data is : " + JSON.stringify(response.data));
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
        window.location = "http://localhost:3000/experimental/arkivdel.html";
    }

    $scope.send_form = function() {
	token = GetUserToken();
	url = 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkiv';
	$http({
	    url: url,
	    method: "POST",
	    headers: {
		'Content-Type': 'application/vnd.noark5-v4+json',
		'Authorization': token,
	    },
	    data: { tittel: $scope.tittel, beskrivelse: $scope.beskrivelse },
	}).then(function(data, status, headers, config) {
            changeLocation($scope, "./fonds.html", true);
	}, function(data, status, headers, config) {
            alert(data.data);
	});
    };
}]);

var GetUserToken = function(t) {
    return localStorage.getItem("token");
};
