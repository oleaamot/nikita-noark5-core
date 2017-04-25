let app = angular.module('nikita', []);

// TODO: use endpoint class
let base_url = "http://localhost:8092/noark5v4/";
let login_url = base_url + "/auth";
let sign_up_url = base_url + "signup";

// TODO: use class for token
var SetUserToken = function(t) {
  localStorage.setItem("token", t);
};

var GetUserToken = function(t) {
  return localStorage.getItem("token");
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

let updateIndexView = function(url, $scope, $http) {
    $scope.current = url;
    if (url.lastIndexOf("/") == (url.length - 1)) {
	parent =  "..";
    } else {
	parent =  ".";
    }
    $scope.parent =  resolveUrl(url, parent);
    token = GetUserToken();
    $http({
	method: 'GET',
	url: url,
	headers: {'Authorization': token },
    }).then(function successCallback(response) {
	$scope.links = response.data._links;
	$scope.data = response.data
	$scope.results = response.data.results
	delete $scope.data._links;
	delete $scope.data.results;
    }, function errorCallback(response) {
	// TODO: what should we do when it fails?
    });
};

let controller = app.controller('MainController', ['$scope', '$http', function ($scope, $http) {
  token = GetUserToken();
  console.log("token="+$scope.token);
  $scope.app_version = "xyz";
  $http({
    method: 'GET',
    url: '/version',
    headers: {'Authorization': token },
  }).then(function successCallback(response) {
    $scope.app_version = response.data;
  }, function errorCallback(response) {
    // TODO: what should we do when it fails?
  });

  updateIndexView(base_url, $scope, $http);

  $scope.hrefSelected = function(href){
      console.log('href link clicked ' + href);
      href = href.split("{")[0];
      updateIndexView(href, $scope, $http);
  }
}]);

let login = app.controller('LoginController', ['$scope', '$http', function($scope, $http) {
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
        changeLocation($scope, "./", true);
    }, function(data, status, headers, config) {

        console.log("hello" + status);
        alert(data.data);
    });
  };
}]);
