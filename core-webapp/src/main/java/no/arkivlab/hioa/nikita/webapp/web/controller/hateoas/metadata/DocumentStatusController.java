package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.DocumentStatus;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IDocumentStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_STATUS;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

/**
 * Created by tsodring on 31/1/18.
 */

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class DocumentStatusController {

    private IDocumentStatusService documentStatusService;
    private IMetadataHateoasHandler metadataHateoasHandler;

    public DocumentStatusController(IDocumentStatusService documentStatusService,
                                    IMetadataHateoasHandler metadataHateoasHandler) {
        this.documentStatusService = documentStatusService;
        this.metadataHateoasHandler = metadataHateoasHandler;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new dokumentstatus
    // POST [contextPath][api]/metadata/dokumentstatus/ny-dokumentstatus
    @ApiOperation(value = "Persists a new DocumentStatus object", notes = "Returns the newly" +
            " created DocumentStatus object after it is persisted to the database", response = DocumentStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentStatus.class),
            @ApiResponse(code = 201, message = "DocumentStatus " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = DOCUMENT_STATUS + SLASH + NEW_DOCUMENT_STATUS)
    public ResponseEntity<MetadataHateoas> createDocumentStatus(
            HttpServletRequest request,
            @RequestBody DocumentStatus documentStatus)
            throws NikitaException {
        documentStatusService.createNewDocumentStatus(documentStatus);
        MetadataHateoas metadataHateoas = new MetadataHateoas(documentStatus);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(documentStatus.getVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all documentStatus
    // GET [contextPath][api]/metadata/dokumentstatus/
    @ApiOperation(value = "Retrieves all DocumentStatus ", response = DocumentStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentStatus codes found",
                    response = DocumentStatus.class),
            @ApiResponse(code = 404, message = "No DocumentStatus found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = DOCUMENT_STATUS)
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(new ArrayList<>(documentStatusService.findAllAsList()),
                DOCUMENT_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // Retrieves a given documentStatus identified by a systemId
    // GET [contextPath][api]/metadata/dokumentstatus/{systemId}/
    @ApiOperation(value = "Gets documentStatus identified by its systemId", notes = "Returns the requested " +
            " documentStatus object", response = DocumentStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentStatus.class),
            @ApiResponse(code = 201, message = "DocumentStatus " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(value = DOCUMENT_STATUS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH, method = RequestMethod.GET)
    public ResponseEntity<MetadataHateoas> findBySystemId(@PathVariable("systemID") final String systemId,
                                                          HttpServletRequest request) {
        DocumentStatus documentStatus = documentStatusService.findBySystemId(systemId);
        MetadataHateoas metadataHateoas = new MetadataHateoas(documentStatus);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(documentStatus.getVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested documentStatus(like a template) with default values (nothing persisted)
    // GET [contextPath][api]/metadata/ny-dokumentstatus
    @ApiOperation(value = "Creates a suggested DocumentStatus", response = DocumentStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentStatus codes found",
                    response = DocumentStatus.class),
            @ApiResponse(code = 404, message = "No DocumentStatus found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = NEW_DOCUMENT_STATUS)
    public ResponseEntity<MetadataHateoas> getDocumentStatusTemplate(HttpServletRequest request) {
        DocumentStatus documentStatus = new DocumentStatus();
        documentStatus.setCode(TEMPLATE_DOCUMENT_STATUS_CODE);
        documentStatus.setDescription(TEMPLATE_DOCUMENT_STATUS_DESCRIPTION);
        MetadataHateoas metadataHateoas = new MetadataHateoas(documentStatus);
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a dokumentstatus
    // PUT [contextPath][api]/metatdata/dokumentstatus/
    @ApiOperation(value = "Updates a DocumentStatus object", notes = "Returns the newly" +
            " updated DocumentStatus object after it is persisted to the database", response = DocumentStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = DOCUMENT_STATUS + SLASH + DOCUMENT_STATUS)
    public ResponseEntity<MetadataHateoas> updateDocumentStatus(@RequestBody DocumentStatus documentStatus,
                                                                HttpServletRequest request)
            throws NikitaException {
        documentStatusService.update(documentStatus);
        MetadataHateoas metadataHateoas = new MetadataHateoas(documentStatus);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
