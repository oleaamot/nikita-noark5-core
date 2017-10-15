var app = angular.module('nikita-document', ['ngFileUpload']);

app.controller('DocumentController', ['$scope', '$http', function ($scope, $http) {

    // Make a breadcrumbs value appear
    $scope.printDocument = true;
    $scope.display_breadcrumb = display_breadcrumb;
    // Display journalpostnr and tittel for UX
    console.log("DocumentController - start! ");
    console.log("GET Current gregistry entry value is " + GetCurrentRegistryEntry());
    $scope.registryEntry = JSON.parse(GetCurrentRegistryEntry());

    // Needed for the breadcrumbs to display Sak(mappeID)
    $scope.caseFile = JSON.parse(GetChosenCaseFile());

    $scope.mimeTypeList = mimeTypeList;
    $scope.variantFormatList = variantFormatList;
    $scope.documentTypeList = documentTypeList;
    $scope.tilknyttetRegistreringSomList = tilknyttetRegistreringSomList;
    $scope.documentStatusList = documentStatusList;

    // NB!!!!! Add in the selected one for lists


    var documentDescription = GetChosenDocumentDescription();

    // check to see urlDocumentDescription exists, if it does, we are fetching real data
    if (documentDescription) {

        var urlDocumentDescription = GetLinkToDocumentDescription();

        console.log("Current urlDocumentDescription is" + JSON.stringify(urlDocumentDescription));
        $scope.createNewDocument = false;
        $scope.label_document = "Dokument";
        var token = GetUserToken();

        $http({
            method: 'GET',
            url: urlDocumentDescription,
            headers: {'Authorization': token}
        }).then(function successCallback(response) {
            var documentDescription = response.data;
            $scope.documentDescriptionETag = response.headers('eTag');
            $scope.documentDescription = response.data;
            for (var rel in documentDescription._links) {
                var relation = documentDescription._links[rel].rel;
                $scope.selectedDocumentType = documentDescription.dokumenttype;
                $scope.selectedTilknyttetRegistreringSom = documentDescription.tilknyttetRegistreringSom;
                $scope.selectedDocumentStatus = documentDescription.dokumentstatus;
                console.log("selectedDocumentStatus" + JSON.stringify($scope.selectedDocumentStatus));

                if (relation === REL_DOCUMENT_OBJECT) {
                    var urlDocumentObject = documentDescription._links[rel].href;
                    $http({
                        method: 'GET',
                        url: urlDocumentObject,
                        headers: {'Authorization': GetUserToken()},
                    }).then(function successCallback(response) {
                        for (var i = 0; i < response.data.results.length; i++) {
                            // TODO: Problem here is that it's picking up the last one
                            // Need to get this to work properly! Currently, there is only one
                            //console.log("Current document object object is" + JSON.stringify(response.data));
                            $scope.documentObject = response.data.results[i];
                            $scope.selectedMimeType = response.data.results[i].mimeType;
                            $scope.selectedVariantFormat = response.data.results[i].variantformat;
                            console.log("documentObject" + JSON.stringify($scope.documentObject));
                            var  documentObject = response.data.results[i];
                            $scope.documentObjectETag = response.headers('eTag');
                            // make a note of the link to upload a file if a user want to upload file
                            for (rel in documentObject._links) {
                                relation = documentObject._links[rel].rel;
                                if (relation == 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/fil/') {
                                    SetLinkToDocumentFile(documentObject._links[rel].href);
                                }
                            }
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
    else {
        $scope.createNewDocument = true;
        $scope.label_document = "Nytt dokument";
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
        $scope.f = file;

        var url = GetLinkToDocumentFile();
        var mimeType = $scope.selectedMimeType;
        $scope.errFile = errFiles && errFiles[0];
        if (file) {
            var xhr = new XMLHttpRequest();
            xhr.withCredentials = true;
            xhr.addEventListener("readystatechange", function () {
                if (this.readyState === 4) {
                    if (this.responseText.message) {
                        alert("Kunne ikke laste opp fil. Kjernen sier følgende: " + this.responseText.message);
                    }
                    else {
                        alert ("Kunne ikke laste opp fil.");
                    }
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
        var mimeType = $scope.selectedMimeType;

        $http({
            method: 'GET',
            cache: false,
            url: url,
            headers: {
                'Authorization': GetUserToken(),
                'Accept': mimeType,
            }
        }).success(function (data, status) {
            console.log("Success on retrieving file to download. File link was " + url);
            return $scope.downloadFile = data;
        }).error(function (data, status) {
            alert("Could not start download of " + url)
        });
    };

    $scope.send_form = function () {
        var urlDocumentDescription = '';
        var urlDocumentObject = ''; // Value set after documentdescription is created
        // check that it's not null, create a popup here if it is
        var method='';
        if($scope.createNewDocument) {
            method = "POST";
            // Check the current series for a link to create a new casefile
            for (var rel in $scope.registryEntry._links) {
                var relation = $scope.registryEntry._links[rel].rel;
                if (relation == REL_NEW_DOCUMENT_DESCRIPTION) {
                    urlDocumentDescription = $scope.registryEntry._links[rel].href;
                    console.log("URL for POST operation on registryEntry is " + urlDocumentDescription);
                }
            }
        } else {
            method = "PUT";

            for (var rel in $scope.documentDescription._links) {
                var relation = $scope.documentDescription._links[rel].rel;
                if (relation == REL_SELF) {
                    urlDocumentDescription = $scope.documentDescription._links[rel].href;
                    console.log("URL for PUT operation on registryEntry is " + urlDocumentDescription);
                }
            }
        }
        console.log("Attempting " + method + " on " + urlDocumentDescription);
        $http({
            url: urlDocumentDescription,
            method: method,
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': GetUserToken(),
                'ETAG': $scope.documentDescriptionETag
            },
            data: {
                tittel: $.trim(document.getElementById("document_description_title").value),
                beskrivelse: $.trim(document.getElementById("document_description_description").value),
                dokumentstatus: $.trim(document.getElementById("document_description_title").value),
                tilknyttetRegistreringSom: $.trim($scope.selectedTilknyttetRegistreringSom),
                dokumenttype: $.trim($scope.selectedDocumentType),
                dokumentnummer: Number($.trim(document.getElementById("document_number").value)),
                dokumentstatus: $.trim(document.getElementById("document_status").value),
                tilknyttetAv: $.trim(document.getElementById("document_associated_by").value),
                tilknyttetDato: $.trim(document.getElementById("document_associated_date").value)
            },
        }).then(function successCallback(response) {
            console.log(method + " documentdescription data returned=" + JSON.stringify(response.data));
            $scope.documentDescription = response.data;
            var documentDescription = response.data;
            for (rel in documentDescription._links) {
                relation = documentDescription._links[rel].rel;
                if (relation == 'self') {
                    href = documentDescription._links[rel].href;
                    SetLinkToDocumentDescription(href);
                }
                if (relation === REL_NEW_DOCUMENT_OBJECT) {
                    urlDocumentObject = documentDescription._links[rel].href;
                }
                if (relation === REL_DOCUMENT_OBJECT) {
                    urlDocumentObject = documentDescription._links[rel].href;
                }
            }
            console.log("Attempting " + method + " on " + urlDocumentObject);
            $http({
                url: urlDocumentObject,
                method: method,
                headers: {
                    'Content-Type': 'application/vnd.noark5-v4+json',
                    'Authorization': GetUserToken(),
                    'ETAG': $scope.documentDescriptionETag
                },
                data: {
                    //sjekksum: $.trim(document.getElementById("checksum").value),
                    versjonsnummer: Number($.trim(document.getElementById("version_number").value)),
                    variantformat: $scope.selectedVariantFormat,
                    //sjekksumAlgoritme: $.trim(document.getElementById("checksum_algorithm").value),
                    //filstoerrelse: Number($.trim(document.getElementById("file_size").value)),
                    mimeType: $.trim($scope.selectedMimeType)
                },
            }).then(function successCallback(response) {
                console.log(method + " documentobject returned=" + JSON.stringify(response.data));
                $scope.documentObject = response.data;
                var documentObject = response.data;
                for (rel in documentObject._links) {
                    relation = documentObject._links[rel].rel;
                    if (relation == 'self') {
                        href = documentObject._links[rel].href;
                        SetLinkToDocumentObject(href);
                    }
                }
                changeLocation($scope, "./dokument.html", false);
            }, function (data, status, headers, config) {
                alert("Could not " + method + " document object " + data.data);
            });
        }, function (data, status, headers, config) {
            alert("Could not " + method + "document description " + data.data);
        });
    };


    $scope.getTitleFromRegistryEntry = function () {
        $scope.documentDescription.tittel = $scope.registryEntry.tittel;
    };

    $scope.getDescriptionFromRegistryEntry = function () {
        $scope.documentDescription.beskrivelse = $scope.registryEntry.beskrivelse;
    }

}]);
