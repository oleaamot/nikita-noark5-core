var app = angular.module('nikita-casefile', []);
var casefile_dashboard = app.controller('CaseFileDashboardController', ['$scope', '$http', function ($scope, $http) {


    Hello();


    var Hello = function (seriesSystemId) {
        localStorage.setItem("currentSeriesSystemId", seriesSystemId);
        console.log("Setting seriesSystemId=" + seriesSystemId);
    };

}]);


