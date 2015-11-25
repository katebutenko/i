package org.activiti.rest.controller;

import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.wf.dp.dniprorada.base.dao.EntityNotFoundException;
import org.wf.dp.dniprorada.base.dao.GenericEntityDao;
import org.wf.dp.dniprorada.constant.HistoryEventMessage;
import org.wf.dp.dniprorada.constant.HistoryEventType;
import org.wf.dp.dniprorada.dao.HistoryEventDao;
import org.wf.dp.dniprorada.dao.HistoryEvent_ServiceDao;
import org.wf.dp.dniprorada.dao.ServerDao;
import org.wf.dp.dniprorada.liqPay.LiqBuyUtil;
import org.wf.dp.dniprorada.model.HistoryEvent;
import org.wf.dp.dniprorada.model.HistoryEvent_Service;
import org.wf.dp.dniprorada.model.Region;
import org.wf.dp.dniprorada.model.Server;
import org.wf.dp.dniprorada.util.GeneralConfig;
import org.wf.dp.dniprorada.util.luna.CRCInvalidException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/services")
public class ActivitiRestHistoryEventController {

    private static final Logger LOG = Logger.getLogger(ActivitiRestHistoryEventController.class);

    @Autowired
    private HistoryEvent_ServiceDao historyEventServiceDao;
    @Autowired
    private HistoryEventDao historyEventDao;

    @Autowired
    @Qualifier("regionDao")
    private GenericEntityDao<Region> regionDao;

    @Autowired
    private ServerDao serverDao;
    @Autowired
    private GeneralConfig generalConfig;

    /**
     * получает объект события по услуге, по одной из следующий комбинаций параметров:
     * - только sID_Order, строка-ид события по услуге, формат XXX-XXXXXX, где первая часть -- ид сервера, где расположена задача,
     * вторая часть -- nID_Protected, т.е. ид задачи + контрольная сумма (по алгоритму Луна)
     * также для sID_Order проверяется соответсвие формату (должен содержать "-"), если черточки нету -- то перед строкой добавляется "0-"
     * - только nID_Protected -- "старая" нумерация, ид сервера в таком случае равно 0
     * - nID_Server + nID_Protected
     *
     * @param sID_Order     -- строка-ид события по услуге, в формате XXX-XXXXXX = nID_Server-nID_Protected (опционально, если есть другие параметры)
     * @param nID_Protected -- зашифрованое ид задачи, nID задачи + контрольная цифра по алгоритму Луна (опционально, если задан sID_Order)
     * @param nID_Process   -- ид задачи (опционально, если задан один из предыдущих параметров)
     * @param nID_Server    -- ид сервера, где расположена задача (опционально, по умолчанию 0)
     * @return the object (if nID_Protected is correct and record exists) otherwise return
     * 403. CRC Error (wrong nID_Protected) or 403. "Record not found"
     * @throws ActivitiRestException
     */
    @RequestMapping(value = "/getHistoryEvent_Service", method = RequestMethod.GET)
    public
    @ResponseBody
    HistoryEvent_Service getHistoryEvent_Service(
            @RequestParam(value = "sID_Order", required = false) String sID_Order,
            @RequestParam(value = "nID_Protected", required = false) Long nID_Protected,
            @RequestParam(value = "nID_Process", required = false) Long nID_Process,
            @RequestParam(value = "nID_Server", required = false) Integer nID_Server)
            throws ActivitiRestException {

        return getHistoryEventService(sID_Order, nID_Protected, nID_Process, nID_Server);
    }

