let app = angular.module('nikita', []);

let base_url = "http://localhost:8092/noark5v4/";
let app_url = "http://localhost:8092/noark5v4/hateoas-api/";
let login_url = base_url + "/auth";
let sign_up_url = base_url + "signup";
let gui_base_url = "http://localhost:3000/experimental";

if (nikitaOptions.enabled) {
    base_url = nikitaOptions.protocol + "://" + nikitaOptions.baseUrl  + "/" + nikitaOptions.appName + "/";
    login_url = base_url + "/" + nikitaOptions.authPoint;
    app_url = base_url + "/" + nikitaOptions.apiName;
}


// TODO: use class for token
var SetUserToken = function(t) {
  localStorage.setItem("token", t);
  console.log("Adding token " + t + " to local storage");

};

var GetUserToken = function(t) {
  return localStorage.getItem("token");
};

var GetFileSystemID = function (t) {
    return localStorage.getItem("current_case_file");
};


var changeLocation = function ($scope, url, forceReload) {
    $scope = $scope || angular.element(document).scope();
    console.log("Changing location to URL" + url);
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

    // Figure out allowed operations
        $http({
        	method: 'OPTIONS',
    	url: url,
        }).then(function successCallback(response) {
    	$scope.allow = response.headers['Allow'];
            console.log("Allowable methods "+ response.headers['Allow']);
            console.log("Allowable methods "+ response);
        }, function errorCallback(response) {
    	$scope.allow = 'UNKNOWN';
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
    $scope = $scope || angular.element(document).scope();
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
        console.log("Logging in. Setting token to " + data.data.token);
        changeLocation($scope, "./arkiv.html", true);
    }, function(data, status, headers, config) {

        console.log("hello" + status);
        alert(data.data);
    });
  };
}]);


let postliste = app.controller('CaseFileRegistryEntryController', ['$scope', '$http', function ($scope, $http) {
    // FIXME find href for rel
    // 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkiv/'
    // dynamically
    url = base_url + "/hateoas-api/arkivstruktur/arkiv";
    token = GetUserToken();
    file = GetFileSystemID();
    $http({
        method: 'GET',
        url: url,
        headers: {'Authorization': token},
    }).then(function successCallback(response) {
        $scope.status = 'success';
        $scope.fonds = response.data.results;
    }, function errorCallback(response) {
        $scope.status = 'failure';
        $scope.fonds = '';
    });
    $scope.series = '';
    $scope.casefiles = '';


    $scope.fileSelected = function (file) {
        console.log('file selected ' + file.tittel);
        if (file.records) {
            file.records = '';
            return;
        }
        for (rel in file._links) {
            relation = file._links[rel].rel;
            if (relation == 'http://rel.kxml.no/noark5/v4/api/sakarkiv/journalpost/') {
                href = file._links[rel].href;
                console.log("fetching " + href);
                $http({
                    method: 'GET',
                    url: href,
                    headers: {'Authorization': GetUserToken()},
                }).then(function successCallback(response) {
                    response.data.results.forEach(function (record) {
                        console.log("record " + record);
                        for (rel in record._links) {
                            relation = record._links[rel].rel;
                            console.log("found " + relation);
                            if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/dokumentbeskrivelse/') {
                                href = record._links[rel].href;
                                console.log("fetching " + href);
                                $http({
                                    method: 'GET',
                                    url: href,
                                    headers: {'Authorization': GetUserToken()},
                                }).then(function successCallback(docdesc) {
                                    record.dokumentbeskrivelse =
                                        docdesc.data.results[0];
                                }, function errorCallback(docdesc) {
                                    record.dokumentbeskrivelse = '';
                                });
                            }
                        }
                    });
                    file.records = response.data.results;
                }, function errorCallback(response) {
                    file.records = '';
                });
            }
        }
    }
}]);
