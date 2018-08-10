package com.cisClient.cisclient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(ids = "com.producer:cis-producer:+:stubs:5000", stubsMode = StubsMode.LOCAL)
public class ProductControllerTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void shouldReturnMongoose() throws Exception {
    mockMvc.perform(get("/find/1")).andExpect(status().isOk())
        .andExpect(jsonPath("$").value("Mongoose"));
  }

  @Test
  public void verifyObject() throws Exception {
    mockMvc.perform(post("/describe").contentType(MediaType.APPLICATION_JSON)
    .param("name", "Playstation").param("price", "120"))
        .andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("name").value("Playstation"))
    .andExpect(jsonPath("price").value(120));
  }

  @Test
  public void verifyFirstObject() throws Exception {
    mockMvc.perform(post("/getProduct").param("name", "Nintendo 64"))
        .andExpect(status().isOk()).andExpect(jsonPath("name").value("Nintendo 64"))
        .andExpect(jsonPath("price").value(65));
  }
}
