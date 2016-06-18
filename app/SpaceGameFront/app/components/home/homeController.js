angular.module("SpaceGame.HomeModule", [])
    .controller("HomeController", ["$scope", "AuthService", "DataService", "resources", "ModalService",
        function ($scope, AuthService, DataService, resources, ModalService) {

            $scope.resources = resources;
            $scope.buildings = [];
            $scope.fleet = [];
            $scope.fleet_status = 0;
            $scope.planetInfo = {};
            $scope.name = "";
            $scope.map = [];
            $scope.selectedTargetPlanet = {};

            DataService.getBuildings().then(function (response) {
                $scope.buildings = response.data;

                for (var i = 0; i < $scope.buildings.length; i++) {

                    var buildingName = "";

                    switch ($scope.buildings[i].typeId) {
                        case 0:
                            buildingName = "Kopalnia Gadolinu";
                            break;
                        case 1:
                            buildingName = "Ekstraktor Ununtrium";
                            break;
                        case 2:
                            buildingName = "Hangar";
                            break;
                        case 3:
                            buildingName = "Systemy obronne";
                            break;
                        default:
                            buildingName = "Budynek";
                            break;
                    }
                    $scope.buildings[i].buildingName = buildingName;
                }
            });

            DataService.getMyPlanet().then(function (response) {
                $scope.planetInfo = response.data;
                $scope.name = "Planeta[" + $scope.planetInfo.coordinateX + "|" + $scope.planetInfo.coordinateY + "]";
            });

            DataService.getMap().then(function success(response) {
                var tds = $(".table-map td");

                $scope.map = response.data;

                for (var i = 0; i < $scope.map.length; i++) {
                    var planet = $scope.map[i];
                    var td = tds[planet.coordinateX * 10 + planet.coordinateY];

                    $(td).addClass("planet");
                    $(td).addClass("locked-planet");
                    $(td).text("");


                    if ($scope.planetInfo.coordinateX == planet.coordinateX && $scope.planetInfo.coordinateY == planet.coordinateY) {
                        $(td).addClass("user-planet");
                    } else {
                        $(td).attr("data-dismiss", "modal");
                    }

                }

            });

            $scope.canAttack = function (x, y) {
                for (var i = 0; i < $scope.map.length; i++) {
                    var planet = $scope.map[i];
                    if (planet.coordinateX == x && planet.coordinateY == y)
                        if (!($scope.planetInfo.coordinateX == x && $scope.planetInfo.coordinateY == y))
                            return true;
                }
                return false;
            };

            $scope.choosePlanet = function (x, y) {

                if (!$scope.canAttack(x, y)) return;

                $scope.selectedTargetPlanet.x = x;
                $scope.selectedTargetPlanet.y = y;
                $scope.selectedTargetPlanet.name = "planeta[" + x + "|" + y + "]";
            };

            var getFleet = function () {
                DataService.getFleet().then(function (response) {
                    $scope.fleet_status = response.data.status;
                    $scope.fleet = response.data.ships;

                    for (var i = 0; i < $scope.fleet.length; i++) {
                        switch ($scope.fleet[i].typeId) {
                            case 0:
                                $scope.fleet[i].name = 'Okręt wojenny';
                                break;
                            case 1:
                                $scope.fleet[i].name = "Bombowiec";
                                break;
                            case 2:
                                $scope.fleet[i].name = "Pancernik";
                                break;
                            default:
                                $scope.fleet[i].name = "Statek";
                                break;
                        }
                        $scope.fleet[i].buildNumber = 0;
                    }
                });
            };

            getFleet();


            $scope.levelUp = function (typeId) {
                DataService.upgradeBuilding(typeId).then(function (response) {
                    ModalService.openModalInfo("OK", response.data);
                    DataService.getResources().then(function (response) {
                        resources.gadolin = response.data.gadolin;
                        resources.ununtrium = response.data.ununtrium;
                    })

                }, function (response) {
                    ModalService.openModalInfo("OK", response.data);
                });
            };

            $scope.buildShip = function (typeId, number) {
                DataService.buildShips(typeId, number).then(function (response) {
                    ModalService.openModalInfo("OK", response.data);
                    DataService.getResources().then(function (response) {
                        resources.gadolin = response.data.gadolin;
                        resources.ununtrium = response.data.ununtrium;
                    });

                    getFleet();
                })
            }
        }])
;