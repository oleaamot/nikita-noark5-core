var app = angular.module('nikita-search', []);

app.controller('SearchController', ['$scope', '$http', function ($scope, $http) {
    $scope.do_search = function () {
	$scope.textValue = $.trim(document.getElementById("content").value);
	var contentValue = $scope.textValue;
	$scope.textValue = $.trim(document.getElementById("element").value);
	var elementValue = $scope.textValue;
	$scope.textValue = $.trim(document.getElementById("filter").value);
	var filterValue = $scope.textValue;
	$scope.textValue = $.trim(document.getElementById("string").value);
	var stringValue = $scope.textValue;
	console.log(" The value of content is " + contentValue);
	console.log(" The value of element is " + elementValue);
	console.log(" The value of filter is " + filterValue);
	console.log(" The value of string is " + stringValue);
	// Shows how to get value
	var urlToFonds = base_url + "odata/arkivstruktur/arkiv?%24filter%3Dcontains%28tittel%2C%27" + stringValue + "%27%29%24top%3D2%24skip%3D0%24orderby%3Dtittel%20desc";
	// noarkObject should get a value from the html page
	var noarkObject = 'arkiv';
	urlToFonds = base_url + "odata/arkivstruktur/" + noarkObject + "?%24filter%3Dcontains%28tittel%2C%27%27%29%24top%3D2%24skip%3D0%24orderby%3Dtittel%20desc";
	console.log("Attempting search on " + urlToFonds);
	var token = GetUserToken();
	$http({
	    method: 'GET',
	    url: urlToFonds,
	    headers: {'Authorization': token, 'Access-Control-Allow-Origin': 'http://www.arkivarium.no/'}
	}).then(function successCallback(response) {
	    console.log("Data received from search is : " + JSON.stringify(response.data));
	}, function errorCallback(response) {
	    console.log("Error when attempting search. Response is " + response);
	});
    }
}]);
