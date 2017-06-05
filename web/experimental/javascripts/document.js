var app = angular.module('nikita-new-document', ['ngFileUpload']);

var GetUserToken = function (t) {
    return localStorage.getItem("token");
};

var SetLinkToCurrentDocumentDescription = function (t) {
    localStorage.setItem("linkToCurrentDocumentDescription", t);
    console.log("Setting linkToCurrentDocumentDescription" + t);
};

var SetLinkToCurrentDocumentObject = function (t) {
    localStorage.setItem("linkToCurrentDocumentObject", t);
    console.log("Setting linkToCurrentDocumentObject" + t);
};

var SetLinkToDocumentFile = function (t) {
    localStorage.setItem("linkToUploadDocumentFile", t);
    console.log("Setting linkToUploadDocumentFile" + t);
};

var GetLinkToDocumentFile = function () {
    return localStorage.getItem("linkToUploadDocumentFile");
};

// href to the current document description, used to retrieve data from nikita when page loads
var GetLinkToCurrentDocumentDescription = function () {
    return localStorage.getItem("linkToCurrentDocumentDescription");
};

// href to the current document object, used to retrieve data from nikita when page loads
var GetLinkToCurrentDocumentObject = function () {
    return localStorage.getItem("linkToCurrentDocumentObject");
};

// href of the link to use when creating a document description
var GetLinkToCreateDocumentDescription = function () {
    return localStorage.getItem("linkToChosenDocumentDescription");
};

// href of the link to use when creating a document object
var SetLinkToCreateDocumentObject = function (t) {
    localStorage.setItem("linkToChosenDocumentObject", t);
    console.log("Setting linkToChosenDocumentObject" + t);
};


var GetLinkToChosenRecord = function () {
    console.log("new_document getlinkToChosenRecord" + localStorage.getItem("linkToChosenRecord"));
    return localStorage.getItem("linkToChosenRecord");
};

