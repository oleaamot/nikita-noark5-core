let app = angular.module('nikita', []);

let controller = app.controller('MainController', ['$scope', '$http', function ($scope, $http) {
  $scope.app_version = "xyz";
  $http({
    method: 'GET',
    url: '/version'
  }).then(function successCallback(response) {
    $scope.app_version = response.data;
  }, function errorCallback(response) {
  });
}]);
