angular.module("SpaceGame.RegisterModule", [])
    .controller("RegisterController", ["$scope", function ($scope) {

        $.backstretch([
            "resources/img/bg-login-1.jpg",
            "resources/img/bg-login-2.jpg"
        ], {
            fade: 1000,
            duration: 7000
        });

        $scope.selectedPlanet = {
            "name": "nie wybrano",
            "x": -1,
            "y": -1
        }
        

    }]);