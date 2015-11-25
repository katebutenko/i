package org.activiti.rest.controller;

import com.google.common.collect.ImmutableMap;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.TaskQuery;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wf.dp.dniprorada.base.util.JsonRestUtils;
import org.wf.dp.dniprorada.util.luna.AlgorithmLuna;
import org.wf.dp.dniprorada.util.luna.CRCInvalidException;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author vit@tym.im
 */
public class ActivityRestApiControllerDeleteProcessScenario extends ActivitiScenarioBase {
    public static final Long TEST_PROCESS_INSTANCEID = 123L;
    public static final String TEST_PROCESS_INSTANCEID_STR = TEST_PROCESS_INSTANCEID.toString();
    public static final String TEST_LOGIN = "testLogin";
    public static final String TEST_REASON = "testReason";

    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void testDeleteProcess_CRCProblem() throws Exception {
        String jsonData = mockMvc.perform(delete("/rest/delete-process").
                param("nID_Protected", "123123")).
                andExpect(status().isForbidden()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Map<String, String> res = JsonRestUtils.readObject(jsonData, Map.class);
        Assert.assertEquals(new CRCInvalidException().getMessage(), res.get("message"));
    }

    @Test
    public void testDeleteProcess_NotFound() throws Exception {
        doThrow(new ActivitiObjectNotFoundException("Not found")).when(runtimeService).deleteProcessInstance(
                TEST_PROCESS_INSTANCEID_STR, TEST_REASON);
        String jsonData = mockMvc.perform(delete("/rest/delete-process").
                param("nID_Protected", String.valueOf(AlgorithmLuna.getProtectedNumber(TEST_PROCESS_INSTANCEID))).
                param("sReason", TEST_REASON).
                param("sLogin", TEST_LOGIN)).
                andExpect(status().isForbidden()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Map<String, String> res = JsonRestUtils.readObject(jsonData, Map.class);
        Assert.assertEquals(new RecordNotFoundException().getMessage(), res.get("message"));
    }

    @Test
    public void testDeleteProcess_OK() throws Exception {
        mockMvc.perform(delete("/rest/delete-process").
                param("nID_Protected", String.valueOf(AlgorithmLuna.getProtectedNumber(TEST_PROCESS_INSTANCEID))).
                param("sReason", TEST_REASON).
                param("sLogin", TEST_LOGIN)).
                andExpect(status().isOk()).
                andExpect(content().string(""));
        verify(runtimeService).deleteProcessInstance(TEST_PROCESS_INSTANCEID_STR, TEST_REASON);
        verify(httpRequester).get("mock://host/wf/service/services/updateHistoryEvent_Service",
                ImmutableMap
                        .of("nID_Process", TEST_PROCESS_INSTANCEID_STR, "sID_Status", "Заявка была удалена (testLogin): testReason"));
    }
}
