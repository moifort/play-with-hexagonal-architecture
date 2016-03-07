(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('file-manager', {
            parent: 'app',
            url: '/file-manager',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/site/front/file-manager/file-manager.html',
                    controller: 'FileManager',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
