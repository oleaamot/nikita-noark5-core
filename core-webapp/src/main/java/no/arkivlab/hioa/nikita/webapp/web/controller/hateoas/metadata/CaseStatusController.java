package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.CaseStatus;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ICaseStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CASE_STATUS;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 13/03/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class CaseStatusController {

    private ICaseStatusService caseStatusService;

    public CaseStatusController(ICaseStatusService caseStatusService) {
        this.caseStatusService = caseStatusService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new saksstatus
    // POST [contextPath][api]/metadata/saksstatus/ny-saksstatus
    @ApiOperation(
            value = "Persists a new CaseStatus object",
            notes = "Returns the newly created CaseStatus object after it " +
                    "is persisted to the database",
            response = CaseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CaseStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CaseStatus.class),
            @ApiResponse(
                    code = 201,
                    message = "CaseStatus " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CaseStatus.class),
            @ApiResponse(code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(
            method = RequestMethod.POST,
            value = CASE_STATUS + SLASH + NEW_CASE_STATUS
    )
    public ResponseEntity<MetadataHateoas> createCaseStatus(
            HttpServletRequest request,
            @RequestBody CaseStatus caseStatus)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                caseStatusService.createNewCaseStatus(caseStatus);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all caseStatus
    // GET [contextPath][api]/metadata/saksstatus/
    @ApiOperation(
            value = "Retrieves all CaseStatus ",
            response = CaseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CaseStatus codes found",
                    response = CaseStatus.class),
            @ApiResponse(
                    code = 404,
                    message = "No CaseStatus found"),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            value = CASE_STATUS
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(caseStatusService.findAll());
    }

    // Retrieves a given caseStatus identified by a systemId
    // GET [contextPath][api]/metadata/saksstatus/{systemId}/
    @ApiOperation(
            value = "Gets caseStatus identified by its systemId",
            notes = "Returns the requested caseStatus object",
            response = CaseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CaseStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CaseStatus.class),
            @ApiResponse(
                    code = 201,
                    message = "CaseStatus " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CaseStatus.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(
            value = CASE_STATUS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = caseStatusService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested caseStatus(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-saksstatus
    @ApiOperation(
            value = "Creates a suggested CaseStatus",
            response = CaseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CaseStatus codes found",
                    response = CaseStatus.class),
            @ApiResponse(
                    code = 404,
                    message = "No CaseStatus found"),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            value = NEW_CASE_STATUS
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultCaseStatus(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (caseStatusService.generateDefaultCaseStatus());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a saksstatus
    // PUT [contextPath][api]/metatdata/saksstatus/
    @ApiOperation(
            value = "Updates a CaseStatus object",
            notes = "Returns the newly updated CaseStatus object after it " +
                    "is persisted to the database",
            response = CaseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CaseStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CaseStatus.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(
            method = RequestMethod.PUT,
            value = CASE_STATUS + SLASH + CASE_STATUS
    )
    public ResponseEntity<MetadataHateoas> updateCaseStatus(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody CaseStatus caseStatus,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = caseStatusService.handleUpdate
                (systemID,
                        CommonUtils.Validation.parseETAG(
                                request.getHeader(ETAG)),
                        caseStatus);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
