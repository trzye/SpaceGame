angular.module("SpaceGame.LoginModule", [])
    .controller("LoginController", ["$scope", "$http", "ApiService", '$state', "$cookies", function ($scope, $http, ApiService, $state, $cookies) {

        $scope.username = "";
        $scope.password = "";

        $scope.login = function() {

            var data = {
                "rawPassword": $scope.password,
                "nickname": $scope.username
            };

            $http({
                method: 'POST',
                url: ApiService.login,
                data: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                $cookies.put("token", response.data.token);
                $cookies.put("username", response.data.nickname);
                $state.go("root.home");
            }, function error(response) {

            });

        }

    }]);