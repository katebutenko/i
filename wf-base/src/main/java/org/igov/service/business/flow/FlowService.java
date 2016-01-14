package org.igov.service.business.flow;

import java.text.SimpleDateFormat;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.igov.service.business.flow.handler.BaseFlowSlotScheduler;
import org.igov.service.business.flow.handler.FlowPropertyHandler;
import org.igov.util.convert.DurationUtil;
import org.igov.service.business.flow.slot.ClearSlotsResult;
import org.igov.service.business.flow.slot.Day;
import org.igov.service.business.flow.slot.Days;
import org.igov.service.business.flow.slot.FlowSlotVO;

import javax.xml.datatype.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.igov.model.flow.FlowLink;
import org.igov.model.flow.FlowLinkDao;
import org.igov.model.flow.FlowProperty;
import org.igov.model.flow.FlowServiceDataDao;
import org.igov.model.flow.FlowSlot;
import org.igov.model.flow.FlowSlotDao;
import org.igov.model.flow.FlowSlotTicket;
import org.igov.model.flow.FlowSlotTicketDao;
import org.igov.model.flow.Flow_ServiceData;

/**
 * User: goodg_000
 * Date: 29.06.2015
 * Time: 18:11
 */
@Service
public class FlowService implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(FlowService.class);

    private FlowSlotDao flowSlotDao;
    private FlowSlotTicketDao oFlowSlotTicketDao;

    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private FlowServiceDataDao flowServiceDataDao;
    
    
    private FlowLinkDao flowLinkDao;

    //private FlowServiceDataDao flowServiceDataDao;

    private ApplicationContext applicationContext;

    @Autowired
    public void setFlowLinkDao(FlowLinkDao flowLinkDao) {
        this.flowLinkDao = flowLinkDao;
    }

    @Autowired
    public void setFlowSlotDao(FlowSlotDao flowSlotDao) {
        this.flowSlotDao = flowSlotDao;
    }

    @Autowired
    public void setFlowSlotTicketDao(FlowSlotTicketDao oFlowSlotTicketDao) {
        this.oFlowSlotTicketDao = oFlowSlotTicketDao;
    }

    @Autowired
    public void setFlowServiceDataDao(FlowServiceDataDao flowServiceDataDao) {
        this.flowServiceDataDao = flowServiceDataDao;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Days getFlowSlots(Long nID_Service, Long nID_ServiceData, String sID_BP, Long nID_SubjectOrganDepartment,
            DateTime startDate, DateTime endDate, boolean bAll,
            int nFreeDays) {

        List<FlowSlot> aFlowSlot;
        Flow_ServiceData oFlow = null;
        if (nID_Service != null) {
            oFlow = getFlowByLink(nID_Service, nID_SubjectOrganDepartment);
        }
        if (oFlow != null) {
            aFlowSlot = flowSlotDao.findFlowSlotsByFlow(oFlow.getId(), startDate, endDate);
        } else {
            if (nID_ServiceData != null) {
                aFlowSlot = flowSlotDao.findFlowSlotsByServiceData(nID_ServiceData, nID_SubjectOrganDepartment, startDate, endDate);
            } else if (sID_BP != null) {
                aFlowSlot = flowSlotDao.findFlowSlotsByBP(sID_BP, nID_SubjectOrganDepartment, startDate, endDate);
            } else {
                throw new IllegalArgumentException("nID_Service, nID_ServiceData, sID_BP are null!");
            }
        }

        Map<DateTime, Day> daysMap = new TreeMap<>();
        if (bAll) {
            DateTime currDate = startDate;
            while (currDate.isBefore(endDate)) {
                Day day = new Day(currDate);
                daysMap.put(currDate, day);
                currDate = currDate.plusDays(1);
            }
        }

        for (FlowSlot flowSlot : aFlowSlot) {
            DateTime currDate = flowSlot.getsDate().withTimeAtStartOfDay();
            FlowSlotVO flowSlotVO = new FlowSlotVO(flowSlot);
            if (!bAll && !flowSlotVO.isbFree()) {
                continue;
            }

            Day day = daysMap.get(currDate);
            if (day == null) {
                day = new Day(currDate);
                daysMap.put(currDate, day);
            }

            day.getaSlot().add(flowSlotVO);

            if (!day.isbHasFree() && flowSlotVO.isbFree()) {
                day.setbHasFree(true);
            }
        }

        Days res = new Days();
        int freeDaysCount = 0;

        for (Map.Entry<DateTime, Day> entry : daysMap.entrySet()) {
            Day day = entry.getValue();
            if (bAll || day.isbHasFree()) {
                res.getaDay().add(day);
            }

            if (day.isbHasFree()) {
                freeDaysCount++;
                if (freeDaysCount >= nFreeDays) {
                    break;
                }
            }
        }

        return res;
    }

    public Flow_ServiceData getFlowByLink(Long nID_Service, Long nID_SubjectOrganDepartment) {
        FlowLink flow = flowLinkDao.findLinkByService(nID_Service, nID_SubjectOrganDepartment);
        return flow != null ? flow.getFlow_ServiceData() : null;
    }

    public FlowSlotTicket saveFlowSlotTicket(Long nID_FlowSlot, Long nID_Subject, Long nID_Task_Activiti)
            throws Exception {

        FlowSlotTicket oFlowSlotTicket = oFlowSlotTicketDao.findFlowSlotTicket(nID_FlowSlot);
        if (oFlowSlotTicket == null) {
            oFlowSlotTicket = new FlowSlotTicket();
        } else {
            //if(oFlowSlotTicket.getnID_Task_Activiti()!=null){
            if (FlowSlotVO.bBusyStatic(oFlowSlotTicket)) {
                //oFlowSlotTicket.getnID_Subject(nID_Subject);
                String sError = "FlowSlotTicket with nID_FlowSlot=" + nID_FlowSlot
                        + " is bBusyStatic by getnID_Task_Activiti()=" + oFlowSlotTicket.getnID_Task_Activiti();
                LOG.error(sError);
                throw new Exception(sError);
            } else if (FlowSlotVO
                    .bBusyTemp(oFlowSlotTicket)) {//oFlowSlotTicket.getsDateEdit(). <oFlowSlotTicket.getsDateEdit()
                //bBusyStatic
                LOG.info("nID_Subject=" + nID_Subject);
                LOG.info("getnID_Subject()=" + oFlowSlotTicket.getnID_Subject());
                if (!nID_Subject.equals(oFlowSlotTicket.getnID_Subject())) {
                    String sError =
                            "FlowSlotTicket with nID_FlowSlot=" + nID_FlowSlot + " is bBusyTemp from getsDateEdit()="
                                    + oFlowSlotTicket.getsDateEdit();
                    LOG.error(sError);
                    throw new Exception(sError);
                }
            }
        }

        //oFlowSlotTicket

        oFlowSlotTicket.setnID_Subject(nID_Subject);
        oFlowSlotTicket.setnID_Task_Activiti(nID_Task_Activiti);

        FlowSlot flowSlot = flowSlotDao.findByIdExpected(nID_FlowSlot);

        oFlowSlotTicket.setoFlowSlot(flowSlot);
        oFlowSlotTicket.setsDateStart(flowSlot.getsDate());

        Duration duration = DurationUtil.parseDuration(flowSlot.getsDuration());
        DateTime finishDateTime = flowSlot.getsDate().plusMinutes(duration.getMinutes());
        oFlowSlotTicket.setsDateFinish(finishDateTime);

        oFlowSlotTicket.setsDateEdit(DateTime.now());

        return oFlowSlotTicketDao.saveOrUpdate(oFlowSlotTicket);
    }

    /**
     * Generates FlowSlots in given interval for specified flow. Slots will not be generated if they are already exist.
     *
     * @param nID_Flow_ServiceData ID of flow
     * @param startDate            start date of generation (inclusive)
     * @param stopDate             stop date of generation (exclusive)
     * @return generated slots.
     */
    public List<FlowSlotVO> buildFlowSlots(Long nID_Flow_ServiceData, DateTime startDate, DateTime stopDate) {

        Flow_ServiceData flow = flowServiceDataDao.findByIdExpected(nID_Flow_ServiceData);

        List<FlowSlotVO> res = new ArrayList<>();

        for (FlowProperty flowProperty : flow.getFlowProperties()) {
            if (flowProperty.getbExclude() == null || !flowProperty.getbExclude()) {
                Class<FlowPropertyHandler> flowPropertyHandlerClass = getFlowPropertyHandlerClass(flowProperty);
                if (BaseFlowSlotScheduler.class.isAssignableFrom(flowPropertyHandlerClass)) {

                    BaseFlowSlotScheduler handler = getFlowPropertyHandlerInstance(
                            flowProperty.getoFlowPropertyClass().getsBeanName(), flowPropertyHandlerClass);
                    handler.setStartDate(startDate);
                    handler.setEndDate(stopDate);
                    handler.setFlow(flow);

                    LOG.info("startDate=" + startDate + ",stopDate=" + stopDate + ",flowProperty.getsData()="
                            + flowProperty.getsData());

                    if (flowProperty.getsData() != null && !"".equals(flowProperty.getsData().trim())) {
                        List<FlowSlot> generatedSlots = handler.generateObjects(flowProperty.getsData());
                        for (FlowSlot slot : generatedSlots) {
                            res.add(new FlowSlotVO(slot));
                        }
                    }
                }
            }
        }

        return res;
    }

    public ClearSlotsResult clearFlowSlots(Long nID_Flow_ServiceData, DateTime startDate, DateTime stopDate,
            boolean bWithTickets) {

        List<FlowSlot> flowSlots = flowSlotDao.findFlowSlotsByFlow(nID_Flow_ServiceData, startDate, stopDate);
        DateTime operationTime = DateTime.now();

        ClearSlotsResult res = new ClearSlotsResult();
        List<FlowSlot> flowSlotsToDelete = new ArrayList<>();
        for (FlowSlot slot : flowSlots) {
            if (bWithTickets || slot.getFlowSlotTickets().isEmpty()) {
                flowSlotsToDelete.add(slot);

                // detach existing tickets from slots
                for (FlowSlotTicket oFlowSlotTicket : slot.getFlowSlotTickets()) {
                    oFlowSlotTicket.setoFlowSlot(null);
                    oFlowSlotTicket.setsDateEdit(operationTime);
                }
                res.getaDeletedSlot().add(new FlowSlotVO(slot));
            }

            if (!slot.getFlowSlotTickets().isEmpty()) {
                res.getaSlotWithTickets().add(new FlowSlotVO(slot));
            }
        }

        flowSlotDao.delete(flowSlotsToDelete);
        return res;
    }

    private Class<FlowPropertyHandler> getFlowPropertyHandlerClass(FlowProperty flowProperty) {
        String fullClassName = flowProperty.getoFlowPropertyClass().getsPath();
        try {
            return (Class<FlowPropertyHandler>) Class.forName(fullClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find class of controller: " + fullClassName, e);
        }
    }

    private <T extends FlowPropertyHandler> T getFlowPropertyHandlerInstance(String beanName,
            Class flowPropertyHandlerClass) {
        Object bean = null;
        if (beanName != null) {
            bean = applicationContext.getBean(beanName);
        } else {
            bean = applicationContext.getBean(flowPropertyHandlerClass);
        }
        Assert.isTrue(flowPropertyHandlerClass.isAssignableFrom(flowPropertyHandlerClass));
        return (T) bean;
    }
    

    
    
    public void addFlowSlowTicketToResult(List<Map<String, String>> res,
            SimpleDateFormat dateFormat, FlowSlotTicket currFlowSlowTicket,
            Task tasksByActivitiID) {
        Map<String, String> currRes = new HashMap<String, String>();

        StringBuilder sb = new StringBuilder();
        sb.append("Adding flow slot ticket: ");
        sb.append(currFlowSlowTicket.getId());
        sb.append(":");
        sb.append(currFlowSlowTicket.getnID_Subject());
        sb.append(":");
        sb.append(currFlowSlowTicket.getsDateStart());
        sb.append(":");
        sb.append(currFlowSlowTicket.getsDateFinish());
        LOG.info(sb.toString());

        currRes.put("nID", currFlowSlowTicket.getId().toString());
        currRes.put("nID_FlowSlot", currFlowSlowTicket.getoFlowSlot() != null ?
                currFlowSlowTicket.getoFlowSlot().getId().toString() : "");
        currRes.put("nID_Subject", currFlowSlowTicket.getnID_Subject().toString());
        Date startDate = new Date(currFlowSlowTicket.getsDateStart().getMillis());
        currRes.put("sDateStart", dateFormat.format(startDate));
        Date finishDate = new Date(currFlowSlowTicket.getsDateFinish().getMillis());
        currRes.put("sDateFinish", dateFormat.format(finishDate));
        Date editDate = new Date(currFlowSlowTicket.getsDateEdit().getMillis());
        currRes.put("sDateEdit", dateFormat.format(editDate));

        currRes.put("nID_Task_Activiti", tasksByActivitiID.getId());

        currRes.put("name", tasksByActivitiID.getName());
        currRes.put("id", tasksByActivitiID.getId());
        currRes.put("assignee", tasksByActivitiID.getAssignee());
        currRes.put("nID_Instance", tasksByActivitiID.getProcessInstanceId());

        currRes.put("sUserTaskName", tasksByActivitiID.getName());
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(tasksByActivitiID.getProcessDefinitionId()).singleResult();
        currRes.put("sNameBP", processDefinition != null ? processDefinition.getName() : "");
        currRes.put("sTaskDate", dateFormat.format(tasksByActivitiID.getCreateTime()));
        res.add(currRes);
    }

    public List<FlowProperty> getFilteredFlowPropertiesForFlowServiceData(Long nID_Flow_ServiceData,
            String sID_BP,
            Long nID_SubjectOrganDepartment,
            Boolean bExclude) throws Exception {

        if (nID_Flow_ServiceData == null) {
            if (sID_BP != null) {
                nID_Flow_ServiceData = flowServiceDataDao.findFlowId(sID_BP, nID_SubjectOrganDepartment);
                LOG.info("(sID_BP={},nID_Flow_ServiceData={})",sID_BP,nID_Flow_ServiceData);
            } else {
                String sError = "nID_Flow_ServiceData==null and sID_BP==null";
                LOG.error(sError);
                throw new Exception(sError);
            }
        }
        if (nID_Flow_ServiceData == null) {
            String sError = "nID_Flow_ServiceData==null";
            LOG.error(sError);
            throw new Exception(sError);
        }

        LOG.info("(nID_Flow_ServiceData={})", nID_Flow_ServiceData);
        Flow_ServiceData flowServiceData = flowServiceDataDao.findByIdExpected(nID_Flow_ServiceData);
        List<FlowProperty> res = new LinkedList<FlowProperty>();
        if (flowServiceData != null) {
            if (flowServiceData.getFlowProperties() != null && !flowServiceData.getFlowProperties().isEmpty()) {
                LOG.info("nID_Flow_ServiceData contains " + flowServiceData.getFlowProperties().size()
                        + " elements. Getting only with bExclude =" + bExclude.toString());
                for (FlowProperty flowProperty : flowServiceData.getFlowProperties()) {
                    LOG.info("flowProperty " + flowProperty.getId() + ":" + flowProperty.getsName() + ":" + flowProperty
                            .getbExclude());
                    if (flowProperty.getbExclude() != null && flowProperty.getbExclude().equals(bExclude)) {
                        res.add(flowProperty);
                    }
                }
                LOG.info("Found " + res.size() + " flow properties with bExclude=" + bExclude + " . Results:" + res
                        .toString());
            } else {
                LOG.info("Flow service data contains empty list of FlowProperty");
            }
        } else {
            LOG.info("Have not found nID_Flow_ServiceData object with ID: " + nID_Flow_ServiceData);
        }
        return res;
    }

    public FlowProperty fillFlowProperty(String sName, String sRegionTime,
            String saRegionWeekDay, String sDateTimeAt, String sDateTimeTo,
            Integer nLen,
            String sLenType,
            String sData,
            FlowProperty flowProperty) {
        flowProperty.setbExclude(false);
        flowProperty.setsName(sName);
        flowProperty.setsRegionTime(sRegionTime);
        flowProperty.setSaRegionWeekDay(saRegionWeekDay);
        if (nLen != null) {
            flowProperty.setLen(nLen);
        }
        if (sLenType != null) {
            flowProperty.setLenType(sLenType);
        }
        if (sData != null) {
            flowProperty.setsData(sData);
        }
        flowProperty.setsDateTimeAt(sDateTimeAt);
        flowProperty.setsDateTimeTo(sDateTimeTo);
        return flowProperty;
    }        
    
}
