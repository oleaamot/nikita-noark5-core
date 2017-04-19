
let app = angular.module('nikita-fonds', []);
let fondsController = app.controller('FondsController', ['$scope', '$http', function ($scope, $http) {
    $scope.token = GetUserToken();
    console.log("token="+$scope.token);
    $scope.fonds = "xyz";
    $http({
        method: 'GET',
        url: 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv',
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        $scope.fonds = response.data.arkiv;
        console.log("data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });
}]);

var GetUserToken = function(t) {
    return localStorage.getItem("token");
};
