angular.module("SpaceGame.ApiModule", [])
    .service("ApiService", [function() {

        var domain = "http://localhost:8080";
        
        return {
            "register" : domain + "/signUp",
            "login" : domain + "/login"
        }
    }]);