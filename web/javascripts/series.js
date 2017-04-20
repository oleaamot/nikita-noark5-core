
let app = angular.module('nikita-series', []);

var SetLinkToChosenFile = function(t) {
    localStorage.setItem("linkToChosenFile", t);
    console.log("Setting linkToChosenFile="+t);
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
    $scope.fonds = "xyz";

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

    // TODO : Add the href to local storage indicating what was clicked
}]);
