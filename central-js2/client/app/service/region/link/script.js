angular.module('app').config(function($stateProvider) {
  $stateProvider
    .state('service.general.region.link', {
      url: '/link',
      views: {
        'content@service.general.region': {
          templateUrl: 'html/service/region/link/index.html',
          controller: 'ServiceLinkController'
        }
      }
    });
});
