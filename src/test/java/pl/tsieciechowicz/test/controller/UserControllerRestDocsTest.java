package pl.tsieciechowicz.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.tsieciechowicz.db.model.User;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tsieciechowicz on 14.03.2017.
 */

@ActiveProfiles(value = "test")
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerRestDocsTest extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Rule //Spring Rest docs
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("build/generated-snippets");

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void testMockMvcFindAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/").
                        header("Authorization", "Basic dXNlcjpzZWNyZXQ=").
                        accept(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isOk()).
                andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").
                                       description("Basic auth credentials")),
                        responseFields(
                                fieldWithPath("[].id").
                                        type(JsonFieldType.STRING).description("User identifier"),
                                fieldWithPath("[].username").
                                        type(JsonFieldType.STRING).description("User login"))
                ));
    }

    @Test
    public void testMockMvcFindById() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/users/{id}", 1).
                        header("Authorization", "Basic dXNlcjpzZWNyZXQ=").
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(print()).
                andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("User identifier")),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic auth credentials")),
                        responseFields(
                                fieldWithPath("id").
                                        type(JsonFieldType.STRING).description("User identifier"),
                                fieldWithPath("username").
                                        type(JsonFieldType.STRING).description("User login"))));
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
                andExpect(status().isOk()).
                andDo(print()).
                andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").
                                        type(JsonFieldType.STRING).description("User identifier"),
                                fieldWithPath("username").
                                        type(JsonFieldType.STRING).description("User login"))
                        ));
    }

    @Test
    public void testMockMvcDelete() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/users/delete/{id}", 1)).
                andExpect(status().isOk()).
                andDo(print()).
                andDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(
                            parameterWithName("id").description("User identifier"))
                        ));
    }

}
