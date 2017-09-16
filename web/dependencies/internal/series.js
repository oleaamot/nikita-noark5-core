var app = angular.module('nikita-series', []);

var seriesController = app.controller('SeriesController', ['$scope', '$http', function ($scope, $http) {

    // Used to show or hide a text message
    $scope.createSeries = false;

    $scope.documentMediumList = documentMediumList;
    $scope.seriesStatusList = seriesStatusList;
    $scope.oppbevaringsStedDisabled = false;

    // Used to show systemID and title of fonds adding the series to
    $scope.fonds = GetChosenFonds();

    // Get the current series (if one exists)
    $scope.series = GetChosenSeries();

    // If the series exists set the contents of some drop down lists
    if ($scope.series != null) {
        $scope.selectedDocumentMedium = $scope.series.dokumentmedium;
        $scope.selectedSeriesStatus = $scope.series.arkivstatus;
        console.log("scope.selectedSeriesStatus is set to [" + $scope.selectedSeriesStatus + "]");

        // Retrieve the latest copy of the data and pull out the ETAG
        // Find the self link of the current series and issue a GET
        for (var rel in $scope.series._links) {
            var relation = $scope.series._links[rel].rel;
            if (relation == REL_SELF) {
                var urlToFonds = $scope.series._links[rel].href;
                var token = GetUserToken();
                $http({
                    method: 'GET',
                    url: urlToFonds,
                    headers: {'Authorization': token}
                }).then(function successCallback(response) {
                    // This returns a list and later we will handle a list properly in GUI, but right now I just
                    // need to fetch the first one. I also need an ETAG in case it is to be edited, so I have to
                    // retrieve (again) the object I am actually out after
                    $scope.series = response.data;
                    $scope.seriesETag = response.headers('eTag');
                    console.log("Retrieved the following series " + JSON.stringify($scope.series));
                    console.log("The ETAG header for the series is " + $scope.seriesETag);
                });
            }
        }

    }
    else {
        $scope.createSeries = true;
    }

    $scope.post_or_put_series = function () {
        var urlSeries = '';

        // check that it's not null, create a popup here if it is
        var method = '';
        if ($scope.createSeries) {
            method = "POST";
            var currentFonds = GetChosenFonds();
            if (currentFonds != null) {
                // Check that currentFonds._links exists??
                // Find the self link
                for (var rel in currentFonds._links) {
                    var relation = currentFonds._links[rel].rel;
                    if (relation === REL_NEW_SERIES) {
                        urlSeries = currentFonds._links[rel].href;
                        console.log(method + " Attempting to create series with following address = " + urlSeries);
                    }
                }
            }
        } else {
            method = "PUT";
            var currentSeries = GetChosenSeries();
            if (currentSeries != null) {
                // Check that currentSeries._links exists??
                // Find the self link
                for (var rel in currentSeries._links) {
                    var relation = currentSeries._links[rel].rel;
                    if (relation === REL_SELF) {
                        urlSeries = currentSeries._links[rel].href;
                        console.log(method + " Attempting to update series with following address = " + urlSeries);
                    }
                }
            }
            else {
                alert("Something went wrong. Attempt to update a fonds object that is not registered as existing yet!");
            }
        }

        console.log("document.getElementById(arkivperiodeStartDato)" + document.getElementById("arkivperiodeStartDato").value);
        console.log("document.getElementById(arkivperiodeStartDato)" + document.getElementById("arkivperiodeStartDato").value);


        $http({
            url: urlSeries,
            method: method,
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': GetUserToken(),
                'ETAG': $scope.seriesETag
            },
            data: {
                tittel: $.trim(document.getElementById("tittel").value),
                beskrivelse: $.trim(document.getElementById("beskrivelse").value),
                dokumentmedium: $.trim($scope.selectedDocumentMedium),
                arkivdelstatus: $.trim($scope.selectedSeriesStatus),
                arkivperiodeStartDato: $.trim(document.getElementById("arkivperiodeStartDato").value),
                arkivperiodeSluttDato: $.trim(document.getElementById("arkivperiodeSluttDato").value),
            },
        }).then(function successCallback(response) {
            console.log(method + " put/post on series. data returned= " + JSON.stringify(response.data));
            // Update the fonds object so fields in GUI are changed
            $scope.series = response.data;
            // Pick up and make a note of the ETAG so we can update the object
            $scope.seriesETag = response.headers('eTag');
            // Now we can edit the fonds object, add fondsCreator
            $scope.createSeries = false;
        });
    };
}]);
