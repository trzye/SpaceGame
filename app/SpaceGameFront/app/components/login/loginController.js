angular.module("SpaceGame.LoginModule", [])
    .controller("LoginController", ["$scope", function ($scope) {

        $.backstretch([
            "resources/img/bg-login-1.jpg",
            "resources/img/bg-login-2.jpg"
        ], {
            fade: 1000,
            duration: 7000
        });

        $scope.username = "";
        $scope.password = "";

        $scope.login = function() {
            console.log($scope.username);
        }

    }]);