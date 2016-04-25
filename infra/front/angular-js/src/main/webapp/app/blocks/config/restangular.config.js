(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(restangularConfig);

    restangularConfig.$inject = ['RestangularProvider'];

    function restangularConfig(RestangularProvider) {
        RestangularProvider.setBaseUrl('http://localhost:8080/');
    }
})();
