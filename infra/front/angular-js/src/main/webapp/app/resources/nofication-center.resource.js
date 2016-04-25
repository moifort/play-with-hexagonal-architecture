(function () {
    'use strict';

    angular
        .module('jhipsterApp')
        .factory('NotificationCenterResource', NotificationCenterResource);

    NotificationCenterResource.$inject = ['Restangular'];

    function NotificationCenterResource(Restangular) {
        return Restangular.all('notification-center/configuration');
    }
})();
