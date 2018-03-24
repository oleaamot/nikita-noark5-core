var app = angular.module('nikita-search', []);

app.controller('SearchController', ['$scope', '$http', function ($scope, $http) {

  $scope.do_search = function () {

    $scope.textValue = $.trim(document.getElementById("archive").value);
    var tittelValue = $scope.textValue;
    console.log(" The value of tittel is " + tittelValue);

    // Shows how to get value
    var urlToFonds = base_url + "odata/arkivstruktur/arkiv?%24filter%3Dcontains%28tittel%2C%27" + tittelValue + "%27%29%24top%3D2%24skip%3D0%24orderby%3Dtittel%20desc";

    // noarkObject should get a value from the html page
    var noarkObject = 'arkiv';

    urlToFonds = base_url + "odata/arkivstruktur/" + noarkObject + "?%24filter%3Dcontains%28tittel%2C%27%27%29%24top%3D2%24skip%3D0%24orderby%3Dtittel%20desc";

    console.log("Attempting search on " + urlToFonds);
    var token = GetUserToken();
    $http({
      method: 'GET',
      url: urlToFonds,
      headers: {'Authorization': token}
    }).then(function successCallback(response) {
      console.log("Data received from search is : " + JSON.stringify(response.data));
    }, function errorCallback(response) {
      console.log("Error when attempting search. Response is " + response);
    });
  }
}]);
