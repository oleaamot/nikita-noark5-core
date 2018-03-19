package nikita.webapp.structure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nikita.common.model.noark5.v4.Fonds;
import nikita.common.model.noark5.v4.NoarkGeneralEntity;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.common.util.deserialisers.hateoas.HateoasDeserializer;
import nikita.webapp.model.Token;
import nikita.webapp.model.User;
import nikita.webapp.utils.NoarkGeneralEntitySerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class FondsIT {

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers;
    private ObjectMapper mapper;

    public FondsIT() {
    }

    @Before
    public void setup() {
        mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(NoarkGeneralEntity.class,
                new NoarkGeneralEntitySerializer());
        module.addDeserializer(HateoasNoarkObject.class,
                new HateoasDeserializer());
        mapper.registerModule(module);
    }

    @Test
    public void test_1_Login() {
        checkLoggedIn();
    }

    @Test
    public void test_2_createFonds() throws JsonProcessingException {

        assertTrue(checkLoggedIn());

        assertNotNull(headers);
        assertNotNull(headers.get("Authorization"));

        Fonds fonds = new Fonds();
        fonds.setDescription("description");
        fonds.setTitle("title");

        String serializedFonds = mapper.writeValueAsString(fonds);

        HttpEntity<String> request = new HttpEntity<>(serializedFonds, headers);

        ResponseEntity<HateoasNoarkObject> responseEntity =
                restTemplate.exchange("/hateoas-api/arkivstruktur/ny-arkiv",
                        HttpMethod.POST,
                        request,
                        HateoasNoarkObject.class);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        HateoasNoarkObject result = responseEntity.getBody();

        ArrayList<INikitaEntity> entities = (ArrayList<INikitaEntity>)
                result.getList();
        assertNotNull(entities);
        assertEquals(entities.size(), 1);

        INoarkGeneralEntity returnedFonds =
                (INoarkGeneralEntity) entities.get(0);

        assertEquals("description", returnedFonds.getDescription());
        assertEquals("title", returnedFonds.getTitle());
    }

    private boolean checkLoggedIn() {

        // If we're not logged in, try logging in
        if (headers != null) {
            return true;
        } else {
            User admin = new User("admin", "password");
            ResponseEntity<Token> responseEntity =
                    restTemplate.postForEntity("/auth", admin, Token.class);
            Token token = responseEntity.getBody();
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertNotNull(token);
            headers = new HttpHeaders();
            headers.add("Accept", "application/vnd.noark5-v4+json");
            headers.add("Content-Type", "application/vnd.noark5-v4+json");
            headers.add("Authorization", token.getToken());
            return true;
        }
    }
}
