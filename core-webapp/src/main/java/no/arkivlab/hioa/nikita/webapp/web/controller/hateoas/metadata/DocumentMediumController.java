package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.metadata.DocumentMedium;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IDocumentMediumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + DOCUMENT_MEDIUM,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class DocumentMediumController {

    private IDocumentMediumService documentMediumService;

    @Autowired
    public DocumentMediumController(IDocumentMediumService documentMediumService) {
        this.documentMediumService = documentMediumService;
    }

    // API - All POST Requests (CRUD - CREATE)

    // Creates a new documentmedium
    // POST [contextPath][api]/metatdata/dockumentmedium/ny-dokumentmedium
    @ApiOperation(value = "Persists a new DocumentMedium object", notes = "Returns the newly" +
            " created DocumentMedium object after it is persisted to the database", response = DocumentMedium.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentMedium " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentMedium.class),
            @ApiResponse(code = 201, message = "DocumentMedium " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentMedium.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + NEW_DOCUMENT_MEDIUM)
    public ResponseEntity<DocumentMedium> createDocumentMedium(@RequestBody DocumentMedium documentMedium)
            throws NikitaException {
        DocumentMedium newDocumentMedium = documentMediumService.createNewDocumentMedium(documentMedium);
        return new ResponseEntity<>(newDocumentMedium, HttpStatus.CREATED);
    }

    // API - All GET Requests (CRUD - READ)

    // Retrieves all documentMedium
    // GET [contextPath][api]/metatdata/dockumentmedium/
    @ApiOperation(value = "Retrieves all DocumentMedium ", response = DocumentMedium.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentMedium codes found",
                    response = DocumentMedium.class),
            @ApiResponse(code = 404, message = "No DocumentMedium found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = SLASH)
    public ResponseEntity<Iterable<DocumentMedium>> findAll() {
        Iterable<DocumentMedium> documentMediumList = documentMediumService.findAll();
        return new ResponseEntity<>(documentMediumList, HttpStatus.OK);
    }

    // Retrieves a given documentMedium identified by a systemId
    // GET [contextPath][api]/metatdata/dockumentmedium/{systemId}/
    @ApiOperation(value = "Gets documentMedium identified by its systemId", notes = "Returns the requested " +
            " documentMedium object", response = DocumentMedium.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentMedium " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentMedium.class),
            @ApiResponse(code = 201, message = "DocumentMedium " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentMedium.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + "{systemID}" + SLASH, method = RequestMethod.GET)
    public ResponseEntity<DocumentMedium> findBySystemId(@PathVariable("systemID") final String systemId) {
        DocumentMedium documentMediumList = documentMediumService.findBySystemId(systemId);
        return new ResponseEntity<>(documentMediumList, HttpStatus.OK);
    }

    /*
    Leaving the methods here commented out, but we need to support search ogn code and des
    cription
    Can be done when OData is in place.


    // Retrieves all documentMedium that use a given code
    // GET [contextPath][api]/metatdata/dockumentmedium/{kode}/
    @ApiOperation(value = "Gets all documentMedium that use a given code", notes = "Returns the requested " +
            " documentMedium objects", response = DocumentMedium.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentMedium " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentMedium.class),
            @ApiResponse(code = 201, message = "DocumentMedium " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentMedium.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(value = CODE + SLASH + "{kode}", method = RequestMethod.GET)
    public ResponseEntity<List<DocumentMedium>> findOne(@PathVariable("kode") final String code) {
        List<DocumentMedium> documentMediumList = documentMediumService.findByCode(code);
        return new ResponseEntity<> (documentMediumList, HttpStatus.OK);
    }

    // Retrieves all documentMedium that use a given description
    // GET [contextPath][api]/metatdata/dockumentmedium/{description}/
    // Note, this most likely will be replaced by a keyword search
    @ApiOperation(value = "Retrieves all DocumentMedium that have a given description", response = DocumentMedium.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentMedium codes found",
                    response = DocumentMedium.class),
            @ApiResponse(code = 404, message = "No DocumentMedium found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = SLASH)
    public ResponseEntity<Iterable <DocumentMedium>> findAllByDescription(
            @PathVariable("beskrivelse") final String description) {
        Iterable <DocumentMedium> documentMediumList = documentMediumService.findByDescription(description);
        return new ResponseEntity<> (documentMediumList, HttpStatus.OK);
    }
    */
}
