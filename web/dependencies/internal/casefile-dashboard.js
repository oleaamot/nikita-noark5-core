var app = angular.module('nikita-casefile', []);
var casefile_dashboard = app.controller('CaseFileDashboardController', ['$scope', '$http', function ($scope, $http) {


    localStorage.setItem("currentSeriesSystemId", seriesSystemId);
    console.log("Setting seriesSystemId=" + seriesSystemId);

    var createCaseFile = function () {
        localStorage.setItem("currentSeriesSystemId", seriesSystemId);
        console.log("Setting seriesSystemId=" + seriesSystemId);
    };

}]);


