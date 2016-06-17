angular.module("SpaceGame.ModalModule", [])
    .service("ModalService", ['$uibModal', function($uibModal) {

        var open = function (type, message) {
            $uibModal.open({
                animation: true,
                templateUrl: 'modals/infoModal.html',
                controller: 'ModalInfoController',
                resolve: {
                    message: function() {
                        return message;
                    },
                    type: function() {
                        return type;
                    }
                }
            });
        };
        
        return {
            openModalInfo : open
        }
    }]);