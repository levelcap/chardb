var chardbApp = angular.module('chardbApp', ['textAngular']);

chardbApp.controller('CharacterController', function ($scope, $http, $sce) {
    $http.get('/character/54dbc0e4646174009a000000').
        success(function(data) {
            $scope.char = data;
            $scope.char.description = $sce.trustAsHtml($scope.char.description);
            $scope.orightml = $scope.char.description;
            $scope.htmlcontent = $scope.orightml;
            $scope.disabled = false;
        });
});

