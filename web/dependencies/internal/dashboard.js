var app = angular.module('nikita-casefile-dashboard', []);

/**
 *
 * slightly complicated approach that should work nicely. When the saksbehandler-dashboard.html page loads,
 * we need a drop down list with all the arkivdel that we can pull case-files out of. To make sure, we are in
 * compliance with the standard interface, we don't store a list of relevant arkivdel that is retrievable.
 * Instead we start at application root (http://localhost:8092/noark5v4/) retrieving a list of functional
 * areas. From there we use the href associated with (http://rel.kxml.no/noark5/v4/api/arkivstruktur/).
 * This gives us a list of high-level noark entities we can interact with. So we fetch the href associated
 * with (http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkivdel/) and that should give us a list of arkivdel
 * that the user can interact with.
 *
 */

app.controller('CaseFileDashboardController', ['$scope', '$http', function ($scope, $http) {

    var seriesList = [];
    var selectedSeries = '';

    $scope.text = "___";

    $scope.seriesList = seriesList;
    $scope.selectedSeries = selectedSeries;
    $scope.token = GetUserToken();

    var urlApplicationRoot = base_url;
    console.log("Attempting connection with " + urlApplicationRoot);
    $http({
        method: 'GET',
        url: urlApplicationRoot,
        headers: {'Authorization': $scope.token},
    }).then(function successCallback(response) {
        var functionality = response.data;
        console.log("Root found. value is " + JSON.stringify(functionality));
        for (var rel in functionality._links) {
            var relation = functionality._links[rel].rel;
            if (relation === REL_FONDS_STRUCTURE) {
                var urlListNoarkObjectParents = functionality._links[rel].href;
                $http({
                    method: 'GET',
                    url: urlListNoarkObjectParents,
                    headers: {'Authorization': $scope.token},
                }).then(function successCallback(response) {
                    var noarkObjectParents = response.data;
                    //console.log("noarkObjectParents found. value is " + JSON.stringify(noarkObjectParents));
                    for (var rel in noarkObjectParents._links) {
                        var relation = noarkObjectParents._links[rel].rel;
                        if (relation === REL_SERIES) {
                            var urlListSeries = noarkObjectParents._links[rel].href;
                            $http({
                                method: 'GET',
                                url: urlListSeries,
                                headers: {'Authorization': $scope.token},
                            }).then(function successCallback(response) {
                                // console.log("seriesList found. value is " + JSON.stringify(response));
                                var count = 1;
                                for (var i in response.data.results) {
                                    var series = response.data.results[i];

                                    console.log("pushing " + JSON.stringify(series.systemID));
                                    seriesList.push({id: count++, value: series.systemID, object: series});
                                    // Set the selected one to the last one
                                    // This will probably be set in the database somewhere
                                    selectedSeries = series.systemID;

                                    getCaseFilesAssociatedWithSeries(series);
                                }
                            }, function errorCallback(response) {
                                alert("Could not find series object to retrieve related files!" + JSON.stringify(response));
                            });
                        }
                    }
                }, function errorCallback(response) {
                    alert("Could not find series object to retrieve related files!" + JSON.stringify(response));
                });
            }
        }
    }, function errorCallback(response) {
        alert("Could not find application starting point!" + JSON.stringify(response));
    });

    function getCaseFilesAssociatedWithSeries(series) {
        console.log("Current series is" + JSON.stringify(series));
        if (!GetChosenSeries()) {
            SetChosenSeries(series);
        }
        for (var rel in series._links) {
            //console.log("Checking all REL found : " + series._links[rel].rel + " Looking for " + REL_CASE_FILE);
            var relation = series._links[rel].rel;
            if (relation === REL_CASE_FILE) {
                series._links[rel].href;
                $scope.linkToFindAllCaseFiles = series._links[rel].href;
                //console.log("Setting linkToFindAllCaseFiles to " + $scope.linkToFindAllCaseFiles);
                $http({
                    method: 'GET',
                    url: $scope.linkToFindAllCaseFiles,
                    headers: {'Authorization': $scope.token}
                }).then(function successCallback(response) {
                    $scope.casefiles = response.data.results;
                    // console.log("result after looking for all casefile is  " + JSON.stringify(response.data.results));
                }, function errorCallback(response) {
                    alert("Could not find series object to retrieve related files!" + JSON.stringify(response));
                });
            }
        }
    }

    $scope.fileSelectedShowDetails = function (casefile) {
        $scope.text = "singleClick will populate fields here";
    };

    $scope.fileSelectedShowDetailedView = function (casefile) {
        $scope.text = "doubleClick calls saksbehandler.html";
        SetCurrentCaseFile(casefile);

        for (var rel in casefile._links) {
            //console.log("Checking all REL found : " + series._links[rel].rel + " Looking for " + REL_CASE_FILE);
            var relation = casefile._links[rel].rel;
            if (relation === REL_SELF) {
                SetLinkToChosenCaseFile(casefile._links[rel].href);
            }
        }
        changeLocation($scope, caseFilePageName, false);
    };

    $scope.selectedSeriesChanged = function (selectedSeries) {
        // Find the series object related to the value that was selected

        for (var i in seriesList) {
            if (seriesList[i].value == selectedSeries) {
                console.log("selectedSeriesChanged " + seriesList[i].value);
                getCaseFilesAssociatedWithSeries(seriesList[i].object);
            }
        }
    };

}]);