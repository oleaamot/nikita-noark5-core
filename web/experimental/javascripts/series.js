
let app = angular.module('nikita-series', []);

var SetLinkToChosenFile = function(t) {
    localStorage.setItem("linkToChosenFile", t);
    console.log("Setting linkToChosenFile="+t);
};

var GetFondsSystemID = function(t) {
    console.log("Getting chose fondSystemId="+localStorage.getItem("chosenfonds"));
    return localStorage.getItem("chosenfonds");
};


var GetUserToken = function(t) {
    return localStorage.getItem("token");
};

var GetLinkToChosenSeries = function(t) {
    return localStorage.getItem("linkToChosenSeries");
};

let seriesController = app.controller('SeriesController', ['$scope', '$http', function ($scope, $http) {
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

    $scope.send_form = function() {
        token = GetUserToken();
        systemID = GetFondsSystemID();
        url = 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/' + systemID + '/ny-arkivdel';
        $http({
            url: url,
            method: "POST",
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': token,
            },
            data: { tittel: $scope.tittel, beskrivelse: $scope.beskrivelse },
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
            //only use this if you want to replace the history stack
            //$location.path(url).replace();

            //this this if you want to change the URL and add it to the history stack
            $location.path(url);
            $scope.$apply();
        }
    };

}]);
