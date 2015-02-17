var chardbApp = angular.module('chardbApp', ['textAngular', 'ngImgur']);

chardbApp.controller('CharacterController', function ($scope, $http, $sce) {
	var id= $("#charId").val();
    $http.get('/character/' + id).
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
        saveCharacter.id = $("#charId").val();
        $http.post('/character/' + id, saveCharacter).
            success(function (data, status, headers, config) {
            	alert("Save success");
            }).
            error(function (data, status, headers, config) {
            	alert("Save failure");
            });
    }
});

chardbApp.controller('RegistrationController', function ($scope, $http, $sce) {
    $scope.submit = function () {
        var saveUser = {};
        saveUser.email = $scope.email;
        saveUser.password = $scope.password;
        
        $http.post('/api/user', saveUser).
            success(function (data, status, headers, config) {
            	window.location.href = "/login";
            }).
            error(function (data, status, headers, config) {
            	alert("A user with that email already exists");
            });
    }
});

chardbApp.config(['$provide', function($provide) {
    // this demonstrates how to register a new tool and add it to the default toolbar
    $provide.decorator('taOptions', ['$delegate', function(taOptions) {
        // $delegate is the taOptions we are decorating
        // here we override the default toolbars and classes specified in taOptions.
        taOptions.toolbar = [
            ['h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'p', 'pre', 'quote'],
            ['bold', 'italics', 'underline', 'ul', 'ol', 'redo', 'undo', 'clear'],
            ['justifyLeft','justifyCenter','justifyRight'],
        ];
        return taOptions; // whatever you return will be the taOptions
    }]);
}]);

chardbApp.controller('UserController', function ($scope, $http, $sce) {

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

