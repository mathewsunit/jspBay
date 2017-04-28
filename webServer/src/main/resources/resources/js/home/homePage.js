angular.module('home', ['secure-rest-angular']).controller('home', function($cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	var userCall = $resource('/user', {}, {
        get: {method: 'GET', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

    var itemCall = $resource('/items/seller/:seller', {}, {
        get: {method: 'GET', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

    var bidCall = $resource('/bids/bidder/:bidder', {}, {
        get: {method: 'GET', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

	userCall.get().$promise.then(function(response) {
        console.log('GET user returned: ', response);
		self.user = response.name;
		itemCall.query({seller: self.user}).$promise.then(function(response) {
            console.log('GET item returned: ', response);
            self.items = response.name;
        });
        bidCall.query({bidder: self.user}).$promise.then(function(response) {
            console.log('GET item returned: ', response);
            self.bids = response.name;
        });
	});
});