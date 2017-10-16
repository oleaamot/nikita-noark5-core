var app = angular.module('nikita', ['nikita-shared']);

/**
 * Controller for registryEntry/ journalpost
 *
 * When called, the script will check to see if the
 * Note his file depends on definitions in config.js These are loaded first in the html file.
 *     <script src="config.js"></script>
 *
 */
app.controller('RegistryEntryController', ['$scope', '$http', 'breadcrumbService', function ($scope, $http, breadcrumbService) {

    $scope.display_breadcrumb = display_breadcrumb;
    // Get the chosen caseFile to display the saksnr and tittel
    $scope.token = GetUserToken();
    $scope.caseFile = JSON.parse(GetChosenCaseFile());
    $scope.registryEntryTypeList = registryEntryTypeList;
    $scope.registryEntryStatusList = registryEntryStatusList;

    // Get the registryEntry to display
    var registryEntry = JSON.parse(GetCurrentRegistryEntry());
    $scope.registryEntry = JSON.parse(GetCurrentRegistryEntry());
    // No chosen registryEntry, means we are creating a new registryEntry
    if (!registryEntry) {
        $scope.createNewRegistryEntry = true;
        $scope.formfields = [
            'tittel', 'beskrivelse', 'journalposttype', 'journalpoststatus',
            'journalaar', 'journalpostnummer', 'journalsekvensnummer',
            'dokumentetsDato', 'journaldato',
        ];
        console.log("token is : " + $scope.token);

        var urlToCreateRegistryEntry = GetLinkToCreateRegistryEntry();
        console.log("urlToCreateRegistryEntry is : " + urlToCreateRegistryEntry);
        if (urlToCreateRegistryEntry) {

            $http({
                method: 'GET',
                url: urlToCreateRegistryEntry,
                headers: {
                    'Accept': 'application/vnd.noark5-v4+json',
                    'Authorization': GetUserToken()
                }
            }).then(function successCallback(response) {
                console.log("ny-journalpost data is : " + JSON.stringify(response.data));
                for (var key of Object.keys(response.data)) {
                    if ("_links" === key) {
                    } else {
                        $scope[key] = response.data[key];
                        $scope.formfields.push(key);
                    }
                }

                console.log("Status : " + JSON.stringify(response.status));
                console.log("Config : " + JSON.stringify(response.config));
            }, function errorCallback(response) {
                console.log("Status : " + JSON.stringify(response.status));
                console.log("Config : " + JSON.stringify(response.config));
            });
        }
        else {
            alert("Internal error. No url available to call GET:ny-journalpost");
        }
    }
    else {
        $scope.createNewRegistryEntry = false;

        var urlCurrentRegistryEntry = '';
        var registryEntry = JSON.parse(GetCurrentRegistryEntry());
        /**
         * Getting the latest copy of the registryEntry and any documents attached to it.
         *  Can be necessary from a UX perspective, if someone changes something or adds
         *   e.g a new document to the registryEntry
         */
        for (var rel in registryEntry._links) {
            var relation = registryEntry._links[rel].rel;
            if (relation === REL_SELF) {
                urlCurrentRegistryEntry = registryEntry._links[rel].href;
            }
        }

        console.log(" registryEntry GET " + urlCurrentRegistryEntry);

        // Get the registryEntry
        $http({
            method: 'GET',
            url: urlCurrentRegistryEntry,
            headers: {'Authorization': $scope.token}
        }).then(function successCallback(response) {
            // registryEntry is in $scope.registryEntry
            $scope.registryEntry = response.data;
            // update the current registryEntry object
            SetCurrentRegistryEntry($scope.registryEntry);
            console.log(" $scope.registryEntry  is " + JSON.stringify($scope.registryEntry));
            $scope.documentDescriptionETag = response.headers('eTag');
            // Now go through each rel and find the one linking to documentDescription
            for (var rel in $scope.registryEntry._links) {
                var relation = $scope.registryEntry._links[rel].rel;
                if (relation === REL_DOCUMENT_DESCRIPTION) {
                    // here we have a link to the documentDescription
                    var documentDescriptionHref = $scope.registryEntry._links[rel].href;
                    console.log("Found HREF " + documentDescriptionHref);
                    (function() {
                        $http({
                            method: 'GET',
                            url: documentDescriptionHref,
                            headers: {'Authorization': GetUserToken()}
                        }).then(function successCallback(response) {
                            $scope.registryEntry.documentDescription = response.data.results;
                            //console.log("document description is " + JSON.stringify(response.data) );

                            // Now go through each rel and find the one linking to documentObject
                            response.data.results.forEach(function (documentdescription) {
                                for (var rel in documentdescription._links) {
                                    var relation = documentdescription._links[rel].rel;
                                    if (relation === REL_DOCUMENT_OBJECT) {
                                        var documentObjectHref = documentdescription._links[rel].href;
                                        (function () {
                                            // here we have a link to the documentObject
                                            // Fetch the documentObject
                                            $http({
                                                method: 'GET',
                                                url: documentObjectHref,
                                                headers: {'Authorization': GetUserToken()}
                                            }).then(function successCallback(response) {
                                                $scope.documentObjectETag = response.headers('eTag');
                                                // This foreach needs to be replaced with a mechanism to handle multiple
                                                // documentObjects on documentDescription. Currently it will just pick up the
                                                // last one
                                                response.data.results.forEach(function (documentObject) {
                                                    $scope.documentObject = documentObject;
                                                });
                                            }, function errorCallback(response) {
                                                alert("Problem with call to url [" + documentObjectHref + "] response is "
                                                    + response);
                                            });
                                        }) (documentObjectHref);
                                    }
                                }
                            });
                        }, function errorCallback(response) {
                            alert("Problem with call to url [" + documentDescriptionHref + "] response is " + response);
                        });
                    }) (documentDescriptionHref);
                }
                if (relation === REL_CORRESPONDENCE_PART_PERSON) {
                    // here we have a link to correpondencepartperson
                    console.log("Found RED REL_CORRESPONDENCE_PART_PERSON " + REL_CORRESPONDENCE_PART_PERSON);
                    var correspondencePartPersonHref = $scope.registryEntry._links[rel].href;
                    console.log("Found HREF " + correspondencePartPersonHref);
                    (function () {
                        $http({
                            method: 'GET',
                            url: correspondencePartPersonHref,
                            headers: {'Authorization': GetUserToken()}
                        }).then(function successCallback(response) {
                            $scope.registryEntry.korrespondansepartperson = response.data.results;
                            console.log("korrespondansepartperson is " + JSON.stringify(response.data));
                        }, function errorCallback(response) {
                            alert("Problem with call to url [" + correspondencePartPersonHref + "] response is " + response);
                        });
                    })(correspondencePartPersonHref);
                }
            }
        }, function errorCallback(response) {
            alert("Problem with call to url [" + urlVal + "] response is " + response);
        });
        console.log("$scope.createNewRegistryEntry = " + $scope.createNewRegistryEntry);
    }

    $scope.newDocumentSelected = function (registryEntry) {
        // setting these to ''so that when we hit the page
        // any previous values will be ignored
        SetLinkToDocumentDescription('');
        SetLinkToCreateDocumentDescription('');
        SetChosenDocumentDescription('');
        SetCurrentRegistryEntry($scope.registryEntry);


        /*
         TODO : This is where you are ... The problem is registryEntry is null on dokument.html/js page
         Need to find out why


         */
        console.log("Current registryEntry is " + JSON.stringify($scope.registryEntry));
        for (var rel in registryEntry._links) {
            relation = registryEntry._links[rel].rel;
            if (relation == REL_NEW_DOCUMENT_DESCRIPTION) {
                var href = registryEntry._links[rel].href;
                SetLinkToCreateDocumentDescription(href);
            }
        }
        window.location = gui_base_url + documentPageName;
    };

    $scope.newCorrespondencePartPerson = function () {
        ClearLinkToCorrespondencePartPerson();
        window.location = gui_base_url + correspondencePartPersonPageName;
    };

    $scope.newCorrespondencePartUnit = function () {
        SetLinkToCorrespondencePartUnit(null);
        window.location = gui_base_url + correspondencePartUnitPageName;
    };

    $scope.documentSelected = function (documentDescription) {
        console.log("documentDescription" + JSON.stringify(documentDescription));
        SetChosenDocumentDescription(documentDescription);
        for (var rel in documentDescription._links) {
            var relation = documentDescription._links[rel].rel;
            if (relation === 'self') {
                console.log("documentDescription" + JSON.stringify(documentDescription) + " href = " +
                    documentDescription._links[rel].href);
                SetLinkToDocumentDescription(documentDescription._links[rel].href);
            }
        }
        window.location = gui_base_url + documentPageName;
    };

    $scope.correspondencePartPersonSelected = function (correspondansepartperson) {
        console.log("correspondansepartperson" + JSON.stringify(correspondansepartperson));
        for (var rel in correspondansepartperson._links) {
            var relation = correspondansepartperson._links[rel].rel;
            if (relation === 'self') {
                console.log("correspondansepartperson" + JSON.stringify(correspondansepartperson) + " href = " +
                    correspondansepartperson._links[rel].href);
                SetLinkToCurrentCorrespondencePartPerson(correspondansepartperson._links[rel].href);
            }
        }
        console.log("registry entry redirect to " + gui_base_url + correspondencePartPersonPageName +
            " for correspondencePartPerson selected");
        window.location = gui_base_url + correspondencePartPersonPageName;
    };


    $scope.send_form = function () {
        url = GetLinkToCreateRegistryEntry();
        formdata = {};
        for (var key of $scope.formfields) {
            formdata[key] = $scope[key];
        }
        // check that it's not null, create a popup here if it is
        console.log("formdata  is " + JSON.stringify(formdata));
        $http({
            url: url,
            method: "POST",
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': GetUserToken()
            },
            data: formdata,
        }).then(function successCallback(response) {
            var registryEntry = response.data;
            $scope.registryEntry = response.data;
            SetCurrentRegistryEntry(response.data);
            for (rel in registryEntry._links) {
                relation = registryEntry._links[rel].rel;
                if (relation == 'self') {
                    href = registryEntry._links[rel].href;
                    SetLinkToChosenRecord(href);
                    window.location = gui_base_url + registryEntryPageName;
                }
            }
        }, function (data) {
            alert(JSON.stringify(data));
        });
    };
}]);

