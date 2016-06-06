angular.module("SpaceGame.RegisterModule", [])
    .controller("RegisterController", ["$scope", "$http", "ApiService", function ($scope, $http, ApiService) {

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

        $scope.selectedPlanet = {
            "name": "nie wybrano",
            "x": -1,
            "y": -1
        };

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

                // console.log(JSON.stringify(data));

                $http({
                    method: 'POST',
                    url: ApiService.register,
                    data: JSON.stringify(data),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(function success(response){
                    console.log(response);
                }, function error(response) {
                    console.log(response);
                });
            }
        }
        

    }]);