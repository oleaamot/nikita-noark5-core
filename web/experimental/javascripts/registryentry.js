var app = angular.module('nikita-registry-entry', ['ngFileUpload']);

let base_url = "http://localhost:8092/noark5v4";
let app_url = "http://localhost:8092/noark5v4/hateoas-api";
let gui_base_url = "http://localhost:3000/experimental";

if (nikitaOptions.enabled) {
    console.log("nikita baseURL" + nikitaOptions.baseUrl);
    base_url = nikitaOptions.protocol + "://" + nikitaOptions.baseUrl + "/" + nikitaOptions.appName;
    app_url = base_url + "/" + nikitaOptions.apiName;
    gui_base_url = nikitaOptions.guiBaseUrl;
}
var GetLinkToChosenRecord = function () {
    return localStorage.getItem("linkToChosenRecord");
};

var SetLinkToCurrentDocumentDescription = function (t) {
    localStorage.setItem("linkToCurrentDocumentDescription", t);
    console.log("Setting linkToCurrentDocumentDescription" + t);
};

// href of the link to use when creating a document object
var SetLinkToCurrentDocumentObject = function (t) {
    localStorage.setItem("linkToCurrentDocumentObject", t);
    console.log("Setting linkToCurrentDocumentObject" + t);
};

// href of the link to use when creating a document object
var SetLinkToCreateDocumentDescription = function (t) {
    localStorage.setItem("linkToChosenDocumentDescription", t);
    console.log("Setting linkToChosenDocumentDescription" + t);
};


var SetCurrentDocumentDescriptionSystemId = function (recordSystemId) {
    localStorage.setItem("currentDocumentDescriptionSystemId", recordSystemId);
    console.log("Setting currentDocumentDescriptionSystemId=" + recordSystemId);
};


var GetUserToken = function () {
    return localStorage.getItem("token");
};


app.controller('RegistryEntryController', ['$scope', '$http', function ($scope, $http) {

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


    $scope.documentSelected = function (documentDescription) {
        console.log("registry entry redirect to " + gui_base_url + "/dokument.html");
        window.location = gui_base_url + "/dokument.html";
    };

    $scope.newDocumentSelected = function (record) {

        // setting these to ''so that when we hit the page
        // any previous values will be ignored
        SetLinkToCurrentDocumentObject('');
        SetLinkToCurrentDocumentDescription('');

        for (rel in record._links) {
            relation = record._links[rel].rel;
            if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-dokumentbeskrivelse/') {
                href = record._links[rel].href;
                SetLinkToCreateDocumentDescription(href);
                window.location = gui_base_url + "/dokument.html";
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
