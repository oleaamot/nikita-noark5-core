let app = angular.module('nikita', []);

// TODO: use endpoint class
let base_url = "http://localhost:8092/noark5v4/";
let login_url = base_url + "/auth";
let sign_up_url = base_url + "signup";

// TODO: use class for token
var SetUserToken = function(t) {
  localStorage.setItem("token", t);
};


var changeLocation = function ($scope, url, forceReload) {
    $scope = $scope || angular.element(document).scope();
    console.log("URL" + url);
    if (forceReload || $scope.$$phase) {
        window.location = url;
    }
    else {
        //only use this if you want to replace the history stack
        //$location.path(url).replace();

        //this this if you want to change the URL and add it to the history stack
        $location.path(url);
        $scope.$apply();
    }
};

var GetUserToken = function(t) {
  return localStorage.getItem("token");
};

let controller = app.controller('MainController', ['$scope', '$http', function ($scope, $http) {
  $scope.token = GetUserToken();
  console.log("token="+$scope.token);
  $scope.app_version = "xyz";
  $http({
    method: 'GET',
    url: '/version',
    headers: {'Authorization': $scope.token },
  }).then(function successCallback(response) {
    $scope.app_version = response.data;
  }, function errorCallback(response) {
    // TODO: what should we do when it fails?
  });

  $http({
    method: 'GET',
    url: base_url,
    headers: {'Authorization': $scope.token },
  }).then(function successCallback(response) {
    $scope.links = response.data._links;
  }, function errorCallback(response) {
    // TODO: what should we do when it fails?
  });

  $scope.hrefSelected = function(href){
      console.log('href link clicked ' + href);
      token = GetUserToken();
      // FIXME figure out why this call uses unimplemented OPTIONS
      // instead of GET.
      $http({
	  method: 'GET',
	  url: href,
	  headers: {'Authorization': token },
      }).then(function successCallback(response) {
	  $scope.links = response.data._links;
      }, function errorCallback(response) {
	  // TODO: what should we do when it fails?
      });
  }
}]);

let login = app.controller('LoginController', ['$scope', '$http', function($scope, $http) {
  $scope.token = GetUserToken();
  console.log("token="+$scope.token);
  console.log("LoginController");
  $scope.send_form = function() {
    console.log($scope.password);
    console.log($scope.username);
    $http({
      url: login_url,
      method: "POST",
      headers: {'Content-Type': 'application/json'},
      data: { username: $scope.username, password: $scope.password },
    }).then(function(data, status, headers, config) {
	SetUserToken(data.data.token);
        console.log("hello" + status);
        changeLocation($scope, base_url, true);
    }, function(data, status, headers, config) {

        console.log("hello" + status);
        alert(data.data);
    });
  };
}]);
