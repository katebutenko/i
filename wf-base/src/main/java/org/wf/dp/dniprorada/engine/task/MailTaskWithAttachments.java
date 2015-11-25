package org.wf.dp.dniprorada.engine.task;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.task.Attachment;
import org.apache.commons.mail.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.wf.dp.dniprorada.util.Mail;

import javax.activation.DataSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BW
 */
@Component("MailTaskWithAttachments")
public class MailTaskWithAttachments extends Abstract_MailTaskCustom {

    private final static Logger log = LoggerFactory.getLogger(MailTaskWithAttachments.class);

    private Expression saAttachmentsForSend;

    @Override
    public void execute(DelegateExecution oExecution) throws Exception {
        System.setProperty("mail.mime.address.strict", "false");

        //MultiPartEmail oMultiPartEmail = MultiPartEmail_BaseFromTask(oExecution);
        Mail oMail = Mail_BaseFromTask(oExecution);

        String sAttachmentsForSend = getStringFromFieldExpression(this.saAttachmentsForSend, oExecution);
        sAttachmentsForSend = sAttachmentsForSend == null ? "" : sAttachmentsForSend;
        log.info("sAttachmentsForSend=" + sAttachmentsForSend);
        List<Attachment> aAttachment = new ArrayList<>();
        String[] asID_Attachment = sAttachmentsForSend.split(",");
        for (String sID_Attachment : asID_Attachment) {
            //log.info("sID_Attachment=" + sID_Attachment);
            if (sID_Attachment != null && !"".equals(sID_Attachment.trim()) && !"null".equals(sID_Attachment.trim())) {
                String sID_AttachmentTrimmed = sID_Attachment.replaceAll("^\"|\"$", "");
                log.info("sID_AttachmentTrimmed= " + sID_AttachmentTrimmed);
                Attachment oAttachment = taskService.getAttachment(sID_AttachmentTrimmed);
                if (oAttachment != null) {
                    aAttachment.add(oAttachment);
                }
            } else {
                log.warn("sID_Attachment=" + sID_Attachment);
            }
        }

        if (aAttachment != null && !aAttachment.isEmpty()) {
            InputStream oInputStream_Attachment = null;
            String sFileName = "document";
            String sFileExt = "txt";
            String sDescription = "";
            for (Attachment oAttachment : aAttachment) {
                sFileName = oAttachment.getName();
                sFileExt = oAttachment.getType().split(";")[0];
                sDescription = oAttachment.getDescription();
                if (sDescription == null || "".equals(sDescription.trim())) {
                    sDescription = "(no description)";
                }
                log.info("oAttachment.getId()=" + oAttachment.getId() + ", sFileName=" + sFileName + ", sFileExt="
                        + sFileExt + ", sDescription=" + sDescription);
                oInputStream_Attachment = oExecution.getEngineServices().getTaskService()
                        .getAttachmentContent(oAttachment.getId());
                if (oInputStream_Attachment == null) {
                    log.error("Attachment with id '" + oAttachment.getId()
                            + "' doesn't have content associated with it.");
                    throw new ActivitiObjectNotFoundException(
                            "Attachment with id '" + oAttachment.getId() + "' doesn't have content associated with it.",
                            Attachment.class);
                }
                DataSource oDataSource = new ByteArrayDataSource(oInputStream_Attachment, sFileExt);
                if (oDataSource == null) {
                    log.error("Attachment: oDataSource == null");
                }

                //oMail._Attach(oDataSource, sFileName + "." + sFileExt, sDescription);
                oMail._Attach(oDataSource, sFileName, sDescription);

                log.info("oMultiPartEmail.attach: Ok!");
            }
        } else {
            log.error("aAttachment has nothing!");
            throw new ActivitiObjectNotFoundException("add the file to send");
        }

        // send the email
        //oMultiPartEmail.send();
        oMail.send();
    }

}
