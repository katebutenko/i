package org.wf.dp.dniprorada.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.wf.dp.dniprorada.base.dao.EntityNotFoundException;
import org.wf.dp.dniprorada.base.dao.GenericEntityDao;
import org.wf.dp.dniprorada.model.HistoryEvent_Service;
import org.wf.dp.dniprorada.util.luna.AlgorithmLuna;
import org.wf.dp.dniprorada.util.luna.CRCInvalidException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class HistoryEvent_ServiceDaoImpl extends GenericEntityDao<HistoryEvent_Service>
        implements HistoryEvent_ServiceDao {

    private static final Logger log = Logger.getLogger(HistoryEvent_ServiceDaoImpl.class);
    private static final String DASH = "-";

    protected HistoryEvent_ServiceDaoImpl() {
        super(HistoryEvent_Service.class);
    }

    @Override
    public HistoryEvent_Service addHistoryEvent_Service(HistoryEvent_Service event_service) {

        try {//check on duplicates
            HistoryEvent_Service duplicateEvent = getHistoryEvent_service(event_service.getnID_Task(),
                    event_service.getnID_Server());
            if (duplicateEvent != null) {
                throw new IllegalArgumentException(
                        "Cannot create event_service with the same nID_Process and nID_Server!");
            }
        } catch (EntityNotFoundException ex) {
            /*NOP*/
        }
        event_service.setsDate(new DateTime());
        Long nID_Protected = AlgorithmLuna.getProtectedNumber(event_service.getnID_Task());
        event_service.setnID_Protected(nID_Protected);
        event_service.setsID_Order(event_service.getnID_Server() + DASH + nID_Protected);
        Session session = getSession();
        session.saveOrUpdate(event_service);
        return event_service;
    }

    @Override
    public HistoryEvent_Service updateHistoryEvent_Service(HistoryEvent_Service event_service) {
        event_service.setsDate(new DateTime());
        return saveOrUpdate(event_service);
    }

    @Override
    public List<Map<String, Long>> getHistoryEvent_ServiceBynID_Service(Long nID_Service) {

        List<Map<String, Long>> resHistoryEventService = new LinkedList<>();
        if (nID_Service == 159) {
            Map<String, Long> currRes = new HashMap<>();
            currRes.put("sName", 5L);
            currRes.put("nCount", 1L);
            currRes.put("nRate", 0L);
            currRes.put("nTimeHours", 0L);
            resHistoryEventService.add(currRes);
        }
        Criteria criteria = getSession().createCriteria(HistoryEvent_Service.class);
        criteria.add(Restrictions.eq("nID_Service", nID_Service));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("nID_Region"))
                .add(Projections.count("nID_Service"))
                .add(Projections.avg("nRate")) //for issue 777
                .add(Projections.avg("nTimeHours"))
        );
        Object res = criteria.list();
        log.info("Received result in getHistoryEvent_ServiceBynID_Service:" + res);
        if (res == null) {
            log.warn("List of records based on nID_Service not found" + nID_Service);
            throw new EntityNotFoundException("Record not found");
        } else {
            int i = 0;
            for (Object item : criteria.list()) {
                Object[] currValue = (Object[]) item;
                log.info(String.format("Line %s: %s, %s, %s, %s", i, currValue[0], currValue[1], currValue[2] != null ? currValue[2] : "",
                		currValue[3] != null ? currValue[3] : ""));
                i++;
                Long rate = 0L;
                try {
                    Double nRate = (Double) currValue[2];
                    log.info("nRate=" + nRate);
                    if (nRate != null) {
                    	String snRate = "" + nRate * 20;
                    	log.info("snRate=" + snRate);
                    	if (snRate.contains(".")) {
	                        rate = Long.valueOf(snRate.substring(0, snRate.indexOf(".")));
	                        log.info("total rate = " + rate);
	                    }
                	}
                } catch (Exception oException) {
                    log.error("cannot get nRate! " + currValue[2] + " caused: " + oException.getMessage(), oException);
                }
                BigDecimal timeHours = null;
                try {
                    Double nTimeHours = (Double) currValue[3];
                    log.info("nTimeHours=" + nTimeHours);
                    if (nTimeHours != null){
                    	timeHours = BigDecimal.valueOf(nTimeHours);
                    	timeHours = timeHours.abs();
                    }
                } catch (Exception oException) {
                    log.error("cannot get nTimeHours! " + currValue[3] + " caused: " + oException.getMessage(), oException);
                }
                Map<String, Long> currRes = new HashMap<>();
                currRes.put("sName", (Long) currValue[0]);
                currRes.put("nCount", (Long) currValue[1]);
                currRes.put("nRate", rate);
                currRes.put("nTimeHours", timeHours != null ? timeHours.longValue() : 0L);
                resHistoryEventService.add(currRes);
            }
            log.info("Found " + resHistoryEventService.size() + " records based on nID_Service " + nID_Service);
        }

        return resHistoryEventService;
    }

    @Override
    public HistoryEvent_Service getOrgerByID(String sID_Order) throws CRCInvalidException {
        Integer nID_Server;
        Long nID_Protected;
        try {
            int dash_position = sID_Order.indexOf(DASH);
            nID_Server = Integer.parseInt(sID_Order.substring(0, dash_position));
            nID_Protected = Long.valueOf(sID_Order.substring(dash_position + 1));
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("sID_Order has incorrect format! expected format:[XXX%sXXXXXX], actual value: %s",
                            DASH, sID_Order), e);
        }
        return getOrgerByProtectedID(nID_Protected, nID_Server);
    }

    @Override
    public HistoryEvent_Service getOrgerByProcessID(Long nID_Process, Integer nID_Server) {
        HistoryEvent_Service event_service = getHistoryEvent_service(nID_Process, nID_Server);
        event_service.setnID_Protected(AlgorithmLuna.getProtectedNumber(nID_Process));
        return event_service;
    }

    @Override
    public HistoryEvent_Service getOrgerByProtectedID(Long nID_Protected, Integer nID_Server)
            throws CRCInvalidException {
        AlgorithmLuna.validateProtectedNumber(nID_Protected);
        Long nID_Process = AlgorithmLuna.getOriginalNumber(nID_Protected);
        HistoryEvent_Service event_service = getHistoryEvent_service(nID_Process, nID_Server);
        event_service.setnID_Protected(nID_Protected);
        return event_service;
    }

    @SuppressWarnings("unchecked")
    private HistoryEvent_Service getHistoryEvent_service(Long nID_Process, Integer nID_Server) {
        Criteria criteria = getSession().createCriteria(HistoryEvent_Service.class);
        criteria.addOrder(
                Order.desc("sDate").nulls(NullPrecedence.LAST));//todo remove after fix dublicates. todo uniqueResult
        criteria.add(Restrictions.eq("nID_Task", nID_Process));
        nID_Server = nID_Server != null ? nID_Server : 0;
        criteria.add(Restrictions.eq("nID_Server", nID_Server));
        List<HistoryEvent_Service> list = (List<HistoryEvent_Service>) criteria.list();
        HistoryEvent_Service event_service = list.size() > 0 ? list.get(0) : null;
        if (event_service == null) {
            throw new EntityNotFoundException(
                    String.format("Record with nID_Server=%s and nID_Process=%s not found!", nID_Server, nID_Process));
        }
        return event_service;
    }

    @Override
    public HistoryEvent_Service getLastTaskHistory(Long nID_Subject, Long nID_Service, String sID_UA) {
        Criteria criteria = getSession().createCriteria(HistoryEvent_Service.class);
        criteria.add(Restrictions.eq("nID_Subject", nID_Subject));
        criteria.add(Restrictions.eq("nID_Service", nID_Service));
        criteria.add(Restrictions.eq("sID_UA", sID_UA));
        criteria.addOrder(Order.desc("id"));
        criteria.setMaxResults(1);
        HistoryEvent_Service event_service = (HistoryEvent_Service) criteria.uniqueResult();
        if (event_service != null) {
            event_service.setnID_Protected(AlgorithmLuna.getProtectedNumber(event_service.getnID_Task()));
        }
        return event_service;
    }

}
