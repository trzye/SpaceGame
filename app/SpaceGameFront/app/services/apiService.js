angular.module("SpaceGame.ApiModule", [])
    .service("ApiService", [function() {

        var domain = "http://localhost:8080";
        
        return {
            "register" : domain + "/signUp",
            "login" : domain + "/signIn",
            "map" : domain + "/map",
            "resources" : domain + "/myResources"
        }
    }]);