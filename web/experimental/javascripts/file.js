let app = angular.module('nikita-file', []);

let base_url = "http://localhost:8092/noark5v4";
let app_url = "http://localhost:8092/noark5v4/hateoas-api";
let gui_base_url = "http://localhost:3000/experimental";

if (nikitaOptions.enabled) {
    console.log("nikita baseURL" + nikitaOptions.baseUrl);
    base_url = nikitaOptions.protocol + "://" + nikitaOptions.baseUrl  + "/" + nikitaOptions.appName;
    app_url = base_url + "/" + nikitaOptions.apiName;
    gui_base_url = nikitaOptions.guiBaseUrl;
}

var SetLinkToChosenFile = function(t) {
    localStorage.setItem("linkToChosenFile", t);
    console.log("Setting linkToChosenFile="+t);
};

var GetSeriesSystemID = function(t) {
    console.log("Getting chosen seriesSystemId="+localStorage.getItem("chosenseries"));
    return localStorage.getItem("chosenseries");
};


var GetUserToken = function(t) {
    return localStorage.getItem("token");
};

var GetLinkToChosenFile = function(t) {
    return localStorage.getItem("linkToChosenFile");
};

let fileController = app.controller('FileController', ['$scope', '$http', function ($scope, $http) {
    $scope.token = GetUserToken();
    console.log("token="+$scope.token);
    $scope.series = GetSeriesSystemID();

    var urlVal = GetLinkToChosenFile();
    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        $scope.files = response.data.results;
        console.log("file data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    $scope.send_form = function() {
        token = GetUserToken();
        systemID = GetSeriesSystemID();
        url = app_url + '/arkivstruktur/arkivdel/' + systemID + '/ny-mappe';
        $http({
            url: url,
            method: "POST",
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': token,
            },
            data: {
		tittel: $scope.tittel,
		beskrivelse: $scope.beskrivelse,
		mappeID: $scope.mappeID,
	    },
        }).then(function(data, status, headers, config) {
            changeLocation($scope, "./mappe.html", true);
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
            //only use this if you want to replace the history stack
            //$location.path(url).replace();

            //this this if you want to change the URL and add it to the history stack
            $location.path(url);
            $scope.$apply();
        }
    };

}]);
