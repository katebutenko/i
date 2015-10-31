package org.activiti.rest.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.wf.dp.dniprorada.base.util.JsonRestUtils;
import org.wf.dp.dniprorada.dao.PlaceDao;
import org.wf.dp.dniprorada.model.*;
import org.wf.dp.dniprorada.service.TableDataService;
import org.wf.dp.dniprorada.viewobject.TableData;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationTestsApplicationConfiguration.class)
public class ActivitiRestServicesControllerScenario {
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private PlaceDao placeDao;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldSuccessfullyGetAndSetServicesAndPlacesTables() throws Exception {
        String jsonData = mockMvc.perform(get("/services/getServicesAndPlacesTables")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$", not(empty()))).
                andReturn().getResponse().getContentAsString();
        TableData[] tableDataList = JsonRestUtils.readObject(jsonData, TableData[].class);
        Assert.assertEquals(TableDataService.TablesSet.ServicesAndPlaces.getEntityClasses().length,
                tableDataList.length);

        mockMvc.perform(post("/services/setServicesAndPlacesTables").content(jsonData).
                contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8));
    }

    @Test
    public void shouldSuccessfullyGetAndSetServicesTree() throws Exception {
        String jsonData = mockMvc.perform(get("/services/getServicesTree")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$", not(empty()))).
                andReturn().getResponse().getContentAsString();
        Category[] categoriesBeforeChange = JsonRestUtils.readObject(jsonData, Category[].class);

        String categoryName = "CategoryName438";
        String subcategoryName = "SubcategoryName9873";
        categoriesBeforeChange[0].setName(categoryName);
        categoriesBeforeChange[0].getSubcategories().get(0).setName(subcategoryName);
        String serviceName = categoriesBeforeChange[0].getSubcategories().get(0).getServices().get(0).getName();

        mockMvc.perform(post("/services/setServicesTree").content(JsonRestUtils.toJson(categoriesBeforeChange)).
                contentType(APPLICATION_JSON_CHARSET_UTF_8).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$[0].sName", is(categoryName)));

        jsonData = mockMvc.perform(get("/services/getServicesTree").
                param("sFind", serviceName).
                contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Category[] categoriesAfterChange = JsonRestUtils.readObject(jsonData, Category[].class);
        Assert.assertEquals(categoryName, categoriesAfterChange[0].getName());
        Assert.assertEquals(subcategoryName, categoriesAfterChange[0].getSubcategories().get(0).getName());
    }

    @Test
    public void shouldSuccessfullyFilterServicesTreeByPlaceId() throws Exception {
        for (String supportedPlaceId : ActivitiRestServicesController.SUPPORTED_PLACE_IDS) {
            String jsonData = mockMvc
                    .perform(get("/services/getServicesTree").param("asID_Place_UA", supportedPlaceId))
                    .andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8))
                    .andReturn().getResponse().getContentAsString();
            Category[] categories = JsonRestUtils.readObject(jsonData, Category[].class);
            if (categories.length == 0) {
                continue;
            }

            for (int i = 0; i < categories.length; i++) {
                Category category = categories[i];
                for (Subcategory subcategory : category.getSubcategories()) {
                    for (Service service : subcategory.getServices()) {
                        String serviceJsonData = mockMvc
                                .perform(get("/services/getService").param("nID", service.getId().toString()))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).andReturn()
                                .getResponse().getContentAsString();
                        Service serviceWithServiceData = JsonRestUtils.readObject(serviceJsonData, Service.class);
                        if (serviceWithServiceData.getServiceDataList() != null) {
                            boolean hasPlaceId = false;
                            boolean nationalService = false;

                            int totalServiceDataCount = 0;
                            for (ServiceData serviceData : serviceWithServiceData.getServiceDataList()) {
                                if (serviceData.getoPlace() == null) {
                                    nationalService = true;
                                    totalServiceDataCount++;
                                    continue; // national service
                                }

                                boolean dataHasPlaceId = ActivitiRestServicesController.checkIdPlacesContainsIdUA(
                                        placeDao, serviceData.getoPlace(), Arrays.asList(supportedPlaceId));

                                if (dataHasPlaceId) {
                                    hasPlaceId = true;
                                    totalServiceDataCount++;
                                }
                            }

                            if (!hasPlaceId && !nationalService) {
                                Assert.assertTrue(String.format("Service [%s] is wrong!", service.getName()) , false);
                            }

                            Assert.assertEquals(service.getSub(), totalServiceDataCount);
                        }
                    }
                }
            }
            break;
        }
    }

