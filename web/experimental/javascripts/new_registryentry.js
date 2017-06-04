var app = angular.module('nikita-new-registry-entry', ['ngFileUpload']);


var base_url = "http://localhost:8092/noark5v4";
var app_url = "http://localhost:8092/noark5v4/hateoas-api";
var gui_base_url = "http://localhost:3000/experimental";


var GetLinkToChosenCaseFile = function (t) {
    return localStorage.getItem("linkToChosenRecord");
};

var SetLinkToChosenCreatedRecord = function (t) {
    localStorage.setItem("linkToCreatedRegistryEntry", t);
    console.log("Setting linkToCreatedRegistryEntry" + t);
};

var GetUserToken = function (t) {
    return localStorage.getItem("token");
};


app.controller('NewRegistryEntryController', ['$scope', '$http', function ($scope, $http) {


    $scope.rootFolders = 'bob';

    $scope.token = GetUserToken();
    console.log("token=" + $scope.token);
    var urlVal = GetLinkToChosenRecord();
    var record = '';

    if (record.documentDescription) {
        record.documentDescription = '';
        return;
    }
    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token},
    }).then(function successCallback(response) {
        $scope.record = response.data;
        console.log("record data is : " + JSON.stringify(response.data));
        for (rel in $scope.record._links) {
            relation = $scope.record._links[rel].rel;
            if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/dokumentbeskrivelse/') {
                documentDescriptionHref = $scope.record._links[rel].href;
                console.log("fetching " + documentDescriptionHref);
                $http({
                    method: 'GET',
                    url: documentDescriptionHref,
                    headers: {'Authorization': GetUserToken()},
                }).then(function successCallback(response) {
                    $scope.record.documentDescription = response.data.results;

                    response.data.results.forEach(function (documentdescription) {
                        for (rel in documentdescription._links) {
                            relation = documentdescription._links[rel].rel;
                            if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/dokumentobjekt/') {
                                documentObjectHref = documentdescription._links[rel].href;
                                //console.log("fetching docdesc docobjects with " + documentDescriptionHref);
                                $http({
                                    method: 'GET',
                                    url: documentObjectHref,
                                    headers: {'Authorization': GetUserToken()},
                                }).then(function successCallback(response) {
                                    // This foreach needs to be replaced with a mechanism to handle multiple
                                    // documentobjects on documentDescription. Currently it will just pick up the
                                    // last one
                                    response.data.results.forEach(function (documentobject) {
                                        $scope.record.documentObject = documentobject;
                                        console.log("Record object in GUI is " + JSON.stringify($scope.record));
                                    }, function errorCallback(response) {
                                        console.log("Could not retrieve doc object " + response);
                                    });
                                    // You need to handle fails here ... somewhere ...
                                });
                            }
                        }
                    });
                    console.log("Setting record.records " + urlVal);
                    $scope.record.records = response.data.results;
                }, function errorCallback(response) {
                    $scope.record.records = '';
                });
            }
        }
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });


    $scope.recordSelected = function (record) {
        console.log('record selected link clicked ' + JSON.stringify(record));
        for (rel in record._links) {
            relation = record._links[rel].rel;
            if (relation == 'self') {
                href = record._links[rel].href;
                SetCurrentDocumentDescriptionSystemId(record.systemID);
                SetLinkToChosenDocumentDescription(href);
                window.location = gui_base_url + "/journalpost.html";
            }
        }
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


    $scope.uploadFiles = function (file, errFiles) {
        console.log("uploadFiles: Hello");
        $scope.f = file;
        console.log("uploadFiles: file is " + file);
        $scope.errFile = errFiles && errFiles[0];
        if (file) {
            console.log("uploadFiles: int file ");

            var xhr = new XMLHttpRequest();
            xhr.withCredentials = true;

            xhr.addEventListener("readystatechange", function () {
                if (this.readyState === 4) {
                    console.log(this.responseText);
                }
            });
            xhr.open("POST", "http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentobjekt/495a3409-24c4-4677-9ff4-5ebcb03e0bb5/referanseFil/");
            var blob = new Blob([file], {type: 'application/pdf'});
            xhr.setRequestHeader('Authorization', GetUserToken());
            xhr.send(blob);

        }
    }
}]);
