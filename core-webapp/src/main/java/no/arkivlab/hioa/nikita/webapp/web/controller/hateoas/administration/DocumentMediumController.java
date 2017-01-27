package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.administration;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.util.exceptions.NikitaException;
import nikita.model.noark5.v4.hateoas.FondsHateoas;
import nikita.model.noark5.v4.metadata.DocumentMedium;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ICaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.Constants.SLASH;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM;
import static nikita.config.N5ResourceMappings.CODE;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + DOCUMENT_MEDIUM)
public class DocumentMediumController {

    @Autowired
    ICaseFileService caseFileService;

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a new DocumentMedium object", notes = "Returns the newly" +
            " created DocumentMedium object after it is persisted to the database", response = DocumentMedium.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentMedium " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 201, message = "DocumentMedium " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMTNED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + NEW_DOCUMENT_MEDIUM)
    public ResponseEntity<DocumentMedium> createDocumentMedium(@RequestBody DocumentMedium documentMedium)  throws NikitaException {
        return new ResponseEntity<> (HttpStatus.NOT_IMPLEMENTED);
    }

    // API - All GET Requests (CRUD - READ)

    // TODO: Here you have to handle returning an iterator of objects
    /*
    @RequestMapping(value = N5ResourceMappings.FONDS + SLASH + "{id}", method = RequestMethod.GET)
    public ResponseEntity<FondsHateoas> findOne(@PathVariable("id") final Long id) {
        Fonds fonds = fondsService.findById(id);
        FondsHateoas fondsHateoas = new FondsHateoas(fonds);
        return new ResponseEntity<> (fondsHateoas, HttpStatus.CREATED);
    }
    */
    @ApiOperation(value = "Gets a particular documentMedium object given a code", notes = "Returns the requested " +
            " documentMedium object", response = DocumentMedium.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentMedium " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 201, message = "DocumentMedium " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMTNED)})
    @Counted
    @Timed

    @RequestMapping(value = CODE + SLASH + "{kode}", method = RequestMethod.GET)
    public ResponseEntity<DocumentMedium> findOne(@PathVariable("kode") final String code) {
        //DocumentMedium documentMedium = documentMediumService.findbyCode(code);
        //return new ResponseEntity<> (fondsHateoas, HttpStatus.CREATED);
        return new ResponseEntity<> (HttpStatus.NOT_IMPLEMENTED);
    }
}
