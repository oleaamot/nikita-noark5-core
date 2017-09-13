var app = angular.module('nikita-seriesList', []);

var seriesListController = app.controller('SeriesListController', ['$scope', '$http', function ($scope, $http) {

    /*
     * Find the current fonds and undertake a GET request to nikita to get all
     * the series associated with the current fonds. Populate data in GUI with
     * the data
     */
    $scope.fonds = GetChosenFonds();

    if ($scope.fonds != null) {
        for (var rel in $scope.fonds._links) {
            var relation = $scope.fonds._links[rel].rel;
            if (relation == REL_SERIES) {
                var urlSeriesList = $scope.fonds._links[rel].href;
                var token = GetUserToken();
                $http({
                    method: 'GET',
                    url: urlSeriesList,
                    headers: {'Authorization': token}
                }).then(function successCallback(response) {
                    $scope.seriesList = response.data;
                    console.log("Found following series " + JSON.stringify($scope.seriesList));
                }, function errorCallback(response) {
                    console.log("Something failed. All  know is : " + JSON.stringify(response));
                });
            }
        }
    }

    /**
     *  series_selected
     *
     *  When a user presses "Opprett nytt arkiv" or clicks on a individual row of a series item from
     *  the list of series, this function calls a change location to the page arkiv.html
     *
     * @param series Note: Can be null if creating a new series
     */
    $scope.series_selected = function (series) {
        console.log('series_selected clicked ' + JSON.stringify(series));
        if (series === null) {
            SetChosenSeries(null);
        }
        else {
            SetChosenSeries(series);
        }
        changeLocation($scope, seriesPageName, false);
    };
}]);
