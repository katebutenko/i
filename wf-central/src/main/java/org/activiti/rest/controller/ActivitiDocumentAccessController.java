package org.activiti.rest.controller;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wf.dp.dniprorada.constant.HistoryEventMessage;
import org.wf.dp.dniprorada.constant.HistoryEventType;
import org.wf.dp.dniprorada.dao.DocumentAccessDao;
import org.wf.dp.dniprorada.dao.DocumentDao;
import org.wf.dp.dniprorada.dao.HistoryEventDao;
import org.wf.dp.dniprorada.model.AccessURL;
import org.wf.dp.dniprorada.model.Document;
import org.wf.dp.dniprorada.model.DocumentAccess;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class ActivitiDocumentAccessController {

    private static final Logger LOG = LoggerFactory.getLogger(ActivitiDocumentAccessController.class);

    @Autowired
    private DocumentAccessDao documentAccessDao;
    @Autowired
    private HistoryEventDao historyEventDao;
    @Autowired
    private DocumentDao documentDao;

    //@Deprecated
    @RequestMapping(value = "/setDocumentLink", method = RequestMethod.GET, headers = { "Accept=application/json" })
    public
    @ResponseBody
    AccessURL setDocumentAccessLink(
            @RequestParam(value = "nID_Document") Long nID_Document,
            @RequestParam(value = "sFIO", required = false) String sFIO,
            @RequestParam(value = "sTarget", required = false) String sTarget,
            @RequestParam(value = "sTelephone", required = false) String sTelephone,
            @RequestParam(value = "nMS") Long nMS,
            @RequestParam(value = "sMail", required = false) String sMail,
            HttpServletResponse response) {
        AccessURL oAccessURL = new AccessURL();
        try {
            oAccessURL.setName("sURL");
            String sValue = "";
            sValue = documentAccessDao.setDocumentLink(nID_Document, sFIO, sTarget, sTelephone, nMS, sMail);
            oAccessURL.setValue(sValue);

            createHistoryEvent(HistoryEventType.SET_DOCUMENT_ACCESS_LINK,
                    nID_Document, sFIO, sTelephone, nMS, sMail);
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("Reason", e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        return oAccessURL;
    }

    @Deprecated
    @RequestMapping(value = "/getDocumentLink", method = RequestMethod.GET, headers = { "Accept=application/json" })
    public
    @ResponseBody
    DocumentAccess getDocumentAccessLink(
            @RequestParam(value = "nID_Access") Long nID_Access,
            @RequestParam(value = "sSecret") String sSecret,
            HttpServletResponse response) {
        DocumentAccess da = null;
        try {
            da = documentAccessDao.getDocumentLink(nID_Access, sSecret);
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Reason", "Access not found\n" + e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        if (da == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Reason", "Access not found");
        } else {
            DateTime now = new DateTime();
            boolean isSuccessAccess = true;
            DateTime d = da.getDateCreate();

            if (d.plusMillis(da.getMS().intValue()).isBefore(now)) {
                isSuccessAccess = false;
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setHeader("Reason", "Access expired");
            }
            if (!sSecret.equals(da.getSecret())) {
                isSuccessAccess = false;
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setHeader("Reason", "Access to another document");
            }
            if (isSuccessAccess) {
                createHistoryEvent(HistoryEventType.SET_DOCUMENT_ACCESS,
                        da.getID_Document(), da.getFIO(), da.getTelephone(), da.getMS(), da.getMail());
            }
        }

        return da;
    }

    @Deprecated
    @RequestMapping(value = "/getDocumentAccess", method = RequestMethod.GET, headers = { "Accept=application/json" })
    public
    @ResponseBody
    AccessURL getDocumentAccess(
            @RequestParam(value = "nID_Access") Long nID_Access,
            @RequestParam(value = "sSecret") String sSecret,
            HttpServletResponse response) {
        AccessURL oAccessURL = new AccessURL();
        try {
            oAccessURL.setName("sURL");
            String sValue = "";
            //sValue = documentAccessDao.getDocumentAccess(nID_Access,sSecret);
            documentAccessDao.getDocumentAccess(nID_Access, sSecret);
            sValue = String.valueOf(nID_Access);
            oAccessURL.setValue(sValue);
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Reason", "Access not found");
            oAccessURL.setValue(e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        return oAccessURL;
    }

    @Deprecated
    @RequestMapping(value = "/setDocumentAccess", method = RequestMethod.GET, headers = { "Accept=application/json" })
    public
    @ResponseBody
    AccessURL setDocumentAccess(
            @RequestParam(value = "nID_Access") Long nID_Access,
            @RequestParam(value = "sSecret") String sSecret,
            @RequestParam(value = "sAnswer") String sAnswer,
            HttpServletResponse response) {
        AccessURL oAccessURL = new AccessURL();
        try {
            String sValue;
            sValue = documentAccessDao.setDocumentAccess(nID_Access, sSecret, sAnswer);
            oAccessURL.setValue(sValue);
            oAccessURL.setName("sURL");
            if (oAccessURL.getValue().isEmpty() || oAccessURL.getValue() == null) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setHeader("Reason", "Access not found");
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("Reason", e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        return oAccessURL;
    }

    private void createHistoryEvent(HistoryEventType eventType, Long nID_Document,
            String sFIO, String sPhone, Long nMs, String sEmail) {
        Map<String, String> values = new HashMap<>();
        try {
            values.put(HistoryEventMessage.FIO, sFIO);
            values.put(HistoryEventMessage.TELEPHONE, sPhone);
            values.put(HistoryEventMessage.EMAIL, sEmail);
            values.put(HistoryEventMessage.DAYS, "" + TimeUnit.MILLISECONDS.toDays(nMs));

            Document oDocument = documentDao.getDocument(nID_Document);
            values.put(HistoryEventMessage.DOCUMENT_NAME, oDocument.getName());
            values.put(HistoryEventMessage.DOCUMENT_TYPE, oDocument.getDocumentType().getName());
            nID_Document = oDocument.getSubject().getId();
        } catch (Exception e) {
            LOG.warn("can't get document info!", e);
        }
        try {
            String eventMessage = HistoryEventMessage.createJournalMessage(eventType, values);
            historyEventDao.setHistoryEvent(nID_Document, eventType.getnID(),
                    eventMessage, eventMessage);
        } catch (IOException e) {
            LOG.error("error during creating HistoryEvent", e);
        }
    }
}