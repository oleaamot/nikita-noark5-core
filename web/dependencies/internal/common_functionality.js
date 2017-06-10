/**
 * Created by tsodring on 6/7/17.
 */


angular.module('footer-module', [])
    .directive('footer', [function () {
        return {
            restrict: 'A',
            templateUrl: 'footer.html',
            scope: true,
            transclude: false
        };
    }]);