    @Test
    public void shouldSuccessfullyGetAndSetService() throws Exception {
        String jsonData = mockMvc.perform(get("/services/getService").
                param("nID", "1")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$.nID", is(1))).
                andExpect(jsonPath("$.sName", not(empty()))).
                andReturn().getResponse().getContentAsString();
        Service serviceBeforeChange = JsonRestUtils.readObject(jsonData, Service.class);

        String serviceName = "ServiceName123";
        String serviceUrl = "ServiceDataUrl7483";
        serviceBeforeChange.setName(serviceName);
        serviceBeforeChange.getServiceDataList().get(0).setUrl(serviceUrl);

        mockMvc.perform(post("/services/setService").content(JsonRestUtils.toJson(serviceBeforeChange)).
                contentType(APPLICATION_JSON_CHARSET_UTF_8).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$.nID", is(1))).
                andExpect(jsonPath("$.sName", is(serviceName)));

        jsonData = mockMvc.perform(get("/services/getService").
                param("nID", "1").
                contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Service serviceAfterChange = JsonRestUtils.readObject(jsonData, Service.class);
        Assert.assertEquals(serviceName, serviceAfterChange.getName());
        Assert.assertEquals(serviceUrl, serviceAfterChange.getServiceDataList().get(0).getUrl());
    }

    @Test
    public void getServiceShouldResolveConcreteFileForFieldsWithSmartPaths() throws Exception {
        testGetSetServiceField("{\"nID\":1, \"sInfo\":\"[/test.html]\"}", "$.sInfo",
                "<html><body><span>info</span></body></html>");
        testGetSetServiceField("{\"nID\":1, \"sFAQ\":\"[/test.html]\"}", "$.sFAQ",
                "<html><body><span>faq</span></body></html>");
        testGetSetServiceField("{\"nID\":1, \"sLaw\":\"[/test.html]\"}", "$.sLaw",
                "<html><body><span>law</span></body></html>");
    }

    @Test
    public void getServiceShouldResolveFileByIdForFieldsWithSmartPaths() throws Exception {
        testGetSetServiceField("{\"nID\":1, \"sInfo\":\"[*]\"}", "$.sInfo",
                "<html><body><span>info</span></body></html>");
        testGetSetServiceField("{\"nID\":1, \"sFAQ\":\"[*]\"}", "$.sFAQ", "<html><body><span>faq</span></body></html>");
        testGetSetServiceField("{\"nID\":1, \"sLaw\":\"[*]\"}", "$.sLaw", "<html><body><span>law</span></body></html>");
    }

    @Test
    public void getServiceShouldResolveInitialValueForFieldsWithoutSmartPaths() throws Exception {
        testGetSetServiceField("{\"nID\":1, \"sInfo\":\"somefile.[asdf]info\"}", "$.sInfo", "somefile.[asdf]info");
        testGetSetServiceField("{\"nID\":1, \"sFAQ\":\"somefile.[asdf]faq\"}", "$.sFAQ", "somefile.[asdf]faq");
        testGetSetServiceField("{\"nID\":1, \"sLaw\":\"somefile.[asdf]law\"}", "$.sLaw", "somefile.[asdf]law");
    }

    @Test
    public void setServiceShouldReturnErrorIfContentFilesCannotBeFoundForFieldsWithSmartPaths() throws Exception {
        testGetSetServiceField("{\"nID\":1, \"sInfo\":\"[/some.file]\"}", "$.sInfo", "[/some.file]");
        testGetSetServiceField("{\"nID\":1, \"sFAQ\":\"[/some.file]\"}", "$.sFAQ", "[/some.file]");
        testGetSetServiceField("{\"nID\":1, \"sLaw\":\"[/some.file]\"}", "$.sLaw", "[/some.file]");
    }

    // region File Pattern Service Helpers

    private void testGetSetServiceField(String service, String jsonPath, String expected) throws Exception {
        assertServiceFieldExpected(performSetService(service), jsonPath, expected);
        assertServiceFieldExpected(performGetService((long) 1), jsonPath, expected);
    }

    //endregion

    @Test
    public void shouldSuccessfullyGetAndSetPlaces() throws Exception {
        String jsonData = mockMvc.perform(get("/services/getPlaces").
                contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$", not(empty()))).
                andReturn().getResponse().getContentAsString();
        Region[] regionsBeforeChange = JsonRestUtils.readObject(jsonData, Region[].class);

        String testName = "Place4378";
        String cityName = "City438";
        regionsBeforeChange[0].setName(testName);
        regionsBeforeChange[0].getCities().get(0).setName(cityName);

        mockMvc.perform(post("/services/setPlaces").content(JsonRestUtils.toJson(regionsBeforeChange)).
                contentType(APPLICATION_JSON_CHARSET_UTF_8).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$[0].sName", is(testName)));

        jsonData = mockMvc.perform(get("/services/getPlaces")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Region[] placesAfterChange = JsonRestUtils.readObject(jsonData, Region[].class);
        Assert.assertEquals(testName, placesAfterChange[0].getName());
        Assert.assertEquals(cityName, placesAfterChange[0].getCities().get(0).getName());
    }

    @Test
    public void recursiveCompletelyDeletedService() throws Exception {

        int serviceId = 4;
        String jsonData = mockMvc.perform(get("/services/getService").
                param("nID", "" + serviceId)).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$.nID", is(serviceId))).
                andExpect(jsonPath("$.aServiceData", not(empty()))).
                andExpect(jsonPath("$.sName", not(empty()))).
                andReturn().getResponse().getContentAsString();
        Service actualService = JsonRestUtils.readObject(jsonData, Service.class);

        jsonData = mockMvc.perform(delete("/services/removeService").
                param("nID", String.valueOf(actualService.getId()))).
                andExpect(status().isNotModified()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("error"));

        jsonData = mockMvc.perform(delete("/services/removeService").
                param("nID", String.valueOf(actualService.getId())).
                param("bRecursive", "true")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("success"));
    }

    @Test
    public void deletedServiceById() throws Exception {
        String jsonData = mockMvc.perform(get("/services/getService").
                param("nID", "215")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath("$.nID", is(215))).
                andExpect(jsonPath("$.aServiceData", is(empty()))).
                andExpect(jsonPath("$.sName", not(empty()))).
                andReturn().getResponse().getContentAsString();
        Service actualService = JsonRestUtils.readObject(jsonData, Service.class);

        jsonData = mockMvc.perform(delete("/services/removeService").
                param("nID", String.valueOf(actualService.getId()))).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("success"));
    }

    @Test
    public void recursiveCompletelyDeletedSubcategory() throws Exception {
        String jsonData = mockMvc.perform(delete("/services/removeSubcategory").
                param("nID", "6")).
                andExpect(status().isNotModified()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("error"));

        jsonData = mockMvc.perform(delete("/services/removeSubcategory").
                param("nID", "6").
                param("bRecursive", "true")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("success"));
    }

    @Test
    public void deletedSubcategoryById() throws Exception {
        String jsonData = mockMvc.perform(delete("/services/removeSubcategory").
                param("nID", "6")).
                andExpect(status().isNotModified()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("error"));

        // currently no subcategory without services

        //       jsonData = mockMvc.perform(delete("/services/removeSubcategory").
        //               param("nID", "6").param("bRecursive", "true")).
        //               andExpect(status().isOk()).
        //               andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
        //               andReturn().getResponse().getContentAsString();
        //       Assert.assertTrue(jsonData.contains("success"));
    }

    @Test
    public void recursiveRemoveCategory() throws Exception {
        String jsonData = mockMvc.perform(delete("/services/removeCategory").
                param("nID", "2").
                param("bRecursive", "true")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("success"));
    }

    @Test
    public void removeCategoryById() throws Exception {
        String jsonData = mockMvc.perform(delete("/services/removeCategory").
                param("nID", "1")).
                andExpect(status().isNotModified()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("error"));
    }

    @Test
    public void removeServiceData() throws Exception {
        String jsonData = mockMvc.perform(delete("/services/removeServiceData").
                param("nID", "1").
                param("bRecursive", "true")).
                andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andReturn().getResponse().getContentAsString();
        Assert.assertTrue(jsonData.contains("success"));
    }

    // region Helpers

    private void assertServiceFieldExpected(ResultActions ra, String jsonPath, String expected) throws Exception {
        ra.andExpect(status().isOk()).
                andExpect(content().contentType(APPLICATION_JSON_CHARSET_UTF_8)).
                andExpect(jsonPath(jsonPath, is(expected)));
    }

    private ResultActions performGetService(Long serviceId) throws Exception {
        return mockMvc.perform(get("/services/getService").
                param("nID", serviceId.toString()).
                contentType(APPLICATION_JSON_CHARSET_UTF_8));
    }

    private ResultActions performSetService(String service) throws Exception {
        return mockMvc.perform(post("/services/setService").content(service).
                contentType(APPLICATION_JSON_CHARSET_UTF_8).
                accept(MediaType.APPLICATION_JSON));
    }

    //endregion
}