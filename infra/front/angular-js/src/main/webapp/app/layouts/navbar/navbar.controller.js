(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$location', '$state', 'ENV'];

    function NavbarController ($location, $state, ENV) {
        var vm = this;

        vm.navCollapsed = true;
        vm.inProduction = ENV === 'prod';
        vm.$state = $state;

        function login () {
        }

        function logout () {
            $state.go('home');
        }
    }
})();
