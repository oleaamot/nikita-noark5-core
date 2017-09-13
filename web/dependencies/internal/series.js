var app = angular.module('nikita-series', []);

var seriesController = app.controller('SeriesController', ['$scope', '$http', function ($scope, $http) {

    // Used to show or hide a text message
    $scope.showCreateSeries = false;

    $scope.documentMediumList = documentMediumList;
    $scope.seriessStatusList = seriesStatusList;
    $scope.oppbevaringsStedDisabled = false;

    $scope.series = GetChosenSeries();

    if ($scope.series != null) {
        $scope.selectedDocumentMedium = $scope.series.dokumentmedium;
        $scope.selectedSeriesStatus = $scope.series.arkivstatus;
        console.log("scope.selectedSeriesStatus is set to [" + $scope.selectedSeriesStatus + "]");
    }
    else {
        $scope.showCreateSeries = true;
    }


}]);
