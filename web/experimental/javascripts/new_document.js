var app = angular.module('nikita-new-registry-entry', ['ngFileUpload']);

var base_url = "http://localhost:8092/noark5v4";
var app_url = "http://localhost:8092/noark5v4/hateoas-api";


var gui_base_url = "http://localhost:3000/experimental";

var GetLinkToChosenRecord = function (t) {
    return localStorage.getItem("linkToChosenRecord");
};

var GetUserToken = function (t) {
    return localStorage.getItem("token");
};

var SetLinkToChosenDocumentDescription = function (t) {
    localStorage.setItem("linkToChosenDocumentDescription", t);
    console.log("Setting linkToChosenDocumentDescription" + t);
};
app.controller('NewDocumentDescriptionController', ['$scope', '$http', function ($scope, $http) {

    // Get todays date
    var currentTime = new Date();
    var month = currentTime.getMonth() + 1;
    var day = currentTime.getDate();
    var year = currentTime.getFullYear();


    // Set default values to aid debugging
    $scope.tittel = "Test title of the new journalpost";
    $scope.beskrivelse = "Test description of the new journalpost";
    $scope.mappeID = "2017/01";
    $scope.journalposttype = "Inngående dokument";
    $scope.journalaar = 2017;
    $scope.journalpostnummer = 201701;
    $scope.journalsekvensnummer = 201701011;
    $scope.dokumentetsDato = month + "-" + day + "-" + year;
    $scope.journaldato = month + "-" + day + "-" + year;


    $scope.token = GetUserToken();
    console.log("token=" + $scope.token);
    var urlVal = GetLinkToChosenFile();

    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token},
    }).then(function successCallback(response) {
        $scope.casefile = response.data;
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

    var linkToCreateRegistryEntry = function () {
        if (!$scope.casefile) {
            alert("CaseFile data is undefined");
        }
        console.log("casefile object is " + JSON.stringify($scope.casefile));
        for (rel in $scope.casefile._links) {
            relation = $scope.casefile._links[rel].rel;
            if (relation == 'http://nikita.arkivlab.no/noark5/v4/ny-journalpost/') {
                href = $scope.casefile._links[rel].href;
                return href;
            }
        }
        return null;
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
    };

    $scope.send_form = function () {
        token = GetUserToken();
        url = linkToCreateRegistryEntry();
        // check that it's not null, create a popup here if it is
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
                journalpoststatus: $scope.journalpoststatus,
                journalposttype: $scope.journalposttype,
                journalaar: $scope.journalaar,
                journalpostnummer: $scope.journalpostnummer,
                journalsekvensnummer: $scope.journalsekvensnummer,
                dokumentetsDato: $scope.dokumentetsDato,
                journaldato: $scope.journaldato,
            },
        }).then(function successCallback(response) {
            console.log("POST new journalpost data returned=" + JSON.stringify(response.data));
            /*console.log("POST new journalpost STATUS returned=" + status);
             console.log("POST new journalpost header returned=" + headers);
             console.log("POST new journalpost header returned=" + config);
             */
            SetLinkToChosenRecord(response);
            changeLocation($scope, "./journalpost.html", true);
        }, function (data, status, headers, config) {
            alert(data.data);
        });
    };


}]);

