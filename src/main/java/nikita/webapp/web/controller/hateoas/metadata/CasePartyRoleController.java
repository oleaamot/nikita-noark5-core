package nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.CasePartyRole;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.service.interfaces.metadata.ICasePartyRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.CASE_PARTY_ROLE;
import static nikita.common.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 31/1/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class CasePartyRoleController {

    private ICasePartyRoleService casePartyRoleService;

    public CasePartyRoleController(ICasePartyRoleService casePartyRoleService) {
        this.casePartyRoleService = casePartyRoleService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new sakspartrolle
    // POST [contextPath][api]/metadata/sakspartrolle/ny-sakspartrolle
    @ApiOperation(
            value = "Persists a new CasePartyRole object",
            notes = "Returns the newly created CasePartyRole object after it " +
                    "is persisted to the database",
            response = CasePartyRole.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CasePartyRole " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CasePartyRole.class),
            @ApiResponse(
                    code = 201,
                    message = "CasePartyRole " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CasePartyRole.class),
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

    @RequestMapping(
            method = RequestMethod.POST,
            value = CASE_PARTY_ROLE + SLASH + NEW_CASE_PARTY_ROLE
    )
    public ResponseEntity<MetadataHateoas> createCasePartyRole(
            HttpServletRequest request,
            @RequestBody CasePartyRole casePartyRole)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                casePartyRoleService.createNewCasePartyRole(casePartyRole);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all casePartyRole
    // GET [contextPath][api]/metadata/sakspartrolle/
    @ApiOperation(
            value = "Retrieves all CasePartyRole ",
            response = CasePartyRole.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CasePartyRole codes found",
                    response = CasePartyRole.class),
            @ApiResponse(
                    code = 404,
                    message = "No CasePartyRole found"),
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

    @RequestMapping(
            method = RequestMethod.GET,
            value = CASE_PARTY_ROLE
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(casePartyRoleService.findAll());
    }

    // Retrieves a given casePartyRole identified by a systemId
    // GET [contextPath][api]/metadata/sakspartrolle/{systemId}/
    @ApiOperation(
            value = "Gets casePartyRole identified by its systemId",
            notes = "Returns the requested casePartyRole object",
            response = CasePartyRole.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CasePartyRole " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CasePartyRole.class),
            @ApiResponse(
                    code = 201,
                    message = "CasePartyRole " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CasePartyRole.class),
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

    @RequestMapping(
            value = CASE_PARTY_ROLE + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = casePartyRoleService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested casePartyRole(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-sakspartrolle
    @ApiOperation(
            value = "Creates a suggested CasePartyRole",
            response = CasePartyRole.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CasePartyRole codes found",
                    response = CasePartyRole.class),
            @ApiResponse(
                    code = 404,
                    message = "No CasePartyRole found"),
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

    @RequestMapping(
            method = RequestMethod.GET,
            value = NEW_CASE_PARTY_ROLE
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultCasePartyRole(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (casePartyRoleService.generateDefaultCasePartyRole());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a sakspartrolle
    // PUT [contextPath][api]/metatdata/sakspartrolle/
    @ApiOperation(
            value = "Updates a CasePartyRole object",
            notes = "Returns the newly updated CasePartyRole object after it " +
                    "is persisted to the database",
            response = CasePartyRole.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CasePartyRole " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CasePartyRole.class),
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

    @RequestMapping(
            method = RequestMethod.PUT,
            value = CASE_PARTY_ROLE + SLASH + CASE_PARTY_ROLE
    )
    public ResponseEntity<MetadataHateoas> updateCasePartyRole(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody CasePartyRole casePartyRole,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = casePartyRoleService.handleUpdate
                (systemID,
                        CommonUtils.Validation.parseETAG(
                                request.getHeader(ETAG)),
                        casePartyRole);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
