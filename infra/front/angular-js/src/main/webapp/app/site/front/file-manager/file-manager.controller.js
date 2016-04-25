(function () {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('FileManagerController', FileManagerController);

    FileManagerController.$inject = ['FileManagerResource', 'NotificationCenterResource'];

    function FileManagerController(FileManagerResource, NotificationCenterResource) {
        var vm = this;
        vm.newFile = {};
        vm.email = '';

        vm.files = [];

        vm.add = function () {
            FileManagerResource.post(vm.newFile).then(function() {
                vm.loadAll();
            });
        };

        vm.delete = function (file) {
            file.remove().then(function() {
                vm.loadAll();
            });
        };

        vm.activateEmail = function (email) {
            NotificationCenterResource.customPOST({id: 'mail', value: email }, 'service');
        };

        vm.setEmailNotification = function (type, isEnable) {
            NotificationCenterResource.post({id: 'mail', type: type, isEnable: isEnable });
        };

        vm.activateIrc = function () {
            NotificationCenterResource.customPOST({id: 'irc', value: '#AwsomeHexagonal' }, 'service');
        };

        vm.setIrcNotification = function (type, isEnable) {
            NotificationCenterResource.post({id: 'irc', type: type, isEnable: isEnable });
        };

        vm.loadAll = function () {
            FileManagerResource.getList().then(function (files) {
                vm.files = files;
            });
        };

        vm.loadAll();
    }
})();
