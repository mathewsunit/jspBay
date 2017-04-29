angular.module('home', ['secure-rest-angular']).controller('home', function($routeParams, $cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	$scope.item = {};

    var itemCall = $resource('/items/:itemId', {}, {
        get: {method: 'GET', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

    var formCall = $resource('/items/bid/:itemId', {}, {
        get: {method: 'POST', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

    itemCall.query({itemId: $routeParams.itemId}).$promise.then(function(response) {
        console.log('GET item returned: ', response);
    });
});