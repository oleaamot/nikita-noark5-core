var app = angular.module('nikita-document', ['ngFileUpload']);


app.controller('DocumentController', ['$scope', '$http', function ($scope, $http) {

    var urlDocumentDescription = GetLinkToDocumentDescription();
    var urlVal = GetLinkToChosenRecord();


    $scope.mimeTypeList = mimeTypeList;



    // Fetch the record to display journlpostnr and tittel
    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': GetUserToken()}
    }).then(function successCallback(response) {
        $scope.registryEntry = response.data;
    }, function errorCallback(response) {
        alert("Could not find registryEntry using link=" + urlVal + " " + response);
    });

    // check to see urlDocumentDescription exists, if it does, we are fetching real data
    if (urlDocumentDescription) {
        console.log("Curent urlDocumentDescription is" + JSON.stringify(urlDocumentDescription));

        $scope.createNewDocument = false;
        var token = GetUserToken();

        $http({
            method: 'GET',
            url: urlDocumentDescription,
            headers: {'Authorization': token}
        }).then(function successCallback(response) {
            var documentDescription = response.data;
       //     console.log("XXXXXXXXXX Curent document description object is" + JSON.stringify(response.data));
            $scope.documentDescription = response.data;
            for (var rel in documentDescription._links) {
                var relation = documentDescription._links[rel].rel;
                if (relation === REL_DOCUMENT_OBJECT) {
                    var urlDocumentObject = documentDescription._links[rel].href;
                    $http({
                        method: 'GET',
                        url: urlDocumentObject,
                        headers: {'Authorization': GetUserToken()},
                    }).then(function successCallback(response) {
     //                   console.log("Curent document object object is" + JSON.stringify(response.data));
                        $scope.documentObjects = response.data.results;
                        for (i = 0; i < response.data.results.length; i++) {
                            console.log(JSON.stringify(response.data.results[i]));
                            //$scope.selectedMimeType[i] = response.data.results[i].mimeType;
                           // text += cars[i] + "<br>";
                        }
                    }, function errorCallback(response) {
                        alert("Could not find documentObject using link=" + urlDocumentObject + " " + response);
                    });
                }
            }
        }, function errorCallback(response) {
            alert("Could not find documentDescription using link=" + urlDocumentDescription + " " + response);
        });
    }

    // These will be picked up from the database


        /*


         }

         else {
         $scope.createNewDocument = true;
         // Get todays date
         var currentTime = new Date();
         var month = currentTime.getMonth() + 1;
         var day = currentTime.getDate();
         var year = currentTime.getFullYear();

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
         $scope.tilknyttetDato = year + "-" + month + "-" + day + "T" + "12:00:00";
         //dokumentobjekt
         $scope.sjekksum = "efad2f48e831e5ae1528b6cfd13330c9eefdeb3dc53e172236c9ca9ec6c8a92c";
         $scope.sjekksumAlgoritme = "SHA-256";
         $scope.filstoerrelse = 21774;
         $scope.mimeType = "application/pdf";

         }
         */

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
                'Authorization': GetUserToken(),
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
        var urlCreateDocumentDescription = GetLinkToCreateDocumentDescription();
        var urlCreateDocumentObject = ""; // Value set after documentdescription is created
        // check that it's not null, create a popup here if it is
        $http({
            url: urlCreateDocumentDescription,
            method: "POST",
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': GetUserToken(),
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
                if (relation === REL_NEW_DOCUMENT_OBJECT) {
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
                    'Authorization': GetUserToken(),
                },
                data: {
                    sjekksum: $scope.sjekksum,
                    sjekksumAlgoritme: $scope.documentDescription.sjekksumAlgoritme,
                    filstoerrelse: $scope.documentDescription.filstoerrelse,
                    mimeType: $scope.documentDescription.mimeType,
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


}]);

