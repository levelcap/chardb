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

    $scope.save = function() {
        var saveCharacter = $scope.char;
        saveCharacter.description = $scope.htmlcontent.toString();
        $http.post('/character/54dbc0e4646174009a000000', saveCharacter).
            success(function(data, status, headers, config) {
                // this callback will be called asynchronously
                // when the response is available
            }).
            error(function(data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    }
});

