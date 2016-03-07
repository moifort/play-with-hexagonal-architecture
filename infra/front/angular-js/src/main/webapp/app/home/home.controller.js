(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope'];

    function HomeController ($scope) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
    }
})();
