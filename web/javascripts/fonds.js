
let app = angular.module('nikita-fonds', []);

var SetLinkToChosenSeries = function(t) {
    localStorage.setItem("linkToChosenSeries", t);
    console.log("Setting linkToChosenSeries="+t);
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
    $scope.seriesSelected = function(href){
        console.log('series selected link clicked ' + href);
        token = GetUserToken();
        SetLinkToChosenSeries(href);
        window.location = "http://localhost:3000/series.html";
    }
}]);

var GetUserToken = function(t) {
    return localStorage.getItem("token");
};
