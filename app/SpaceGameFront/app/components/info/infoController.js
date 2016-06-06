angular.module("SpaceGame.InfoModule", [])
    .controller("InfoController", ["$scope", "$stateParams", "$state", function ($scope, $stateParams, $state) {

        $.backstretch([
            "resources/img/bg-login-1.jpg",
            "resources/img/bg-login-2.jpg"
        ], {
            fade: 1000,
            duration: 7000
        });

        $scope.message = $stateParams.message;
        $scope.panel_class = "panel-default";

        if($scope.message == "") $state.go("login");
        
        switch ($stateParams.type) {
            case "OK":
                $scope.panel_class = "panel-success";
                break;
            case "ERROR":
                $scope.panel_class = "panel-danger";
                break;
        }
        
    }]);