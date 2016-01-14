/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.igov.service.business.action.task.core;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.*;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.igov.service.business.action.event.HistoryEventService;
import org.igov.service.business.action.task.form.QueueDataFormType;
import org.igov.io.GeneralConfig;
import org.igov.service.business.access.BankIDConfig;
import org.igov.io.db.kv.temp.IBytesDataInmemoryStorage;
import org.igov.io.mail.Mail;
import org.igov.model.flow.FlowSlotTicketDao;
import org.igov.service.exception.CommonServiceException;
import org.igov.service.exception.CRCInvalidException;
import org.igov.service.exception.RecordNotFoundException;
import org.igov.service.exception.TaskAlreadyUnboundException;
import org.igov.util.EGovStringUtils;
import org.igov.util.convert.AlgorithmLuna;
import org.igov.util.convert.JSExpressionUtil;
import org.igov.util.convert.JsonDateTimeSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author bw
 */
//@Component
@Service
public class ActionTaskService {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss", Locale.ENGLISH);
    public static final String CANCEL_INFO_FIELD = "sCancelInfo";
    private static final int DEFAULT_REPORT_FIELD_SPLITTER = 59;
    private static final int MILLIS_IN_HOUR = 1000 * 60 * 60;

    private static final Logger LOG = LoggerFactory.getLogger(ActionTaskService.class);

    @Autowired
    public BankIDConfig bankIDConfig;
    //@Autowired
    //private ExceptionCommonController exceptionController;
    //@Autowired
    //private ExceptionCommonController exceptionController;
    @Autowired
    public RuntimeService runtimeService;
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    @Autowired
    public TaskService taskService;
    //private HistoryService historyService;
    @Autowired
    public HistoryEventService historyEventService;
    //private FormService formService;
    @Autowired
    public Mail oMail;
    //@Autowired
    //private RuntimeService runtimeService;
    //@Autowired
    //private TaskService taskService;
    @Autowired
    public RepositoryService repositoryService;
    @Autowired
    public FormService formService;
    @Autowired
    public IBytesDataInmemoryStorage oBytesDataInmemoryStorage;
    @Autowired
    public IdentityService identityService;
    @Autowired
    public HistoryService historyService;
    @Autowired
    public GeneralConfig generalConfig;
    @Autowired
    private FlowSlotTicketDao flowSlotTicketDao;
    
    public static String parseEnumValue(String sEnumName) {
        LOG.info("sEnumName=" + sEnumName);
        String res = StringUtils.defaultString(sEnumName);
        LOG.info("sEnumName(2)=" + sEnumName);
        if (res.contains("|")) {
            String[] as = sEnumName.split("\\|");
            LOG.info("as.length - 1=" + (as.length - 1));
            LOG.info("as=" + as);
            res = as[as.length - 1];
        }
        return res;
    }

    /*@ExceptionHandler({CRCInvalidException.class, EntityNotFoundException.class, RecordNotFoundException.class, TaskAlreadyUnboundException.class})
    @ResponseBody
    public ResponseEntity<String> handleAccessException(Exception e) throws CommonServiceException {
    return exceptionController.catchActivitiRestException(new CommonServiceException(
    ExceptionCommonController.BUSINESS_ERROR_CODE,
    e.getMessage(), e,
    HttpStatus.FORBIDDEN));
    }*/
    public static String parseEnumProperty(FormProperty property) {
        Object oValues = property.getType().getInformation("values");
        if (oValues instanceof Map) {
            Map<String, String> mValue = (Map) oValues;
            LOG.info("m=" + mValue);
            String sName = property.getValue();
            LOG.info("sName=" + sName);
            String sValue = mValue.get(sName);
            LOG.info("sValue=" + sValue);
            return parseEnumValue(sValue);
        } else {
            LOG.error("Cannot parse values for property - {}", property);
            return "";
        }
    }

    public static String parseEnumProperty(FormProperty property, String sName) {
        Object oValues = property.getType().getInformation("values");
        if (oValues instanceof Map) {
            Map<String, String> mValue = (Map) oValues;
            LOG.info("m=" + mValue);
            LOG.info("sName=" + sName);
            String sValue = mValue.get(sName);
            LOG.info("sValue=" + sValue);
            return parseEnumValue(sValue);
        } else {
            LOG.error("Cannot parse values for property - {}", property);
            return "";
        }
    }

