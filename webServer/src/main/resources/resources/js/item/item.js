angular.module('item', ['secure-rest-angular']).controller('item', function($routeParams, $cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	$scope.item = {};
	$scope.errorMessage = null;
    console.log($location.path());
    if($location.path() == '/item/postItem') {
        console.log("In Post item clicked.");
        var itemCreateCall = $resource('/items/create/', {}, {
            post: {method: 'POST', cache: false, isArray: false},
            options: {method: 'OPTIONS', cache: false}
        });
        $scope.createItem = function(item1) {
            console.log("Create item clicked.");
            var deadlineTs = new Date(item1.deadline).getTime();
            itemCreateCall.post({itemName : item1.itemName, itemDesc : item1.desc, itemDeadline : deadlineTs, itemCost : item1.costAmount}).$promise.then(function(response) {
                console.log("Item create Response : " + response);
                if(response.itemId == -1) {
                    console.log("Got error message");
                    $scope.errorMessage = response.errorMsg;
                } else {
                    console.log("Item creation Success.");
                    $location.path('/item/' + response.id);
                }
            });
        }
    } else {
        console.log("Not in Post item clicked.");
        var itemCall = $resource('/items/:itemId', {}, {
            get: {method: 'GET', cache: false, isArray: false},
            options: {method: 'OPTIONS', cache: false}
        });

        var bidCall = $resource('/items/bid/', {}, {
            post: {method: 'POST', cache: false, isArray: false},
            options: {method: 'OPTIONS', cache: false}
        });
        itemCall.get({itemId : $routeParams.itemId}).$promise.then(function(response) {
            $scope.item = response;
        });
        $scope.sendForm = function(amount) {
            console.log("Send form clicked");
            /*
            var csrfToken = Cookies.getFromDocument($http.defaults.xsrfCookieName);
            // Prepare the headers
            var headers = {
              'Content-Type': 'application/x-www-form-urlencoded'
            };
            headers[$http.defaults.xsrfHeaderName] = csrfToken;
            console.log($http.defaults.xsrfHeaderName + ":" + csrfToken)
            $http.post('/items/bid', {itemId : $routeParams.itemId, bidAmount : amount}, {headers: headers}).then(function() {
                console.log("Bid Response : " + response);
                if(response.id == -1)
                    $scope.errorMessage = response.errorMessage;
                else
                    $location.path('/item/' + $routeParams.itemId);
            });
            */
            bidCall.post({'itemNumber' : $routeParams.itemId, 'bidAmount' : amount}).$promise.then(function(response) {
                console.log("Bid Response : " + response);
                if(response.id == -1) {
                    console.log("Got error message");
                    $scope.errorMessage = response.errorMessage;
                } else {
                    console.log("Bid Success.");
                    $location.path('/item/' + $routeParams.itemId);
                }
            });
        }
    }
});