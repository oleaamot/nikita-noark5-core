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
    $scope.token = GetUserToken();

    $scope.documentMediumList = documentMediumList;
    $scope.caseStatusList = caseStatusList;
    $scope.registryEntryTypeList = registryEntryTypeList;
    $scope.registryEntryStatusList = registryEntryStatusList;

    $scope.showDetails = false;
    $scope.showDetailsText = "Flere detaljer";

    $scope.caseFile = JSON.parse(GetChosenCaseFile());
    $scope.registryEntry = JSON.parse(GetChosenRegistryEntryObject());

    console.log("$scope.registryEntry is set to " + $scope.registryEntry);
    // No chosen registryEntry, means we are creating a new registryEntry
    if ($scope.registryEntry == '') {
        $scope.createNewRegistryEntry = true;
        console.log("registryEntryController. Creating a new RegistryEntry");

        // Using the current casefile object, go and find the
        // REL_NEW_REGISTRY_ENTRY href and issue a GET to get any default
        // values defined in the core
        for (var rel in $scope.caseFile._links) {
            var relation = $scope.caseFile._links[rel].rel;
            if (relation == REL_NEW_REGISTRY_ENTRY) {
                var urlGetNewRegistryEntry = $scope.caseFile._links[rel].href;
                console.log("Doing a GET on " + urlGetNewRegistryEntry);
                $http({
                    method: 'GET',
                    url: urlGetNewRegistryEntry,
                    headers: {'Authorization': $scope.token},
                }).then(function successCallback(response) {
                    $scope.registryEntry = response.data;
                    console.log("urlGetNewRegistryEntry: " + urlGetNewRegistryEntry +
                        " results " + JSON.stringify(response.data));
                }, function errorCallback(response) {
                    alert(JSON.stringify(response));
                });
            }
        }
    }
    else {
        console.log("registryEntryController. Showing an existing RegistryEntry");
        console.log("registryEntryController. RegistryEntry is " + $scope.registryEntry);
        $scope.createNewRegistryEntry = false;
        $scope.registryEntryETag = -1;

        console.log("registryEntryController. RegistryEntry _links is " + $scope.registryEntry._links);

        // You need to do a GET because you need an ETAG!
        // So you get the SELF REL of the current registryEntry
        // and retrieve it again so you have an ETAG
        for (var rel in $scope.registryEntry._links) {
            var relation = $scope.registryEntry._links[rel].rel;
            if (relation == REL_SELF) {
                var urlRegistryEntry = $scope.registryEntry._links[rel].href;
                console.log("Attempting GET on registryEntry : " + urlRegistryEntry);
                $http({
                    method: 'GET',
                    url: urlRegistryEntry,
                    headers: {'Authorization': $scope.token},
                }).then(function successCallback(response) {
                    $scope.registryEntry = response.data;
                    SetChosenRegistryEntryObject(response.data);

                    $scope.registryEntryETag = response.headers('eTag');
                    $scope.selectedRegistryEntryType = $scope.registryEntry.journalposttype;
                    $scope.selectedRegistryEntryStatus = $scope.registryEntry.journalstatus;

                    console.log("GET on registryEntry : " + urlRegistryEntry +
                        " results " + JSON.stringify(response.data));

                    // Now go through each rel and find the one linking to documentDescription
                    for (var rel in $scope.registryEntry._links) {
                        var relation = $scope.registryEntry._links[rel].rel;
                        if (relation === REL_DOCUMENT_DESCRIPTION) {
                            // here we have a link to the documentDescription
                            var documentDescriptionHref = $scope.registryEntry._links[rel].href;
                            console.log("Found HREF " + documentDescriptionHref);
                            (function () {
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
                                                })(documentObjectHref);
                                            }
                                        }
                                    });
                                }, function errorCallback(response) {
                                    alert("Problem with call to url [" + documentDescriptionHref + "] response is " + response);
                                });
                            })(documentDescriptionHref);
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
                    alert(JSON.stringify(response));
                });
            }
        }

        // Next go and get any registryEntires associated with the registryEntry
        for (var rel in $scope.registryEntry._links) {
            var relation = $scope.registryEntry._links[rel].rel;
            if (relation == REL_REGISTRY_ENTRY) {
                SetLinkToGetRegistryEntry($scope.registryEntry._links[rel].href);
                var urlAllRegistryEntries = $scope.registryEntry._links[rel].href;
                $http({
                    method: 'GET',
                    url: urlAllRegistryEntries,
                    headers: {'Authorization': $scope.token},
                }).then(function successCallback(response) {
                    $scope.registryEntry.records = response.data.results;
                    console.log("urlAllRegistryEntries: " + urlAllRegistryEntries +
                        " results " + JSON.stringify(response.data));
                }, function errorCallback(response) {
                    alert(JSON.stringify(response));
                });
            }
        }
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


    /**
     * Copy the registryEntry values from the DOM and send them to the noark core. Checks to see if this should
     * be an update or a create operation and acts accordingly.
     */

    $scope.post_or_put_registry_entry = function () {
        var urlRegistryEntry = '';
        // check that it's not null, create a popup here if it is
        var method = '';

        console.log("post_or_put_registry_entry using casefile " + $scope.caseFile);
        // First find the REL/HREF pair of the current series so we have an address we can POST/PUT
        // data to
        if ($scope.createNewRegistryEntry) {
            method = "POST";

            for (var rel in $scope.caseFile._links) {
                var relation = $scope.caseFile._links[rel].rel;
                if (relation == REL_NEW_REGISTRY_ENTRY) {
                    urlRegistryEntry = $scope.caseFile._links[rel].href;
                    console.log("URL for POST operation on RegistryEntry is " + urlRegistryEntry);
                }
            }
        } else {
            method = "PUT";
            // Find the url to self object
            for (var rel in $scope.registryEntry._links) {
                var relation = $scope.registryEntry._links[rel].rel;
                if (relation == REL_SELF) {
                    urlRegistryEntry = $scope.registryEntry._links[rel].href;
                    console.log("URL for PUT operation on RegistryEntry is " + urlRegistryEntry);
                }
            }
        }


        $http({
            url: urlRegistryEntry,
            method: method,
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': GetUserToken(),
                'ETAG': $scope.registryEntryETag
            },
            data: {
                tittel: $.trim(document.getElementById("tittel").value),
                beskrivelse: $.trim(document.getElementById("beskrivelse").value),
                journalpostnummer: Number($.trim(document.getElementById("journalpostnummer").value)),
                journalaar: Number($.trim(document.getElementById("journalaar").value)),
                dokumentetsDato: $.trim(document.getElementById("dokumentetsDato").value),
                journaldato: $.trim(document.getElementById("journaldato").value),
                journalstatus: $.trim(document.getElementById("journalpoststatus").value),
                journalsekvensnummer: Number($.trim(document.getElementById("journalsekvensnummer").value)),
                journalposttype: $.trim(document.getElementById("journalposttype").value),
            },
        }).then(function successCallback(response) {
            console.log(method + " RegistryEntry data returned=" + JSON.stringify(response.data));
            $scope.createNewRegistryEntry = false;
            $scope.registryEntry = response.data;
            SetChosenRegistryEntryObject($scope.registryEntry);
        }, function (data, status, headers, config) {
            alert("Could not " + method + "RegistryEntry " + data.data);
        });
    };
}]);

/*

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

*/
