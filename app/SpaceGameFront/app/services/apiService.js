angular.module("SpaceGame.ApiModule", [])
    .service("ApiService", [function() {

        var domain = "http://localhost:8080";
        
        return {
            "register" : domain + "/signUp",
            "login" : domain + "/signIn",
            "map" : domain + "/map",
            "myResources" : domain + "/myResources",
            "myBuildings" : domain + "/myBuildings",
            "myFleet" : domain + "/myfleet",
            "myPlanet" : domain + "/myPlanet",
            "attackHistory" : domain + "/attackHistory",
            "allianceHistory" : domain + "/allianceHistory",
            "upgradeBuilding" : domain + "/upgradeBuilding",
            "buildShips" : domain + "/buildShips",
            "attack" : domain + "/attack",
            "help" : domain + "/help",
            "getBackFleet" : domain + "/getBackFleet",
            "incomingAttacksAndAlliances" : domain + "/incomingAttacksAndAlliances",
            "alliancesOnMyPlanet" : domain + "/alliancesOnMyPlanet",
            "otherPlanet" : domain + "/otherPlanet"
        }
    }]);