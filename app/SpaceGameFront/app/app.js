'use strict';

angular.module('SpaceGame', [
      'ui.router',
      'SpaceGame.HeaderModule',
      'SpaceGame.LoginModule',
      'SpaceGame.HomeModule'
    ])
    .config(["$stateProvider", "$urlRouterProvider", function($stateProvider, $urlRouterProvider) {
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