    /**
     * add the object of HistoryEvent_Service to db
     * with record to My journal
     *
     * @param nID_Process-          ИД-номер задачи (long)
     * @param nID_Server            - ид сервера, где расположена задача (опционально, по умолчанию 0)
     * @param nID_Subject-          ИД-номер (long)
     * @param sID_Status            - строка-статус
     * @param sProcessInstanceName- название услуги (для Журнала событий)
     * @param nID_Service           -- ид услуги (long, опционально)
     * @param nID_Region            -- ид области (long, опционально)
     * @param sID_UA                -- ид страны (строка, опционально)
     * @param soData-               строка-объект с данными (опционально, для поддержки дополнения заявки со стороны гражданина)
     * @param sToken                - строка-токена (опционально, для поддержки дополнения заявки со стороны гражданина)
     * @param sHead                 - строка заглавия сообщения (опционально, для поддержки дополнения заявки со стороны гражданина)
     * @param sBody                 - строка тела сообщения (опционально, для поддержки дополнения заявки со стороны гражданина)
     * @param nID_Proccess_Feedback - ид запущенного процесса для обработки фидбеков (issue 962)
     * @param nID_Proccess_Escalation - поле на перспективу для следующего тз по эскалации
     * @return created object or Exception "Cannot create event_service with the same nID_Process and nID_Server!"
     */
    @RequestMapping(value = "/addHistoryEvent_Service", method = RequestMethod.GET)
    public
    @ResponseBody
    HistoryEvent_Service addHistoryEvent_Service(
            @RequestParam(value = "nID_Process") Long nID_Process,
            @RequestParam(value = "nID_Server", required = false, defaultValue = "0") Integer nID_Server,
            @RequestParam(value = "nID_Subject") Long nID_Subject,
            @RequestParam(value = "sID_Status") String sID_Status,
            @RequestParam(value = "sProcessInstanceName") String sProcessInstanceName,
            @RequestParam(value = "nID_Service", required = false) Long nID_Service,
            @RequestParam(value = "nID_Region", required = false) Long nID_Region,
            @RequestParam(value = "sID_UA", required = false) String sID_UA,
            @RequestParam(value = "soData", required = false) String soData,
            @RequestParam(value = "sToken", required = false) String sToken,
            @RequestParam(value = "sHead", required = false) String sHead,
            @RequestParam(value = "sBody", required = false) String sBody,
            @RequestParam(value = "nID_Proccess_Feedback", required = false) Long nID_Proccess_Feedback,
            @RequestParam(value = "nID_Proccess_Escalation", required = false) Long nID_Proccess_Escalation
    ) {

        HistoryEvent_Service event_service = new HistoryEvent_Service();
        event_service.setnID_Task(nID_Process);
        event_service.setsStatus(sID_Status);
        event_service.setsID_Status(sID_Status);
        event_service.setnID_Subject(nID_Subject);
        event_service.setnID_Region(nID_Region);
        event_service.setnID_Service(nID_Service);
        event_service.setsID_UA(sID_UA);
        event_service.setnRate(0);
        event_service.setSoData(soData);
        event_service.setsToken(sToken);
        event_service.setsHead(sHead);
        event_service.setsBody(sBody);
        event_service.setnID_Server(nID_Server);
        event_service.setnID_Proccess_Feedback(nID_Proccess_Feedback);
        event_service.setnID_Proccess_Escalation(nID_Proccess_Escalation);
        event_service = historyEventServiceDao.addHistoryEvent_Service(event_service);
        //get_service history event
        Map<String, String> mParamMessage = new HashMap<>();
        mParamMessage.put(HistoryEventMessage.SERVICE_NAME, sProcessInstanceName);
        mParamMessage.put(HistoryEventMessage.SERVICE_STATE, sID_Status);
        setHistoryEvent(HistoryEventType.GET_SERVICE, nID_Subject, mParamMessage);
        //My journal. setTaskQuestions (issue 808)
        createHistoryEventForTaskQuestions(HistoryEventType.SET_TASK_QUESTIONS, soData, soData,
                event_service.getnID_Protected(), nID_Subject);
        return event_service;
    }

