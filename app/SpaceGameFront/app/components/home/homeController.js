angular.module("SpaceGame.HomeModule", [])
    .controller("HomeController", ["$scope", "AuthService", "DataService", "resources",
        function ($scope, AuthService, DataService, resources) {

            // AuthService.auth();
            $scope.resources = resources;

            DataService.getFleet().then(function(response) {
                console.log("dziala");
            }, function (response) {
                console.log("dziala");
            });


        }]);