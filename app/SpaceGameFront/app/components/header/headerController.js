angular.module("SpaceGame.HeaderModule", [])
    .controller("HeaderController", ["$scope", "resources", function ($scope, resources) {

        $scope.resources = resources;
        console.log(resources);

    }]);