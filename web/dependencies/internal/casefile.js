var app = angular.module('nikita-casefile', []);

var caseFileController = app.controller('CaseFileController', ['$scope', '$http', function ($scope, $http) {

    $scope.token = GetUserToken();

    $scope.casefile = GetCurrentCaseFile();
    /*
    $http({
        method: 'GET',
     url: GetLinkToChosenCaseFile(),
     headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        $scope.casefile = response.data;

     console.log("casefile data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });
     */
}]);
