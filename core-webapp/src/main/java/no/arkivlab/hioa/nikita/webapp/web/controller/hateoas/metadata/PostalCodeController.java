package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.PostalCode;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IPostalCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 14/03/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class PostalCodeController {

    private IPostalCodeService PostalCodeService;

    public PostalCodeController(IPostalCodeService PostalCodeService) {
        this.PostalCodeService = PostalCodeService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new land
    // POST [contextPath][api]/metadata/land/ny-land
    @ApiOperation(
            value = "Persists a new PostalCode object",
            notes = "Returns the newly created PostalCode object after it " +
                    "is persisted to the database",
            response = PostalCode.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "PostalCode " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = PostalCode.class),
            @ApiResponse(
                    code = 201,
                    message = "PostalCode " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = PostalCode.class),
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
            value = PostalCode + SLASH + NEW_PostalCode
    )
    public ResponseEntity<MetadataHateoas> createPostalCode(
            HttpServletRequest request,
            @RequestBody PostalCode PostalCode)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                PostalCodeService.createNewPostalCode(PostalCode);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all PostalCode
    // GET [contextPath][api]/metadata/land/
    @ApiOperation(
            value = "Retrieves all PostalCode ",
            response = PostalCode.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "PostalCode codes found",
                    response = PostalCode.class),
            @ApiResponse(
                    code = 404,
                    message = "No PostalCode found"),
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
            value = PostalCode
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(PostalCodeService.findAll());
    }

    // Retrieves a given PostalCode identified by a systemId
    // GET [contextPath][api]/metadata/land/{systemId}/
    @ApiOperation(
            value = "Gets PostalCode identified by its systemId",
            notes = "Returns the requested PostalCode object",
            response = PostalCode.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "PostalCode " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = PostalCode.class),
            @ApiResponse(
                    code = 201,
                    message = "PostalCode " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = PostalCode.class),
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
            value = PostalCode + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = PostalCodeService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested PostalCode(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-land
    @ApiOperation(
            value = "Creates a suggested PostalCode",
            response = PostalCode.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "PostalCode codes found",
                    response = PostalCode.class),
            @ApiResponse(
                    code = 404,
                    message = "No PostalCode found"),
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
            value = NEW_PostalCode
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultPostalCode(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (PostalCodeService.generateDefaultPostalCode());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a land
    // PUT [contextPath][api]/metatdata/land/
    @ApiOperation(
            value = "Updates a PostalCode object",
            notes = "Returns the newly updated PostalCode object after it " +
                    "is persisted to the database",
            response = PostalCode.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "PostalCode " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = PostalCode.class),
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
            value = PostalCode + SLASH + PostalCode
    )
    public ResponseEntity<MetadataHateoas> updatePostalCode(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody PostalCode PostalCode,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = PostalCodeService.handleUpdate
                (systemID,
                        CommonUtils.Validation.parseETAG(
                                request.getHeader(ETAG)),
                        PostalCode);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