app.controller('NewDocumentController', ['$scope', '$http', function ($scope, $http) {

    // Get todays date
    var currentTime = new Date();
    var month = currentTime.getMonth() + 1;
    var day = currentTime.getDate();
    var year = currentTime.getFullYear();

    var urlCurrentDocumentDescription = GetLinkToCurrentDocumentDescription();
    var urlCurrentDocumentObject = GetLinkToCurrentDocumentObject();


    $scope.token = GetUserToken();
    console.log("document.js: using auth token=" + $scope.token);
    var urlVal = GetLinkToChosenRecord();

    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token},
    }).then(function successCallback(response) {
        $scope.record = response.data;

    }, function errorCallback(response) {
        alert("Could not find record using link=" + urlVal + " " + response);
    });


    // check to see if link to documentObject exists, if it does, we are reloading real data
    if (urlCurrentDocumentObject) {

        $scope.uploadFile = true;
        $scope.newDocument = false;

        console.log("Retrieving values from nikita... " + urlCurrentDocumentObject);
        $http({
            method: 'GET',
            url: urlCurrentDocumentDescription,
            headers: {'Authorization': $scope.token},
        }).then(function successCallback(response) {
            var documentDescription = response.data;
            $scope.tittel = documentDescription.tittel;
            $scope.beskrivelse = documentDescription.beskrivelse;
            $scope.dokumentstatus = documentDescription.dokumentstatus;
            $scope.tilknyttetRegistreringSom = documentDescription.tilknyttetRegistreringSom;
            $scope.dokumenttype = documentDescription.dokumenttype;
            $scope.dokumentnummer = documentDescription.dokumentnummer;
            $scope.tilknyttetAv = documentDescription.tilknyttetAv;
            $scope.tilknyttetDato = documentDescription.tilknyttetDato;
        }, function errorCallback(response) {
            alert("Could not find documentDescription using link=" + urlCurrentDocumentDescription + " " + response);
        });

        $http({
            method: 'GET',
            url: urlCurrentDocumentObject,
            headers: {'Authorization': $scope.token},
        }).then(function successCallback(response) {
            var documentObject = response.data;
            $scope.sjekksum = documentObject.sjekksum;
            $scope.sjekksumAlgoritme = documentObject.sjekksumAlgoritme;
            // TODO: Find out why this is defined as a string, probably happening in js before POSTING
            $scope.filstoerrelse = Number(documentObject.filstoerrelse);
            $scope.mimeType = documentObject.mimeType;
            // make a note of the link to uplad a file if a user want to upload file
            for (rel in documentObject._links) {
                relation = documentObject._links[rel].rel;
                console.log("relation : " + relation);
                if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/fil/') {
                    SetLinkToDocumentFile(documentObject._links[rel].href);
                }
            }
        }, function errorCallback(response) {
            alert("Could not find documentObject using link=" + urlCurrentDocumentObject + " " + response);
        });
    }
    else {
        console.log("Setting default values ... ");
        $scope.uploadFile = false;
        $scope.newDocument = true;
        // if documentObject does not exist, we add data
        // Here we would get data from ny-dokumentobjekt
        // Set default values to aid debugging
        // dokumentbeskrivelse
        $scope.tittel = "Test title of the new document description";
        $scope.beskrivelse = "Test description of the new document description";
        $scope.dokumentstatus = "Dokumentet er under redigering";
        $scope.tilknyttetRegistreringSom = "Hoveddokument";
        $scope.dokumenttype = "Brev";
        $scope.dokumentnummer = 12;
        $scope.tilknyttetAv = "Frank Grimes";
        $scope.tilknyttetDato = "2019" + "-" + month + "-" + day + "T" + "12:00:00";
        //dokumentobjekt
        $scope.sjekksum = "efad2f48e831e5ae1528b6cfd13330c9eefdeb3dc53e172236c9ca9ec6c8a92c";
        $scope.sjekksumAlgoritme = "SHA-256";
        $scope.filstoerrelse = 21774;
        $scope.mimeType = "application/pdf";

    }

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

        var url = GetLinkToDocumentFile();
        var mimeType = $scope.mimeType;
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
            xhr.open("POST", url);
            var blob = new Blob([file], {type: mimeType});
            xhr.setRequestHeader('Authorization', GetUserToken());
            xhr.send(blob);
        }
    };


    $scope.downloadFile = function () {

        var url = GetLinkToDocumentFile();
        var mimeType = $scope.mimeType;

        $http({
            method: 'GET',
            cache: false,
            url: url,
            headers: {
                'Authorization': $scope.token,
                'Accept': mimeType,
            }
        }).success(function (data, status) {
            console.log("Success on retrievinf file to download. File link was " + url);
            return $scope.downloadFile = data;
        }).error(function (data, status) {
            alert("Could not start download of " + url)
        });
    };

    $scope.send_form = function () {
        token = GetUserToken();
        var urlCreateDocumentDescription = GetLinkToCreateDocumentDescription();
        var urlCreateDocumentObject = ""; // Value set after documentdescription is created
        // check that it's not null, create a popup here if it is
        $http({
            url: urlCreateDocumentDescription,
            method: "POST",
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': token,
            },
            data: {
                tittel: $scope.tittel,
                beskrivelse: $scope.beskrivelse,
                dokumentstatus: $scope.dokumentstatus,
                tilknyttetRegistreringSom: $scope.tilknyttetRegistreringSom,
                dokumenttype: $scope.dokumenttype,
                dokumentnummer: $scope.dokumentnummer,
                tilknyttetAv: $scope.tilknyttetAv,
                tilknyttetDato: $scope.tilknyttetDato,
            },
        }).then(function successCallback(response) {
            console.log("POST new documentdescription data returned=" + JSON.stringify(response.data));
            $scope.documentDescription = response.data;
            var documentDescription = response.data;
            console.log("POST new documentdescription data returned=" + JSON.stringify(response.data));
            for (rel in documentDescription._links) {
                relation = documentDescription._links[rel].rel;
                if (relation == 'self') {
                    href = documentDescription._links[rel].href;
                    SetLinkToCurrentDocumentDescription(href);
                }
                if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-dokumentobjekt/') {
                    href = documentDescription._links[rel].href;
                    SetLinkToCreateDocumentObject(href);
                    urlCreateDocumentObject = href;
                }
            }
            $http({
                url: urlCreateDocumentObject,
                method: "POST",
                headers: {
                    'Content-Type': 'application/vnd.noark5-v4+json',
                    'Authorization': token,
                },
                data: {
                    sjekksum: $scope.sjekksum,
                    sjekksumAlgoritme: $scope.sjekksumAlgoritme,
                    filstoerrelse: $scope.filstoerrelse,
                    mimeType: $scope.mimeType,
                },
            }).then(function successCallback(response) {
                console.log("POST to new documentobject returned=" + JSON.stringify(response.data));
                $scope.documentObject = response.data;
                var documentObject = response.data;
                for (rel in documentObject._links) {
                    relation = documentObject._links[rel].rel;
                    if (relation == 'self') {
                        href = documentObject._links[rel].href;
                        SetLinkToCurrentDocumentObject(href);
                    }
                }
                changeLocation($scope, "./dokument.html", true);
            }, function (data, status, headers, config) {
                alert("Could not POST document description " + data.data);
            });
        }, function (data, status, headers, config) {
            alert("Could not POST document object " + data.data);
        });
    };


}])
;

