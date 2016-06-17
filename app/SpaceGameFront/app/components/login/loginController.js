angular.module("SpaceGame.LoginModule", [])
    .controller("LoginController", ["$scope", "$http", "ApiService", '$state', "$cookies", "AuthService",
        function ($scope, $http, ApiService, $state, $cookies, AuthService) {

            $scope.username = "";
            $scope.password = "";

            $scope.login = function () {

                var data = {
                    "rawPassword": $scope.password,
                    "nickname": $scope.username
                };

                AuthService.auth(data)
                    .then(function () {
                        $state.go("root.home");
                    });
            }

        }]);