angular.module("SpaceGame.RegisterModule", [])
    .controller("RegisterController", ["$scope", "$http", "ApiService", "$state", function ($scope, $http, ApiService, $state) {

        $.backstretch([
            "resources/img/bg-login-1.jpg",
            "resources/img/bg-login-2.jpg"
        ], {
            fade: 1000,
            duration: 7000
        });

        $scope.username = "";
        $scope.password = "";
        $scope.passwordRepeat = "";
        $scope.email = "";
        $scope.error = [];
        $scope.map = [];

        $scope.selectedPlanet = {
            "name": "nie wybrano",
            "x": -1,
            "y": -1
        };

        $http({
            method: "GET",
            url: ApiService.map,
            headers: {
                'Content-Type' : 'application/json'
            }
        }).then(function success(response) {
            $scope.map = response.data;
        });

        $scope.register = function() {

            $scope.error = [];

            if ($scope.password.length < 3) {
                $scope.error.push("Hasło powinno być dłuższe niż 3 znaki!")
            }

            if ($scope.password != $scope.passwordRepeat) {
                $scope.error.push("Hasła nie są takie same");
            }

            if ($scope.username.length < 3) {
                $scope.error.push("Nazwa użytkownika powinna mieć więzej niż 3 zanki!");
            }

            if ($scope.error.length == 0) {
                var data = {
                    "nickname" : $scope.username,
                    "rawPassword": $scope.password,
                    "email": $scope.email,
                    "coordinateX" : "1",
                    "coordinateY" : "3"
                };

                $http({
                    method: 'POST',
                    url: ApiService.register,
                    data: JSON.stringify(data),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(function success(response){
                    $state.go("info", {"type" : "OK", "message" : response.data});
                }, function error(response) {
                    $state.go("info", {"type" : "ERROR", "message" : response.data});
                });
            }
        }
        
        $scope.choosePlanet = function(x, y) {
            $scope.selectedPlanet.x = x;
            $scope.selectedPlanet.y = y;
            $scope.selectedPlanet.name = "planeta[" + x + "|" + y + "]";
        }

    }]);