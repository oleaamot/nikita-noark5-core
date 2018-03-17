package nikita.webapp.structure;

import nikita.common.model.noark5.v4.Fonds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles()
public class FondsTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createClient() {

        Fonds fonds = new Fonds();
        fonds.setDescription("description");
        fonds.setTitle("title");

        ResponseEntity<Fonds> responseEntity =
                restTemplate.postForEntity("/funksjon", fonds, Fonds.class);
        Fonds returnedFonds = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("description", returnedFonds.getDescription());
        assertEquals("title", returnedFonds.getTitle());
    }
}
