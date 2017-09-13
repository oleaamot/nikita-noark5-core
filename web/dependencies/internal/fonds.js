var app = angular.module('nikita-fonds', []);

/**
 * Enables the following functionality:
 *  1. Sets the fonds object correctly for the arkiv.html page
 *  2. Allows a user to POST the contents of the arkiv data
 *
 *  Note: Because we have trouble getting
 *
 *  if ($scope.selectedDocumentMedium == 'Elektronisk arkiv '. Note the extra space. Needs to be removed later!
 *
 */

var fondsController = app.controller('FondsController', ['$scope', '$http',
    function ($scope, $http) {

        // Used to show or hide a text message
        $scope.showCreateFonds = false;

        $scope.documentMediumList = documentMediumList;
        $scope.fondsStatusList = fondsStatusList;
        $scope.oppbevaringsStedDisabled = false;

        $scope.fond = GetChosenFonds();

        if ($scope.fond != null) {
            $scope.selectedDocumentMedium = $scope.fond.dokumentmedium;
            $scope.selectedFondsStatus = $scope.fond.arkivstatus;
            console.log("scope.selectedFondsStatus is set to [" + $scope.selectedFondsStatus + "]");

            for (var rel in $scope.fond._links) {
                var relation = $scope.fond._links[rel].rel;
                if (relation == REL_FONDS_CREATOR) {
                    var urlFondsCreators = $scope.fond._links[rel].href;
                    var token = GetUserToken();
                    $http({
                        method: 'GET',
                        url: urlFondsCreators,
                        headers: {'Authorization': token}
                    }).then(function successCallback(response) {
                        // This returns a list and later we will handle a list properly in GUI, but right now I just
                        // need to fetch the first one. I also need an ETAG in case it is to be edited, so I have to
                        // retrieve (again) the object I am actually out after
                        $scope.fondsCreator = response.data;
                        console.log("Found following fondsCreaots " + JSON.stringify($scope.fondsCreator));
                    });
                }
            }
        }
        else {
            $scope.showCreateFonds = true;
        }


        $scope.documentMedium_selected = function () {

            if ($scope.selectedDocumentMedium == 'Elektronisk arkiv ') {
                $scope.oppbevaringsStedDisabled = true;
            }
            else {
                $scope.oppbevaringsStedDisabled = false;
            }
            console.log("oppbevaringsStedDisabled value [" + $scope.oppbevaringsStedDisabled +
                "] val is [" + $scope.selectedDocumentMedium + "], [Elektronisk arkiv]");
        };

        /**
         * post_or_put_fonds
         *
         * Undertakes either a PUT or a POST request to the core with data fields from the webpage
         *
         * The action is decided by whether or not $scope.showCreateFonds == true. If it's true then we will
         * create a fonds. If it's false, we are updating a fonds.
         *
         */

        $scope.post_or_put_fonds = function () {
            var urlDocumentDescription = '';
            var urlDocumentObject = ''; // Value set after documentdescription is created
            // check that it's not null, create a popup here if it is
            var method = '';
            if ($scope.createNewDocument) {
                method = "POST";
                urlDocumentDescription = GetLinkToCreateDocumentDescription();
            } else {
                method = "PUT";
                urlDocumentDescription = GetLinkToDocumentDescription();
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
    }]);

