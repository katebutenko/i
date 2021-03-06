package org.wf.dp.dniprorada.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.wf.dp.dniprorada.base.dao.EntityNotFoundException;
import org.wf.dp.dniprorada.base.dao.GenericEntityDao;
import org.wf.dp.dniprorada.model.HistoryEvent_Service;
import org.wf.dp.dniprorada.util.luna.AlgorithmLuna;
import org.wf.dp.dniprorada.util.luna.CRCInvalidException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class HistoryEvent_ServiceDaoImpl extends GenericEntityDao<HistoryEvent_Service> implements HistoryEvent_ServiceDao {

    private static final Logger log = Logger.getLogger(HistoryEvent_ServiceDaoImpl.class);

    protected HistoryEvent_ServiceDaoImpl() {
        super(HistoryEvent_Service.class);
    }

    @Override
    public HistoryEvent_Service getHistoryEvent_ServiceBynID(Long nID) {
        return findById(nID).orNull();
    }

    @Override
    public HistoryEvent_Service getHistoryEvent_ServiceBynID_Task(Long nID_Task) {
        return findBy("nID_Task", nID_Task).orNull();
    }

    @Override
    public HistoryEvent_Service getHistoryEvent_ServiceBysID(String sID) {
        return findBy("sID", sID).orNull();
    }

    @Override
    public HistoryEvent_Service getHistoryEvent_ServiceByID_Protected(Long nID_Protected) throws CRCInvalidException {
        AlgorithmLuna.validateProtectedNumber(nID_Protected);

        Criteria criteria = getSession().createCriteria(HistoryEvent_Service.class);
        //criteria.add(Restrictions.eq("id", nID_Protected / 10));
        criteria.add(Restrictions.eq("nID_Task", nID_Protected / 10));
        HistoryEvent_Service event_service = (HistoryEvent_Service) criteria.uniqueResult();
        if (event_service == null) {
            log.warn("Record not found");
            throw new EntityNotFoundException("Record not found");
        } else {
            log.info("Ok");
            event_service.setnID_Protected(nID_Protected);
        }
        return event_service;
    }

    @Override
    public HistoryEvent_Service addHistoryEvent_Service(Long nID_task, String sStatus, Long nID_subject, String sID_status, Long nID_Service, Long nID_Region, String sID_UA) {
        HistoryEvent_Service event_service = new HistoryEvent_Service();
        event_service.setnID_Task(nID_task);
        event_service.setsStatus(sStatus);
        event_service.setsID_Status(sID_status);
        event_service.setnID_Subject(nID_subject);
        event_service.setsDate(new DateTime());
        event_service.setnID_Region(nID_Region);
        event_service.setnID_Service(nID_Service);
        event_service.setsID_UA(sID_UA);
        Session session = getSession();
        session.saveOrUpdate(event_service);

        long nID_Reference = nID_task;
        event_service.setnID_Protected(AlgorithmLuna.getProtectedNumber(nID_Reference));
        
        return event_service;
    }

    @Override
    public HistoryEvent_Service updateHistoryEvent_Service(HistoryEvent_Service event_service) {
        event_service.setsDate(new DateTime());
        return saveOrUpdate(event_service);
    }

	@Override
	public List<Map<String, Long>> getHistoryEvent_ServiceBynID_Service(Long nID_Service) {
		List<Map<String, Long>> resHistoryEventService = new LinkedList<Map<String, Long>>();
		Criteria criteria = getSession().createCriteria(HistoryEvent_Service.class);
        criteria.add(Restrictions.eq("nID_Service", nID_Service));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("nID_Region"))
                .add(Projections.count("nID_Service")));           
        Object res = criteria.list();
        log.info("Received result in getHistoryEvent_ServiceBynID_Service:"  + res);
        if (res == null) {
            log.warn("List of records based on nID_Service not found" + nID_Service);
            throw new EntityNotFoundException("Record not found");
        } else {
        	for(Object item:criteria.list()){
        		Object[] currValue = (Object[]) item;
        		log.info("Curr value:"  + currValue);
        		Map<String, Long> currRes = new HashMap<String, Long>();
        		currRes.put("sName", (Long) currValue[0]);
        		currRes.put("nCount", (Long) currValue[1]);
        		
        		resHistoryEventService.add(currRes);
        	}
            log.info("Found " + resHistoryEventService.size() + " records based on nID_Service " + nID_Service);
        }
        return resHistoryEventService;
	}
}
