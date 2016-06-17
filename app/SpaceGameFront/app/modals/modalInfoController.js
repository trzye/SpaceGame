angular.module("SpaceGame.ModalCtrlModule", [])
    .controller("ModalInfoController", ["$scope", "$uibModalInstance", "type", "message",
        function ($scope, $uibModalInstance, type, message) {

            $scope.type = type;
            $scope.message = message;
            
            $scope.ok = function () {
                $uibModalInstance.close();
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };

        }]);