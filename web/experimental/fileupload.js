/**
 * Created by tsodring on 6/3/17.
 */

var app = angular.module('fileUpload', ['ngFileUpload']);

app.controller('MyCtrl', ['$scope', 'Upload', '$timeout', function ($scope, Upload, $timeout) {
    $scope.uploadFiles = function (file, errFiles) {
        console.log("Hello");
        $scope.f = file;
        $scope.errFile = errFiles && errFiles[0];
        if (file) {
            console.log("Hello");
            file.upload = Upload.upload({
                url: 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentobjekt/2ef8e368-3a4c-4027-a377-83da14165de6/referanseFil/',
                data: {
                    "Authorization": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTQ5NjMwMjgwNzM0OSwiZXhwIjoxNDk2OTA3NjA3fQ.ccGDxiPMjae427rJNrLIikmu9gniNq9-DJ6Y-r2L1V4cfIQordXgE4x94ssI9oZwKqW-mDLA-Clns2zZff9fww",
                    file: file
                }
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    file.result = response.data;
                });
            }, function (response) {
                if (response.status > 0)
                    $scope.errorMsg = response.status + ': ' + response.data;
            }, function (evt) {
                file.progress = Math.min(100, parseInt(100.0 *
                    evt.loaded / evt.total));
            });
        }
    }
}]);
