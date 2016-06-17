angular.module("SpaceGame.AuthModule", [])
    .service("AuthService", ["$state", "$cookies", "$http", "ApiService", function ($state, $cookies, $http, ApiService) {

        return {
            "auth": function () {
                if (typeof this.getToken() === "undefined" || this.getUsername() === "undefined") {
                    $state.go("login");
                }
                else {
                    var data = {
                        "token": this.getToken(),
                        "nickname": this.getUsername()
                    };

                    $http({
                        method: "POST",
                        url: ApiService.resources,
                        data: JSON.stringify(data),
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }).then(function () {
                    }, function () {
                        $state.go("login");
                    });
                }
            },
            "notLogged": function () {
                $state.go("login");
            }

            ,
            "getToken": function () {
                return $cookies.get("token");
            }

            ,
            "getUsername": function () {
                return $cookies.get("username");
            }
        }
    }
    ]);