package run;

import nikita.config.ServerConstants;
import nikita.model.noark5.v4.*;
import nikita.config.N5ResourceMappings;
import nikita.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class WebappRestTest {

    private static final Logger logger = LoggerFactory.getLogger(WebappRestTest.class);

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(final String... args) throws UnknownHostException, URISyntaxException {

        // Configuration information. This should be set via a property file
        String appName = "noark5v4";
        String port = "8092";
        String address = "127.0.0.1";
        String username="admin";
        String password="pass";
        String addressAuthentication = "http://"+address+":"+port+"/"+appName+"/doLogin";
        String addressApplication = "http://"+address+":"+port+"/"+appName+"/hateoas-api/";

        String sessionId = ""; // Used to hold sessionId after login

        List<HttpMessageConverter<?>> messageConverters = new LinkedList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        // Set up the rest template to allow communication with REST sever
        ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        restTemplate.setMessageConverters(messageConverters);

        // Create an object that can hold the username and password as headers
        MultiValueMap<String, String> map = new LinkedMultiValueMap <String, String>();
        map.add("accept", "application/json");
        map.add("username", username);
        map.add("password", password);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map,
                requestHeaders);


        logger.debug("Attempting to authenticate against [" + addressAuthentication + "]");
        // do the login
        ResponseEntity<String> result = restTemplate.exchange(addressAuthentication, HttpMethod.POST, entity, String.class);
        // Problem is that there is no difference between successful or unsuccessful!!!!
        assertThat(result.getStatusCode(), is(HttpStatus.FOUND));
        HttpHeaders respHeaders = result.getHeaders();
        // Pick out the sessionId
        if (respHeaders.containsKey(ServerConstants.SET_COOKIE)) {
            List<String> list = respHeaders.get(ServerConstants.SET_COOKIE);
            // Assuming only one session
            sessionId = list.get(0);
        }

        // Login should return 302
        assertThat(result.getStatusCode(), is(HttpStatus.FOUND));

        logger.debug("Attempting to interact with the application [" + addressApplication + "]");

        // Create a fonds object to be persisted to the database via REST
        Fonds fonds = new Fonds();
        fonds.setTitle(Constants.TEST_TITLE);
        fonds.setDocumentMedium(N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC);

        HttpHeaders createObjectHeaders = new HttpHeaders();

        // Use the sessionId from the login
        createObjectHeaders.add("Cookie", sessionId);

        // set content types to JSON
        createObjectHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        createObjectHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Fonds> fondsEntity = new HttpEntity<>(fonds, createObjectHeaders);
        String fondsURI= addressApplication + N5ResourceMappings.FONDS;

        // Attempt to create the fonds object
        ResponseEntity<Fonds> responseFondsCreate = restTemplate.exchange(fondsURI, HttpMethod.POST, fondsEntity, Fonds.class);

        Fonds createdFonds = responseFondsCreate.getBody();
        assertThat(responseFondsCreate.getStatusCode(), is(HttpStatus.OK));

        Fonds childFonds = new Fonds();
        childFonds.setTitle(Constants.TEST_TITLE);
        childFonds.setDocumentMedium(N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC);
        childFonds.setReferenceParentFonds(createdFonds);

        fondsEntity = new HttpEntity<>(childFonds, createObjectHeaders);

        // Attempt to create the child fonds object
        ResponseEntity<Fonds> responseCreateChildFonds = restTemplate.exchange(fondsURI, HttpMethod.POST, fondsEntity, Fonds.class);
        assertThat(responseCreateChildFonds.getStatusCode(), is(HttpStatus.OK));
        Fonds persistedFonds = responseCreateChildFonds.getBody();

        // Create a series object to be persisted to the database via REST
        Series series = new Series();
        series.setTitle(Constants.TEST_TITLE);
        series.setDocumentMedium(N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC);
        series.setReferenceFonds(persistedFonds);

        HttpEntity<Series> seriesEntity = new HttpEntity<>(series, createObjectHeaders);
        String seriesURI = addressApplication + N5ResourceMappings.SERIES;
        // Attempt to create the series object
        ResponseEntity<Series> responseCreateSeries = restTemplate.exchange(seriesURI, HttpMethod.POST, seriesEntity, Series.class);
        Series persistedSeries = responseCreateSeries.getBody();
        assertThat(responseCreateSeries.getStatusCode(), is(HttpStatus.OK));

        // Create a file object to be persisted to the database via REST
        File file = new File();
        file.setTitle(Constants.TEST_TITLE);
        file.setDocumentMedium(N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC);
        file.setReferenceSeries(persistedSeries);

        HttpEntity<File> fileEntity = new HttpEntity<>(file, createObjectHeaders);
        String fileURI = addressApplication + N5ResourceMappings.FILE;
        // Attempt to create the file object
        ResponseEntity<File> responseCreateFile = restTemplate.exchange(fileURI, HttpMethod.POST, fileEntity, File.class);
        File persistedFile = responseCreateFile.getBody();
        assertThat(responseCreateSeries.getStatusCode(), is(HttpStatus.OK));

        // Create a record object to be persisted to the database via REST
        Record record = new Record();
        record.setReferenceFile(persistedFile);
        HttpEntity<Record> recordEntity = new HttpEntity<>(record, createObjectHeaders);
        String recordURI = addressApplication + N5ResourceMappings.REGISTRATION;
        // Attempt to create the record object
        ResponseEntity<Record> responseCreateRecord = restTemplate.exchange(recordURI, HttpMethod.POST, recordEntity, Record.class);
        assertThat(responseCreateSeries.getStatusCode(), is(HttpStatus.OK));
        Record persistedRecord = responseCreateRecord.getBody();

        // Create a documentDescription object to be persisted to the database via REST
        DocumentDescription documentDescription = new DocumentDescription();
        documentDescription.addReferenceRecord(persistedRecord);
        HttpEntity<DocumentDescription> documentDescriptionEntity = new HttpEntity<>(documentDescription, createObjectHeaders);
        String documentDescriptionURI = addressApplication + N5ResourceMappings.DOCUMENT_DESCRIPTION;
        // Attempt to create the documentDescription object
        ResponseEntity<DocumentDescription> responseCreateDocumentDescription = restTemplate.exchange(documentDescriptionURI, HttpMethod.POST, documentDescriptionEntity, DocumentDescription.class);
        assertThat(responseCreateSeries.getStatusCode(), is(HttpStatus.OK));
        DocumentDescription persistedDocumentDescription = responseCreateDocumentDescription.getBody();

        // Create a documentObject object to be persisted to the database via REST
        DocumentObject documentObject = new DocumentObject();
        documentObject.setReferenceDocumentDescription(persistedDocumentDescription);
        HttpEntity<DocumentObject> documentObjectEntity = new HttpEntity<>(documentObject, createObjectHeaders);
        String documentObjectURI = addressApplication + N5ResourceMappings.DOCUMENT_OBJECT;
        // Attempt to create the documentObject object
        ResponseEntity<DocumentObject> responseCreateDocumentObject = restTemplate.exchange(documentObjectURI, HttpMethod.POST, documentObjectEntity, DocumentObject.class);
        assertThat(responseCreateSeries.getStatusCode(), is(HttpStatus.OK));
        DocumentObject persistedDocumentObject = responseCreateDocumentObject.getBody();

        
        // read all fonds objects
        ResponseEntity <Fonds[]> responseFondsGet = restTemplate.exchange(fondsURI, HttpMethod.GET, fondsEntity, Fonds[].class);
        assertThat(responseFondsGet.getStatusCode(), is(HttpStatus.OK));

        Fonds[] fondsList = responseFondsGet.getBody();
        for (int i=0; i<fondsList.length; i++) {
            assertThat(fondsList[i], notNullValue());
            assertThat(fondsList[i].getTitle(), is(Constants.TEST_TITLE));
            assertThat(fondsList[i].getDocumentMedium(), is(N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC));
        }
    }
}
