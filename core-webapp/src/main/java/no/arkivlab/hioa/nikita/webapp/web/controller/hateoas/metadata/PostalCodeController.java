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
import static nikita.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 16/03/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class PostalCodeController {

    private IPostalCodeService postalCodeService;

    public PostalCodeController(IPostalCodeService postalCodeService) {
        this.postalCodeService = postalCodeService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new postnummer
    // POST [contextPath][api]/metadata/postnummer/ny-postnummer
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
            value = POST_CODE + SLASH + NEW_POST_CODE
    )
    public ResponseEntity<MetadataHateoas> createPostalCode(
            HttpServletRequest request,
            @RequestBody PostalCode postalCode)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                postalCodeService.createNewPostalCode(postalCode);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all postalCode
    // GET [contextPath][api]/metadata/postnummer/
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
            value = POST_CODE
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(postalCodeService.findAll());
    }

    // Retrieves a given postalCode identified by a systemId
    // GET [contextPath][api]/metadata/postnummer/{systemId}/
    @ApiOperation(
            value = "Gets postalCode identified by its systemId",
            notes = "Returns the requested postalCode object",
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
            value = POST_CODE + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = postalCodeService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested postalCode(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-postnummer
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
            value = NEW_POST_CODE
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultPostalCode(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (postalCodeService.generateDefaultPostalCode());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a postnummer
    // PUT [contextPath][api]/metatdata/postnummer/
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
            value = POST_CODE + SLASH + POST_CODE
    )
    public ResponseEntity<MetadataHateoas> updatePostalCode(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody PostalCode postalCode,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = postalCodeService.handleUpdate
                (systemID,
                        CommonUtils.Validation.parseETAG(
                                request.getHeader(ETAG)),
                        postalCode);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
