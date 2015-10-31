'use strict';

// alpha specific configuration
// =================================
module.exports = {
  bankid: {
    sProtocol_AccessService_BankID: 'https',
    sHost_AccessService_BankID: 'bankid.privatbank.ua',
    sProtocol_ResourceService_BankID: 'https',
    sHost_ResourceService_BankID: 'bankid.privatbank.ua'
  },

  soccard : {
    socCardAPIProtocol: 'https',
    socCardAPIHostname: 'test.kyivcard.com.ua',
    socCardAPIVersion : '1.0'
  },

  server: {
    session: {
      secure: true,
      maxAge: 14400000 // 4h*60m*60s*1000ms*/
    }
  }

};
