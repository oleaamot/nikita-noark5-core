package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.FileType;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IFileTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FILE_TYPE;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 03/03/18.
 */

@RestController
@RequestMapping(
        value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class FileTypeController {

    private IFileTypeService fileTypeService;

    public FileTypeController(IFileTypeService fileTypeService) {
        this.fileTypeService = fileTypeService;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new mappetype
    // POST [contextPath][api]/metadata/mappetype/ny-mappetype
    @ApiOperation(
            value = "Persists a new FileType object",
            notes = "Returns the newly created FileType object after it " +
                    "is persisted to the database",
            response = FileType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FileType " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileType.class),
            @ApiResponse(
                    code = 201,
                    message = "FileType " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileType.class),
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
            value = FILE_TYPE + SLASH + NEW_FILE_TYPE
    )
    public ResponseEntity<MetadataHateoas> createFileType(
            HttpServletRequest request,
            @RequestBody FileType fileType)
            throws NikitaException {

        MetadataHateoas metadataHateoas =
                fileTypeService.createNewFileType(fileType);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all fileType
    // GET [contextPath][api]/metadata/mappetype/
    @ApiOperation(
            value = "Retrieves all FileType ",
            response = FileType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FileType codes found",
                    response = FileType.class),
            @ApiResponse(
                    code = 404,
                    message = "No FileType found"),
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
            value = FILE_TYPE
    )
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fileTypeService.findAll());
    }

    // Retrieves a given fileType identified by a systemId
    // GET [contextPath][api]/metadata/mappetype/{systemId}/
    @ApiOperation(
            value = "Gets fileType identified by its systemId",
            notes = "Returns the requested fileType object",
            response = FileType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FileType " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileType.class),
            @ApiResponse(
                    code = 201,
                    message = "FileType " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileType.class),
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
            value = FILE_TYPE + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET
    )
    public ResponseEntity<MetadataHateoas> findBySystemId(
            @PathVariable("systemID") final String systemId,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = fileTypeService.find(systemId);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(metadataHateoas.getEntityVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested fileType(like a template) with default values
    // (nothing persisted)
    // GET [contextPath][api]/metadata/ny-mappetype
    @ApiOperation(
            value = "Creates a suggested FileType",
            response = FileType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FileType codes found",
                    response = FileType.class),
            @ApiResponse(
                    code = 404,
                    message = "No FileType found"),
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
            value = NEW_FILE_TYPE
    )
    public ResponseEntity<MetadataHateoas>
    generateDefaultFileType(HttpServletRequest request) {

        MetadataHateoas metadataHateoas = new MetadataHateoas
                (fileTypeService.generateDefaultFileType());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a mappetype
    // PUT [contextPath][api]/metatdata/mappetype/
    @ApiOperation(
            value = "Updates a FileType object",
            notes = "Returns the newly updated FileType object after it " +
                    "is persisted to the database",
            response = FileType.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FileType " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileType.class),
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
            value = FILE_TYPE + SLASH + FILE_TYPE
    )
    public ResponseEntity<MetadataHateoas> updateFileType(
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @RequestBody FileType fileType,
            HttpServletRequest request) {

        MetadataHateoas metadataHateoas = fileTypeService.handleUpdate
                (systemID,
                        CommonUtils.Validation.parseETAG(
                                request.getHeader(ETAG)),
                        fileType);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
