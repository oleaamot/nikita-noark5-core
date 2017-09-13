var app = angular.module('nikita-fonds', []);

var fondsListController = app.controller('FondsListController', ['$scope', '$http',
    function ($scope, $http) {

        $scope.display_footer_note = display_footer_note;
        $scope.token = GetUserToken();
        $scope.show_create_fonds = true;

        $http({
            method: 'GET',
            url: app_url + '/arkivstruktur/arkiv',
            headers: {'Authorization': $scope.token},
        }).then(function successCallback(response) {
            $scope.fonds = response.data.results;
            console.log("data is : " + JSON.stringify(response.data));
        }, function errorCallback(response) {
            // TODO: what should we do when it fails?
        });

        url = app_url + '/arkivstruktur/ny-arkiv';
        $scope.formfields = ['tittel', 'beskrivelse'];
        $http({
            method: 'GET',
            url: url,
            headers: {'Authorization': $scope.token},
        }).then(function successCallback(response) {
            for (var key of Object.keys(response.data)) {
                if ("_links" === key) {
                } else {
                    //console.log(key,response.data[key]);
                    $scope[key] = response.data[key];
                    $scope.formfields.push(key);
                }
            }
            //console.log("ny-arkiv data is : " + JSON.stringify(response.data));
        }, function errorCallback(response) {
            // TODO: what should we do when it fails?
        });

        /**
         *  fonds_selected
         *
         *  When a user presses "Opprett nytt arkiv" or clicks on a individual row of a fonds item from
         *  the list of fonds, this function calls a change location to the page arkiv.html
         *
         * @param fonds Note: Can be null if creating a new fonds
         */
        $scope.fonds_selected = function (fonds) {
            console.log('fonds_selected clicked ' + JSON.stringify(fonds));
            if (fonds == null) {
                SetChosenFonds(null);
            }
            else {
                SetChosenFonds(fonds);
            }
            changeLocation($scope, fondsPageName, false);
        };

    }
]);

var fondsController = app.controller('FondsController', ['$scope', '$http',
    function ($scope, $http) {


        $scope.send_form = function () {
            token = GetUserToken();
            url = app_url + '/arkivstruktur/ny-arkiv';
            formdata = {};
            for (var key of $scope.formfields) {
                formdata[key] = $scope[key];
            }
            $http({
                url: url,
                method: "POST",
                headers: {
                    'Content-Type': 'application/vnd.noark5-v4+json',
                    'Authorization': token,
                },
                data: formdata,
            }).then(function (data, status, headers, config) {
                changeLocation($scope, "./arkiv.html", true);
            }, function (data, status, headers, config) {
                alert(data.data);
            });
        };
    }]);
