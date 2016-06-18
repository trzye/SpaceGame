angular.module("SpaceGame.HeaderModule", [])
    .controller("HeaderController", ["$scope", "resources","AuthService", function ($scope, resources, AuthService) {

        $scope.resources = resources;

        $scope.logout = function () {
            AuthService.logout();
        }

    }]);