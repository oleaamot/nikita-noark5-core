package nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.CommentType;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.service.interfaces.metadata.ICommentTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.COMMENT_TYPE;
import static nikita.common.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 04/03/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class CommentTypeController {

    private ICommentTypeService commentTypeService;

    public CommentTypeController(ICommentTypeService commentTypeService) {
        this.commentTypeService = commentTypeService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new merknadstype
    // POST [contextPath][api]/metadata/merknadstype/ny-merknadstype
    @ApiOperation(
            value = "Persists a new CommentType object",
            notes = "Returns the newly created CommentType object after it " +
                    "is persisted to the database",
            response = CommentType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CommentType " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CommentType.class),
            @ApiResponse(
                    code = 201,
                    message = "CommentType " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CommentType.class),
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
            value = COMMENT_TYPE + SLASH + NEW_COMMENT_TYPE
    )
    public ResponseEntity<MetadataHateoas> createCommentType(
            HttpServletRequest request,
            @RequestBody CommentType commentType)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                commentTypeService.createNewCommentType(commentType);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all commentType
    // GET [contextPath][api]/metadata/merknadstype/
    @ApiOperation(
            value = "Retrieves all CommentType ",
            response = CommentType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CommentType codes found",
                    response = CommentType.class),
            @ApiResponse(
                    code = 404,
                    message = "No CommentType found"),
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
            value = COMMENT_TYPE
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(commentTypeService.findAll());
    }

    // Retrieves a given commentType identified by a systemId
    // GET [contextPath][api]/metadata/merknadstype/{systemId}/
    @ApiOperation(
            value = "Gets commentType identified by its systemId",
            notes = "Returns the requested commentType object",
            response = CommentType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CommentType " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CommentType.class),
            @ApiResponse(
                    code = 201,
                    message = "CommentType " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CommentType.class),
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
            value = COMMENT_TYPE + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = commentTypeService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested commentType(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-merknadstype
    @ApiOperation(
            value = "Creates a suggested CommentType",
            response = CommentType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CommentType codes found",
                    response = CommentType.class),
            @ApiResponse(
                    code = 404,
                    message = "No CommentType found"),
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
            value = NEW_COMMENT_TYPE
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultCommentType(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (commentTypeService.generateDefaultCommentType());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a merknadstype
    // PUT [contextPath][api]/metatdata/merknadstype/
    @ApiOperation(
            value = "Updates a CommentType object",
            notes = "Returns the newly updated CommentType object after it " +
                    "is persisted to the database",
            response = CommentType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "CommentType " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CommentType.class),
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
            value = COMMENT_TYPE + SLASH + COMMENT_TYPE
    )
    public ResponseEntity<MetadataHateoas> updateCommentType(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody CommentType commentType,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = commentTypeService.handleUpdate
                (systemID,
                        CommonUtils.Validation.parseETAG(
                                request.getHeader(ETAG)),
                        commentType);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
