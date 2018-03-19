package nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.FlowStatus;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.service.interfaces.metadata.IFlowStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.FLOW_STATUS;
import static nikita.common.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 15/02/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH +
                SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON,
                NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class FlowStatusController {

    private IFlowStatusService flowStatusService;

    public FlowStatusController(IFlowStatusService flowStatusService) {
        this.flowStatusService = flowStatusService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new flowStatus
    // POST [contextPath][api]/metadata/flytstatus/ny-flytstatus
    @ApiOperation(
            value = "Persists a new FlowStatus object",
            notes = "Returns the newly created FlowStatus object after it is " +
                    "persisted to the database",
            response = FlowStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FlowStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FlowStatus.class),
            @ApiResponse(
                    code = 201,
                    message = "FlowStatus " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FlowStatus.class),
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
            value = FLOW_STATUS + SLASH + NEW_FLOW_STATUS
    )
    public ResponseEntity<MetadataHateoas>
    createFlowStatus(
            HttpServletRequest request,
            @RequestBody FlowStatus
                    flowStatus)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                flowStatusService.
                        createNewFlowStatus(
                                flowStatus);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all FlowStatus
    // GET [contextPath][api]/metadata/flytstatus/
    @ApiOperation(
            value = "Retrieves all FlowStatus ",
            response = FlowStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FlowStatus codes found",
                    response = FlowStatus.class),
            @ApiResponse(
                    code = 404,
                    message = "No FlowStatus found"),
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
            value = FLOW_STATUS
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(flowStatusService.findAll());
    }

    // Retrieves a given FlowStatus identified by a
    // systemId
    // GET [contextPath][api]/metadata/flytstatus/
    // {systemId}/
    @ApiOperation(
            value = "Gets FlowStatus identified by its systemId",
            notes = "Returns the requested FlowStatus object",
            response = FlowStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FlowStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FlowStatus.class),
            @ApiResponse(
                    code = 201,
                    message = "FlowStatus " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FlowStatus.class),
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
            value = FLOW_STATUS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas =
                flowStatusService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested FlowStatus(like a template) with
    // default values (nothing persisted)
    // GET [contextPath][api]/metadata/ny-flowStatus
    @ApiOperation(
            value = "Creates a suggested FlowStatus",
            response = FlowStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FlowStatus codes found",
                    response = FlowStatus.class),
            @ApiResponse(
                    code = 404,
                    message = "No FlowStatus found"),
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
            value = NEW_FLOW_STATUS
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultFlowStatus(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (flowStatusService
                        .generateDefaultFlowStatus());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a flowStatus
    // PUT [contextPath][api]/metadata/flytstatus/
    @ApiOperation(
            value = "Updates a FlowStatus object",
            notes = "Returns the newly updated FlowStatus object after it is " +
                    "persisted to the database",
            response = FlowStatus.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FlowStatus " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FlowStatus.class),
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
            value = FLOW_STATUS + SLASH + FLOW_STATUS
    )
    public ResponseEntity<MetadataHateoas>
    updateFlowStatus(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody FlowStatus flowStatus,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas =
                flowStatusService
                        .handleUpdate
                                (systemID, CommonUtils.Validation.
                                        parseETAG(request.getHeader
                                                (ETAG)), flowStatus);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
