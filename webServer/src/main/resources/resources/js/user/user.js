angular.module('user', ['secure-rest-angular']).controller('user', function($routeParams, $cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	$scope.user = {};
	$scope.userInfo = {};

	$scope.errorMessage = null;

    var userCall = $resource('/user/create', {}, {
        post: {method: 'POST', cache: false, isArray: false},
        options: {method: 'OPTIONS', cache: false}
    });

    var userIdCall = $resource('/user/details', {}, {
      get: {method: 'GET', cache: false, isArray: false},
      options: {method: 'OPTIONS', cache: false}
    });

    var modifyCall = $resource('/user/modify', {}, {
      post: {method: 'POST', cache: false, isArray: false},
      options: {method: 'OPTIONS', cache: false}
    });

	userIdCall.get().$promise.then(function(response) {
	    console.log('GET user returned: ', response);
	    $scope.userInfo = response;
	},function(){
	});

    $scope.register = function() {
        console.log("Send form clicked");
        if($scope.userInfo.password != $scope.userInfo.confirmpassword){
            window.alert("Passwords Dont Match");
        }else{
            userCall.post($scope.userInfo).$promise.then(function(response) {
                console.log("Create Response : " + response);
                window.alert("User created successfully, Please log in now to continue.");
                $location.path('/user/' + $routeParams.userId);
            },function(error) {
                console.log(error["status"]);
                if(error["status"] == 409){
                window.alert("User already exists.");
                }
                if(error["status"] == 417){
                window.alert("Email already exists.");
                }
            });
        }
    };

    $scope.modify = function() {
        console.log("Modify form clicked");
        if($scope.userInfo.newpassword != $scope.userInfo.confirmnewpassword){
            window.alert("Passwords Dont Match");
        }else{
            modifyCall.post($scope.userInfo).$promise.then(function(response) {
                console.log("Create Response : " + response);
                window.alert("User Modified successfully");
                $location.path('/');
            },function(error) {
                console.log(error["status"]);
                if(error["status"] == 409){
                window.alert("Please Enter Correct Password");
                }
            });
        }
    };
});