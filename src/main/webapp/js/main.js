var chardbApp = angular.module('chardbApp', ['textAngular', 'ngImgur']);

chardbApp.controller('CharacterController', function ($scope, $http, $sce) {
    $http.get('/character/54dbc0e4646174009a000000').
        success(function (data) {
            $scope.char = data;
            $scope.char.description = $sce.trustAsHtml($scope.char.description);
            $scope.orightml = $scope.char.description;
            $scope.htmlcontent = $scope.orightml;
            $scope.disabled = false;
        });

    $scope.save = function () {
        var saveCharacter = $scope.char;
        saveCharacter.description = $scope.htmlcontent.toString();
        $http.post('/character/54dbc0e4646174009a000000', saveCharacter).
            success(function (data, status, headers, config) {
                // this callback will be called asynchronously
                // when the response is available
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    }
});

chardbApp.directive('drop', function ($timeout, imgur) {
    return {
        restrict: 'EAC',
        scope: false,
        link: function link($scope, element) {
            imgur.setAPIKey('Client-ID 7ad9e6431502c89');
            $scope.link = '';
            $scope.message = 'Drop Image...';
            $scope.preventDefaultBehaviour = function preventDefaultBehaviour(event) {
                event.preventDefault();
                event.stopPropagation();
            };
            element.on('drop', function onDrag(event) {
                $scope.preventDefaultBehaviour(event);
                var image = event.dataTransfer.files[0];
                $scope.message = 'Uploading Image...';

                imgur.upload(image).then(function then(model) {

                    $scope.link = model.link;
                    $scope.char.url = model.link;
                    $scope.message = 'Uploaded Image!';

                    $timeout(function timeout() {
                        $scope.message = 'Drop Image...';
                    }, 2500);

                });

            });
            // Prevent the default behaviour on certain events.
            element.on('dragover dragend dragleave', $scope.preventDefaultBehaviour);
        }
    };
});

