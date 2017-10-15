var app = angular.module('nikita-casefile', []);

var caseFileController = app.controller('CaseFileController', ['$scope', '$http', function ($scope, $http) {

    $scope.display_breadcrumb = display_breadcrumb;
    $scope.token = GetUserToken();

    $scope.documentMediumList = documentMediumList;
    $scope.caseStatusList = caseStatusList;
    $scope.showDetails = false;
    $scope.showDetailsText = "Flere detaljer";

    $scope.caseFile = JSON.parse(GetChosenCaseFile());

    // No chosen caseFile, means we are creating a new caseFile
    if (!$scope.caseFile) {
        $scope.createNewCaseFile = true;
        console.log("caseFileController. Creating a new CaseFile");
    }
    else {
        console.log("caseFileController. Showing an existing CaseFile");
        console.log("caseFileController. CaseFile is " + $scope.caseFile);
        $scope.createNewCaseFile = false;
        $scope.caseFileETag = -1;

        console.log("caseFileController. CaseFile _links is " + $scope.caseFile._links);

        // You need to do a GET because you need an ETAG!
        // So you get the SELF REL of the current caseFile
        // and retrieve it again so you have an ETAG
        for (var rel in $scope.caseFile._links) {
            var relation = $scope.caseFile._links[rel].rel;
            if (relation == REL_SELF) {
                var urlCaseFile = $scope.caseFile._links[rel].href;
                console.log("Attempting GET on caseFile : " + urlCaseFile);
                $http({
                    method: 'GET',
                    url: urlCaseFile,
                    headers: {'Authorization': $scope.token},
                }).then(function successCallback(response) {
                    $scope.caseFile = response.data;
                    SetChosenCaseFile(response.data);

                    $scope.caseFileETag = response.headers('eTag');

                    console.log("GET on caseFile : " + urlCaseFile +
                        " results " + JSON.stringify(response.data));
                }, function errorCallback(response) {
                    alert(JSON.stringify(response));
                });
            }
        }

        // Next go and get any registryEntires associated with the casefile
        for (var rel in $scope.caseFile._links) {
            var relation = $scope.caseFile._links[rel].rel;
            if (relation == REL_REGISTRY_ENTRY) {
                SetLinkToGetRegistryEntry($scope.caseFile._links[rel].href);
                var urlAllRegistryEntries = $scope.caseFile._links[rel].href;
                $http({
                    method: 'GET',
                    url: urlAllRegistryEntries,
                    headers: {'Authorization': $scope.token},
                }).then(function successCallback(response) {
                    $scope.caseFile.records = response.data.results;
                    console.log("urlAllRegistryEntries: " + urlAllRegistryEntries +
                        " results " + JSON.stringify(response.data));
                }, function errorCallback(response) {
                    alert(JSON.stringify(response));
                });
            }
        }
    }

    $scope.registryEntrySelected = function (registryEntry) {
        SetCurrentRegistryEntry(registryEntry);
        changeLocation($scope, registryEntryPageName, true);
    };


    /**
     * Simply changes the text in a button in the GUI, and it has a follow-up visual effect
     * where information is hid.
     */

    $scope.showDetailsChanged = function () {
        $scope.showDetails = !$scope.showDetails;
        if (!$scope.showDetails) {
            $scope.showDetailsText = "Flere detaljer"
        }
        else {
            $scope.showDetailsText = "Mindre detaljer"
        }
    };


    $scope.createNewRegistryEntryPressed = function () {
        SetCurrentRegistryEntry('');
        console.log("Current casefile is " + $scope.caseFile);
        for (var rel in $scope.caseFile._links) {
            var relation = $scope.caseFile._links[rel].rel;
            if (relation == REL_NEW_REGISTRY_ENTRY) {
                SetLinkToCreateRegistryEntry($scope.caseFile._links[rel].href);
            }
            // Do we need this here???
            if (relation == REL_REGISTRY_ENTRY) {
                SetLinkToGetRegistryEntry($scope.caseFile._links[rel].href);
            }
        }
        changeLocation($scope, registryEntryPageName, true);
    };


    /**
     * Copy the casefile values from the DOM and send them to the noark core. Checks to see if this should
     * be an update or a create operation and acts accordingly.
     */

    $scope.post_or_put_case_file = function () {
        var urlCaseFile = '';
        // check that it's not null, create a popup here if it is
        var method = '';

        // First find the REL/HREF pair of the current series so we have an address we can POST/PUT
        // data to
        if ($scope.createNewCaseFile) {
            method = "POST";
            var series = GetChosenSeries();
            // Check the current series for a link to create a new casefile
            for (var rel in series._links) {
                var relation = series._links[rel].rel;
                if (relation == REL_NEW_CASE_FILE) {
                    urlCaseFile = series._links[rel].href;
                    console.log("URL for POST operation on casefile is " + urlCaseFile);
                }
            }
        } else {
            method = "PUT";
            // Find the url to self object
            for (var rel in $scope.caseFile._links) {
                var relation = $scope.caseFile._links[rel].rel;
                if (relation == REL_SELF) {
                    urlCaseFile = $scope.caseFile._links[rel].href;
                    console.log("URL for PUT operation on casefile is " + urlCaseFile);
                }
            }
        }

        console.log("Attempting " + method + " on " + urlCaseFile);
        $http({
            url: urlCaseFile,
            method: method,
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': GetUserToken(),
                'ETAG': $scope.caseFileETag
            },
            data: {
                mappeID: $.trim(document.getElementById("case_file_file_id").value),
                tittel: $.trim(document.getElementById("case_file_title").value),
                offentligTittel: $.trim(document.getElementById("case_file_public_title").value),
                beskrivelse: $.trim(document.getElementById("case_file_description").value),
                dokumentmedium: $.trim($scope.selectedDocumentMedium),
                noekkelord: $.trim(document.getElementById("case_file_keywords").value),
                saksaar: $.trim(document.getElementById("case_file_case_year").value),
                saksdato: $.trim(document.getElementById("case_file_case_date").value),
                sakssekvensnummer: $.trim(document.getElementById("case_file_case_sequence_number").value),
                administrativEnhet: $.trim(document.getElementById("case_file_administrative_unit").value),
                saksansvarlig: $.trim(document.getElementById("case_file_case_responsible").value),
                journalenhet: $.trim(document.getElementById("case_file_record_keeping_unit").value),
                saksstatus: $.trim($scope.selectedCaseFileCaseStatus)
            },
        }).then(function successCallback(response) {
            console.log(method + " casefile data returned=" + JSON.stringify(response.data));
            $scope.caseFile = response.data;
            SetChosenCaseFile($scope.caseFile);
        }, function (data, status, headers, config) {
            alert("Could not " + method + "casefile " + data.data);
        });
    };
}]);
