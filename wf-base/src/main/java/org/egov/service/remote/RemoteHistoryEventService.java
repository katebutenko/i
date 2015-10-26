package org.egov.service.remote;

import com.google.common.collect.ImmutableMap;
import org.egov.service.HistoryEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wf.dp.dniprorada.base.dao.AccessDataDao;
import org.wf.dp.dniprorada.rest.HttpRequester;
import org.wf.dp.dniprorada.util.GeneralConfig;

/**
 * @author vit@tym.im
 */
@Service
public class RemoteHistoryEventService implements HistoryEventService {
    private static final Logger log = LoggerFactory.getLogger(RemoteHistoryEventService.class);
    @Autowired
    private HttpRequester httpRequester;
    @Autowired
    private AccessDataDao accessDataDao;
    @Autowired
    private GeneralConfig generalConfig;

    @Override
    public String getHistoryEvent(String sID_Order, Long nID_Protected, Integer nID_Server) throws Exception {
        String URI = "/wf/service/services/getHistoryEvent_Service";
        ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
        params.put("sID_Order", "" + sID_Order);
        params.put("nID_Protected", "" + nID_Protected);
        params.put("nID_Server", "" + nID_Server);
        return doRemoteRequest(URI, params);
    }

    private String doRemoteRequest(String URI, ImmutableMap.Builder<String, String> params) throws Exception {
        ImmutableMap<String, String> paramsMap = params.build();
        log.info("Getting URL with parameters: " + generalConfig.sHostCentral() + URI + ":" + paramsMap);
        String soJSON_HistoryEvent = httpRequester.get(
                generalConfig.sHostCentral() + URI, paramsMap);
        log.info("soJSON_HistoryEvent=" + soJSON_HistoryEvent);
        return soJSON_HistoryEvent;
    }

    public String doRemoteRequest(String URI, ImmutableMap.Builder<String, String> params, String sID_Process, String sID_Status)
            throws Exception {
        if (sID_Process == null) {
            log.warn("For service operation '%s' nID_Process is null. Operation will not be called!", URI);
            return null;
        } else {
            params.put("nID_Process", sID_Process);
            params.put("sID_Status", sID_Status);
            return doRemoteRequest(URI, params);
        }
    }

    @Override
    public String updateHistoryEvent(String sID_Process, String sID_Status, boolean addAccessKey,
            ImmutableMap.Builder<String, String> params) throws Exception {
        String URI = "/wf/service/services/updateHistoryEvent_Service";
        if (params == null) {
            params = ImmutableMap.builder();
        }
        if (addAccessKey) {
            String sAccessKey_HistoryEvent = accessDataDao.setAccessData(
                    httpRequester.getFullURL(URI, params.build()));
            params.put("sAccessKey", sAccessKey_HistoryEvent);
            params.put("sAccessContract", "Request");
            log.info("sAccessKey=" + sAccessKey_HistoryEvent);
        }
        return doRemoteRequest(URI, params, sID_Process, sID_Status);
    }

    @Override
    public void addHistoryEvent(String sID_Process, String sID_Status, ImmutableMap.Builder<String, String> params)
            throws Exception {
        String URI = "/wf/service/services/addHistoryEvent_Service";
        doRemoteRequest(URI, params, sID_Process, sID_Status);
    }
}