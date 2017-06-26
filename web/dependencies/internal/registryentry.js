var app = angular.module('nikita-new-registry-entry', []);

/**
 * Controller for registryEntry/ journalpost
 *
 * When called, the script will check to see if the
 * Note his file depends on definitions in config.js These are loaded first in the html file.
 *     <script src="config.js"></script>
 *
 */

app.controller('RegistryEntryController', ['$scope', '$http', function ($scope, $http) {

    // Get the chosen caseFile to display the saksnr and tittel
    $scope.token = GetUserToken();
    var urlVal = GetLinkToChosenFile();

    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token}
    }).then(function successCallback(response) {
        $scope.casefile = response.data;
    }, function errorCallback(response) {
        alert("Problem with call to url [" + urlVal + "] response is " + response);
    });

    // Get the registryEntry to display
    urlVal = GetLinkToChosenRecord();

    // No chosen registryEntry, means we are creating a new registryEntry
    if (!urlVal) {
        $scope.createNewRegistryEntry = true;
        $scope.formfields = [
            'tittel', 'beskrivelse', 'journalposttype', 'journalpoststatus',
            'journalaar', 'journalpostnummer', 'journalsekvensnummer',
            'dokumentetsDato', 'journaldato',
        ];
        console.log("token is : " + $scope.token);
        $http({
            method: 'GET',
            // TODO figure out how to look up URL in _links
            url: GetLinkToChosenFile() + 'ny-journalpost',
            headers: {'Authorization': $scope.token}
        }).then(function successCallback(response) {
            for (var key of Object.keys(response.data)) {
                if ("_links" === key) {
                } else {
                    $scope[key] = response.data[key];
                    $scope.formfields.push(key);
                }
            }
            console.log("ny-journalpost data is : " + JSON.stringify(response.data));
        }, function errorCallback(response) {
            // TODO: what should we do when it fails?
        });
    }
    else {
        $scope.createNewRegistryEntry = false;

        // Get the registryEntry
        $http({
            method: 'GET',
            url: urlVal,
            headers: {'Authorization': $scope.token}
        }).then(function successCallback(response) {
            // registryEntry is in $scope.registryEntry
            $scope.registryEntry = response.data;
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
    }

    $scope.newDocumentSelected = function (registryEntry) {
        // setting these to ''so that when we hit the page
        // any previous values will be ignored
        SetLinkToDocumentDescription(null);
        SetLinkToCreateDocumentDescription('');

        for (var rel in registryEntry._links) {
            relation = registryEntry._links[rel].rel;
            if (relation === REL_NEW_DOCUMENT_DESCRIPTION) {
                var href = registryEntry._links[rel].href;
                SetLinkToCreateDocumentDescription(href);
            }
            if (relation === REL_DOCUMENT_DESCRIPTION) {
                var href = registryEntry._links[rel].href;
                SetLinkToDocumentDescription(href);
            }
        }

        if (GetLinkToCreateDocumentDescription()) {
            window.location = gui_base_url + documentPageName;
        }
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
        for (var rel in documentDescription._links) {
            var relation = documentDescription._links[rel].rel;
            if (relation === 'self') {
                SetLinkToDocumentDescription(documentDescription._links[rel].href);
            }
        }
        console.log("registry entry redirect to " + gui_base_url + documentPageName + " for document selected");
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

    var linkToCreateRegistryEntry = function () {
        if (!$scope.casefile) {
            alert("CaseFile data is undefined");
        }
        for (rel in $scope.casefile._links) {
            relation = $scope.casefile._links[rel].rel;
            if (relation === REL_NEW_REGISTRY_ENTRY) {
                return $scope.casefile._links[rel].href;
            }
        }
        return null;
    };

    $scope.send_form = function () {
        url = linkToCreateRegistryEntry();
        formdata = {};
        for (var key of $scope.formfields) {
            formdata[key] = $scope[key];
        }
        // check that it's not null, create a popup here if it is
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
            for (rel in registryEntry._links) {
                relation = registryEntry._links[rel].rel;
                if (relation === 'self') {
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