    /**
     * обновляет объект события по услуге, с записью в Мой журнал
     *
     * @param sID_Order     -- строка-ид события по услуге, в формате XXX-XXXXXX = nID_Server-nID_Protected(опционально, если задан sID_Order или nID_Process с/без nID_Server)
     * @param nID_Protected -- зашифрованое ид задачи, nID задачи + контрольная цифра по алгоритму Луна (опционально, если задан sID_Order или nID_Process с/без nID_Server)
     * @param nID_Process   - ид задачи (опционально, если задан sID_Order или nID_Protected с/без nID_Server)
     * @param nID_Server    -- ид сервера, где расположена задача (опционально, по умолчанию 0)
     * @param sID_Status    - строка-статус
     * @param soData        - строка-объект с данными (опционально, для поддержки дополнения заявки со стороны гражданина)
     * @param sToken        - строка-токена (опционально, для поддержки дополнения заявки со стороны гражданина)
     * @param sHead         - строка заглавия сообщения (опционально, для поддержки дополнения заявки со стороны гражданина)
     * @param sBody         - строка тела сообщения (опционально, для поддержки дополнения заявки со стороны гражданина)
     * @param nTimeHours    - время обработки задачи (в часах, опционально)
     * @param nID_Proccess_Feedback - ид запущенного процесса для обработки фидбеков (issue 962)
     * @param nID_Proccess_Escalation - поле на перспективу для следующего тз по эскалации
     * @return 200ok or "Record not found"
     * @throws ActivitiRestException
     */
    @RequestMapping(value = "/updateHistoryEvent_Service", method = RequestMethod.GET)
    public
    @ResponseBody
    HistoryEvent_Service updateHistoryEvent_Service(
            @RequestParam(value = "sID_Order", required = false) String sID_Order,
            @RequestParam(value = "nID_Protected", required = false) Long nID_Protected,
            @RequestParam(value = "nID_Process", required = false) Long nID_Process,
            @RequestParam(value = "nID_Server", required = false) Integer nID_Server,
            @RequestParam(value = "sID_Status") String sID_Status,
            @RequestParam(value = "soData", required = false) String soData,
            @RequestParam(value = "sToken", required = false) String sToken,
            @RequestParam(value = "sHead", required = false) String sHead,
            @RequestParam(value = "sBody", required = false) String sBody,
            @RequestParam(value = "nTimeHours", required = false) String nTimeHours,
            @RequestParam(value = "nID_Proccess_Feedback", required = false) Long nID_Proccess_Feedback,
            @RequestParam(value = "nID_Proccess_Escalation", required = false) Long nID_Proccess_Escalation
    ) throws ActivitiRestException {

        HistoryEvent_Service event_service = getHistoryEventService(sID_Order, nID_Protected, nID_Process, nID_Server);

        boolean isChanged = false;
        if (sID_Status != null && !sID_Status.equals(event_service.getsID_Status())) {
            event_service.setsID_Status(sID_Status);
            isChanged = true;
        }
        if (soData != null && !soData.equals(event_service.getSoData())) {
            event_service.setSoData(soData);
            isChanged = true;
            if (sHead == null) {
                sHead = "Необхідно уточнити дані";
            }
        }
        if (sHead != null && !sHead.equals(event_service.getsHead())) {
            event_service.setsHead(sHead);
            isChanged = true;
        }
        if (sBody != null && !sBody.equals(event_service.getsBody())) {
            event_service.setsBody(sBody);
            isChanged = true;
        }
        if (sToken == null || !sToken.equals(event_service.getsToken())) {
            event_service.setsToken(sToken);
            isChanged = true;
        }
        if (nTimeHours != null && !nTimeHours.isEmpty()) {
            Integer nHours;
            try {
                nHours = Integer.valueOf(nTimeHours);
            } catch (NumberFormatException ignored) {
                nHours = 0;
            }
            event_service.setnTimeHours(nHours);
            isChanged = true;
        }
        if (nID_Proccess_Feedback != null && !nID_Proccess_Feedback.equals(event_service.getnID_Proccess_Feedback())) {
            event_service.setnID_Proccess_Feedback(nID_Proccess_Feedback);
            isChanged = true;
        }
        if (nID_Proccess_Escalation != null && !nID_Proccess_Escalation
                .equals(event_service.getnID_Proccess_Escalation())) {
            event_service.setnID_Proccess_Escalation(nID_Proccess_Escalation);
            isChanged = true;
        }
        //for new numeration of historyEvent_services (889)
        nID_Protected = event_service.getnID_Protected();
        nID_Server = nID_Server != null ? nID_Server : 0;
        String sID_Server = (sID_Order != null && sID_Order.contains("-")) ? ""
                : ("" + nID_Server + "-");
        sID_Order = sID_Server + (sID_Order != null ? sID_Order : nID_Protected);
        event_service.setsID_Order(sID_Order);
        //        event_service.setnID_Server(nID_Server);
        //        if (isChanged) { temp -- for sID_Order. todo remove after deleting dublicates (889)
        historyEventServiceDao.updateHistoryEvent_Service(event_service);
        //        }

        Long nID_Subject = event_service.getnID_Subject();
        //My journal. change status of task
        Map<String, String> mParamMessage = new HashMap<>();
        mParamMessage.put(HistoryEventMessage.SERVICE_STATE, sID_Status);
        mParamMessage.put(HistoryEventMessage.TASK_NUMBER, sID_Order);
        setHistoryEvent(HistoryEventType.ACTIVITY_STATUS_NEW, nID_Subject, mParamMessage);
        //My journal. setTaskQuestions (issue 808, 809)
        if (soData != null) {
            createHistoryEventForTaskQuestions(
                    sToken != null ? HistoryEventType.SET_TASK_QUESTIONS : HistoryEventType.SET_TASK_ANSWERS,
                    soData, sBody, nID_Protected, nID_Subject);
        }
        return event_service;
    }

