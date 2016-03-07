(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('FileManagerController', FileManagerController);

    FileManagerController.$inject = ['$scope', '$state', 'FileManagerResource'];

    function FileManagerController ($scope, $state, FileManagerResource) {
        var vm = this;
        vm.files = [];
        vm.loadAll = function() {
            FileManagerResource.query(function(result) {
                vm.files = result;
            });
        };

        vm.loadAll();
    }
})();
