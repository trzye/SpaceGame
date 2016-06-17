angular.module("SpaceGame.HomeModule", [])
    .controller("HomeController", ["$scope", "AuthService", "DataService", "resources", "ModalService",
        function ($scope, AuthService, DataService, resources, ModalService) {

            $scope.resources = resources;
            $scope.buildings = [];
            $scope.fleet = [];

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
            

            $scope.levelUp = function (typeId) {
                var flag = false;
                DataService.upgradeBuilding(typeId).then(function (response) {
                    ModalService.openModalInfo("OK", response.data);
                    DataService.getResources().then(function(response) {
                        resources.gadolin = response.data.gadolin;
                        resources.ununtrium = response.data.ununtrium;
                    })

                }, function (response) {
                    ModalService.openModalInfo("OK", response.data);
                });
            }
        }]);