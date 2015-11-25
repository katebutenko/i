package org.activiti.rest.controller;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wf.dp.dniprorada.base.dao.EntityDao;
import org.wf.dp.dniprorada.base.util.JsonRestUtils;
import org.wf.dp.dniprorada.dao.HistoryEvent_ServiceDao;
import org.wf.dp.dniprorada.dao.SubjectMessagesDao;
import org.wf.dp.dniprorada.model.HistoryEvent_Service;
import org.wf.dp.dniprorada.model.SubjectMessage;
import org.wf.dp.dniprorada.model.SubjectMessageType;
import org.wf.dp.dniprorada.util.luna.CRCInvalidException;

import com.google.common.base.Optional;

@Controller
@RequestMapping(value = "/messages")
public class ActivitiRestSubjectMessageController {

    private static final Logger LOG = LoggerFactory.getLogger(ActivitiRestSubjectMessageController.class);

    @Autowired
    private HistoryEvent_ServiceDao historyEventServiceDao;
    @Autowired
    private SubjectMessagesDao subjectMessagesDao;
    @Autowired
    @Qualifier("subjectMessageTypeDao")
    private EntityDao<SubjectMessageType> subjectMessageTypeDao;

    @RequestMapping(value = "/setMessage", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity setMessage(
            @RequestParam(value = "sHead") String sHead,
            @RequestParam(value = "sBody", required = false) String sBody,
            @RequestParam(value = "nID_Subject", required = false) Long nID_Subject,
            @RequestParam(value = "sMail", required = false) String sMail,
            @RequestParam(value = "sContacts", required = false) String sContacts,
            @RequestParam(value = "sData", required = false) String sData,
            @RequestParam(value = "nID_SubjectMessageType", required = false) Long nID_SubjectMessageType,
            @RequestParam(value = "sID_Order", required = false) String sID_Order,
            @RequestParam(value = "nID_Protected", required = false) Long nID_Protected,
            @RequestParam(value = "nID_Server", required = false, defaultValue = "0") Integer nID_Server,
            @RequestParam(value = "sID_Rate", required = false) String sID_Rate) throws ActivitiRestException {

        SubjectMessage message =
                createSubjectMessage(sHead, sBody, nID_Subject, sMail, sContacts, sData, nID_SubjectMessageType);
        subjectMessagesDao.setMessage(message);
        message = subjectMessagesDao.getMessage(message.getId());
        checkRate(sID_Order, nID_Protected, nID_Server, sID_Rate);
        return JsonRestUtils.toJsonResponse(message);
    }

    @RequestMapping(value = "/setMessageFeedback", method = RequestMethod.POST)//Feedback
    public
    @ResponseBody
    String setMessageFeedback(
            @RequestParam(value = "sHead") String sHead,
            @RequestParam(value = "sBody", required = false) String sBody,
            @RequestParam(value = "warnSignal", required = false) String sWarnSignal,
            @RequestParam(value = "nID_Subject", required = false) Long nID_Subject,
            @RequestParam(value = "sMail", required = false) String sMail,
            @RequestParam(value = "sContacts", required = false) String sContacts,
            @RequestParam(value = "sData", required = false) String sData,
            @RequestParam(value = "nID_SubjectMessageType", required = false) Long nID_SubjectMessageType,
            @RequestParam(value = "sID_Order", required = false) String sID_Order,
            @RequestParam(value = "nID_Protected", required = false) Long nID_Protected,
            @RequestParam(value = "nID_Server", required = false, defaultValue = "0") Integer nID_Server,
            @RequestParam(value = "sID_Rate", required = false) String sID_Rate) throws ActivitiRestException {

        SubjectMessage message =
                createSubjectMessage(
                        sHead + (sID_Rate != null ? " (sID_Rate=" + sID_Rate + ")" : "") + ("on".equals(sWarnSignal) ?
                                " (anonymous)" :
                                ""), sBody, nID_Subject, sMail, sContacts, sData, nID_SubjectMessageType);
        subjectMessagesDao.setMessage(message);
        message = subjectMessagesDao.getMessage(message.getId());
        checkRate(sID_Order, nID_Protected, nID_Server, sID_Rate);
        //return "Спасибо! Вы успешно отправили отзыв!";
        return "Ok!";
    }
    
    @RequestMapping(value = "/setMessageRate", method = RequestMethod.GET)//Rate
    public
    @ResponseBody
    String setMessageRate(
            @RequestParam(value = "sHead") String sHead,
            @RequestParam(value = "sBody", required = false) String sBody,
            @RequestParam(value = "warnSignal", required = false) String sWarnSignal,
            @RequestParam(value = "nID_Subject", required = false) Long nID_Subject,
            @RequestParam(value = "sMail", required = false) String sMail,
            @RequestParam(value = "sContacts", required = false) String sContacts,
            @RequestParam(value = "sData", required = false) String sData,
            @RequestParam(value = "nID_SubjectMessageType", required = false) Long nID_SubjectMessageType,
            @RequestParam(value = "sID_Order", required = false) String sID_Order,
            @RequestParam(value = "nID_Protected", required = false) Long nID_Protected,
            @RequestParam(value = "nID_Server", required = false, defaultValue = "0") Integer nID_Server,
            @RequestParam(value = "sID_Rate", required = false) String sID_Rate) throws ActivitiRestException {

        SubjectMessage message =
                createSubjectMessage(
                        sHead + (sID_Rate != null ? " (sID_Rate=" + sID_Rate + ")" : "") + ("on".equals(sWarnSignal) ?
                                " (anonymous)" :
                                ""), sBody, nID_Subject, sMail, sContacts, sData, nID_SubjectMessageType);
        subjectMessagesDao.setMessage(message);
        message = subjectMessagesDao.getMessage(message.getId());
        checkRate(sID_Order, nID_Protected, nID_Server, sID_Rate);
        //return "Спасибо! Вы успешно отправили отзыв!";
        return "Ok!";
    }

    @RequestMapping(value = "/getMessageTest", method = RequestMethod.GET)
    public
    @ResponseBody
    String getMessageTest() {
        return "Test Проверка";
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = { "Accept=application/json" })
    public
    @ResponseBody
    ResponseEntity getMessages() {

        List<SubjectMessage> messages = subjectMessagesDao.getMessages();
        return JsonRestUtils.toJsonResponse(messages);
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = { "Accept=application/json" })
    public
    @ResponseBody
    ResponseEntity getMessage(
            @RequestParam(value = "nID") Long nID) {

        SubjectMessage message = subjectMessagesDao.getMessage(nID);
        return JsonRestUtils.toJsonResponse(message);
    }

    @RequestMapping(value = "/setMessageFeedback_Indirectly", method = RequestMethod.GET)
    public void setMessageFeedback_Indirectly(
            @RequestParam(value = "nID_Protected", required = true) Long nID_Protected,
            @RequestParam(value = "nID_Proccess_Feedback", required = true) String nID_Proccess_Feedback,
            @RequestParam(value = "sBody_Indirectly", required = true) String sBody_Indirectly,
            @RequestParam(value = "sID_Rate_Indirectly", required = true) String sID_Rate_Indirectly,
            @RequestParam(value = "nID_Server", required = false, defaultValue = "0") Integer nID_Server) throws ActivitiRestException {

        Optional<HistoryEvent_Service> eventServiceOptional = historyEventServiceDao.findBy("nID_Proccess_Feedback", Long.valueOf(nID_Proccess_Feedback));
        if (eventServiceOptional.isPresent()){
        	HistoryEvent_Service historyEventService = eventServiceOptional.get();
        	if (historyEventService != null){
        		historyEventService.setsID_Rate_Indirectly(sID_Rate_Indirectly);
        		historyEventServiceDao.saveOrUpdate(historyEventService);
        		LOG.info("Successfully updated historyEvent_Service with the rate " + sID_Rate_Indirectly);
        	}
        } else {
			LOG.error("Didn't find event service");
			return;
        }
        LOG.error("Finished execution");
    }
    
    private SubjectMessage createSubjectMessage(String sHead, String sBody, Long nID_subject, String sMail,
            String sContacts, String sData, Long nID_subjectMessageType) {
        SubjectMessage message = new SubjectMessage();
        message.setHead(sHead);
        message.setBody(sBody == null ? "" : sBody);
        message.setId_subject((nID_subject == null) ? 0 : nID_subject);
        message.setMail((sMail == null) ? "" : sMail);
        message.setContacts((sContacts == null) ? "" : sContacts);
        message.setData((sData == null) ? "" : sData);
        message.setDate(new DateTime());
        if (nID_subjectMessageType != null) {
            SubjectMessageType subjectMessageType = subjectMessageTypeDao.findByIdExpected(nID_subjectMessageType);
            message.setSubjectMessageType(subjectMessageType);
        }
        return message;
    }

    private void checkRate(String sID_Order, Long nID_Protected, Integer nID_Server, String sID_Rate)
            throws ActivitiRestException {

        if (nID_Protected == null && sID_Order == null && nID_Server == null && sID_Rate == null) {
            return;
        }
        Integer nRate;
        try {
            nRate = Integer.valueOf(sID_Rate);
        } catch (NumberFormatException ex) {
            LOG.warn("incorrect param sID_Rate (not a number): " + sID_Rate);
            return;
        }
        if (nRate < 1 || nRate > 5) {
            LOG.warn("incorrect param sID_Rate (not in range[1..5]): " + sID_Rate);
            return;
        }
        try {
            HistoryEvent_Service event_service;
            if (sID_Order != null) {
                String sID_Server = (sID_Order.contains("-") ?
                        "" :
                        (nID_Server != null ? ("" + nID_Server + "-") : "0-"));
                sID_Order = sID_Server + sID_Order;
                event_service = historyEventServiceDao.getOrgerByID(sID_Order);
            } else if (nID_Protected != null) {
                event_service = historyEventServiceDao.getOrgerByProtectedID(nID_Protected, nID_Server);
            } else {
                LOG.warn("incorrect input data!! must be: [sID_Order] OR [nID_Protected + nID_Server (optional)]");
                return;
            }
            LOG.info(String.format("set rate=%s to the task=%s, nID_Protected=%s", nRate, event_service.getnID_Task(), event_service.getnID_Protected()));
            event_service.setnRate(nRate);
            historyEventServiceDao.saveOrUpdate(event_service);
            LOG.info(String.format("set rate=%s to the task=%s, nID_Protected=%s Success!", nRate, event_service.getnID_Task(), event_service.getnID_Protected()));
        } catch (CRCInvalidException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
