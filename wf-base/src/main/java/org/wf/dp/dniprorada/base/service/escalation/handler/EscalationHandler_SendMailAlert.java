package org.wf.dp.dniprorada.base.service.escalation.handler;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wf.dp.dniprorada.util.Mail;
import org.wf.dp.dniprorada.util.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("EscalationHandler_SendMailAlert")
public class EscalationHandler_SendMailAlert
        implements EscalationHandler {

    private static final Logger log = Logger.getLogger(EscalationHandler_SendMailAlert.class);

    @Autowired
    private Mail oMail;


    @Override
    public void execute(Map<String, Object> mParam, String[] asRecipientMail, String sPatternFile) {
//        //check input data
//        if (params.length != 3){
//            throw new IllegalArgumentException("wrong input data!");
//        }
//        Map<String, Object> mParam = (Map<String, Object>) params[0];
//        String[] asRecipientMail = (String[]) params[1];
//        String sPatternFile = (String) params[2];

        //create email body
        String sBody = null;
        try {
            byte[] bytes = Util.getPatternFile(sPatternFile);
            sBody = Util.sData(bytes);
            log.info(">>>>>>>pattern body=");
            log.info(sBody);
            log.info(">>>>>>>--------");
        } catch (IOException e) {//??
            log.error("error during finding the pattern file! path=" + sPatternFile, e);
            //throw new IllegalArgumentException("wrong pattern path! path=" + sPatternFile, e);
        }
        if (sBody == null) {
            sBody = "test body";
            //throw new IllegalArgumentException("wrong pattern data! path=" + sPatternFile);
        }
        //??
        String sHead = "Task escalation";//"Ескалація задачі";//, "UTF-8");
        //
        for (String key : mParam.keySet()) {
            if (sBody.contains(key)) {
                log.info("replace key [" + key + "] by value " + mParam.get(key));
                sBody = sBody.replace("[" + key + "]", mParam.get(key).toString());
            }
        }
        log.info(">>>>>>>total sbody=");
        log.info(sBody);
        log.info(">>>>>>>--------");
        log.info ("@Autowired oMail=" + oMail );
        oMail = oMail == null ? new Mail(): oMail;
        log.info ("oMail=" + oMail );
        for (String recipient : asRecipientMail) {
            try {
                sendEmail(sHead, sBody, recipient);
            } catch (EmailException e) {
                log.error("error sending email!", e);
            }
        }

    }

    private void sendEmail(String sHead, String sBody, String recipient) throws EmailException {

        oMail.reset();
        oMail
//                ._From("noreplay@gmail.com")
                ._To(recipient)
                ._Head(sHead)
                ._Body(sBody)
        //._AuthUser(mailServerUsername)
        //._AuthPassword(mailServerPassword)
//        ._Host("gmail.com")
//                ._Port(Integer.valueOf("gmail.com"))
        //._SSL(true)
        //._TLS(true)
        ;
        oMail.send();
    }

    public static void main(String[] args) {
        Map<String, Object> param = new HashMap<>();
        //[Surname],[Name],[Middlename]
        param.put("[Surname]", "Petrenko");
        param.put("[Name]", "Petro");
        param.put("[Middlename]", "Petrovych");

        String[] recipients = new String[2];
        recipients[0] = "olga2012olga@gmail.com";
        recipients[1] = "olga.prylypko@gmail.com";

        String file = "print/kiev_dms_print1.html";

        new EscalationHandler_SendMailAlert().execute(param, recipients, file);

    }
}
