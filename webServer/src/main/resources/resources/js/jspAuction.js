angular
		.module('jspAuction', [ 'ngRoute','ngCookies','ngCookies','ngResource', 'secure-rest-angular', 'home', 'message', 'navigation' ])
		.provider('myCSRF',[function(){
           var headerName = 'X-CSRF-TOKEN';
           var cookieName = 'CSRF-TOKEN';
           var allowedMethods = ['GET','OPTIONS','POST'];

           this.setHeaderName = function(n) {
             headerName = n;
           }
           this.setCookieName = function(n) {
             cookieName = n;
           }
           this.setAllowedMethods = function(n) {
             allowedMethods = n;
           }
           this.$get = ['$cookies', function($cookies){
             return {
               'request': function(config) {
                 if(allowedMethods.indexOf(config.method) === -1) {
                   config.headers[headerName] = $cookies[cookieName];
                 }
                 console.log('Obtained config', config.headers[headerName]);
                 return config;
               }
             }
           }];
         }]).config(function($routeProvider, $httpProvider, $locationProvider) {
					$locationProvider.html5Mode(true);

					$routeProvider.when('/', {
						templateUrl : 'js/home/home.html',
						controller : 'home',
						controllerAs : 'controller'
					}).when('/message', {
						templateUrl : 'js/message/message.html',
						controller : 'message',
						controllerAs : 'controller'
					}).when('/login', {
						templateUrl : 'js/navigation/login.html',
						controller : 'navigation',
						controllerAs : 'controller'
					}).otherwise('/');

                    $httpProvider.interceptors.push('myCSRF');
                    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
                }).run(function(Login) {
                    Login.init('/', '/login', '/logout');
                });