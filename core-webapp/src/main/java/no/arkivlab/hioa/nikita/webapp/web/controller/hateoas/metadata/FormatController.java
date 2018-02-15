package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.Format;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IFormatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FORMAT;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;
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
public class FormatController {

    private IFormatService
            formatService;

    public FormatController(
            IFormatService
                    formatService) {
        this.formatService =
                formatService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new format
    // POST [contextPath][api]/metadata/format/ny-format
    @ApiOperation(
            value = "Persists a new Format object",
            notes = "Returns the newly created Format object after it is " +
                    "persisted to the database",
            response = Format.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Format " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = Format.class),
            @ApiResponse(
                    code = 201,
                    message = "Format " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = Format.class),
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
            value = FORMAT + SLASH +
                    NEW_FORMAT
    )
    public ResponseEntity<MetadataHateoas>
    createFormat(
            HttpServletRequest request,
            @RequestBody Format
                    format)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                formatService.
                        createNewFormat(
                                format);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all Format
    // GET [contextPath][api]/metadata/format/
    @ApiOperation(
            value = "Retrieves all Format ",
            response = Format.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Format codes found",
                    response = Format.class),
            @ApiResponse(
                    code = 404,
                    message = "No Format found"),
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
            value = FORMAT
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(formatService.findAll());
    }

    // Retrieves a given Format identified by a
    // systemId
    // GET [contextPath][api]/metadata/format/
    // {systemId}/
    @ApiOperation(
            value = "Gets Format identified by its systemId",
            notes = "Returns the requested Format object",
            response = Format.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Format " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = Format.class),
            @ApiResponse(
                    code = 201,
                    message = "Format " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = Format.class),
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
            value = FORMAT + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas =
                formatService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested Format(like a template) with
    // default values (nothing persisted)
    // GET [contextPath][api]/metadata/ny-format
    @ApiOperation(
            value = "Creates a suggested Format",
            response = Format.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Format codes found",
                    response = Format.class),
            @ApiResponse(
                    code = 404,
                    message = "No Format found"),
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
            value = NEW_FORMAT
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultFormat(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (formatService
                        .generateDefaultFormat());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a format
    // PUT [contextPath][api]/metadata/format/
    @ApiOperation(
            value = "Updates a Format object",
            notes = "Returns the newly updated Format object after it is " +
                    "persisted to the database",
            response = Format.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Format " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = Format.class),
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
            value = FORMAT + SLASH +
                    FORMAT
    )
    public ResponseEntity<MetadataHateoas>
    updateFormat(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody Format
                    format,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas =
                formatService
                        .handleUpdate
                                (systemID, CommonUtils.Validation.
                                                parseETAG(request.getHeader
                                                        (ETAG)),
                                        format);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
