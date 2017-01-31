package no.arkivlab.hioa.nikita.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import nikita.model.noark5.v4.Fonds;
import no.arkivlab.hioa.nikita.webapp.run.N5CoreApp;
import no.arkivlab.hioa.nikita.webapp.spring.datasource.TestDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

import static nikita.config.Constants.HATEOAS_API_PATH;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC;
import static nikita.config.N5ResourceMappings.FONDS;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * AnnotationConfigWebContextLoader allows application to run configured with annotations, not xml
 * ConfigFileApplicationContextInitializer is required to load the application-test.yaml file
 * classes={TestDataSource.class} required to load persistence
 * Transactional: We will be using a database and need transaction support
 * WebAppConfiguration: This is a web application we are testing
 * ActiveProfiles("test"): Run application with test profile
 *
 * All tests extend this class
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={AnnotationConfigWebContextLoader.class,
        TestDataSource.class, N5CoreApp.class
//        AppWebMvcConfiguration.class
})


@ContextConfiguration(/*loader=AnnotationConfigWebContextLoader.class,*/initializers = ConfigFileApplicationContextInitializer.class)

   //                      classes={TestDataSource.class, ServiceConfig.class, AppWebMvcConfiguration.class})
@ComponentScan({"no.arkivlab.hioa.nikita.webapp.web.controller", "no.arkivlab.hioa.nikita.webapp.service.spring"})
@WebAppConfiguration
//@EnableWebMvc
//@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles({"test"})
@WithMockUser(username="admin", roles={"USER","ADMIN"})
public class ApplicationTests {

    @Autowired
    Environment environment;

    private MockMvc mockMvc;

    private String contextPath;

    @Autowired
    private FilterChainProxy springSecurityFilter;

    @Autowired
    protected UserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void contextLoads() {
        assertTrue(true);
    }

    // Required to generate JSON content from Java objects and back
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilter)
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

    // Create a set ordering of tests
    /*@Test

    public void aRunTests() throws Exception {
        createFondsObject();
        getFondsObject();
    }
*/


    //@Test

    public void loginUserOK() throws Exception {

        HttpSession session = mockMvc.perform(post("/doLogin").param("username", "admin").param("password", "pass").with(user("admin")))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/"))
                .andReturn()
                .getRequest()
                .getSession();

        Assert.assertNotNull(session);

        Map<String,Object> model = mockMvc.perform(get("/").session((MockHttpSession)session).locale(Locale.ENGLISH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("logged_in"))
                .andExpect(model().attributeExists("user"))
                .andReturn()
                .getModelAndView()
                .getModel();

        User userFromModel = (User) model.get("admin");

        Assert.assertEquals("admin", userFromModel.getUsername());

    }


    /**
     * Test ability to create a correct fonds object with required administration. Expected return value
     * is 200 OK and the original Fonds object
     */
    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    public void createFondsObject() throws Exception {

        String fondsTitle = "Test fonds title   ";
        String fondsDescription = "Test fonds description. This fonds should be automatically deleted after tests are undertaken";

        Fonds fonds = new Fonds();
        fonds.setTitle(fondsTitle);
        fonds.setDescription(fondsDescription);
        fonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);

        mockMvc.perform(post("/" + HATEOAS_API_PATH + "/" + FONDS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fonds)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    public void getFondsObject() throws Exception {

        String fondsTitle = "Test fonds title   ";
        String fondsDescription = "Test fonds description. This fonds should be automatically deleted after tests are undertaken";

        Fonds fonds = new Fonds();
        fonds.setTitle(fondsTitle);
        fonds.setDescription(fondsDescription);
        fonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);


        ResultActions result = mockMvc.perform(get("/" + HATEOAS_API_PATH + "/" + FONDS + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title").value(fondsTitle));

    }
}