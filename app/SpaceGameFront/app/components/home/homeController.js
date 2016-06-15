angular.module("SpaceGame.HomeModule", [])
    .controller("HomeController", ["$scope", "AuthService", "ApiService", "$http", function ($scope, AuthService, ApiService, $http) {

        AuthService.auth();

        $scope.resources = {};

        var data = {
            "token": AuthService.getToken(),
            "nickname": AuthService.getUsername()
        };

        $http({
            method: "POST",
            url: ApiService.resources,
            data: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.resources = response.data;
        }, function (response) {
            if (response.status == 401 || response.status == 403) {
                AuthService.notLogged();
            }
        });


    }]);