angular.module('home', ['secure-rest-angular','smart-table']).controller('home', function($cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	$scope.onSaleItems = [];
	$scope.onSaleItemsDisplay = [];
    $scope.soldItems = [];
	$scope.soldItemsDisplay = [];
    $scope.activeBids = [];
	$scope.closedBids = [];
    $scope.activeBidsDisplay = [];
	$scope.closedBidsDisplay = [];
	$scope.loginDetails = [];

	$scope.activebidarray = [];
    $scope.closedbidarray = [];


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

    var lastlogResources = $resource('/user/lastlogin/retrieve', {}, {
        get: {
        method: 'get',
        cache: false
      }
    });

    var removeItemResource = $resource('/user/lastlogin/retrieve', {}, {
        post: {
        method: 'post',
        cache: false
      }
    });

    var removeBidResource = $resource('/user/lastlogin/retrieve', {}, {
        post: {
        method: 'post',
        cache: false
      }
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
            $scope.onSaleItemsDisplay = $scope.onSaleItems;
            $scope.soldItemsDisplay = $scope.soldItems;
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
            $scope.activeBidsDisplay = $scope.activeBids;
            $scope.closedBidsDisplay = $scope.closedBids;
            console.log('Active bids: ', $scope.activeBids);
        });
	});

    $scope.viewItem = function(item) {
       $location.path('/item/' + item.itemId);
    };

    $scope.postItem = function() {
       $location.path('/item/postItem');
    };

    	lastlogResources.get().$promise.then(function(response) {
    	    console.log('GET login returned: ', response);
    	    $scope.loginDetails = response;
    	},function(){
    	});

    	$scope.viewItem = function(item)
        {
           $location.path('/item/' + item.itemId);
        };

        $scope.search = function()
        {
        if ($scope.searchVal) {
                $location.path('/search/' + $scope.searchVal);
            }
        };

        $scope.keyPress = function(keyCode){
           console.log(keyCode);
          if(keyCode == 13)
            {
              if ($scope.searchVal) {
                  $location.path('/search/' + $scope.searchVal);
              }
            }
        };

        $scope.removeItem = function(itemR)
        {
            console.log('You are goung to remove ',itemR)
        };

        $scope.removeBid = function(bidR)
        {
            console.log('You are goung to remove ',bidR)
        };
});