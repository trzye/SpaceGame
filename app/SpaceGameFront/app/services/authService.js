angular.module("SpaceGame.AuthModule", [])
    .service("AuthService", ["$state", "$cookies", function($state, $cookies) {

        return {
            "auth" : function () {
                if(typeof $cookies.get("token") == "undefined") $state.go("login");
            },
            "notLogged" : function() {
                $state.go("login");
            },
            "getToken" : function () {
                return $cookies.get("token");
            },
            "getUsername" : function () {
                return $cookies.get("username");
            }
        }
    }]);