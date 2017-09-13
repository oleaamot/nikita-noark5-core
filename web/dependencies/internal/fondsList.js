var app = angular.module('nikita-fonds', []);

/**
 * Supports the following functionality:
 *  1. Fills a list of all arkiv that a user has access to
 *  2. Changes location to the arkiv.html page when a row is clicked
 */
var fondsListController = app.controller('FondsListController', ['$scope', '$http',
    function ($scope, $http) {

        /**
         *
         * Retrieve a list of all fonds and populate the object the GUI expects
         *
         */
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
            console.log("Something failed. All  know is : " + JSON.stringify(response));
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