    private HistoryEvent_Service getHistoryEventService(
            String sID_Order, Long nID_Protected, Long nID_Process, Integer nID_Server) throws ActivitiRestException {

        HistoryEvent_Service event_service;
        try {
            if (sID_Order != null) {
                String sID_Server = (sID_Order.contains("-") ?
                        "" :
                        (nID_Server != null ? ("" + nID_Server + "-") : "0-"));
                sID_Order = sID_Server + sID_Order;
                event_service = historyEventServiceDao.getOrgerByID(sID_Order);
            } else if (nID_Protected != null) {
                event_service = historyEventServiceDao.getOrgerByProtectedID(nID_Protected, nID_Server);
            } else if (nID_Process != null) {
                event_service = historyEventServiceDao.getOrgerByProcessID(nID_Process, nID_Server);
            } else {
                throw new ActivitiRestException(
                        ActivitiExceptionController.BUSINESS_ERROR_CODE,
                        "incorrect input data!! must be: [sID_Order] OR [nID_Protected + nID_Server (optional)] OR [nID_Process + nID_Server(optional)]",
                        HttpStatus.FORBIDDEN);
            }
        } catch (CRCInvalidException | EntityNotFoundException e) {
            throw new ActivitiRestException(
                    ActivitiExceptionController.BUSINESS_ERROR_CODE,
                    e.getMessage(), e,
                    HttpStatus.FORBIDDEN);
        }
        return event_service;
    }

    private void createHistoryEventForTaskQuestions(HistoryEventType eventType, String soData, String data,
            Long nID_Protected, Long nID_Subject) {
        Map<String, String> mParamMessage = new HashMap<>();
        if (soData != null && !"[]".equals(soData)) {
            LOG.info(">>>>create history event for SET_TASK_QUESTIONS.TASK_NUMBER=" + nID_Protected);
            mParamMessage.put(HistoryEventMessage.TASK_NUMBER, "" + nID_Protected);
            LOG.info(">>>>create history event for SET_TASK_QUESTIONS.data=" + data);
            mParamMessage.put(HistoryEventMessage.S_BODY, data == null ? "" : data);
            LOG.info(">>>>create history event for SET_TASK_QUESTIONS.TABLE_BODY=" + HistoryEventMessage
                    .createTable(soData));
            mParamMessage.put(HistoryEventMessage.TABLE_BODY, HistoryEventMessage.createTable(soData));
            LOG.info(">>>>create history event for SET_TASK_QUESTIONS.nID_Subject=" + nID_Subject);
            setHistoryEvent(eventType, nID_Subject, mParamMessage);
            LOG.info(">>>>create history event for SET_TASK_QUESTIONS... ok!");
        }
    }

    /**
     * @param nID_Subject - номер-ИД субьекта
     * @param sID_UA      - строка-ИД места Услуги
     * @param nID_Service - номер-ИД услугии
     * @return the object found or to throw error
     */
    @RequestMapping(value = "/getLastTaskHistory", method = RequestMethod.GET)
    public
    @ResponseBody
    HistoryEvent_Service getLastTaskHistory(
            @RequestParam(value = "nID_Subject", required = true) Long nID_Subject,
            @RequestParam(value = "nID_Service", required = true) Long nID_Service,
            @RequestParam(value = "sID_UA", required = true) String sID_UA) throws ActivitiRestException {

        HistoryEvent_Service historyEvent_Service = historyEventServiceDao.getLastTaskHistory(nID_Subject, nID_Service,
                sID_UA);
        if (historyEvent_Service == null) {
            throw new ActivitiRestException(ActivitiExceptionController.BUSINESS_ERROR_CODE, "Record not found");
        }
        return historyEvent_Service;
    }

    //################ HistoryEvent services ###################

    @RequestMapping(value = "/setHistoryEvent", method = RequestMethod.POST)
    public
    @ResponseBody
    Long setHistoryEvent(
            @RequestParam(value = "nID_Subject", required = false) long nID_Subject,
            @RequestParam(value = "nID_HistoryEventType", required = false) Long nID_HistoryEventType,
            @RequestParam(value = "sEventName", required = false) String sEventName_Custom,
            @RequestParam(value = "sMessage") String sMessage)
            throws IOException {

        return historyEventDao.setHistoryEvent(nID_Subject,
                nID_HistoryEventType, sEventName_Custom, sMessage);

    }

