'use strict';

var spaceGameApp = angular.module('SpaceGame', [
        'ui.router',
        'ngCookies',
        'SpaceGame.ApiModule',
        'SpaceGame.HeaderModule',
        'SpaceGame.LoginModule',
        'SpaceGame.RegisterModule',
        'SpaceGame.HomeModule',
        'SpaceGame.InfoModule'

    ])
    .config(["$stateProvider", "$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('root', {
                resolve: {
                    'auth': function ($state, $cookies) {
                        console.log('root state')
                    }
                }
            })
            .state('root.login', {
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
            .state('root.register', {
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
                url: "/",
                views: {
                    'content@': {
                        templateUrl: "components/home/home.html",
                        controller: "HomeController"
                    }
                }
            })
    }]);