package nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.RegistryEntryStatus;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.service.interfaces.metadata.IRegistryEntryStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.REGISTRY_ENTRY_STATUS;
import static nikita.common.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 12/02/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class RegistryEntryStatusController {

    private IRegistryEntryStatusService registryEntryStatusService;

    public RegistryEntryStatusController(
            IRegistryEntryStatusService registryEntryStatusService) {
        this.registryEntryStatusService = registryEntryStatusService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new journalpoststatus
    // POST [contextPath][api]/metadata/journalpoststatus/ny-journalpoststatus
    @ApiOperation(
            value = "Persists a new RegistryEntryStatus object",
            notes = "Returns the newly created RegistryEntryStatus object  " +
                    "after it is persisted to the database",
            response = RegistryEntryStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "RegistryEntryStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RegistryEntryStatus.class),
            @ApiResponse(
                    code = 201,
                    message = "RegistryEntryStatus " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RegistryEntryStatus.class),
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
            value = REGISTRY_ENTRY_STATUS + SLASH + NEW_REGISTRY_ENTRY_STATUS
    )
    public ResponseEntity<MetadataHateoas> createRegistryEntryStatus(
            HttpServletRequest request,
            @RequestBody RegistryEntryStatus registryEntryStatus)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                registryEntryStatusService.createNewRegistryEntryStatus(
                        registryEntryStatus);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all RegistryEntryStatus
    // GET [contextPath][api]/metadata/journalpoststatus/
    @ApiOperation(
            value = "Retrieves all RegistryEntryStatus ",
            response = RegistryEntryStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "RegistryEntryStatus codes found",
                    response = RegistryEntryStatus.class),
            @ApiResponse(
                    code = 404,
                    message = "No RegistryEntryStatus found"),
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
            value = REGISTRY_ENTRY_STATUS
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(registryEntryStatusService.findAll());
    }

    // Retrieves a given RegistryEntryStatus identified by a systemId
    // GET [contextPath][api]/metadata/journalpoststatus/{systemId}/
    @ApiOperation(
            value = "Gets RegistryEntryStatus identified by its systemId",
            notes = "Returns the requested RegistryEntryStatus object",
            response = RegistryEntryStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "RegistryEntryStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RegistryEntryStatus.class),
            @ApiResponse(
                    code = 201,
                    message = "RegistryEntryStatus " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RegistryEntryStatus.class),
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
            value = REGISTRY_ENTRY_STATUS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = registryEntryStatusService.find
                (systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested RegistryEntryStatus(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-journalpoststatus
    @ApiOperation(
            value = "Creates a suggested RegistryEntryStatus",
            response = RegistryEntryStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "RegistryEntryStatus codes found",
                    response = RegistryEntryStatus.class),
            @ApiResponse(
                    code = 404,
                    message = "No RegistryEntryStatus found"),
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
            value = NEW_REGISTRY_ENTRY_STATUS
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultRegistryEntryStatus(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (registryEntryStatusService
                        .generateDefaultRegistryEntryStatus());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a journalpoststatus
    // PUT [contextPath][api]/metatdata/journalpoststatus/
    @ApiOperation(
            value = "Updates a RegistryEntryStatus object",
            notes = "Returns the newly updated RegistryEntryStatus object after it " +
                    "is persisted to the database",
            response = RegistryEntryStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "RegistryEntryStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RegistryEntryStatus.class),
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
            value = REGISTRY_ENTRY_STATUS + SLASH + REGISTRY_ENTRY_STATUS
    )
    public ResponseEntity<MetadataHateoas> updateRegistryEntryStatus(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody RegistryEntryStatus RegistryEntryStatus,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = registryEntryStatusService
                .handleUpdate
                        (systemID,
                                CommonUtils.Validation.parseETAG(request.getHeader(ETAG)),
                                RegistryEntryStatus);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
