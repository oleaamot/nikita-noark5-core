var app = angular.module('nikita-casefile', []);

var caseFileController = app.controller('CaseFileController', ['$scope', '$http', function ($scope, $http) {

    $scope.display_breadcrumb = display_breadcrumb;
    $scope.caseFile = GetCurrentCaseFile();
    $scope.token = GetUserToken();

    $scope.showDetails = false;
    $scope.showDetailsText = "Flere detaljer";

    for (var rel in $scope.caseFile._links) {
        var relation = $scope.caseFile._links[rel].rel;
        console.log("relation is : " + relation + " looking for " + REL_REGISTRY_ENTRY);
        if (relation == REL_REGISTRY_ENTRY) {
            SetLinkToGetRegistryEntry($scope.caseFile._links[rel].href);
            var urlAllRegistryEntries = $scope.caseFile._links[rel].href;
            $http({
                method: 'GET',
                url: urlAllRegistryEntries,
                headers: {'Authorization': $scope.token},
            }).then(function successCallback(response) {
                $scope.caseFile.records = response.data.results;
                console.log("urlAllRegistryEntries: " + urlAllRegistryEntries +
                    " results " + JSON.stringify(response.data));
            }, function errorCallback(response) {
                alert(JSON.stringify(response));
            });
        }
    }

    $scope.registryEntrySelected = function (registryEntry) {
        SetCurrentRegistryEntry(registryEntry);
        changeLocation($scope, registryEntryPageName, true);
    };

    $scope.showDetailsChanged = function () {
        $scope.showDetails = !$scope.showDetails;
        if (!$scope.showDetails) {
            $scope.showDetailsText = "Flere detaljer"
        }
        else {
            $scope.showDetailsText = "Mindre detaljer"
        }
    };


    $scope.createNewRegistryEntryPressed = function () {
        SetCurrentRegistryEntry('');
        console.log("Current casefile is " + $scope.caseFile);
        for (var rel in $scope.caseFile._links) {
            var relation = $scope.caseFile._links[rel].rel;
            console.log("Current rel is " + relation);
            if (relation == REL_NEW_REGISTRY_ENTRY) {
                SetLinkToCreateRegistryEntry($scope.caseFile._links[rel].href);
            }
            // Do we need this here???
            if (relation == REL_REGISTRY_ENTRY) {
                SetLinkToGetRegistryEntry($scope.caseFile._links[rel].href);
            }
        }
        changeLocation($scope, registryEntryPageName, true);
    };
}]);
