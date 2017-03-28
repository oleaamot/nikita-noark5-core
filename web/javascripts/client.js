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
    // TODO: what should we do when it fails?
  });

  $http({
    method: 'GET',
    url: base_url
  }).then(function successCallback(response) {
    $scope.links = response.data._links;
  }, function errorCallback(response) {
    // TODO: what should we do when it fails?
  });
}]);

let login = app.controller('LoginController', ['$scope', '$http', function($scope, $http) {
  console.log("LoginController");
  $scope.send_form = function() {
    console.log($scope.password);
    console.log($scope.username);
    $http({
      url: login_url,
      method: "POST",
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: {username: $scope.username, password: $scope.password },
    }).then(function(data, status, headers, config) {
      console.log("success");
      console.log(data);
      $scope.status = status;
    }, function(data, status, headers, config) {
      console.log(headers);
      console.log(status);
      console.log(config);
      console.log(data);
    });
  };
}]);
