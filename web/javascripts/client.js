let app = angular.module('nikita', []);

// TODO: use endpoint class
let base_url = "http://127.0.0.1:8092/noark5v4/";
let login_url = base_url + "doLogin";
let sign_up_url = base_url + "signup";


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
