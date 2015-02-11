var phonecatApp = angular.module('chardbApp', []);

phonecatApp.controller('HelloController', function ($scope, $http) {
    $http.get('/character/54dbc0e4646174009a000000').
        success(function(data) {
            console.log(data);
        });
});