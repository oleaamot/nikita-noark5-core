package nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.ElectronicSignatureSecurityLevel;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.service.interfaces.metadata.IElectronicSignatureSecurityLevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.ELECTRONIC_SIGNATURE_SECURITY_LEVEL;
import static nikita.common.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 13/02/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH +
                SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON,
                NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class ElectronicSignatureSecurityLevelController {

    private IElectronicSignatureSecurityLevelService
            electronicSignatureSecurityLevelService;

    public ElectronicSignatureSecurityLevelController(
            IElectronicSignatureSecurityLevelService
                    electronicSignatureSecurityLevelService) {
        this.electronicSignatureSecurityLevelService =
                electronicSignatureSecurityLevelService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new elektronisksignatursikkerhetsnivaa
    // POST [contextPath][api]/metadata/elektronisksignatursikkerhetsnivaa/
    // ny-elektronisksignatursikkerhetsnivaa
    @ApiOperation(
            value = "Persists a new ElectronicSignatureSecurityLevel object",
            notes = "Returns the newly created " +
                    "ElectronicSignatureSecurityLevel  object after it is " +
                    "persisted to the database",
            response = ElectronicSignatureSecurityLevel.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ElectronicSignatureSecurityLevel " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ElectronicSignatureSecurityLevel.class),
            @ApiResponse(
                    code = 201,
                    message = "ElectronicSignatureSecurityLevel " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = ElectronicSignatureSecurityLevel.class),
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
            value = ELECTRONIC_SIGNATURE_SECURITY_LEVEL + SLASH +
                    NEW_ELECTRONIC_SIGNATURE_SECURITY_LEVEL
    )
    public ResponseEntity<MetadataHateoas>
    createElectronicSignatureSecurityLevel(
            HttpServletRequest request,
            @RequestBody ElectronicSignatureSecurityLevel
                    electronicSignatureSecurityLevel)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                electronicSignatureSecurityLevelService.
                        createNewElectronicSignatureSecurityLevel(
                                electronicSignatureSecurityLevel);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all ElectronicSignatureSecurityLevel
    // GET [contextPath][api]/metadata/elektronisksignatursikkerhetsnivaa/
    @ApiOperation(
            value = "Retrieves all ElectronicSignatureSecurityLevel ",
            response = ElectronicSignatureSecurityLevel.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ElectronicSignatureSecurityLevel codes found",
                    response = ElectronicSignatureSecurityLevel.class),
            @ApiResponse(
                    code = 404,
                    message = "No ElectronicSignatureSecurityLevel found"),
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
            value = ELECTRONIC_SIGNATURE_SECURITY_LEVEL
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(electronicSignatureSecurityLevelService.findAll());
    }

    // Retrieves a given ElectronicSignatureSecurityLevel identified by a
    // systemId
    // GET [contextPath][api]/metadata/elektronisksignatursikkerhetsnivaa/
    // {systemId}/
    @ApiOperation(
            value = "Gets ElectronicSignatureSecurityLevel identified by its " +
                    "systemId",
            notes = "Returns the requested ElectronicSignatureSecurityLevel " +
                    "object",
            response = ElectronicSignatureSecurityLevel.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ElectronicSignatureSecurityLevel " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ElectronicSignatureSecurityLevel.class),
            @ApiResponse(
                    code = 201,
                    message = "ElectronicSignatureSecurityLevel " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = ElectronicSignatureSecurityLevel.class),
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
            value = ELECTRONIC_SIGNATURE_SECURITY_LEVEL + SLASH +
                    LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas =
                electronicSignatureSecurityLevelService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested ElectronicSignatureSecurityLevel(like a template) with
    // default values (nothing persisted)
    // GET [contextPath][api]/metadata/ny-elektronisksignatursikkerhetsnivaa
    @ApiOperation(
            value = "Creates a suggested ElectronicSignatureSecurityLevel",
            response = ElectronicSignatureSecurityLevel.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ElectronicSignatureSecurityLevel codes found",
                    response = ElectronicSignatureSecurityLevel.class),
            @ApiResponse(
                    code = 404,
                    message = "No ElectronicSignatureSecurityLevel found"),
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
            value = NEW_ELECTRONIC_SIGNATURE_SECURITY_LEVEL
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultElectronicSignatureSecurityLevel(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (electronicSignatureSecurityLevelService
                        .generateDefaultElectronicSignatureSecurityLevel());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a elektronisksignatursikkerhetsnivaa
    // PUT [contextPath][api]/metatdata/elektronisksignatursikkerhetsnivaa/
    @ApiOperation(
            value = "Updates a ElectronicSignatureSecurityLevel object",
            notes = "Returns the newly updated " +
                    "ElectronicSignatureSecurityLevel object after it " +
                    "is persisted to the database",
            response = ElectronicSignatureSecurityLevel.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "ElectronicSignatureSecurityLevel " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ElectronicSignatureSecurityLevel.class),
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
            value = ELECTRONIC_SIGNATURE_SECURITY_LEVEL + SLASH +
                    ELECTRONIC_SIGNATURE_SECURITY_LEVEL
    )
    public ResponseEntity<MetadataHateoas>
    updateElectronicSignatureSecurityLevel(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody ElectronicSignatureSecurityLevel
                    electronicSignatureSecurityLevel,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas =
                electronicSignatureSecurityLevelService
                        .handleUpdate
                                (systemID,
                                        CommonUtils.Validation.
                                                parseETAG(request.getHeader(ETAG)),
                                        electronicSignatureSecurityLevel);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
