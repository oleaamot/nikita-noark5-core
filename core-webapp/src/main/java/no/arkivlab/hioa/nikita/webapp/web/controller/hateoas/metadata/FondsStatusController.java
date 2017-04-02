package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.metadata.FondsStatus;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IFondsStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM;
import static nikita.config.N5ResourceMappings.FONDS_STATUS;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + FONDS_STATUS,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class FondsStatusController {

    private IFondsStatusService fondsStatusService;

    public FondsStatusController(IFondsStatusService fondsStatusService) {
        this.fondsStatusService = fondsStatusService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new fondsStatus
    // POST [contextPath][api]/metadata/arkivstatus/ny-arkivstatus
    @ApiOperation(value = "Persists a new FondsStatus object", notes = "Returns the newly" +
            " created FondsStatus object after it is persisted to the database", response = FondsStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsStatus.class),
            @ApiResponse(code = 201, message = "FondsStatus " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + NEW_DOCUMENT_MEDIUM)
    public ResponseEntity<FondsStatus> createFondsStatus(@RequestBody FondsStatus fondsStatus)
            throws NikitaException {
        FondsStatus newFondsStatus = fondsStatusService.createNewFondsStatus(fondsStatus);
        return new ResponseEntity<>(newFondsStatus, HttpStatus.CREATED);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all fondsStatus
    // GET [contextPath][api]/metadata/arkivstatus/
    @ApiOperation(value = "Retrieves all FondsStatus", response = FondsStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsStatus codes found",
                    response = FondsStatus.class),
            @ApiResponse(code = 404, message = "No FondsStatus found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = SLASH)
    public ResponseEntity<Iterable<FondsStatus>> findAll() {
        Iterable<FondsStatus> fondsStatusList = fondsStatusService.findAll();
        return new ResponseEntity<>(fondsStatusList, HttpStatus.OK);
    }

    // Create a suggested fondsStatus(like a template) with default values (nothing persisted)
    // GET [contextPath][api]/metadata/arkivstatus/ny-arkivstatus
    @ApiOperation(value = "Retrieves all FondsStatus", response = FondsStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsStatus codes found",
                    response = FondsStatus.class),
            @ApiResponse(code = 404, message = "No FondsStatus found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = SLASH + NEW_FONDS_STATUS)
    public ResponseEntity<FondsStatus> getFondsStatusTemplate() {
        FondsStatus fondsStatus = new FondsStatus();
        fondsStatus.setCode(TEMPLATE_FONDS_STATUS_CODE);
        fondsStatus.setDescription(TEMPLATE_FONDS_STATUS_DESCRIPTION);
        return new ResponseEntity<>(fondsStatus, HttpStatus.OK);
    }

    // Retrieves a given fondsStatus identified by a systemId
    // GET [contextPath][api]/metadata/arkivstatus/{systemId}/
    @ApiOperation(value = "Gets fondsStatus identified by its systemId", notes = "Returns the requested " +
            " fondsStatus object", response = FondsStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsStatus.class),
            @ApiResponse(code = 201, message = "FondsStatus " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + "{systemID}" + SLASH, method = RequestMethod.GET)
    public ResponseEntity<FondsStatus> findBySystemId(@PathVariable("systemID") final String systemId) {
        FondsStatus fondsStatusList = fondsStatusService.findBySystemId(systemId);
        return new ResponseEntity<>(fondsStatusList, HttpStatus.OK);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a fondsStatus
    // PUT [contextPath][api]/metatdata/arkivstatus/
    @ApiOperation(value = "Updates a FondsStatus object", notes = "Returns the newly" +
            " updated FondsStatus object after it is persisted to the database", response = FondsStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = SLASH + DOCUMENT_MEDIUM)
    public ResponseEntity<FondsStatus> updateFondsStatus(@RequestBody FondsStatus fondsStatus)
            throws NikitaException {
        FondsStatus newFondsStatus = fondsStatusService.update(fondsStatus);
        return new ResponseEntity<>(newFondsStatus, HttpStatus.OK);
    }
}