    @RequestMapping(value = "/getHistoryEvent", method = RequestMethod.GET)
    public
    @ResponseBody
    HistoryEvent getHistoryEvent(@RequestParam(value = "nID") Long id) {
        return historyEventDao.getHistoryEvent(id);
    }

    @RequestMapping(value = "/getHistoryEvents", method = RequestMethod.GET)
    public
    @ResponseBody
    List<HistoryEvent> getHistoryEvents(
            @RequestParam(value = "nID_Subject") long nID_Subject) {
        return historyEventDao.getHistoryEvents(nID_Subject);
    }

    ////-------------Statistics--------

    @RequestMapping(value = "/getStatisticServiceCounts", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String getStatisticServiceCounts(@RequestParam(value = "nID_Service") Long nID_Service) {

        List<Map<String, Object>> listOfHistoryEventsWithMeaningfulNames = getListOfHistoryEvents(nID_Service);
        return JSONValue.toJSONString(listOfHistoryEventsWithMeaningfulNames);
    }

    private List<Map<String, Object>> getListOfHistoryEvents(Long nID_Service) {

        List<Map<String, Object>> listOfHistoryEventsWithMeaningfulNames = new LinkedList<>();
        List<Map<String, Long>> listOfHistoryEvents = historyEventServiceDao
                .getHistoryEvent_ServiceBynID_Service(nID_Service);
        Map<String, Object> currMapWithName;
        Region region;
        Long nRate;
        Long nCount;
        for (Map<String, Long> currMap : listOfHistoryEvents) {
            currMapWithName = new HashMap<>();

            region = regionDao.findByIdExpected(currMap.get("sName"));
            Long averageDuration = currMap.get("nTimeHours");

            LOG.info("[getListOfHistoryEvents]sName=" + region.getName());
            currMapWithName.put("sName", region.getName());

            nRate = currMap.get("nRate") == null ? 0L : currMap.get("nRate");
            nCount = currMap.get("nCount") == null ? 0L : currMap.get("nCount");

            nCount = addSomeServicesCount(nCount, nID_Service, region);

            if (nID_Service == 159) {//issue 750 + 777
                LOG.info("[getListOfHistoryEvents]!!!nID_Service=" + nID_Service);
                List<Map<String, Object>> am;
                Long[] arr;
                Long nSumRate = nRate * nCount;
                for (Long nID = 726L; nID < 734L; nID++) {
                    am = getListOfHistoryEvents(nID);
                    arr = getCountFromStatisticArrayMap(am);
                    nCount += arr[0];
                    nSumRate += arr[1];
                }
                LOG.info("[getListOfHistoryEvents]nCount(summ)=" + nCount);
                nRate = nSumRate / nCount;
                LOG.info("[getListOfHistoryEvents]nRAte(summ)=" + nRate);
            }
            LOG.info("[getListOfHistoryEvents]nCount=" + nCount);
            currMapWithName.put("nCount", nCount);
            currMapWithName.put("nRate", nRate);
            currMapWithName.put("nTimeHours", averageDuration != null ? averageDuration : "0");
            listOfHistoryEventsWithMeaningfulNames.add(currMapWithName);
        }
        return listOfHistoryEventsWithMeaningfulNames;
    }

    @RequestMapping(value = "/getStartFormByTask", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String getStartFormByTask(
            @RequestParam(value = "nID_Subject") Long nID_Subject,
            @RequestParam(value = "nID_Server", required = false, defaultValue = "0") Integer nID_Server,
            @RequestParam(value = "nID_Service") Long nID_Service,
            @RequestParam(value = "sID_UA") String sID_UA) throws RecordNotFoundException {
        String URI = "/service/rest/tasks/getStartFormData?nID_Task=";

        HistoryEvent_Service historyEventService = historyEventServiceDao
                .getLastTaskHistory(nID_Subject, nID_Service,
                        sID_UA);
        if (historyEventService == null) {
            throw new RecordNotFoundException("HistoryEvent_Service wasn't found.");
        }

        Long nID_Task = historyEventService.getnID_Task();
        nID_Server = historyEventService.getnID_Server();
        nID_Server = nID_Server == null ? 0 : nID_Server;

        Optional<Server> serverOpt = serverDao.findById(new Long(nID_Server));
        if (!serverOpt.isPresent()) {
            throw new RecordNotFoundException("Server with nID_Server " + nID_Server + " wasn't found.");
        }
        Server server = serverOpt.get();
        String serverUrl = server.getsURL();
        if (server.getId().equals(0L)) {
            serverUrl = "https://test.region.igov.org.ua/wf";
        }

        serverUrl = serverUrl + URI + nID_Task;

        String sUser = generalConfig.sAuthLogin();
        String sPassword = generalConfig.sAuthPassword();
        String sAuth = LiqBuyUtil.base64_encode(sUser + ":" + sPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + sAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        LOG.info("Calling URL with parametes " + serverUrl);
        ResponseEntity<String> result = null;

        try {
            result = template.exchange(serverUrl, HttpMethod.GET, httpEntity, String.class);
        } catch (RestClientException e) {
            LOG.warn(e);
            throw new RecordNotFoundException();
        }

        return result.getBody();
    }

    private Long addSomeServicesCount(Long nCount, Long nID_Service, Region region) {
        //currMapWithName.put("nCount", currMap.get("nCount"));
              /*https://igov.org.ua/service/661/general - 43
                https://igov.org.ua/service/655/generall - 75
				https://igov.org.ua/service/176/general - 546
				https://igov.org.ua/service/654/general - 307   */

        boolean magicID = "1200000000".equals(region.getsID_UA());
        if (nID_Service == 661) {
            if (magicID) {
                nCount += 43;
            }
        } else if (nID_Service == 665) {
            if (magicID) {
                nCount += 75;
            }
        } else if (nID_Service == 176) {
            if (magicID) {
                nCount += 546;
            }
        } else if (nID_Service == 654) {
            if (magicID) {
                nCount += 307;
            }
        } else if (nID_Service == 159) {
				/*https://igov.org.ua/service/159/general
				Днепропетровская область - 53
                Киевская область - 69
                1;Дніпропетровська;"1200000000"
                5;Київ;"8000000000"
                16;Київська;"3200000000"*/
            if (magicID) {
                nCount += 53;
            } else if ("8000000000".equals(region.getsID_UA()) || "3200000000".equals(region.getsID_UA())) {
                nCount += 69;
            }
        } else if (nID_Service == 1) {
			 /*https://igov.org.ua/service/1/general
			Днепропетровская область - 812*/
			  /*if("".equals(region.getsID_UA())){
				nCount+=53;
              }else if("".equals(region.getsID_UA())){
                nCount+=69;
              }*/
            if (magicID) {
                nCount += 812;
            }
        } else if (nID_Service == 772) {
            if (magicID) {
                nCount += 96;
            }
        } else if (nID_Service == 4) {
			  /*
			https://igov.org.ua/service/4/general -
            Днепропетровская область - услуга временно приостановлена
            по иным регионам заявок вне было.
              */
            nCount += 0;
        } else if (nID_Service == 0) {
            nCount += 0;
            //region.getsID_UA()
        }
        return nCount;
    }

    private Long[] getCountFromStatisticArrayMap(List<Map<String, Object>> am) {
        Long n = 0L;
        Long nRate = 0L;
        LOG.info("[getCountFromStatisticArrayMap] am=" + am);
        if (am.size() > 0) {
            if (am.get(0).containsKey("nCount")) {
                String s = am.get(0).get("nCount") + "";
                if (!"null".equals(s)) {
                    n = new Long(s);
                    LOG.info("[getCountFromStatisticArrayMap] n=" + n);
                }
            }
            if (am.get(0).containsKey("nRate")) {
                String s = am.get(0).get("nRate") + "";
                if (!"null".equals(s)) {
                    nRate = new Long(s);
                    LOG.info("[getCountFromStatisticArrayMap] nRate=" + n);
                }
            }
        }
        return new Long[] { n, nRate * n };
    }

    private void setHistoryEvent(HistoryEventType eventType,
            Long nID_Subject, Map<String, String> mParamMessage) {
        try {
            String eventMessage = HistoryEventMessage.createJournalMessage(
                    eventType, mParamMessage);
            historyEventDao.setHistoryEvent(nID_Subject, eventType.getnID(),
                    eventMessage, eventMessage);
        } catch (IOException e) {
            LOG.error("error during creating HistoryEvent", e);
        }
    }

}
