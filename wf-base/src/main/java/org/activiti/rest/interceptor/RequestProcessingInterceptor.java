/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.activiti.rest.interceptor;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.rest.controller.adapter.MultiReaderHttpServletResponse;
import org.activiti.rest.interceptor.utils.JsonRequestDataResolver;
import org.egov.service.HistoryEventService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.wf.dp.dniprorada.base.service.notification.NotificationService;
import org.wf.dp.dniprorada.rest.HttpRequester;
import org.wf.dp.dniprorada.util.GeneralConfig;
import org.wf.dp.dniprorada.util.luna.AlgorithmLuna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author olya
 */
public class RequestProcessingInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory
            .getLogger(RequestProcessingInterceptor.class);

    @Autowired
    GeneralConfig generalConfig;
    @Autowired
    HttpRequester httpRequester;
    @Autowired
    NotificationService notificationService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryEventService historyEventService;
    private JSONParser parser = new JSONParser();

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        logger.info("[preHandle] Request URL = " + request.getRequestURL().toString()
                + ":: Start Time = " + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        saveHistory(request, response, false);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("[afterCompletion] Request URL = " + request.getRequestURL().toString()
                + ":: Time Taken = " + (System.currentTimeMillis() - (Long) request.getAttribute("startTime")));
        response = ((MultiReaderHttpServletResponse) request.getAttribute("responseMultiRead") != null
                ? (MultiReaderHttpServletResponse) request.getAttribute("responseMultiRead") : response);
        saveHistory(request, response, true);
    }

    private void saveHistory(HttpServletRequest request, HttpServletResponse response, boolean saveHistory)
            throws IOException {

        Map<String, String> mParamRequest = new HashMap();
        Enumeration paramsName = request.getParameterNames();
        while (paramsName.hasMoreElements()) {
            String sKey = (String) paramsName.nextElement();
            mParamRequest.put(sKey, request.getParameter(sKey));
        }

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        if (reader != null) {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            //mParamRequest.put("requestBody", buffer.toString()); 
            //TODO temp
        }
        String sRequestBody = buffer.toString();

        logger.info("mParamRequest: " + mParamRequest);

        String sResponseBody = response.toString();

        if (generalConfig.bTest()) {
            /*if (sResponseBody != null) {
                logger.info("sResponseBody: " + sResponseBody.substring(0, sResponseBody.length() < 100 ? sResponseBody.length() : 99));
            } else {
                logger.info("sResponseBody: null");
            }*/
            //logger.info("sResponseBody: " + sResponseBody);
            logger.info("sResponseBody: " + (sResponseBody != null ?
                    (sResponseBody.length() > 1000 ? sResponseBody.substring(0, 1000) : sResponseBody) :
                    "null"));
        } else {
            //logger.info("sResponseBody: " + (sResponseBody != null ? sResponseBody.length() : "null"));
            logger.info("sResponseBody: " + (sResponseBody != null ?
                    (sResponseBody.length() > 1000 ? sResponseBody.substring(0, 2000) : sResponseBody) :
                    "null"));
        }

        if (!saveHistory || !(response.getStatus() >= HttpStatus.OK.value()
                && response.getStatus() < HttpStatus.BAD_REQUEST.value())) {
            return;
        }

        try {
            //logger.info("sRequestBody: " + sRequestBody);

            //logger.info("sRequestBody: " + (sRequestBody != null ? (sRequestBody.length()>2000?sRequestBody.substring(0, 2000):sRequestBody ) : "null"));
            if (sRequestBody != null) {
                if (sRequestBody.indexOf("Content-Disposition:") >= 0) {
                    logger.info("sRequestBody: " + (sRequestBody.length() > 200 ?
                            sRequestBody.substring(0, 2000) :
                            sRequestBody));
                } else {
                    logger.info("sRequestBody: " + (sRequestBody.length() > 2000 ?
                            sRequestBody.substring(0, 2000) :
                            sRequestBody));
                }
            } else {
                logger.info("sRequestBody: null");
            }

            if (isSaveTask(request, sResponseBody)) {
                saveNewTaskInfo(sRequestBody, sResponseBody, mParamRequest);
            } else if (isCloseTask(request, sResponseBody)) {
                saveClosedTaskInfo(sRequestBody);
            } else if (isUpdateTask(request)) {
                saveUpdatedTaskInfo(sResponseBody);
            }
        } catch (Exception ex) {
            logger.error("************************Error!!!!", ex);
        }
    }

    private boolean isUpdateTask(HttpServletRequest request) {
        return request.getRequestURL().toString().indexOf("/runtime/tasks") > 0
                && "PUT".equalsIgnoreCase(request.getMethod().trim());
    }

    private boolean isCloseTask(HttpServletRequest request, String sResponseBody) {
        return sResponseBody == null && request.getRequestURL().toString().indexOf("/form/form-data") > 0
                && "POST".equalsIgnoreCase(request.getMethod().trim());
    }

    private boolean isSaveTask(HttpServletRequest request, String sResponseBody) {
        return sResponseBody != null && request.getRequestURL().toString().indexOf("/form/form-data") > 0
                && "POST".equalsIgnoreCase(request.getMethod().trim());
    }

    private void saveNewTaskInfo(String sRequestBody, String sResponseBody, Map<String, String> mParamRequest)
            throws Exception {
        Map<String, String> params = new HashMap<>();
        JSONObject jsonObjectRequest = (JSONObject) parser.parse(sRequestBody);
        JSONObject jsonObjectResponse = (JSONObject) parser.parse(sResponseBody);

        String sID_Process = (String) jsonObjectResponse.get("id");
        String taskName = "Заявка подана";

        HistoricProcessInstance historicProcessInstances =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(sID_Process).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(historicProcessInstances.getProcessDefinitionId()).singleResult();
        params.put("sProcessInstanceName", processDefinition.getName() != null ? processDefinition.getName() + "!" :
                "Non name!");
        params.put("nID_Subject", String.valueOf(jsonObjectRequest.get("nID_Subject")));
        //nID_Service, Long nID_Region, String sID_UA
        String snID_Region = mParamRequest.get("nID_Region");
        if (snID_Region != null) {
            params.put("nID_Region", snID_Region);
        }

        String snID_Service = mParamRequest.get("nID_Service");
        if (snID_Service != null) {
            params.put("nID_Service", snID_Service);
        }

        String sID_UA = mParamRequest.get("sID_UA");
        if (sID_UA != null) {
            params.put("sID_UA", sID_UA);
        }

        String nID_Server = mParamRequest.get("nID_Server");
        logger.info("   >>> nID_Server=" + nID_Server);
        logger.info("   >>> generalConfig.nID_Server()=" + generalConfig.nID_Server());
        nID_Server = (nID_Server != null) ? nID_Server : "" + generalConfig.nID_Server();
        params.put("nID_Server", nID_Server); //issue 889
        logger.info("   >>> put nID_Server=" + nID_Server);

        historyEventService.addHistoryEvent(sID_Process, taskName, params);

        String taskCreatorEmail = JsonRequestDataResolver.getEmail(jsonObjectRequest);
        if (taskCreatorEmail != null) {
            Long nID_Protected = AlgorithmLuna.getProtectedNumber(Long.parseLong(sID_Process));
            notificationService.sendTaskCreatedInfoEmail(taskCreatorEmail, nID_Protected);
        }
    }

    private void saveClosedTaskInfo(String sRequestBody) throws Exception {
        String taskName;

        Map<String, String> params = new HashMap<>();
        JSONObject jsonObjectRequest = (JSONObject) parser.parse(sRequestBody);

        String task_ID = (String) jsonObjectRequest.get("taskId");
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(
                task_ID).singleResult();

        String sID_Process = historicTaskInstance.getProcessInstanceId();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(sID_Process).list();
        if (tasks == null || tasks.size() == 0) {
            taskName = "Заявка виконана";
        } else {
            taskName = tasks.get(0).getName();
        }
        params.put("nTimeHours", getTotalTimeOfExecution(sID_Process));
        historyEventService.updateHistoryEvent(sID_Process, taskName, false, params);
    }

    protected String getTotalTimeOfExecution(String sID_Process){
    	HistoricProcessInstance foundResult = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(sID_Process).singleResult();

    	String res = "-1";
    	long totalDuration = 0;
    	logger.info(String.format("Found completed process with ID %s ", sID_Process));
        if (foundResult != null) {
            totalDuration = totalDuration + foundResult.getDurationInMillis() / (1000 * 60 * 60);

            res = Long.valueOf(totalDuration).toString();
        }
        logger.info(String.format("Calculated time of execution of process %s:%s", sID_Process, totalDuration));

        return res;
    }

    private void saveUpdatedTaskInfo(String sResponseBody) throws Exception {
        JSONObject jsonObjectResponse = (JSONObject) parser.parse(sResponseBody);
        String sID_Process = (String) jsonObjectResponse.get("processInstanceId");
        String taskName = jsonObjectResponse.get("name") + " (у роботi)";
        historyEventService.updateHistoryEvent(sID_Process, taskName, false, null);
    }

}
