var app = angular.module('nikita-file', []);

var fileController = app.controller('FileController', ['$scope', '$http', function ($scope, $http) {
    $scope.token = GetUserToken();
    $scope.series = GetSeriesSystemID();

    var urlVal = GetLinkToSeriesAllFile();
    console.log("*****Link to chosen file is =" + urlVal);

    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        $scope.files = response.data.results;
        //console.log("file data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    url = app_url + '/arkivstruktur/arkivdel/' + $scope.series + '/ny-mappe';
    $scope.formfields = ['tittel', 'beskrivelse', 'mappeID'];
    $http({
        method: 'GET',
        url: url,
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        for (var key of Object.keys(response.data)) {
            if ("_links" === key) {
            } else {
                //console.log(key,response.data[key]);
                $scope[key] = response.data[key];
                $scope.formfields.push(key);
            }
        }
        //console.log("ny-mappe data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    $scope.fileSelected = function (file) {
        console.log('file selected link clicked ' + JSON.stringify(file));

        for (rel in file._links) {
            relation = file._links[rel].rel;
            if (relation == 'self') {
                href = file._links[rel].href;
                SetLinkToChosenFile(href);
                console.log("fetching " + href);
                window.location = gui_base_url + "/saksmappe.html";
            }
        }
    };

    $scope.send_form = function() {
        token = GetUserToken();
        systemID = GetSeriesSystemID();
        url = app_url + '/arkivstruktur/arkivdel/' + systemID + '/ny-mappe';
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
        }).then(function(data, status, headers, config) {
            changeLocation($scope, "./mappe.html", true);
        }, function(data, status, headers, config) {
            alert(data.data);
        });
    };

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

}]);
