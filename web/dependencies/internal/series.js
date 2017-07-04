var app = angular.module('nikita-series', []);

var SetLinkToSeriesAllFile = function (t) {
    localStorage.setItem("linkToSeriesAllFile", t);
    console.log("Setting linkToSeriesAllFile=" + t);
};

var GetFondsSystemID = function(t) {
    console.log("Getting chosen fondsSystemId="+localStorage.getItem("chosenfonds"));
    return localStorage.getItem("chosenfonds");
};

var GetUserToken = function(t) {
    return localStorage.getItem("token");
};

var GetLinkToChosenSeries = function(t) {
    return localStorage.getItem("linkToChosenSeries");
};

var SetChosenSeries = function(seriesSystemId) {
    localStorage.setItem("currentSeriesSystemId", seriesSystemId);
    console.log("Setting seriesSystemId="+seriesSystemId);
};

var seriesController = app.controller('SeriesController', ['$scope', '$http', function ($scope, $http) {
    $scope.token = GetUserToken();
    console.log("token="+$scope.token);
    $scope.fonds = GetFondsSystemID();

    var urlVal = GetLinkToChosenSeries();
    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        $scope.seriess = response.data.results;
        console.log("series data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    url = app_url + '/arkivstruktur/arkiv/' +  $scope.fonds + '/ny-arkivdel';
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
        //console.log("ny-arkivdel data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    $scope.casefileSelected = function (series) {
        console.log('series selected clicked ' + JSON.stringify(series));
        SetChosenSeries(series);
        window.location = gui_base_url + "/saksmappe.html";
    };

    $scope.send_form = function() {
        token = GetUserToken();
        systemID = GetFondsSystemID();
        url = app_url + '/arkivstruktur/arkiv/' + systemID + '/ny-arkivdel';
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
            changeLocation($scope, "./arkivdel.html", true);
        }, function(data, status, headers, config) {
            alert(data.data);
        });
    };

    var changeLocation = function ($scope, url, forceReload) {
        $scope = $scope || angular.element(document).scope();
        console.log("URL" + url);
        if (forceReload || $scope.$$phase) {
            window.location = url;
        }
        else {
            $location.path(url);
            $scope.$apply();
        }
    };

    var findAndSetChosenSeries = function (series) {
        SetCurrentSeries(series);
        for (var rel in series._links) {
            var relation = series._links[rel].rel;
            // find one that contains a link to a self
            if (relation === REL_SELF) {
                SetLinkToCurrentSeries(series._links[rel].href);
            }
        }
    }

}]);
