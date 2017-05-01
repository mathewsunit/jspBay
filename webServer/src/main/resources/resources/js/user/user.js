angular.module('user', ['secure-rest-angular']).controller('user', function($routeParams, $cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	$scope.user = {};
	$scope.userInfo = {};

	$scope.errorMessage = null;

    var userCall = $resource('/users/create', {}, {
        post: {method: 'POST', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

    $scope.register = function() {
        console.log("Send form clicked");
        if($scope.userInfo.password != $scope.userInfo.confirmpassword){
            window.alert("Passwords Dont Match");
        }else{
            userCall.post($scope.userInfo).$promise.then(function(response) {
                console.log("Create Response : " + response);
                if(response.id == -1)
                    $scope.errorMessage = response.errorMessage;
                else
                    $location.path('/user/' + $routeParams.userId);
            });
        }
    }
});