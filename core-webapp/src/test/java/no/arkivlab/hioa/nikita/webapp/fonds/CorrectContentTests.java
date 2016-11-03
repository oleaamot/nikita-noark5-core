package no.arkivlab.hioa.nikita.webapp.fonds;

import nikita.model.noark5.v4.Fonds;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

import static nikita.config.Constants.API_PATH;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC;
import static nikita.config.N5ResourceMappings.FONDS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.arkivlab.hioa.nikita.webapp.ApplicationTests;


/**
 * Unit tests for core-webapp checking content.
 *
 * Other tests to consider:
 *  - Test returns application/json application/xml
 *  - Test security works
 *  - Test security with roles access denied etc
 */
public class CorrectContentTests extends ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    Environment environment;

    private MockMvc mockMvc;

    private String contextPath;

    // Required to generate JSON content from Java objects and back
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        /*
         * The preferred way would to get contextPath would be to get it directly from the servlet context:
         *
         *  contextPath = webApplicationContext.getServletContext().getContextPath();
         *
         * but contextPath is null/"" and I have not been able to find out why. This may pose a problem with
         * reusability *if* someone overwrites the contextPath in code. If you do that you might be an idiot!
         *
         * So we are picking the contextPath up this way:
         *    contextPath = environment.getProperty("server.contextPath");
        */
        contextPath = environment.getProperty("server.contextPath");
    }

    /**
     * Test ability to create a correct fonds object with required metadata. Expected return value
     * is 200 OK and the original Fonds object
     */
    //@Test
    public void testCorrectContentInFonds() throws Exception {

        String fondsTitle = "Test fonds title";
        String fondsDescription = "Test fonds description. This fonds should be automatically deleted after tests are undertaken";

        Fonds fonds = new Fonds();
        fonds.setTitle(fondsTitle);
        fonds.setDescription(fondsDescription);
        fonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);


        mockMvc.perform(put(contextPath + "/" + API_PATH + "/" + FONDS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fonds)))
                .andExpect(status().isOk());

        mockMvc.perform(get(contextPath + "/" + API_PATH + "/" + FONDS))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.tittel").value(fondsTitle));
    }

    /**
     * Test ability to create a correct fonds object with required metadata. Expected return value
     * is 200 OK and the original fonds object
     */

    //@Test
    public void testCorrectContentInFonds2() throws Exception {

        String fondsTitle = "Test fonds title";
        String fondsDescription = "Test fonds description. This fonds should be automatically deleted after tests are undertaken";

        Fonds fonds = new Fonds();
        fonds.setTitle(fondsTitle);
        fonds.setDescription(fondsDescription);
        fonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);

        mockMvc.perform(put("/" + FONDS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fonds)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/" + FONDS))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.tittel").value(fondsTitle));
    }

}