let app = angular.module('nikita', []);

// TODO: use endpoint class
//let base_url = "http://n5test.kxml.no/api";
let base_url = "http://localhost:8092/noark5v4";

if (nikitaOptions.enabled) {
    console.log("nikita baseURL" + nikitaOptions.baseUrl);
    base_url = nikitaOptions.protocol + "://" + nikitaOptions.baseUrl  + "/" + nikitaOptions.appName;
}

let login_url = base_url + "/auth";

// TODO: use class for token
var SetUserToken = function(t) {
  localStorage.setItem("token", t);
  console.log("Adding token " + t + " to local storage");

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
	$scope.allow = response.headers('Allow');
	$scope.links = response.data._links;
	$scope.data = response.data;
	$scope.results = response.data.results
	delete $scope.data._links;
	delete $scope.data.results;
    }, function errorCallback(response) {
	// TODO: what should we do when it fails?
	$scope.allow = '[unknown - GET failed]';
	$scope.links = '';
	$scope.data = '';
	$scope.results = '';
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
        console.log("Loggin in. Setting token to " + data.data.token);
        changeLocation($scope, "./", true);
    }, function(data, status, headers, config) {

        console.log("hello" + status);
        alert(data.data);
    });
  };
}]);

let postliste = app.controller('PostlisteController', ['$scope', '$http', function ($scope, $http) {
    // FIXME find link dynamically
    url = base_url + "/hateoas-api/arkivstruktur/arkiv";
    token = GetUserToken();
    $http({
	method: 'GET',
	url: url,
	headers: {'Authorization': token },
    }).then(function successCallback(response) {
	$scope.status = 'success';
	$scope.fonds = response.data.results;
    }, function errorCallback(response) {
	$scope.status = 'failure';
	$scope.fonds = '';
    });
    $scope.series = '';
    $scope.casefiles = '';

    $scope.fondsUpdate = function(fonds){
	console.log('fonds selected ' + fonds.tittel);
	$scope.casefiles = '';
	for (rel in fonds._links) {
	    href = fonds._links[rel].href;
	    relation = fonds._links[rel].rel;
	    if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkivdel/'){
		console.log("fetching " + href);
		$http({
		    method: 'GET',
		    url: href,
		    headers: {'Authorization': GetUserToken() },
		}).then(function successCallback(response) {
		    $scope.series = response.data.results;
		}, function errorCallback(response) {
		    $scope.series = '';
		});
	    }
	}
    }
    $scope.seriesUpdate = function(series){
	console.log('series selected ' + series.tittel);
	if (!series) {
	    return
	}
	for (rel in series._links) {
	    href = series._links[rel].href;
	    relation = series._links[rel].rel;
	    // FIXME use http://rel.kxml.no/noark5/v4/api/sakarkiv/saksmappe/ when it work
	    if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/mappe/'){
		console.log("fetching " + href);
		$http({
		    method: 'GET',
		    url: href,
		    headers: {'Authorization': GetUserToken() },
		}).then(function successCallback(response) {
		    $scope.casefiles = response.data.results;
		}, function errorCallback(response) {
		    $scope.casefiles = '';
		});
	    }
	}
    }
    $scope.fileSelected = function(file){
	console.log('file selected ' + file.tittel);
	if (file.records) {
            file.records = '';
            return;
	}
	for (rel in file._links) {
	    relation = file._links[rel].rel;
	    // FIXME use http://rel.kxml.no/noark5/v4/api/sakarkiv/journalpost/ when it work
	    if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/registrering/'){
		href = file._links[rel].href;
		console.log("fetching " + href);
		$http({
		    method: 'GET',
		    url: href,
		    headers: {'Authorization': GetUserToken() },
		}).then(function successCallback(response) {
		    for (record in response.data.results) {
			response.data.results.forEach(function(record) {
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
					headers: {'Authorization': GetUserToken() },
				    }).then(docdesc => {
					record.dokumentbeskrivelse =
					    docdesc.data.results[0];
				    }, response2 => {
					record.dokumentbeskrivelse = '';
				    });
				}
			    }
			});
		    }
		    file.records = response.data.results;
		}, function errorCallback(response) {
		    file.records = '';
		});
	    }
	}
    }
}]);
