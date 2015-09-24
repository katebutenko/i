'use strict';

angular.module('dashboardJsApp')
  .factory('escalations', function services($http, $q) {


    var setRule = function(url, sID_BP, rule, callback){
      var cb = callback || angular.noop;
      var deferred = $q.defer();

      var request = {
        method: 'POST',
        url: url,
        params: {
          //sID_BP: sID_BP,
        nID: rule.id,
        sID_BP: rule.sID_BP,
        sID_UserTask: rule.sID_UserTask,
        sCondition: rule.sCondition,
        soData: rule.soData,
        sPatternFile: rule.sPatternFile,
        nID_EscalationRuleFunction: rule.nID_EscalationRuleFunction,
        }
      };

      $http(request).
        success(function (data) {
          var rule = angular.fromJson(data);
          deferred.resolve(rule);
          return cb();
        }).
        error(function (err) {
          deferred.reject(err);
          return cb(err);
        }.bind(this));

      return deferred.promise;
    };

    

    return {
      
      setRule: function(sID_BP, rule, callback) {
        return setRule('/service/escalation/setEscalationRule', sID_BP, rule, callback);
      },
      


    };
  });
