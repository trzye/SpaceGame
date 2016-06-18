angular.module("SpaceGame.AuthModule", [])
    .service("AuthService", ["$state", "$cookies", "$http", "ApiService", "$q", function ($state, $cookies, $http, ApiService, $q) {

        return {
            "auth": function (data) {

                var deferred = $q.defer();

                $http({
                    method: "POST",
                    url: ApiService.login,
                    data: data,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(function (response) {
                    $cookies.put("token", response.data.token);
                    $cookies.put("username", response.data.nickname);
                    deferred.resolve();
                }, function () {
                    deferred.reject();
                });
                return deferred.promise;
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
            },
            logout: function () {
                $cookies.remove("token");
                $cookies.remove("username");
                $state.go("login");
            }
        }
    }
    ]);