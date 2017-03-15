package pl.tsieciechowicz.test.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.tsieciechowicz.controller.UserController;
import pl.tsieciechowicz.db.model.User;

/**
 * Created by tsieciechowicz on 12.03.2017.
 */
@ActiveProfiles(value = "test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //starting entire spring application context
@AutoConfigureMockMvc
//@WebMvcTest -> starting only web layer
public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    UserController userController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate; //first testing possibility

    @Autowired
    private MockMvc mockMvc; //second testing possibility


    @Test
    public void testIsApplicationRunning() {
        assertThat(userController).isNotNull(); //checking if spring context was started
    }

//    @Test
//    public void testRestTemplateFindAll() {
//        logger.info("response={}", testRestTemplate.getForObject("http://localhost:" + port + "/", String.class));
//
//    }

    @Test
    public void testMockMvcFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/").accept(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isOk());
    }

    @Test
    public void testMockMvcFindById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isOk());
    }

    @Test
    public void testMockMvcSave() throws Exception {
        User user = User.builder().
                id("101").
                username("amickiewicz").
                build();

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(user);
        logger.info("generated requestBody={}", requestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/save").
                contentType(MediaType.APPLICATION_JSON).
                content(requestBody)).
                andDo(print()).
                andExpect(status().isOk());
    }

    @Test
    public void testMockMvcDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1")).
                andDo(print()).
                andExpect(status().isOk());
    }


}
