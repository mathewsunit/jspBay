angular.module('home', ['secure-rest-angular']).controller('home', function($cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	$scope.onSaleItems = [];
	$scope.soldItems = [];
	$scope.activeBids = [];
	$scope.closedBids = [];

	var userCall = $resource('/user', {}, {
        get: {method: 'GET', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

    var itemCall = $resource('/items/seller/:seller', {}, {
        get: {method: 'GET', cache: false, isArray: true},
        options: {method: 'OPTIONS', cache: false}
    });

    var bidCall = $resource('/bids/bidder/:bidder', {}, {
        get: {method: 'GET', cache: false, isArray: true},
        options: {method: 'OPTIONS', cache: false}
    });

	userCall.get().$promise.then(function(response) {
        //console.log('GET user returned: ', response);
		self.user = response.name;
		itemCall.query({seller: self.user}).$promise.then(function(response) {
            console.log('GET item returned: ', response);
            for(var obj in response) {
                console.log("obj is ",response[obj].itemStatus);
                if(response[obj].itemStatus === 'ONSALE') {
                    $scope.onSaleItems.push(response[obj]);
                } else if(response[obj].itemStatus === 'SOLD') {
                    $scope.soldItems.push(response[obj]);
                }
            }
            //console.log($scope.onSaleItems);
        });
        bidCall.query({bidder: self.user}).$promise.then(function(response) {
            console.log('GET bid returned: ', response);
            for(var obj in response) {
                console.log("obj is ",response[obj].itemStatus);
                if(response[obj].bidStatus === 'LEADING') {
                    $scope.activeBids.push(response[obj]);
                } else if(response[obj].itemStatus === 'ACCEPTED' || response[obj].itemStatus === 'REJECTED') {
                    $scope.closedBids.push(response[obj]);
                }
            }
            console.log('Active bids: ', $scope.activeBids);
        });
	});

	$scope.viewItem = function(item)
    {
       console.log(item.itemId);
       $location.path('/item/:itemid')
    };
});