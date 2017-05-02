angular.module('search', ['secure-rest-angular','smart-table']).controller('search', function($routeParams,$cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	$scope.items = [];

    var searchCall = $resource('/search/:search', {}, {
        get: {method: 'GET', cache: true, isArray: true},
        options: {method: 'OPTIONS', cache: false}
    });

    searchCall.query({search: $routeParams.search}).$promise.then(function(response) {
       console.log('GET item returned: ', response);
       for(var obj in response) {
           console.log("obj is ",response[obj].itemStatus);
           if(response[obj].itemStatus === 'ONSALE') {
               $scope.items.push(response[obj]);
           } else if(response[obj].itemStatus === 'SOLD') {
               $scope.items.push(response[obj]);
           }
       }
   });
});