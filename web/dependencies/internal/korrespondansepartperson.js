/**
 *  We want to use ng-model instead of brackets, but mdl seems to have problems showing values correctly
 *  when a page loads.
 *
 *  This is briefly discussed in https://github.com/HiOA-ABI/nikita-noark5-core/issues/91
 *
 *  So that has to be fixed. But I'd like to be able to disable the button "Oppdater korrespondansepart" until
 *  someone actually changes a value in one of the fields. ngChange can do this, but is reliant on using ng-model,
 *  we can't use ng-model or the pages looks like puke. Getting this to work with brackets {{}} seemed complicated
 *  and I don't have time to figure it out at the moment.
 *
 * So currently, we use use a pop-up (ugly - yes!) until we come with a nicer way to show that the update was
 * successful. I'm sure mdl / angular has nice ways to solve this.
 *
 */

var app = angular.module('nikita', [])
    .controller('CorrespondencePartPersonController', ['$scope', '$http', function ($scope, $http) {

        $scope.correspondencePartTypeList = correspondencePartTypeList;
        var urlCorrespondencePartPerson = GetLinkToCurrentCorrespondencePartPerson();

        // Display journalpostnr and tittel for UX
        $scope.registryEntry = GetCurrentRegistryEntry();
        // Needed for the breadcrumbs to display Sak(mappeID)
        $scope.casefile = GetCurrentCaseFile();
        // Make a breadcrumbs value appear
        $scope.printCorrespondencePart = true;
        $scope.display_breadcrumb = display_breadcrumb;

        if (urlCorrespondencePartPerson) {
            console.log("Curent urlCorrespondencePartPerson is" + JSON.stringify(urlCorrespondencePartPerson));
            $scope.createCorrespondencePartPerson = false;
            $scope.buttonLabel = "Oppdater korrespondansepartperson";
            $scope.label_correspondansepart = "Korrespondansepartperson";
            var token = GetUserToken();
            $http({
                method: 'GET',
                url: urlCorrespondencePartPerson,
                headers: {'Authorization': token}
            }).then(function successCallback(response) {
                $scope.correspondencePartPersonETag = response.headers('eTag');
                $scope.correspondencepartPerson = response.data;
                setCurrentCorrepsondencePartPersonFromRels($scope.correspondencepartPerson);
            }, function errorCallback(response) {
                alert("Could not find correspondencePartPerson using link=" + urlCorrespondencePartPerson + " " + response);
            });
        }
        // There is no current correspondencepartperson, so we must be making a new one, fetch default values
        // from the noark 5 core
        else {
            $scope.createCorrespondencePartPerson = true;
            $scope.label_correspondansepart = "Ny Korrespondansepartperson";
            $scope.buttonLabel = "Lagre korrespondansepartperson";
            // Go through each rel of the registryentry
            for (var rel in $scope.registryEntry._links) {
                var relation = $scope.registryEntry._links[rel].rel;
                // find one that contains a link to a  ny-korrespondansepartperson
                if (relation === REL_NEW_CORRESPONDENCE_PART_PERSON) {
                    var correspondencePartPersonHref = $scope.registryEntry._links[rel].href;
                    SetLinkToCreateCorrespondencePartPerson($scope.registryEntry._links[rel].href);
                    // Issue a GET for the ny-korrespondansepartperson
                    $http({
                        method: 'GET',
                        url: correspondencePartPersonHref,
                        headers: {'Authorization': GetUserToken()}
                    }).then(function successCallback(response) {
                        console.log("Result from GET (" + correspondencePartPersonHref + ") is " + JSON.stringify(response.data));
                        // populate the values in the GUI
                        $scope.correspondencepartPerson = response.data;
                        $scope.correspondencePartPersonETag = response.headers('eTag');
                        $scope.selectedCorrespondencePartType = response.data.korrespondanseparttype.beskrivelse;
                    }, function errorCallback(response) {
                        alert("Problem with call to url [" + correspondencePartPersonHref + "] response is "
                            + response);
                    });
                }
            }
        }

        var setCurrentCorrepsondencePartPersonFromRels = function (correspondencepartPerson) {
            // Find the self link, need it when updating
            for (var rel in correspondencepartPerson._links) {
                var relation = correspondencepartPerson._links[rel].rel;
                // find one that contains a link to a  ny-korrespondansepartperson
                if (relation === REL_SELF) {
                    SetLinkToCurrentCorrespondencePartPerson(correspondencepartPerson._links[rel].href);
                }
            }
        };

        $scope.send_form = function () {
            var urlCorrespondencePartPerson = '';
            var method = '';
            if ($scope.createCorrespondencePartPerson) {
                method = "POST";
                urlCorrespondencePartPerson = GetLinkToCreateCorrespondencePartPerson();
            } else {
                method = "PUT";
                urlCorrespondencePartPerson = GetLinkToCurrentCorrespondencePartPerson();
            }
            console.log("Attempting " + method + " on " + urlCorrespondencePartPerson);
            $http({
                url: urlCorrespondencePartPerson,
                method: method,
                headers: {
                    'Content-Type': 'application/vnd.noark5-v4+json',
                    'Authorization': GetUserToken(),
                    'ETAG': $scope.correspondencePartPersonETag
                },
                data: {
                    korrespondanseparttype: {
                        kode: "EA",
                        beskrivelse: $scope.selectedCorrespondencePartType
                    },
                    foedselsnummer: $.trim(document.getElementById("foedselsnummer").value),
                    dnummer: $.trim(document.getElementById("d_number").value),
                    navn: $.trim(document.getElementById("navn").value),
                    postadresse: {
                        adresselinje1: $.trim(document.getElementById("pa_adresselinje1").value),
                        adresselinje2: $.trim(document.getElementById("pa_adresselinje2").value),
                        adresselinje3: $.trim(document.getElementById("pa_adresselinje3").value),
                        postnummer: $.trim(document.getElementById("pa_postnummer").value),
                        poststed: $.trim(document.getElementById("pa_poststed").value),
                        landkode: $.trim(document.getElementById("pa_landkode").value)
                    },
                    bostedsadresse: {
                        adresselinje1: $.trim(document.getElementById("bo_adresselinje1").value),
                        adresselinje2: $.trim(document.getElementById("bo_adresselinje2").value),
                        adresselinje3: $.trim(document.getElementById("bo_adresselinje3").value),
                        postnummer: $.trim(document.getElementById("bo_postnummer").value),
                        poststed: $.trim(document.getElementById("bo_poststed").value),
                        landkode: $.trim(document.getElementById("bo_landkode").value)
                    },
                    kontaktinformasjon: {
                        epostadresse: $.trim(document.getElementById("epostadresse").value),
                        mobiltelefon: $.trim(document.getElementById("mobiltelefon").value),
                        telefonnummer: $.trim(document.getElementById("telefonnummer").value)
                    }
                }
            }).then(function successCallback(response) {
                console.log(method + " correspondencePartPerson returned=" + JSON.stringify(response.data));
                $scope.correspondencePartPersonETag = response.headers('eTag');
                setCurrentCorrepsondencePartPersonFromRels(response.data);
                $scope.buttonLabel = "Oppdater korrespondansepartperson";
                var message = '';
                if (!$scope.createCorrespondencePartPerson) {
                    message = "korrespondansepartperson oppdatert";
                    alert(message);
                }
                // set to false so next time this method is called, it's a PUT, not a POST
                $scope.createCorrespondencePartPerson = false;
            }, function (data, status, headers, config) {
                alert("Could not " + method + " document object " + data.data);
            });
        };
    }])
;