    public static String createTable_TaskProperties(String soData) {
        if (soData == null || "[]".equals(soData) || "".equals(soData)) {
            return "";
        }
        StringBuilder tableStr = new StringBuilder("Поле \t/ Тип \t/ Поточне значення\n");
        JSONObject jsnobject = new JSONObject("{ \"soData\":" + soData + "}");
        JSONArray jsonArray = jsnobject.getJSONArray("soData");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject record = jsonArray.getJSONObject(i);
            tableStr.append(record.opt("id") != null ? record.get("id") : "?")
                    .append(" \t ")
                    .append(record.opt("type") != null ? record.get("type").toString() : "??")
                    .append(" \t ")
                    .append(record.opt("value") != null ? record.get("value").toString() : "")
                    .append(" \n");
        }
        return tableStr.toString();
    }

    public TaskQuery buildTaskQuery(String sLogin, String bAssigned) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (bAssigned != null) {
            if (!Boolean.valueOf(bAssigned)) {
                taskQuery.taskUnassigned();
                if (sLogin != null && !sLogin.isEmpty()) {
                    taskQuery.taskCandidateUser(sLogin);
                }
            } else if (sLogin != null && !sLogin.isEmpty()) {
                taskQuery.taskAssignee(sLogin);
            }
        } else {
            if (sLogin != null && !sLogin.isEmpty()) {
                taskQuery.taskCandidateOrAssigned(sLogin);
            }
        }
        return taskQuery;
    }

    public void cancelTasksInternal(Long nID_Protected, String sInfo) throws CommonServiceException, CRCInvalidException, RecordNotFoundException, TaskAlreadyUnboundException {
        String processInstanceId = getOriginalProcessInstanceId(nID_Protected);
        getTasksByProcessInstanceId(processInstanceId);
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        FormData formData = formService.getStartFormData(processInstance.getProcessDefinitionId());
        List<String> propertyIds = AbstractModelTask.getListField_QueueDataFormType(formData);
        List<String> queueDataList = AbstractModelTask.getVariableValues(runtimeService, processInstanceId, propertyIds);
        if (queueDataList.isEmpty()) {
            LOG.error(String.format("Queue data list for Process Instance [id = '%s'] not found", processInstanceId));
            throw new RecordNotFoundException("\u041c\u0435\u0442\u0430\u0434\u0430\u043d\u043d\u044b\u0435 \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043d\u043d\u043e\u0439 \u043e\u0447\u0435\u0440\u0435\u0434\u0438 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u044b");
        }
        for (String queueData : queueDataList) {
            Map<String, Object> m = QueueDataFormType.parseQueueData(queueData);
            long nID_FlowSlotTicket = QueueDataFormType.get_nID_FlowSlotTicket(m);
            if (!flowSlotTicketDao.unbindFromTask(nID_FlowSlotTicket)) {
                throw new TaskAlreadyUnboundException("\u0417\u0430\u044f\u0432\u043a\u0430 \u0443\u0436\u0435 \u043e\u0442\u043c\u0435\u043d\u0435\u043d\u0430");
            }
        }
        runtimeService.setVariable(processInstanceId, CANCEL_INFO_FIELD, String.format(
                "[%s] \u0417\u0430\u044f\u0432\u043a\u0430 \u0441\u043a\u0430\u0441\u043e\u0432\u0430\u043d\u0430: %s",
                DateTime.now(), sInfo == null ? "" : sInfo));
    }

    public String createEmailBody(Long nID_Protected, String soData, String sBody, String sToken) throws UnsupportedEncodingException {
        StringBuilder emailBody = new StringBuilder(sBody);
        emailBody.append("<br/>").append(createTable_TaskProperties(soData)).append("<br/>");
        String link = (new StringBuilder(generalConfig.sHostCentral()).append("/order/search?nID=").append(nID_Protected).append("&sToken=").append(sToken)).toString();
        emailBody.append(link).append("<br/>");
        return emailBody.toString();
    }

    private String addCalculatedFields(String saFieldsCalc, TaskInfo curTask, String currentRow) {
        HistoricTaskInstance details = historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskId(curTask.getId()).singleResult();
        LOG.info("Process variables of the task " + curTask.getId() + ":" + details.getProcessVariables());
        if (details != null && details.getProcessVariables() != null) {
            Set<String> headersExtra = new HashSet<>();
            for (String key : details.getProcessVariables().keySet()) {
                if (!key.startsWith("sBody")) {
                    headersExtra.add(key);
                }
            }
            saFieldsCalc = StringUtils.substringAfter(saFieldsCalc, "\"");
            saFieldsCalc = StringUtils.substringBeforeLast(saFieldsCalc, "\"");
            for (String expression : saFieldsCalc.split(";")) {
                String variableName = StringUtils.substringBefore(expression, "=");
                String condition = StringUtils.substringAfter(expression, "=");
                LOG.info("Checking variable with name " + variableName + " and condition " + condition + " from expression:" + expression);
                try {
                    Object conditionResult = getObjectResultofCondition(headersExtra, details, details, condition);
                    currentRow = currentRow + ";" + conditionResult;
                    LOG.info("Adding calculated field " + variableName + " with the value " + conditionResult);
                } catch (Exception e) {
                    LOG.error("Error occured while processing variable " + variableName, e);
                }
            }
        }
        return currentRow;
    }

    public ResponseEntity<String> unclaimUserTask(String nID_UserTask) throws CommonServiceException, RecordNotFoundException {
        Task task = taskService.createTaskQuery().taskId(nID_UserTask).singleResult();
        if (task == null) {
            throw new RecordNotFoundException();
        }
        if (task.getAssignee() == null || task.getAssignee().isEmpty()) {
            return new ResponseEntity<>("Not assigned UserTask", HttpStatus.OK);
        }
        taskService.unclaim(task.getId());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    public void setInfo_ToActiviti(String snID_Process, String saField, String sBody) {
        try {
            LOG.info(String.format("try to set saField=%s and sBody=%s to snID_Process=%s", saField, sBody, snID_Process));
            runtimeService.setVariable(snID_Process, "saFieldQuestion", saField);
            runtimeService.setVariable(snID_Process, "sQuestion", sBody);
            LOG.info(String.format("completed set saField=%s and sBody=%s to snID_Process=%s", saField, sBody, snID_Process));
        } catch (Exception ex) {
            LOG.error("error during set variables to Activiti!", ex);
        }
    }

    public void loadFormPropertiesToMap(FormData formData, Map<String, Object> variables, Map<String, String> formValues) {
        List<FormProperty> aFormProperty = formData.getFormProperties();
        if (!aFormProperty.isEmpty()) {
            for (FormProperty oFormProperty : aFormProperty) {
                String sType = oFormProperty.getType().getName();
                if (variables.containsKey(oFormProperty.getId())) {
                    if ("enum".equals(sType)) {
                        Object variable = variables.get(oFormProperty.getId());
                        if (variable != null) {
                            String sID_Enum = variable.toString();
                            LOG.info("execution.getVariable()(sID_Enum)=" + sID_Enum);
                            String sValue = parseEnumProperty(oFormProperty, sID_Enum);
                            formValues.put(oFormProperty.getId(), sValue);
                        }
                    } else {
                        formValues.put(oFormProperty.getId(), variables.get(oFormProperty.getId()) != null ? String.valueOf(variables.get(oFormProperty.getId())) : null);
                    }
                }
            }
        }
    }

    public Date getBeginDate(Date date) {
        if (date == null) {
            return DateTime.now().minusDays(1).toDate();
        }
        return date;
    }

    private Object getObjectResultofCondition(Set<String> headersExtra, HistoricTaskInstance currTask, HistoricTaskInstance details, String condition) throws ScriptException, NoSuchMethodException {
        Map<String, Object> params = new HashMap<>();
        for (String headerExtra : headersExtra) {
            Object variableValue = details.getProcessVariables().get(headerExtra);
            String propertyValue = EGovStringUtils.toStringWithBlankIfNull(variableValue);
            params.put(headerExtra, propertyValue);
        }
        params.put("sAssignedLogin", currTask.getAssignee());
        params.put("sID_UserTask", currTask.getTaskDefinitionKey());
        LOG.info("Calculating expression with params: " + params);
        Object conditionResult = new JSExpressionUtil().getObjectResultOfCondition(new HashMap<String, Object>(),
                params, condition);
        LOG.info("Condition of the expression is " + conditionResult.toString());
        return conditionResult;
    }

    public Task findBasicTask(String ID_task) {
        boolean nextCycle = true;
        Task task = getTaskByID(ID_task);
        while (nextCycle) {
            if (task.getParentTaskId() == null || task.getParentTaskId().equals("")) {
                nextCycle = false;
            } else {
                task = getTaskByID(task.getParentTaskId());
            }
        }
        return task;
    }

    protected void processExtractFieldsParameter(Set<String> headersExtra, HistoricTaskInstance currTask, String saFields, Map<String, Object> line) {
        HistoricTaskInstance details = historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskId(currTask.getId()).singleResult();
        LOG.info("Process variables of the task " + currTask.getId() + ":" + details.getProcessVariables());
        if (details != null && details.getProcessVariables() != null) {
            LOG.info("Cleaned saFields:" + saFields);
            String[] expressions = saFields.split(";");
            if (expressions != null) {
                for (String expression : expressions) {
                    String variableName = StringUtils.substringBefore(expression, "=");
                    String condition = StringUtils.substringAfter(expression, "=");
                    LOG.info("Checking variable with name " + variableName + " and condition " + condition + " from expression:" + expression);
                    try {
                        Object conditionResult = getObjectResultofCondition(headersExtra, currTask, details, condition);
                        line.put(variableName, conditionResult);
                    } catch (Exception e) {
                        LOG.error("Error occured while processing variable " + variableName, e);
                    }
                }
            }
        }
    }

    public void loadCandidateStarterGroup(ProcessDefinition processDef, Set<String> candidateCroupsToCheck) {
        List<IdentityLink> identityLinks = repositoryService.getIdentityLinksForProcessDefinition(processDef.getId());
        LOG.info(String.format("Found %d identity links for the process %s", identityLinks.size(), processDef.getKey()));
        for (IdentityLink identity : identityLinks) {
            if (IdentityLinkType.CANDIDATE.equals(identity.getType())) {
                String groupId = identity.getGroupId();
                candidateCroupsToCheck.add(groupId);
                LOG.info(String.format("Added candidate starter group %s ", groupId));
            }
        }
    }

    //@RequestMapping("/web")
    //public class StartWebController {
    /*private final Logger LOG = LoggerFactory
    .getLogger(StartWebController.class);
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @RequestMapping(value = "/activiti/index", method = RequestMethod.GET)
    public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("index");
    List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().latestVersion()
    .list();
    modelAndView.addObject("processList", processDefinitions);
    return modelAndView;
    }
    @RequestMapping(value = "/activiti/startForm/{id}", method = RequestMethod.GET)
    public ModelAndView startForm(@PathVariable("id") String id) {
    StartFormData sfd = formService.getStartFormData(id);
    List<FormProperty> fpList = sfd.getFormProperties();
    ModelAndView modelAndView = new ModelAndView("startForm");
    modelAndView.addObject("fpList", fpList);
    modelAndView.addObject("id", id);
    return modelAndView;
    }
    @RequestMapping(value = "/activiti/startProcess/{id}", method = RequestMethod.POST)
    public ModelAndView startProcess(@PathVariable("id") String id, @RequestParam Map<String, String> params) {
    ProcessInstance pi = formService.submitStartFormData(id, params);
    ModelAndView modelAndView = new ModelAndView("startedProcess");
    modelAndView.addObject("pi", pi.getProcessInstanceId());
    modelAndView.addObject("bk", pi.getBusinessKey());
    return modelAndView;
    }*/
    public String getOriginalProcessInstanceId(Long nID_Protected) throws CRCInvalidException {
        return Long.toString(AlgorithmLuna.getValidatedOriginalNumber(nID_Protected));
    }

    public Attachment getAttachment(String attachmentId, String taskId, Integer nFile, String processInstanceId) {
        List<Attachment> attachments = taskService.getProcessInstanceAttachments(processInstanceId);
        Attachment attachmentRequested = null;
        for (int i = 0; i < attachments.size(); i++) {
            if (attachments.get(i).getId().equalsIgnoreCase(attachmentId) || (null != nFile && nFile.equals(i + 1))) {
                attachmentRequested = attachments.get(i);
                break;
            }
        }
        if (attachmentRequested == null && !attachments.isEmpty()) {
            attachmentRequested = attachments.get(0);
        }
        if (attachmentRequested == null) {
            throw new ActivitiObjectNotFoundException("Attachment for taskId '" + taskId + "' not found.", Attachment.class);
        }
        return attachmentRequested;
    }

    public Attachment getAttachment(String attachmentId, String taskId, String processInstanceId) {
        List<Attachment> attachments = taskService.getProcessInstanceAttachments(processInstanceId);
        Attachment attachmentRequested = null;
        for (int i = 0; i < attachments.size(); i++) {
            if (attachments.get(i).getId().equalsIgnoreCase(attachmentId)) {
                attachmentRequested = attachments.get(i);
                break;
            }
        }
        if (attachmentRequested == null) {
            throw new ActivitiObjectNotFoundException("Attachment for taskId '" + taskId + "' not found.", Attachment.class);
        }
        return attachmentRequested;
    }

    public void fillTheCSVMapHistoricTasks(String sID_BP, Date dateAt, Date dateTo, List<HistoricTaskInstance> foundResults, SimpleDateFormat sDateCreateDF, List<Map<String, Object>> csvLines, String pattern, Set<String> tasksIdToExclude, String saFieldsCalc, String[] headers) {
        if (CollectionUtils.isEmpty(foundResults)) {
            LOG.info(String.format("No historic tasks found for business process %s for date period %s - %s", sID_BP, DATE_TIME_FORMAT.format(dateAt), DATE_TIME_FORMAT.format(dateTo)));
            return;
        }
        LOG.info(String.format("Found %s historic tasks for business process %s for date period %s - %s", foundResults.size(), sID_BP, DATE_TIME_FORMAT.format(dateAt), DATE_TIME_FORMAT.format(dateTo)));
        if (pattern != null) {
            LOG.info("List of fields to retrieve: " + pattern);
        } else {
            LOG.info("Will retreive all fields from tasks");
        }
        LOG.info("Tasks to skip" + tasksIdToExclude);
        for (HistoricTaskInstance curTask : foundResults) {
            if (tasksIdToExclude.contains(curTask.getId())) {
                LOG.info("Skipping historic task " + curTask.getId() + " from processing as it is already in the response");
                continue;
            }
            String currentRow = pattern;
            Map<String, Object> variables = curTask.getProcessVariables();
            LOG.info("Loaded historic variables for the task " + curTask.getId() + "|" + variables);
            currentRow = replaceFormProperties(currentRow, variables);
            if (saFieldsCalc != null) {
                currentRow = addCalculatedFields(saFieldsCalc, curTask, currentRow);
            }
            if (pattern != null) {
                currentRow = replaceReportFields(sDateCreateDF, curTask, currentRow);
                currentRow = currentRow.replaceAll("\\$\\{.*?\\}", "");
            }
            String[] values = currentRow.split(";");
            if (headers.length != values.length) {
                LOG.info("Size of header : " + headers.length + " Size of values array:" + values.length);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < headers.length; i++) {
                    sb.append(headers[i]);
                    sb.append(";");
                }
                LOG.info("headers:" + sb.toString());
                sb = new StringBuilder();
                for (int i = 0; i < values.length; i++) {
                    sb.append(values[i]);
                    sb.append(";");
                }
                LOG.info("values:" + sb.toString());
            }
            Map<String, Object> currRow = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                currRow.put(headers[i], values[i]);
            }
            csvLines.add(currRow);
        }
    }

    /*
     * private void clearEmptyValues(Map<String, Object> params) {
     * Iterator<String> iterator = params.keySet().iterator(); while
     * (iterator.hasNext()){ String key = iterator.next(); if (params.get(key)
     * == null){ iterator.remove(); } } }
     */
    private void addTasksDetailsToLine(Set<String> headersExtra, HistoricTaskInstance currTask, Map<String, Object> resultLine) {
        LOG.debug("currTask: " + currTask.getId());
        HistoricTaskInstance details = historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskId(currTask.getId()).singleResult();
        if (details != null && details.getProcessVariables() != null) {
            for (String headerExtra : headersExtra) {
                Object variableValue = details.getProcessVariables().get(headerExtra);
                resultLine.put(headerExtra, variableValue);
            }
        }
    }

    private Set<String> findExtraHeadersForDetail(List<HistoricTaskInstance> foundResults, List<String> headers) {
        Set<String> headersExtra = new TreeSet<>();
        for (HistoricTaskInstance currTask : foundResults) {
            HistoricTaskInstance details = historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskId(currTask.getId()).singleResult();
            if (details != null && details.getProcessVariables() != null) {
                LOG.info(" proccessVariavles: " + details.getProcessVariables());
                for (String key : details.getProcessVariables().keySet()) {
                    if (!key.startsWith("sBody")) {
                        headersExtra.add(key);
                    }
                }
            }
        }
        headers.addAll(headersExtra);
        return headersExtra;
    }

    private String replaceFormProperties(String currentRow, Map<String, Object> data) {
        String res = currentRow;
        for (Map.Entry<String, Object> property : data.entrySet()) {
            LOG.info(String.format("Matching property %s:%s with fieldNames", property.getKey(), property.getValue()));
            if (currentRow != null && res.contains("${" + property.getKey() + "}")) {
                LOG.info(String.format("Found field with id %s in the pattern. Adding value to the result", "${" + property.getKey() + "}"));
                if (property.getValue() != null) {
                    String sValue = property.getValue().toString();
                    LOG.info("sValue=" + sValue);
                    if (sValue != null) {
                        LOG.info(String.format("Replacing field with the value %s", sValue));
                        res = res.replace("${" + property.getKey() + "}", sValue);
                    }
                }
            }
        }
        return res;
    }

    private String replaceFormProperties(String currentRow, TaskFormData data) {
        String res = currentRow;
        for (FormProperty property : data.getFormProperties()) {
            LOG.info(String.format("Matching property %s:%s:%s with fieldNames", property.getId(), property.getName(), property.getType().getName()));
            if (currentRow != null && res.contains("${" + property.getId() + "}")) {
                LOG.info(String.format("Found field with id %s in the pattern. Adding value to the result", "${" + property.getId() + "}"));
                String sValue = getPropertyValue(property);
                if (sValue != null) {
                    LOG.info(String.format("Replacing field with the value %s", sValue));
                    res = res.replace("${" + property.getId() + "}", sValue);
                }
            }
        }
        return res;
    }

    public Charset getCharset(String sID_Codepage) {
        Charset charset;
        String codePage = sID_Codepage.replaceAll("-", "");
        try {
            if ("win1251".equalsIgnoreCase(codePage) || "CL8MSWIN1251".equalsIgnoreCase(codePage)) {
                codePage = "CP1251";
            }
            charset = Charset.forName(codePage);
            LOG.debug("use charset - {}", charset);
        } catch (IllegalArgumentException e) {
            LOG.error("Do not support charset - {}", codePage, e);
            throw new ActivitiObjectNotFoundException("Statistics for the business task for charset '" + codePage + "' cannot be construct.", Task.class, e);
        }
        return charset;
    }

    public String getFileExtention(MultipartFile file) {
        String[] parts = file.getOriginalFilename().split("\\.");
        if (parts.length != 0) {
            return parts[parts.length - 1];
        }
        return "";
    }

    public String getFileExtention(String fileName) {
        String[] parts = fileName.split("\\.");
        if (parts.length != 0) {
            return parts[parts.length - 1];
        }
        return "";
    }
    /*private static class TaskAlreadyUnboundException extends Exception {
    private TaskAlreadyUnboundException(String message) {
    super(message);
    }
    }*/

    public String getCreateTime(Task task) {
        DateTimeFormatter formatter = JsonDateTimeSerializer.DATETIME_FORMATTER;
        Date date = task.getCreateTime();
        return formatter.print(date.getTime());
    }

    public Map<String, Object> createCsvLine(boolean bDetail, Set<String> headersExtra, HistoricTaskInstance currTask, String saFields) {
        Map<String, Object> line = new HashMap<>();
        line.put("nID_Process", currTask.getProcessInstanceId());
        line.put("sLoginAssignee", currTask.getAssignee());
        Date startDate = currTask.getStartTime();
        line.put("sDateTimeStart", DATE_TIME_FORMAT.format(startDate));
        line.put("nDurationMS", String.valueOf(currTask.getDurationInMillis()));
        long durationInHours = currTask.getDurationInMillis() / MILLIS_IN_HOUR;
        line.put("nDurationHour", String.valueOf(durationInHours));
        line.put("sName", currTask.getName());
        if (bDetail) {
            addTasksDetailsToLine(headersExtra, currTask, line);
        }
        if (saFields != null) {
            processExtractFieldsParameter(headersExtra, currTask, saFields, line);
        }
        return line;
    }

    public String getSeparator(String sID_BP, String nASCI_Spliter) {
        if (nASCI_Spliter == null) {
            return String.valueOf(Character.toChars(DEFAULT_REPORT_FIELD_SPLITTER));
        }
        if (!StringUtils.isNumeric(nASCI_Spliter)) {
            LOG.error("ASCI code is not a number {}", nASCI_Spliter);
            throw new ActivitiObjectNotFoundException("Statistics for the business task with name '" + sID_BP + "' not found. Wrong splitter.", Task.class);
        }
        return String.valueOf(Character.toChars(Integer.valueOf(nASCI_Spliter)));
    }

    public String formHeader(String saFields, List<HistoricTaskInstance> foundHistoricResults, String saFieldsCalc) {
        String res = null;
        if (saFields != null && !"".equals(saFields.trim())) {
            LOG.info("Fields have custom header names");
            StringBuilder sb = new StringBuilder();
            String[] fields = saFields.split(";");
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].contains("\\=")) {
                    sb.append(StringUtils.substringBefore(fields[i], "\\="));
                } else {
                    sb.append(fields[i]);
                }
                if (i < fields.length - 1) {
                    sb.append(";");
                }
            }
            res = sb.toString();
            res = res.replaceAll("\\$\\{", "");
            res = res.replaceAll("\\}", "");
            LOG.info("Formed header from list of fields: " + res);
        } else {
            if (foundHistoricResults != null && foundHistoricResults.size() > 0) {
                HistoricTaskInstance historicTask = foundHistoricResults.get(0);
                Set<String> keys = historicTask.getProcessVariables().keySet();
                StringBuilder sb = new StringBuilder();
                Iterator<String> iter = keys.iterator();
                while (iter.hasNext()) {
                    sb.append(iter.next());
                    if (iter.hasNext()) {
                        sb.append(";");
                    }
                }
                res = sb.toString();
            }
            LOG.info("Formed header from all the fields of a task: " + res);
        }
        if (saFieldsCalc != null) {
            saFieldsCalc = StringUtils.substringAfter(saFieldsCalc, "\"");
            saFieldsCalc = StringUtils.substringBeforeLast(saFieldsCalc, "\"");
            String[] params = saFieldsCalc.split(";");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < params.length; i++) {
                String currParam = params[i];
                String cutHeader = StringUtils.substringBefore(currParam, "=");
                LOG.info("Adding header to the csv file from saFieldsCalc: " + cutHeader);
                sb.append(cutHeader);
                if (i < params.length - 1) {
                    sb.append(";");
                }
            }
            res = res + ";" + sb.toString();
            LOG.info("Header with calculated fields: " + res);
        }
        return res;
    }

    public Task getTaskByID(String taskID) {
        return taskService.createTaskQuery().taskId(taskID).singleResult();
    }

    private List<Task> getTasksByProcessInstanceId(String processInstanceID) throws RecordNotFoundException {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceID).list();
        if (tasks == null || tasks.isEmpty()) {
            LOG.error(String.format("Tasks for Process Instance [id = '%s'] not found", processInstanceID));
            throw new RecordNotFoundException();
        }
        return tasks;
    }

    private String getPropertyValue(FormProperty property) {
        String sValue;
        String sType = property.getType().getName();
        LOG.info("sType=" + sType);
        if ("enum".equalsIgnoreCase(sType)) {
            sValue = parseEnumProperty(property);
        } else {
            sValue = property.getValue();
        }
        LOG.info("sValue=" + sValue);
        return sValue;
    }

    public Set<String> findExtraHeaders(Boolean bDetail, List<HistoricTaskInstance> foundResults, List<String> headers) {
        if (bDetail) {
            Set<String> headersExtra = findExtraHeadersForDetail(foundResults, headers);
            return headersExtra;
        } else {
            return new TreeSet<>();
        }
    }

    private String replaceReportFields(SimpleDateFormat sDateCreateDF, Task curTask, String currentRow) {
        String res = currentRow;
        for (TaskReportField field : TaskReportField.values()) {
            if (res.contains(field.getPattern())) {
                res = field.replaceValue(res, curTask, sDateCreateDF);
            }
        }
        return res;
    }

    private String replaceReportFields(SimpleDateFormat sDateCreateDF, HistoricTaskInstance curTask, String currentRow) {
        String res = currentRow;
        for (TaskReportField field : TaskReportField.values()) {
            if (res.contains(field.getPattern())) {
                res = field.replaceValue(res, curTask, sDateCreateDF);
            }
        }
        return res;
    }

    /*private String createTable(String soData) throws UnsupportedEncodingException {
        if (soData == null || "[]".equals(soData) || "".equals(soData)) {
            return "";
        }
        StringBuilder tableStr = new StringBuilder("<table><tr><th>Поле</th><th>Тип </th><th> Поточне значення</th></tr>");
        JSONObject jsnobject = new JSONObject("{ soData:" + soData + "}");
        JSONArray jsonArray = jsnobject.getJSONArray("soData");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject record = jsonArray.getJSONObject(i);
            tableStr.append("<tr><td>")
                    .append(record.opt("id") != null ? record.get("id") : "?")
                    .append("</td><td>")
                    .append(record.opt("type") != null ? record.get("type").toString() : "??")
                    .append("</td><td>")
                    .append(record.opt("value") != null ? record.get("value")
                            .toString() : "").append("</td></tr>");
        }
        tableStr.append("</table>");
        return tableStr.toString();
    }*/

    public void loadCandidateGroupsFromTasks(ProcessDefinition processDef, Set<String> candidateCroupsToCheck) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDef.getId());
        for (FlowElement flowElement : bpmnModel.getMainProcess().getFlowElements()) {
            if (flowElement instanceof UserTask) {
                UserTask userTask = (UserTask) flowElement;
                List<String> candidateGroups = userTask.getCandidateGroups();
                if (candidateGroups != null && !candidateGroups.isEmpty()) {
                    candidateCroupsToCheck.addAll(candidateGroups);
                    LOG.info(String.format("Added candidate groups %s from user task %s", candidateGroups,
                            userTask.getId()));
                }
            }
        }
    }
    
    /**
     * saFeilds paramter may contain name of headers or can be empty. Before
     * forming the result - we need to cut header names
     *
     //     * @param saFields
     //     * @param foundHistoricResults
     //     * @return
     */
    public String processSaFields(String saFields, List<HistoricTaskInstance> foundHistoricResults) {
        String res = null;
        if (saFields != null) {
            LOG.info("saFields has custom header names");
            StringBuilder sb = new StringBuilder();
            String[] fields = saFields.split(";");
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].contains("=")) {
                    sb.append(StringUtils.substringAfter(fields[i], "="));
                } else {
                    sb.append(fields[i]);
                }
                if (i < fields.length - 1) {
                    sb.append(";");
                }
            }
            res = sb.toString();
        } else {
            if (foundHistoricResults != null && foundHistoricResults.size() > 0) {
                HistoricTaskInstance historicTask = foundHistoricResults.get(0);
                Set<String> keys = historicTask.getProcessVariables().keySet();
                StringBuilder sb = new StringBuilder();
                Iterator<String> iter = keys.iterator();
                while (iter.hasNext()) {
                    sb.append("${").append(iter.next()).append("}");
                    if (iter.hasNext()) {
                        sb.append(";");
                    }
                }
                res = sb.toString();
            }
            LOG.info("Formed header from all the fields of a task: " + res);
        }
        return res;
    }

    // private Long getProcessId(String sID_Order, Long nID_Protected, Long
    // nID_Process) {
    // Long result = null;
    // if (nID_Process != null) {
    // result = nID_Process;
    // } else if (nID_Protected != null) {
    // result = AlgorithmLuna.getOriginalNumber(nID_Protected);
    // } else if (sID_Order != null && !sID_Order.isEmpty()) {
    // Long protectedId;
    // if (sID_Order.contains("-")) {
    // int dash_position = sID_Order.indexOf("-");
    // protectedId = Long.valueOf(sID_Order.substring(dash_position + 1));
    // } else {
    // protectedId = Long.valueOf(sID_Order);
    // }
    // result = AlgorithmLuna.getOriginalNumber(protectedId);
    // }
    // return result;
    // }
    public void sendEmail(String sHead, String sBody, String recipient) throws EmailException {
        oMail.reset();
        oMail._To(recipient)._Head(sHead)._Body(sBody);
        oMail.send();
    }

    public Long getIDProtectedFromIDOrder(String sID_order) {
        String ID_Protected = "";
        int hyphenPosition = sID_order.lastIndexOf("-");
        if (hyphenPosition < 0) {
            ID_Protected = sID_order;
        } else {
            for (int i = hyphenPosition + 1; i < sID_order.length(); i++) {
                ID_Protected = ID_Protected + sID_order.charAt(i);
            }
        }
        return Long.parseLong(ID_Protected);
    }

    public Date getEndDate(Date date) {
        if (date == null) {
            return DateTime.now().toDate();
        }
        return date;
    }

    public List<String> getTaskIdsByProcessInstanceId(String processInstanceID) throws RecordNotFoundException {
        List<Task> aTask = getTasksByProcessInstanceId(processInstanceID);
        List<String> res = new ArrayList<>();
        for (Task task : aTask) {
            res.add(task.getId());
        }
        return res;
    }

    public void fillTheCSVMap(String sID_BP, Date dateAt, Date dateTo, List<Task> foundResults, SimpleDateFormat sDateCreateDF, List<Map<String, Object>> csvLines, String pattern, String saFieldsCalc, String[] headers) {
        if (CollectionUtils.isEmpty(foundResults)) {
            LOG.info(String.format("No tasks found for business process %s for date period %s - %s", sID_BP, DATE_TIME_FORMAT.format(dateAt), DATE_TIME_FORMAT.format(dateTo)));
            return;
        }
        LOG.info(String.format("Found %s tasks for business process %s for date period %s - %s", foundResults.size(), sID_BP, DATE_TIME_FORMAT.format(dateAt), DATE_TIME_FORMAT.format(dateTo)));
        if (pattern != null) {
            LOG.info("List of fields to retrieve: " + pattern);
        } else {
            LOG.info("Will retreive all fields from tasks");
        }
        for (Task curTask : foundResults) {
            String currentRow = pattern;
            LOG.trace("Process task - {}", curTask);
            TaskFormData data = formService.getTaskFormData(curTask.getId());
            currentRow = replaceFormProperties(currentRow, data);
            if (saFieldsCalc != null) {
                currentRow = addCalculatedFields(saFieldsCalc, curTask, currentRow);
            }
            if (pattern != null) {
                currentRow = replaceReportFields(sDateCreateDF, curTask, currentRow);
                currentRow = currentRow.replaceAll("\\$\\{.*?\\}", "");
            }
            String[] values = currentRow.split(";");
            Map<String, Object> currRow = new HashMap<>();
            for (int i = 0; i < values.length; i++) {
                currRow.put(headers[i], values[i]);
            }
            csvLines.add(currRow);
        }
    }

    public String[] createStringArray(Map<String, Object> csvLine, List<String> headers) {
        List<String> result = new LinkedList<>();
        for (String header : headers) {
            Object value = csvLine.get(header);
            result.add(value == null ? "" : value.toString());
        }
        return result.toArray(new String[result.size()]);
    }

    public void findUsersGroups(List<Group> groups, List<Map<String, String>> res, ProcessDefinition processDef, Set<String> candidateCroupsToCheck) {
        for (Group group : groups) {
            for (String groupFromProcess : candidateCroupsToCheck) {
                if (groupFromProcess.contains("${")) {
                    groupFromProcess = groupFromProcess.replaceAll("\\$\\{?.*}", "(.*)");
                }
                if (group.getId().matches(groupFromProcess)) {
                    Map<String, String> process = new HashMap<>();
                    process.put("sID", processDef.getKey());
                    process.put("sName", processDef.getName());
                    LOG.info(String.format("Added record to response %s", process.toString()));
                    res.add(process);
                    return;
                }
            }
        }
    }

    
    public Map<String, String> getTaskFormDataInternal(Long nID_Task) throws CommonServiceException {
        Map<String, String> result = new HashMap<>();
        Task task = taskService.createTaskQuery().taskId(nID_Task.toString()).singleResult();
        LOG.info("Found task with ID:" + nID_Task + " process inctanse ID:" + task.getProcessInstanceId());
        FormData taskFormData = formService.getTaskFormData(task.getId());
        Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());
        if (taskFormData != null) {
            loadFormPropertiesToMap(taskFormData, variables, result);
        }
        return result;
    }
    
    
    public Map<String, Object> sendProccessToGRESInternal(Long nID_Task) throws CommonServiceException {
        Map<String, Object> res = new HashMap<>();

        Task task = taskService.createTaskQuery().taskId(nID_Task.toString()).singleResult();

        LOG.info("Found task with ID:" + nID_Task + " process inctanse ID:" + task.getProcessInstanceId());

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(
                task.getProcessInstanceId()).singleResult();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(task.getProcessDefinitionId()).singleResult();

        FormData startFormData = formService.getStartFormData(processInstance.getProcessDefinitionId());
        FormData taskFormData = formService.getTaskFormData(task.getId());

        res.put("nID_Task", nID_Task.toString());
        res.put("nID_Proccess", task.getProcessInstanceId());
        res.put("sProcessName", processDefinition.getName());
        res.put("sProcessDefinitionKey", processDefinition.getKey());

        Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());

        Map<String, String> startFormValues = new HashMap<>();
        Map<String, String> taskFormValues = new HashMap<>();
        if (startFormData != null) {
            loadFormPropertiesToMap(startFormData, variables, startFormValues);
        }
        if (taskFormData != null) {
            loadFormPropertiesToMap(taskFormData, variables, taskFormValues);
        }

        res.put("startFormData", startFormValues);
        res.put("taskFormData", taskFormValues);

        return res;
    }    
 
    public String updateHistoryEvent_Service(String sID_Order,
            Long nID_Protected, Long nID_Process, Integer nID_Server,
            String saField, String sHead, String sBody, String sToken,
            String sUserTaskName) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("sID_Order", sID_Order);
        params.put("nID_Protected", nID_Protected != null ? "" + nID_Protected : null);
        String sID_Process = nID_Process != null ? "" + nID_Process : null;
        params.put("nID_Process", sID_Process);
        params.put("nID_Server", nID_Server != null ? "" + nID_Server : null);
        params.put("soData", saField);
        params.put("sHead", sHead);
        params.put("sBody", sBody);
        params.put("sToken", sToken);
        params.put("sUserTaskName", sUserTaskName);
        return historyEventService.updateHistoryEvent(sID_Process, sUserTaskName, true, params);
    }
    
    public List<Task> getTasksForChecking(String sLogin,
            Boolean bEmployeeUnassigned) {
        List<Task> tasks;
        if (bEmployeeUnassigned) {
            //tasks = taskService.createTaskQuery().taskUnassigned().active().list();
            tasks = taskService.createTaskQuery().taskCandidateUser(sLogin).taskUnassigned().active().list();
            LOG.info("Looking for unassigned tasks. Found " + (tasks != null ? tasks.size() : 0) + " tasks");
        } else {
            tasks = taskService.createTaskQuery().taskAssignee(sLogin).active().list();
            LOG.info("Looking for tasks assigned to user:" + sLogin + ". Found " + (tasks != null ? tasks.size() : 0)
                    + " tasks");
        }
        return tasks;
    }

    /*public static void main(String[] args) {
        System.out.println(createTable_TaskProperties("[{'id':'bankIdfirstName','type':'string','value':'3119325858'}]"));
    }*/
    
}
