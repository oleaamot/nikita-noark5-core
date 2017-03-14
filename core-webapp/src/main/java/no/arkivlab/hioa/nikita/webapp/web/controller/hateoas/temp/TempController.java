package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.temp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static nikita.config.Constants.*;

/**
 * Created by tsodring on 1/31/17.
 * <p>
 * Temporary place holder to return a 501 Not implemented
 */

@RestController
@RequestMapping(produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML}, method = RequestMethod.GET)
public class TempController {

    @RequestMapping(value = NOARK_CASE_HANDLING_PATH + SLASH)
    public ResponseEntity<String> noSakarkiv() {
        return new ResponseEntity<>("{\"message\": \"Not implemented\"}", HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = IMPORT_API_PATH + SLASH)
    public ResponseEntity<String> noImport() {
        return new ResponseEntity<>("{\"message\": \"Not implemented\"}", HttpStatus.NOT_IMPLEMENTED);
    }

}
