var app = angular.module('nikita-document', []);

app.controller('DocumentController', ['$scope', '$http', function ($scope, $http) {
/*
    angular.element(document).ready(function () {
        var nodeList = document.querySelectorAll('.mdl-textfield');
        console.log("Document loaded");
        Array.prototype.forEach.call(nodeList, function (elem) {
            elem.MaterialTextfield.checkDirty();
        });
    });*/

        $scope.textValue = "Something something hello";
        $http({
            method: 'GET',
            url: 'http://nikita.hioa.no:8092/noark5v4/'
        }).then(function successCallback(response) {
            var documentDescription = response.data;
            $scope.textValue2 = "Something something else";
        }, function errorCallback(response) {
           console.log("could not");
        });

}]);
