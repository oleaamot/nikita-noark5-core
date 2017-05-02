
let app = angular.module('nikita-series', []);

let base_url = "http://localhost:8092/noark5v4";
let app_url = "http://localhost:8092/noark5v4/hateoas-api";
let gui_base_url = "http://localhost:3000/experimental";

if (nikitaOptions.enabled) {
    base_url = nikitaOptions.protocol + "://" + nikitaOptions.baseUrl  + "/" + nikitaOptions.appName;
    app_url = base_url + "/" + nikitaOptions.apiName;
    gui_base_url = nikitaOptions.guiBaseUrl;
    console.log("nikita baseURL" + base_url);
    console.log("nikita appURL" + app_url );
    console.log("nikita guiURL" + gui_base_url );
}

var SetLinkToChosenFile = function(t) {
    localStorage.setItem("linkToChosenFile", t);
    console.log("Setting linkToChosenFile="+t);
};

var GetFondsSystemID = function(t) {
    console.log("Getting chosen fondsSystemId="+localStorage.getItem("chosenfonds"));
    return localStorage.getItem("chosenfonds");
};


var GetUserToken = function(t) {
    return localStorage.getItem("token");
};

var GetLinkToChosenSeries = function(t) {
    return localStorage.getItem("linkToChosenSeries");
};

var SetChosenSeries = function(seriesSystemId) {
    localStorage.setItem("chosenseries", seriesSystemId);
    console.log("Setting seriesSystemId="+seriesSystemId);
};

let seriesController = app.controller('SeriesController', ['$scope', '$http', function ($scope, $http) {
    $scope.token = GetUserToken();
    console.log("token="+$scope.token);
    $scope.fonds = GetFondsSystemID();

    var urlVal = GetLinkToChosenSeries();
    $http({
        method: 'GET',
        url: urlVal,
        headers: {'Authorization': $scope.token },
    }).then(function successCallback(response) {
        $scope.seriess = response.data.results;
        console.log("series data is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
        // TODO: what should we do when it fails?
    });

    // TODO : Add the href to local storage indicating what was clicked
    //
    $scope.fileSelected = function(href, seriesSystemId){
        console.log('series selected link clicked ' + href);
        token = GetUserToken();
        SetLinkToChosenFile(href);
        SetChosenSeries(seriesSystemId);
        window.location = gui_base_url + "/mappe.html";
    }

    $scope.send_form = function() {
        token = GetUserToken();
        systemID = GetFondsSystemID();
        url = app_url + '/arkivstruktur/arkiv/' + systemID + '/ny-arkivdel';
        $http({
            url: url,
            method: "POST",
            headers: {
                'Content-Type': 'application/vnd.noark5-v4+json',
                'Authorization': token,
            },
            data: { tittel: $scope.tittel, beskrivelse: $scope.beskrivelse },
        }).then(function(data, status, headers, config) {
            changeLocation($scope, "./arkivdel.html", true);
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