'use strict';

angular.module('dashboardJsApp')
  .factory('schedule', function services($http, $q) {

    var stringToWeekDays = function(str){
      var weekDays = {
        mo: false,
        tu: false,
        we: false,
        th: false,
        fr: false,
        sa: false,
        su: false
      };

      var keys = str.split(',');
      keys.forEach(function(key){
        weekDays[key] = true;
      });

      return weekDays;
    };

    var clearDateTime = function(dateStr){
      var date = dateStr.replace(/['"]+/g, '');
      return date;
    };

    var clearAndConvert = function (slot){
      slot.sDateTimeAt = clearDateTime(slot.sDateTimeAt);
      slot.sDateTimeTo = clearDateTime(slot.sDateTimeTo);

      slot.saRegionWeekDay = slot.saRegionWeekDay.replace(/['"]+/g, '');
      slot.saRegionWeekDay = stringToWeekDays(slot.saRegionWeekDay);
    };

    var weekDaysToString = function(weekDays){
      var days = [];
      var keys = Object.keys(weekDays);

      keys.forEach(function(key){
        if (weekDays[key]){
          days.push(key);
        }
      });

      return days.join();
    };

    var getTime = function(url, sID_BP, callback){
      var cb = callback || angular.noop;
      var deferred = $q.defer();

      var request = {
        method: 'GET',
        url: url,
        data: {},
        params: {
          sID_BP : sID_BP
        }
      };

      $http(request).
        success(function(data) {
          var slots = angular.fromJson(data);
          slots.forEach(clearAndConvert);
          deferred.resolve(slots);
          return cb();
        }).
        error(function(err) {
          deferred.reject(err);
          return cb(err);
        }.bind(this));

      return deferred.promise;
    };

    var setTime = function(url, sID_BP, slot, callback){
      var cb = callback || angular.noop;
      var deferred = $q.defer();

      var stringWeekDays = weekDaysToString(slot.saRegionWeekDay);

      var request = {
        method: 'POST',
        url: url,
        params: {
          sID_BP: sID_BP,
          nID: slot.nID,
          sName: slot.sName,
          sRegionTime: slot.sRegionTime,
          saRegionWeekDay: stringWeekDays,
          sDateTimeAt: slot.sDateTimeAt,
          sDateTimeTo: slot.sDateTimeTo
        }
      };

      $http(request).
        success(function (data) {
          var slot = angular.fromJson(data);
          clearAndConvert(slot);
          deferred.resolve(slot);
          return cb();
        }).
        error(function (err) {
          deferred.reject(err);
          return cb(err);
        }.bind(this));

      return deferred.promise;
    };

    var deleteTime = function(url, sID_BP, slotId, callback){
      var cb = callback || angular.noop;
      var deferred = $q.defer();

      var request = {
        method: 'DELETE',
        url: url,
        params: {
          sID_BP: sID_BP,
          nID: slotId
        }
      };

      $http(request).
        success(function (data) {
          data = angular.fromJson(data);
          deferred.resolve(data);
          return cb();
        }).
        error(function (err) {
          deferred.reject(err);
          return cb(err);
        }.bind(this));

      return deferred.promise;
    };

    return {
      getSchedule: function(sID_BP, callback) {
        return getTime('/api/schedule/schedule', sID_BP, callback);
      },
      setSchedule: function(sID_BP, slot, callback) {
        return setTime('/api/schedule/schedule', sID_BP, slot, callback);
      },
      deleteSchedule: function(sID_BP, slotId, callback) {
        return deleteTime('/api/schedule/schedule', sID_BP, slotId, callback);
      },
      getExemptions: function(sID_BP, callback) {
        return getTime('/api/schedule/exemption', sID_BP, callback);
      },
      setExemption: function(sID_BP, slot, callback) {
        return setTime('/api/schedule/exemption', sID_BP, slot, callback);
      },
      deleteExemption: function(sID_BP, slotId, callback) {
        return deleteTime('/api/schedule/exemption', sID_BP, slotId, callback);
      },
      /**
       * Get service slots grouped by days
       *
       * @param  {Function} callback - optional
       * @return {Promise}
       */
      getFlowSlots: function(sID_BP, bAll, nDays, sDateStart, callback){
        var cb = callback || angular.noop;
        var deferred = $q.defer();

        var request = {
          method: 'GET',
          url: '/api/schedule/flowSlots',
          params: {
            sID_BP: sID_BP,
            bAll: bAll,
            nDays: nDays,
            sDateStart: sDateStart
          }
        };

        $http(request).
          success(function(data) {
            var json = angular.fromJson(data);
            deferred.resolve(json);
            return cb();
          }).
          error(function(err) {
            deferred.reject(err);
            return cb(err);
          }.bind(this));

        return deferred.promise;
      },

      buildFlowSlots: function(sID_BP, sDateStart, sDateStop, callback){
        var cb = callback || angular.noop;
        var deferred = $q.defer();

        var request = {
          method: 'POST',
          url: '/api/schedule/buildFlowSlots',
          data: {},
          params: {
            sID_BP: sID_BP,
            sDateStart: sDateStart,
            sDateStop: sDateStop
          }
        };

        $http(request).
          success(function(data) {
            var json = angular.fromJson(data);
            deferred.resolve(json);
            return cb();
          }).
          error(function(err) {
            deferred.reject(err);
            return cb(err);
          }.bind(this));

        return deferred.promise;
      },

      deleteFlowSlots: function(sID_BP, sDateStart, sDateStop, bWithTickets, callback){
        var cb = callback || angular.noop;
        var deferred = $q.defer();

        var request = {
          method: 'DELETE',
          url: '/api/schedule/flowSlots',
          params: {
            sID_BP: sID_BP,
            sDateStart: sDateStart,
            sDateStop: sDateStop,
            bWithTickets: bWithTickets
          }
        };

        $http(request).
          success(function(data) {
            var json = angular.fromJson(data);
            deferred.resolve(json);
            return cb();
          }).
          error(function(err) {
            deferred.reject(err);
            return cb(err);
          }.bind(this));

        return deferred.promise;
      }
    };
  });