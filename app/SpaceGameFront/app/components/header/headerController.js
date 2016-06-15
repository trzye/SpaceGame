angular.module("SpaceGame.HeaderModule", [])
    .controller("HeaderController", ["$scope", "$cookies", "$state", function ($scope, $cookies, $state) {

        if(typeof $cookies.get("token") == "undefined") $state.go("login");

    }]);