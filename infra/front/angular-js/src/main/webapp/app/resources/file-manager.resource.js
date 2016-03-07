(function () {
    'use strict';

    angular
        .module('jhipsterApp')
        .factory('FileManagerResource', FileManagerResource);

    FileManagerResource.$inject = ['$resource'];

    function FileManagerResource($resource) {
        var service = $resource('file/:id', {}, {
            'getAll': {
                method: 'GET',
                isArray: true
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });

        return service;
    }
})();
