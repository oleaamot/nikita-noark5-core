package no.arkivlab.hioa.nikita.webapp.fonds;

import com.fasterxml.jackson.databind.ObjectMapper;
import nikita.model.noark5.v4.Fonds;
import nikita.odata.SearchCriteria;
import no.arkivlab.hioa.nikita.webapp.ApplicationTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static nikita.config.Constants.API_PATH;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC;
import static nikita.config.N5ResourceMappings.FONDS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Unit tests for core-webapp checking OData support.
 *
 */

public class ODataTests extends ApplicationTests {

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

        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        //params.add(new SearchCriteria("createdDate", "gt", "John"));
        //params.add(new SearchCriteria("createdDate", "lt", "Doe"));

        mockMvc.perform(post(contextPath + "/" + API_PATH + "/" + FONDS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fonds)))
                .andExpect(status().isOk());

        ResultActions result = mockMvc.perform(get(contextPath + "/" + API_PATH + "/" + FONDS))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.tittel").value(fondsTitle));

        System.out.println("hello");
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