package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentObjectHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentObjectService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.StorageException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_OBJECT;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT)
public class DocumentObjectHateoasController {

    private IDocumentObjectService documentObjectService;
    private IDocumentObjectHateoasHandler documentObjectHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public DocumentObjectHateoasController(IDocumentObjectService documentObjectService,
                                           IDocumentObjectHateoasHandler documentObjectHateoasHandler,
                                           ApplicationEventPublisher applicationEventPublisher) {
        this.documentObjectService = documentObjectService;
        this.documentObjectHateoasHandler = documentObjectHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All GET Requests (CRUD - READ)

    @ApiOperation(value = "Retrieves a single DocumentObject entity given a systemId", response = DocumentObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject returned", response = DocumentObject.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET,
            produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
    public ResponseEntity<DocumentObjectHateoas> findOneDocumentObjectBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the documentObject to retrieve",
                    required = true)
            @PathVariable("systemID") final String documentObjectSystemId) {
        DocumentObject createdDocumentObject = documentObjectService.findBySystemId(documentObjectSystemId);
        if (createdDocumentObject == null) {
            throw new NikitaEntityNotFoundException(documentObjectSystemId);
        }
        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas(createdDocumentObject);
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdDocumentObject.getVersion().toString())
                .body(documentObjectHateoas);
    }

    @ApiOperation(value = "Retrieves multiple DocumentObject entities limited by ownership rights", notes = "The field skip" +
            "tells how many DocumentObject rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = DocumentObjectHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject list found",
                    response = DocumentObjectHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
    public ResponseEntity<DocumentObjectHateoas> findAllDocumentObject(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                documentObjectService.findDocumentObjectByOwnerPaginated(top, skip));
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentObjectHateoas, HttpStatus.OK);
    }

    @ApiOperation(value = "Downloads a file associated with the documentObject identified by a systemId",
            response = DocumentObjectHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File uploaded successfully", response = DocumentObjectHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + REFERENCE_FILE,
            method = RequestMethod.GET)
    public ResponseEntity<Resource> handleFileDownload(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the documentObject that has a file associated with it",
                    required = true)
            @PathVariable("systemID") final String documentObjectSystemId) {
        DocumentObject documentObject = documentObjectService.findBySystemId(documentObjectSystemId);
        if (documentObject == null) {
            throw new NikitaEntityNotFoundException(documentObjectSystemId);
        }
        Resource fileResource = documentObjectService.loadAsResource(documentObject);
        response.setContentType(documentObject.getMimeType());
        response.setContentLength(documentObject.getFileSize().intValue());
        response.addHeader("content-disposition", "inline; filename=" + documentObject.getOriginalFilename());

        return new ResponseEntity<>(fileResource, HttpStatus.OK);
    }

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Uploads a file and associates it with the documentObject identified by a systemId",
            response = DocumentObjectHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File uploaded successfully", response = DocumentObjectHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + REFERENCE_FILE,
            method = RequestMethod.POST, headers = "Accept=*/*", produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
    public ResponseEntity<DocumentObjectHateoas> handleFileUpload(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the documentObject you wish to associate a file with",
                    required = true)
            @PathVariable("systemID") final String documentObjectSystemId) {
        try {
            DocumentObject documentObject = documentObjectService.findBySystemId(documentObjectSystemId);
            if (documentObject == null) {
                throw new NikitaEntityNotFoundException(documentObjectSystemId);
            }
            InputStream inputStream;
            // Following will be needed for uploading file in chunks
            //String headerContentRange = request.getHeader("content-range");//Content-Range:bytes 737280-819199/845769

            // Check that content-length is set, > 0 and in agreement with the value set in documentObject
            Long contentLength = 0L;
            if (request.getHeader("content-length") == null) {
                throw new StorageException("Attempt to upload a document without content-length set. The document " +
                        "was attempted to be associated with " + documentObject);
            }
            contentLength = (long) request.getIntHeader("content-length");
            if (contentLength < 1) {
                throw new StorageException("Attempt to upload a document with 0 or negative content-length set. "
                        + "Actual value was (" + contentLength + "). The document  was attempted to be associated with "
                        + documentObject);
            }

            if (null == documentObject.getFileSize()) {
                throw new StorageException("Attempt to upload a document with a content-length set in the header ("
                        + contentLength + "), but the value in documentObject has not been set (== null).  The " +
                        "document was attempted to be associated with " + documentObject);
            }

            if (!contentLength.equals(documentObject.getFileSize())) {
                throw new StorageException("Attempt to upload a document with a content-length set in the header ("
                        + contentLength + ") that is not the same as the value in documentObject (" +
                        documentObject.getFileSize() + ").  The document was attempted to be associated with "
                        + documentObject);
            }

            // Check that the content-type is set and in agreement with mimeType value in documentObject
            String headerContentType = request.getHeader("content-type");
            if (headerContentType == null) {
                throw new StorageException("Attempt to upload a document without content-type set. The document " +
                        "was attempted to be associated with " + documentObject);
            }

            if (!headerContentType.equals(documentObject.getMimeType())) {
                throw new StorageException("Attempt to upload a document with a content-type set in the header ("
                        + contentLength + ") that is not the same as the mimeType in documentObject (" +
                        documentObject.getMimeType() + ").  The document was attempted to be associated with "
                        + documentObject);
            }

            documentObjectService.storeAndCalculateChecksum(request.getInputStream(), documentObject);

            // We need to update the documentObject in the database as checksum and checksum algorithm are set after
            // the document has been uploaded
            documentObjectService.update(documentObject);
            DocumentObjectHateoas documentObjectHateoas = new
                    DocumentObjectHateoas(documentObject);
            documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
            return new ResponseEntity<>(documentObjectHateoas, HttpStatus.OK);
        } catch (IOException e) {
            throw new StorageException(e.toString());
        }
    }
}
