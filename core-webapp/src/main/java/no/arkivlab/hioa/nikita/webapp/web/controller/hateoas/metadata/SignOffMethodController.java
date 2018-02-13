package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.SignOffMethod;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ISignOffMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.SIGN_OFF_METHOD;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 13/02/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class SignOffMethodController {

    private ISignOffMethodService signOffMethodService;

    public SignOffMethodController(
            ISignOffMethodService signOffMethodService) {
        this.signOffMethodService = signOffMethodService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new avskrivningsmaate
    // POST [contextPath][api]/metadata/avskrivningsmaate/ny-avskrivningsmaate
    @ApiOperation(
            value = "Persists a new SignOffMethod object",
            notes = "Returns the newly created SignOffMethod object  " +
                    "after it is persisted to the database",
            response = SignOffMethod.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "SignOffMethod " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SignOffMethod.class),
            @ApiResponse(
                    code = 201,
                    message = "SignOffMethod " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SignOffMethod.class),
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
            value = SIGN_OFF_METHOD + SLASH + NEW_SIGN_OFF_METHOD
    )
    public ResponseEntity<MetadataHateoas> createSignOffMethod(
            HttpServletRequest request,
            @RequestBody SignOffMethod signOffMethod)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                signOffMethodService.createNewSignOffMethod(
                        signOffMethod);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all SignOffMethod
    // GET [contextPath][api]/metadata/avskrivningsmaate/
    @ApiOperation(
            value = "Retrieves all SignOffMethod ",
            response = SignOffMethod.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "SignOffMethod codes found",
                    response = SignOffMethod.class),
            @ApiResponse(
                    code = 404,
                    message = "No SignOffMethod found"),
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
            value = SIGN_OFF_METHOD
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(signOffMethodService.findAll());
    }

    // Retrieves a given SignOffMethod identified by a systemId
    // GET [contextPath][api]/metadata/avskrivningsmaate/{systemId}/
    @ApiOperation(
            value = "Gets SignOffMethod identified by its systemId",
            notes = "Returns the requested SignOffMethod object",
            response = SignOffMethod.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "SignOffMethod " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SignOffMethod.class),
            @ApiResponse(
                    code = 201,
                    message = "SignOffMethod " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SignOffMethod.class),
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
            value = SIGN_OFF_METHOD + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = signOffMethodService.find
                (systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested SignOffMethod(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-avskrivningsmaate
    @ApiOperation(
            value = "Creates a suggested SignOffMethod",
            response = SignOffMethod.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "SignOffMethod codes found",
                    response = SignOffMethod.class),
            @ApiResponse(
                    code = 404,
                    message = "No SignOffMethod found"),
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
            value = NEW_SIGN_OFF_METHOD
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultSignOffMethod(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (signOffMethodService
                        .generateDefaultSignOffMethod());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a avskrivningsmaate
    // PUT [contextPath][api]/metatdata/avskrivningsmaate/
    @ApiOperation(
            value = "Updates a SignOffMethod object",
            notes = "Returns the newly updated SignOffMethod object after it " +
                    "is persisted to the database",
            response = SignOffMethod.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "SignOffMethod " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SignOffMethod.class),
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
            value = SIGN_OFF_METHOD + SLASH + SIGN_OFF_METHOD
    )
    public ResponseEntity<MetadataHateoas> updateSignOffMethod(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody SignOffMethod SignOffMethod,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = signOffMethodService
                .handleUpdate
                        (systemID,
                                CommonUtils.Validation.parseETAG(request.getHeader(ETAG)),
                                SignOffMethod);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
