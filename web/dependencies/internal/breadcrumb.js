var app = angular.module("nikita-shared", []);

//service style, probably the simplest one
app.service('breadcrumbService', function () {

    this.getSaksNrSet = function () {
        return true
    };

    this.getSaksNr = function () {
        return "Hello, World!"
    };
});
