package no.arkivlab.hioa.nikita.webapp.structure;


import nikita.config.Constants;
import nikita.model.noark5.v4.Fonds;
import no.arkivlab.hioa.nikita.webapp.ApplicationTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC;
import static nikita.config.N5ResourceMappings.FONDS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;



/**
 * Unit tests for core-webapp. This set of tests will test correct creation of archive structure.
 *
 * 1. testCorrectArchiveStructureFull
 *    This will test that it is possible to build the following structure:
 *      Fonds>Series>ClassificationSystem>Class>CaseFile>RegistryEntry>DocumentDescription>DocumentObject
 * 2.
 *
 * n. testIncorrectArchiveStructure
 */
public class ArchiveStructureTests extends ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockHttpSession session;

    @Autowired
    private MockHttpServletRequest request;


    private MockMvc mockMvc;


    // Generate JSON content from Java objects and back
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {

          mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }


    @Test
    public void testLogin() throws Exception {
        //RequestBuilder requestBuilder = formLogin().user().password();

        RequestBuilder requestBuilder = post("/doLogin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", Constants.USERNAME_ADMIN)
                .param("password", Constants.PASSWORD_ADMIN);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(cookie().exists("JSESSIONID"))
                .andExpect(status().isOk());    }

//    @Test
    public void testCorrectArchiveStructureFull() throws Exception {
        String fondsTitle = "Test fonds title";
        String fondsDescription = "Test fonds description. This fonds should be automatically deleted after tests are undertaken";

        Fonds fonds = new Fonds();
        fonds.setTitle(fondsTitle);
        fonds.setDescription(fondsDescription);
        fonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);

        MvcResult result = mockMvc.perform(post("/" + FONDS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fonds)))
                .andExpect(status().isOk())
                .andReturn();

        String responseObject = result.getResponse().getContentAsString();
        // How do I check this is actually a fonds, a try catch?
        fonds = objectMapper.readValue(responseObject, Fonds.class);

        System.out.println(fonds);
    }
}