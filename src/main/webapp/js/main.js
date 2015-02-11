var phonecatApp = angular.module('chardbApp', []);

phonecatApp.controller('HelloController', function ($scope, $http, $sce) {
    $http.get('/character/54dbc0e4646174009a000000').
        success(function(data) {
            $scope.char = data;
            $scope.char.description = $sce.trustAsHtml($scope.char.description);
        });
});