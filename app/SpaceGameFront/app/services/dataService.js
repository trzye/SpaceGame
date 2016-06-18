angular.module("SpaceGame.DataModule", [])
    .service("DataService", ["$state", "$http", "ApiService", "AuthService", function ($state, $http, ApiService, AuthService) {

        var authData = {
            "token": AuthService.getToken(),
            "nickname": AuthService.getUsername()
        };

        var getHttp = function (data, url) {
            return $http({
                method: "POST",
                url: url,
                data: data,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };

        var getMap = function () {
            return $http({
                method: "GET",
                url: ApiService.map,
                headers: {
                    'Content-Type': 'application/json'
                }
            })
        };

        var getResources = function () {
            return getHttp(authData, ApiService.myResources);
        };

        var getBuildings = function () {
            return getHttp(authData, ApiService.myBuildings);
        };

        var getFleet = function () {
            return getHttp(authData, ApiService.myFleet);
        };

        var getMyPlanet = function () {
            return getHttp(authData, ApiService.myPlanet);
        };

        var getAttackHistory = function () {
            return getHttp(authData, ApiService.attackHistory);
        };

        var getAllianceHistory = function () {
            return getHttp(authData, ApiService.allianceHistory);
        };

        var upgradeBuilding = function (typeId) {
            var data = {
                "authenticationData": authData,
                "typeId": typeId
            };
            return getHttp(data, ApiService.upgradeBuilding);
        };

        var buildShips = function (typeId, number) {
            var data = {
                "authenticationData": authData,
                "typeId": typeId,
                "number": number
            };
            return getHttp(data, ApiService.buildShips);
        };

        var attack = function (coordinateX, coordinateY) {
            var data = {
                "authenticationData": authData,
                "coordinateX": coordinateX,
                "coordinateY": coordinateY
            };
            return getHttp(data, ApiService.attack);
        };

        var help = function (coordinateX, coordinateY) {
            var data = {
                "authenticationData": authData,
                "coordinateX": coordinateX,
                "coordinateY": coordinateY
            };
            return getHttp(data, ApiService.help);
        };

        var getBackFleet = function () {
            return getHttp(authData, ApiService.getBackFleet);
        };

        var incomingAttacksAndAlliances = function () {
            return getHttp(authData, ApiService.incomingAttacksAndAlliances);
        };

        var alliancesOnMyPlanet = function () {
            return getHttp(authData, ApiService.alliancesOnMyPlanet);
        };

        var otherPlanet = function (coordinateX, coordinateY) {
            var data = {
                "authenticationData": authData,
                "coordinateX": coordinateX,
                "coordinateY": coordinateY
            };
            return getHttp(data, ApiService.otherPlanet);
        };

        var outgoingAttacksAndAlliances = function () {
            return getHttp(authData, ApiService.outgoingAttacksAndAlliances);
        };

        return {
            getMap: getMap,
            getResources: getResources,
            getBuildings: getBuildings,
            getFleet: getFleet,
            getMyPlanet: getMyPlanet,
            getAttackHistory: getAttackHistory,
            getAllianceHistory: getAllianceHistory,
            upgradeBuilding: upgradeBuilding,
            buildShips: buildShips,
            attack: attack,
            help: help,
            getBackFleet: getBackFleet,
            incomingAttacksAndAlliances: incomingAttacksAndAlliances,
            alliancesOnMyPlanet: alliancesOnMyPlanet,
            otherPlanet: otherPlanet,
            outgoingAttacksAndAlliances: outgoingAttacksAndAlliances
        }
    }]);