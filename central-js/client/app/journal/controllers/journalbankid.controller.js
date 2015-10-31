angular.module('journal').controller('JournalBankIdController', function ($rootScope, $scope, $location, $state, $window, BankIDService, ErrorsFactory) {

  $scope.loginWithBankId = function () {
    var stateForRedirect = $state.href('index.journal.bankid', {});
    var redirectURI = $location.protocol() +
      '://' + $location.host() + ':'
      + $location.port()
      + stateForRedirect;
    $window.location.href = './auth/bankID?link=' + redirectURI;
  };

  $scope.loginWithEds = function () {
    var stateForRedirect = $state.href('index.journal.bankid', {error: ''});
    var redirectURI = $location.protocol() +
      '://' + $location.host() + ':'
      + $location.port()
      + stateForRedirect;
    $window.location.href = './auth/eds?link=' + redirectURI;
  };

  $scope.loginWithSoccard = function () {
    var stateForRedirect = $state.href('index.journal.bankid', {error: ''});
    var redirectURI = $location.protocol() +
      '://' + $location.host() + ':'
      + $location.port()
      + stateForRedirect;
    $window.location.href = './auth/soccard?link=' + redirectURI;
  };

  if ($state.is('index.journal.bankid')) {
    if ($state.params.error) {
      if ($state.params.error) {
        var errorText;
        try {
          errorText = JSON.parse($state.params.error).error;
        } catch (error) {
          errorText = $state.params.error;
        }

        ErrorsFactory.push({
          type: "danger",
          text:  errorText
        });

        $state.go('index.journal', {});
      }
    } else {
      BankIDService.isLoggedIn().then(function () {
        return $state.go('index.journal.content');
      });
    }
  }
});
