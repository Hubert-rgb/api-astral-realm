package HubertRoszyk.company.controller;

import HubertRoszyk.company.service.BuildingService;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(BuildingsController.class)
class BuildingsControllerTest {
    @Autowired
    private MockMvc mvc;

    /*@InjectMocks
    BuildingsController buildingsController;
    @Mock
    private BuildingService buildingService;*/

    @Test
    void addBuilding() {
    }

    @Test
    void upgradeBuilding() {
    }

    @Test
    void getBuildingListOnPlanet() {
    }

    @Test
    void getAllBuildingTypes() throws Exception {
        String actual = "[\n" +
                "    {\n" +
                "        \"name\": \"INDUSTRY\",\n" +
                "        \"levelNums\": 11,\n" +
                "        \"buildingPrice\": 10,\n" +
                "        \"volume\": 1,\n" +
                "        \"constructionCycles\": 5\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"SCIENCE\",\n" +
                "        \"levelNums\": 6,\n" +
                "        \"buildingPrice\": 20,\n" +
                "        \"volume\": 2,\n" +
                "        \"constructionCycles\": 8\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"DEFENSE\",\n" +
                "        \"levelNums\": 4,\n" +
                "        \"buildingPrice\": 15,\n" +
                "        \"volume\": 1,\n" +
                "        \"constructionCycles\": 7\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"ATTACK\",\n" +
                "        \"levelNums\": 4,\n" +
                "        \"buildingPrice\": 15,\n" +
                "        \"volume\": 3,\n" +
                "        \"constructionCycles\": 7\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"STORAGE\",\n" +
                "        \"levelNums\": 10,\n" +
                "        \"buildingPrice\": 15,\n" +
                "        \"volume\": 30,\n" +
                "        \"constructionCycles\": 3\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"SHIP_YARD\",\n" +
                "        \"levelNums\": 4,\n" +
                "        \"buildingPrice\": 25,\n" +
                "        \"volume\": 2,\n" +
                "        \"constructionCycles\": 10\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"HARBOUR\",\n" +
                "        \"levelNums\": 5,\n" +
                "        \"buildingPrice\": 30,\n" +
                "        \"volume\": 3,\n" +
                "        \"constructionCycles\": 10\n" +
                "    }\n" +
                "]";
        RequestBuilder request = MockMvcRequestBuilders.get("/building-controller/building-types");
        MvcResult result = mvc.perform(request).andReturn();
        JSONAssert.assertEquals(actual, result.toString(), false);
    }
}