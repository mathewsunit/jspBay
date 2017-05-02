angular.module('navigation', ['ngRoute', 'secure-rest-angular']).controller(
		'navigation',

		function($route, Login) {

			var self = this;

			self.credentials = {};

			self.username = function() {
                return Login.username;
            };

			self.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			self.authenticated = function() {
				return Login.authenticated;
			}

			self.login = function() {
				Login.authenticate(self.credentials, function(authenticated) {
					if (authenticated) {
						self.error = false;
					} else {
						self.error = true;
					}
				})
			};

			self.logout = function() {
                console.log('Log out');
                Login.clear('',function(authenticated) {
                    if (authenticated) {
                        self.error = false;
                    } else {
                        self.error = true;
                    }
                })
            };
});