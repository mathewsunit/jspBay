angular.module('item', ['secure-rest-angular']).controller('item', function($routeParams, $cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	$scope.item = {};

    var itemCall = $resource('/items/1', {}, {
        get: {method: 'GET', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

    var formCall = $resource('/items/bid/1', {}, {
        get: {method: 'POST', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

//    itemCall.query({itemId: $routeParams.itemId}).$promise.then(function(response) {
//        console.log('GET item returned: ', response);
//    });

    itemCall.query().$promise.then(function(response) {
            console.log('GET item returned: ', response);
        });
});