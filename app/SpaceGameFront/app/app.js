'use strict';

angular.module('SpaceGame', [
        'ui.router',
        'SpaceGame.ApiModule',
        'SpaceGame.HeaderModule',
        'SpaceGame.LoginModule',
        'SpaceGame.RegisterModule',
        'SpaceGame.HomeModule',
        'SpaceGame.InfoModule'

    ])
    .config(["$stateProvider", "$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
        $stateProvider
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
                    "type" : "",
                    "message" : ""
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
            .state('header', {
                views: {
                    'header@': {
                        templateUrl: "components/header/header.html",
                        controller: "HeaderController"
                    }
                }
            })
            .state('header.home', {
                url: "/home",
                views: {
                    'content@': {
                        templateUrl: "components/home/home.html",
                        controller: "HomeController"
                    }
                }
            })
    }]);