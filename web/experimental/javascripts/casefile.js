let app = angular.module('nikita-casefile', []);

let base_url = "http://localhost:8092/noark5v4";
let app_url = "http://localhost:8092/noark5v4/hateoas-api";
let gui_base_url = "http://localhost:3000/experimental";

if (nikitaOptions.enabled) {
    console.log("nikita baseURL" + nikitaOptions.baseUrl);
    base_url = nikitaOptions.protocol + "://" + nikitaOptions.baseUrl + "/" + nikitaOptions.appName;
    app_url = base_url + "/" + nikitaOptions.apiName;
    gui_base_url = nikitaOptions.guiBaseUrl;
}

var GetLinkToChosenFile = function (t) {
    return localStorage.getItem("linkToChosenFile");
};

var SetLinkToChosenFile = function (t) {
    localStorage.setItem("linkToChosenFile", t);
    console.log("Setting linkToChosenFile=" + t);
};

var SetCurrentFileSystemId = function (fileSystemId) {
    localStorage.setItem("currentFileSystemId", fileSystemId);
    console.log("Setting currentFileSystemId=" + fileSystemId);
};

var GetSeriesSystemID = function (t) {
    console.log("Getting chosen currentSeriesSystemId=" + localStorage.getItem("currentSeriesSystemId"));
    return localStorage.getItem("currentSeriesSystemId");
};

var GetUserToken = function (t) {
    return localStorage.getItem("token");
};


let caseFileController = app.controller('CaseFileController', ['$scope', '$http', function ($scope, $http) {
    $scope.token = GetUserToken();
    console.log("token=" + $scope.token);
    var urlVal = GetLinkToChosenFile();
    var casefile = '';
    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token},
    }).then(function successCallback(response) {
        $scope.casefile = response.data;
        casefile = response.data;
        console.log("case file data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    console.log('casefileController starting selected ' + casefile.tittel);
    if (casefile.records) {
        casefile.records = '';
        return;
    }
    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token},
    }).then(function successCallback(response) {
        $scope.casefile = response.data;

        console.log("casefile data is : " + JSON.stringify(response.data));
        for (rel in $scope.casefile._links) {
            relation = $scope.casefile._links[rel].rel;
            if (relation == 'http://rel.kxml.no/noark5/v4/api/sakarkiv/journalpost/') {
                href = $scope.casefile._links[rel].href;
                console.log("fetching " + href);
                $http({
                    method: 'GET',
                    url: href,
                    headers: {'Authorization': GetUserToken()},
                }).then(function successCallback(response) {
                    response.data.results.forEach(function (record) {
                        console.log("record " + JSON.stringify(record));
                        for (rel in record._links) {
                            relation = record._links[rel].rel;
                            //console.log("found " + relation);
                        }
                    });
                    console.log("Setting casefile.records " + href);
                    $scope.casefile.records = response.data.results;
                }, function errorCallback(response) {
                    $scope.casefile.records = '';
                });
            }
        }


    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    $scope.send_form = function () {
        token = GetUserToken();
        systemID = GetSeriesSystemID();
        url = app_url + '/arkivstruktur/arkivdel/' + systemID + '/ny-mappe';
        $http({
            url: url,
            method: "POST",
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': token,
            },
            data: {
                tittel: $scope.tittel,
                beskrivelse: $scope.beskrivelse,
                mappeID: $scope.mappeID,
            },
        }).then(function (data, status, headers, config) {
            changeLocation($scope, "./mappe.html", true);
        }, function (data, status, headers, config) {
            alert(data.data);
        });
    };
    console.log("Retrieving data from URL " + urlVal);

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

}]);
