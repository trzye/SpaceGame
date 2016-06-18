'use strict';

var spaceGameApp = angular.module('SpaceGame', [
        'ui.router',
        'ngCookies',
        'ui.bootstrap',
        'SpaceGame.ApiModule',
        'SpaceGame.AuthModule',
        'SpaceGame.DataModule',
        'SpaceGame.ModalModule',
        'SpaceGame.ModalCtrlModule',
        'SpaceGame.HeaderModule',
        'SpaceGame.LoginModule',
        'SpaceGame.RegisterModule',
        'SpaceGame.HomeModule',
        'SpaceGame.InfoModule'

    ])
    .config(["$stateProvider", "$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('root', {
                views: {
                    'header@': {
                        templateUrl: "components/header/header.html",
                        controller: "HeaderController"
                    }
                },
                resolve: {
                    "resources": ['DataService', '$q', '$state', '$cookies', function (DataService, $q, $state, $cookies) {
                        var deferred = $q.defer();
                        DataService.getResources().then(function (response) {
                            deferred.resolve(response.data);
                        }, function (reponse) {
                            $state.go("login");
                            deferred.reject("Error");
                        });
                        return deferred.promise;
                    }]
                }
            })
            .state('login', {
                url: "/login",
                views: {
                    'content@': {
                        templateUrl: "components/login/login.html",
                        controller: "LoginController"
                    }
                }
            })
            .state('info', {
                url: "/info",
                views: {
                    'content@': {
                        templateUrl: "components/info/info.html",
                        controller: "InfoController"
                    }
                },
                params: {
                    "type": "",
                    "message": ""
                }
            })
            .state('register', {
                url: "/register",
                views: {
                    'content@': {
                        templateUrl: "components/register/register.html",
                        controller: "RegisterController"
                    }
                }
            })
            .state('root.home', {
                url: "/",
                views: {
                    'content@': {
                        templateUrl: "components/home/home.html",
                        controller: "HomeController"
                    }
                }
            })
    }]);
