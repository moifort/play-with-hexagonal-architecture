(function () {
    'use strict';

    angular
        .module('jhipsterApp')
        .factory('FileManagerResource', FileManagerResource);

    FileManagerResource.$inject = ['Restangular'];

    function FileManagerResource(Restangular) {
        return Restangular.service('file');
    }
})();
