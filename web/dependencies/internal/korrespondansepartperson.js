var app = angular.module('nikita-correspondencepart-person', [])
    .controller('CorrespondencePartPersonController', ['$scope', '$http', function ($scope, $http) {

        $scope.correspondencePartTypeList = correspondencePartTypeList;
        var urlCorrespondencePartPerson = GetLinkToCorrespondencePartPerson();
        var urlVal = GetLinkToChosenRecord();

        // Fetch the record to display journalpostnr and tittel
        $http({
            method: 'GET',
            url: urlVal,
            headers: {'Authorization': GetUserToken()}
        }).then(function successCallback(response) {
            $scope.registryEntry = response.data;
            if (urlCorrespondencePartPerson) {
                console.log("Curent urlCorrespondencePartPerson is" + JSON.stringify(urlCorrespondencePartPerson));
                $scope.createCorrespondencePartPerson = false;
                var token = GetUserToken();
                $http({
                    method: 'GET',
                    url: urlCorrespondencePartPerson,
                    headers: {'Authorization': token}
                }).then(function successCallback(response) {
                    $scope.correspondencePartPersonETag = response.headers('eTag');
                    $scope.correspondencepartPerson = response.data;
                }, function errorCallback(response) {
                    alert("Could not find correspondencePartPerson using link=" + urlCorrespondencePartPerson + " " + response);
                });
            }
            else {
                $scope.createCorrespondencePartPerson = true;
                var relFound = false;
                for (var rel in $scope.registryEntry._links) {
                    var relation = $scope.registryEntry._links[rel].rel;
                    if (relation === REL_NEW_CORRESPONDENCE_PART_PERSON) {
                        var correspondencePartPersonHref = $scope.registryEntry._links[rel].href;
                        SetLinkToCorrespondencePartPerson($scope.registryEntry._links[rel].href);
                        relFound = true;
                    }
                    /*
                     if (relation === REL_CORRESPONDENCE_PART_PERSON) {
                     var correspondencePartPersonHref = $scope.registryEntry._links[rel].href;
                     SetLinkToCorrespondencePartPerson($scope.registryEntry._links[rel].href);
                     }*/
                } // for
                // here we have a link to the new correspondencePartPerson
                // Fetch a new correspondencePartPerson
                if (relFound == true) {
                    $http({
                        method: 'GET',
                        url: correspondencePartPersonHref,
                        headers: {'Authorization': GetUserToken()}
                    }).then(function successCallback(response) {
                        console.log("Result from GET (" + correspondencePartPersonHref + ") is " + JSON.stringify(response.data));
                        $scope.correspondencepartPerson = response.data;
                        $scope.selectedCorrespondencePartType = response.data.korrespondanseparttype.beskrivelse;
                    }, function errorCallback(response) {
                        alert("Problem with call to url [" + correspondencePartPersonHref + "] response is "
                            + response);
                    });
                }
            }
        }, function errorCallback(response) {
            alert("Could not find registryEntry using link=" + urlVal + " " + response);
        });

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

        $scope.send_form = function () {
            var urlCreateCorrespondencePartPerson = GetLinkToCorrespondencePartPerson();
            // check that it's not null, create a popup here if it is
            var method = '';
            if ($scope.createCorrespondencePartPerson) {
                method = "POST";
            } else {
                method = "PUT";
            }
            console.log("Attempting " + method + " on " + urlCreateCorrespondencePartPerson);
            $http({
                url: urlCreateCorrespondencePartPerson,
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
                changeLocation($scope, correspondencePartPersonPageName, true);
            }, function (data, status, headers, config) {
                alert("Could not " + method + " document object " + data.data);
            });
        };
    }])
;
