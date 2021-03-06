package org.wf.dp.dniprorada.base.service.escalation;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.rest.controller.ActivitiRestException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.wf.dp.dniprorada.base.dao.EscalationRuleDao;
import org.wf.dp.dniprorada.base.dao.EscalationRuleFunctionDao;
import org.wf.dp.dniprorada.base.model.EscalationRule;
import org.wf.dp.dniprorada.base.model.EscalationRuleFunction;
import org.wf.dp.dniprorada.util.EscalationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EscalationService {
    private static final Logger log = Logger.getLogger(EscalationService.class);


    @Autowired
    private EscalationRuleFunctionDao escalationRuleFunctionDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private EscalationRuleDao escalationRuleDao;

    public void runEscalationAll() throws ActivitiRestException {
        //@RequestParam(value = "nID", required = false) Long nID ,
        //@RequestParam(value = "sName") String sName ,
        //@RequestParam(value = "sBeanHandler", required = false) String sBeanHandler

        try {
            List<EscalationRule> aEscalationRule = escalationRuleDao.findAll();
            for (EscalationRule oEscalationRule : aEscalationRule) {
                EscalationRuleFunction oEscalationRuleFunction = oEscalationRule.getoEscalationRuleFunction();

                String sID_BP = oEscalationRule.getsID_BP();
                log.info("[getTaskData]:sID_BP=" + sID_BP);
                TaskQuery oTaskQuery = taskService.createTaskQuery().processDefinitionKey(sID_BP);//.taskCreatedAfter(dateAt).taskCreatedBefore(dateTo)

                String sID_State_BP = oEscalationRule.getsID_UserTask();
                log.info("[getTaskData]:sID_State_BP=" + sID_State_BP);
                if (sID_State_BP != null && !"*".equals(sID_State_BP)) {
                    oTaskQuery = oTaskQuery.taskDefinitionKey(sID_State_BP);
                }

                Integer nRowStart = 0;
                Integer nRowsMax = 1000;
                List<Task> aTask = oTaskQuery.listPage(nRowStart, nRowsMax);

                for (Task oTask : aTask) {
                    //long nID_task_activiti = Long.valueOf(oTask.getId());
                    //Map<String, Object> mTaskParam = getTaskData(nID_task_activiti);//new HashMap()
                    Map<String, Object> mTaskParam = getTaskData(oTask);

                    log.info("[getTaskData]:checkTaskOnEscalation mTaskParam=" + mTaskParam);
                    new EscalationUtil().checkTaskOnEscalation(mTaskParam
                            , oEscalationRule.getsCondition()
                            , oEscalationRule.getSoData()
                            , oEscalationRule.getsPatternFile()
                            , oEscalationRuleFunction.getsBeanHandler()
                    );
                }

            }
            //return escalationRuleFunctionDao.saveOrUpdate(nID, sName, sBeanHandler);
        } catch (Exception oException) {
            log.error("[getTaskData]:" + oException);
            throw new ActivitiRestException("ex in controller!", oException);
        }

    }


    private Map<String, Object> getTaskData(Task oTask) {//Long nID_task_activiti
        long nID_task_activiti = Long.valueOf(oTask.getId());
        log.info("[getTaskData]:nID_task_activiti=" + nID_task_activiti);
        log.info("[getTaskData]:oTask.getCreateTime().toString()=" + oTask.getCreateTime());
        log.info("[getTaskData]:oTask.getDueDate().toString()=" + oTask.getDueDate());

        Map<String, Object> m = new HashMap();

        //Date = Date
        long nDiffMS = 0;
        if (oTask.getDueDate() != null) {
            nDiffMS = oTask.getDueDate().getTime() - oTask.getCreateTime().getTime();
        }else{
            nDiffMS = DateTime.now().toDate().getTime() - oTask.getCreateTime().getTime();
            //Date oDateTo = DateTime.now().toDate();
        }
        log.info("[getTaskData]:nDiffMS=" + nDiffMS);
        
        long nElapsedHours = nDiffMS / 1000 / 60 / 60;
        log.info("[getTaskData]:nElapsedHours=" + nElapsedHours);
        m.put("nElapsedHours", nElapsedHours);
        
        long nElapsedDays = nElapsedHours / 24;
        log.info("[getTaskData]:nElapsedDays=" + nElapsedDays);
        m.put("nElapsedDays", nElapsedDays);
        m.put("nDays", nElapsedDays);


        TaskFormData oTaskFormData = formService.getTaskFormData(oTask.getId());
        for (FormProperty oFormProperty : oTaskFormData.getFormProperties()) {
            log.info(String.format("[getTaskData]Matching property %s:%s:%s with fieldNames", oFormProperty.getId(), oFormProperty.getName(), oFormProperty.getType().getName()));
            if ("long".equalsIgnoreCase(oFormProperty.getType().getName())) {
                m.put(oFormProperty.getId(), Long.valueOf(oFormProperty.getValue()));
            } else {
                m.put(oFormProperty.getId(), oFormProperty.getValue());
            }
        }

        return m;
    }
}
