package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.hateoas.DocumentDescriptionHateoas;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.RecordHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import nikita.util.exceptions.NoarkNotAcceptableException;
import nikita.util.exceptions.StorageException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.DocumentDescriptionHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.RecordHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentObjectHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentObjectService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityDeletedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_OBJECT;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT)
public class DocumentObjectHateoasController extends NoarkController {

    private IDocumentObjectService documentObjectService;
    private IDocumentObjectHateoasHandler documentObjectHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;
    private DocumentDescriptionHateoasHandler documentDescriptionHateoasHandler;
    private RecordHateoasHandler recordHateoasHandler;

    public DocumentObjectHateoasController(IDocumentObjectService documentObjectService,
                                           IDocumentObjectHateoasHandler documentObjectHateoasHandler,
                                           ApplicationEventPublisher applicationEventPublisher,
                                           DocumentDescriptionHateoasHandler documentDescriptionHateoasHandler,
                                           RecordHateoasHandler recordHateoasHandler) {

        this.documentObjectService = documentObjectService;
        this.documentObjectHateoasHandler = documentObjectHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.documentDescriptionHateoasHandler = documentDescriptionHateoasHandler;
        this.recordHateoasHandler = recordHateoasHandler;
    }

    // API - All GET Requests (CRUD - READ)
    // Get a documentObject identified by systemID
    // GET [contextPath][api]/arkivstruktur/dokumentobjekt/{systemID}
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
            throw new NoarkEntityNotFoundException(documentObjectSystemId);
        }
        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas(createdDocumentObject);
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdDocumentObject.getVersion().toString())
                .body(documentObjectHateoas);
    }

    // Get all documentObject
    // GET [contextPath][api]/arkivstruktur/dokumentobjekt/
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
            @RequestParam(name = "skip", required = false) Integer skip,
            @RequestParam(name = "filter", required = false) String filter) {

        String reg = " ";
        String[] pieces;
        DocumentObjectHateoas documentObjectHateoas = null;

        if (filter != null) {
            pieces = filter.split(reg);

            if (pieces.length == 3 && pieces[1].equalsIgnoreCase("eq")) {
                pieces[2] = pieces[2].replace("\'", "");
                documentObjectHateoas = new
                        DocumentObjectHateoas((List<INikitaEntity>) (List)
                        documentObjectService.findDocumentObjectByAnyColumn(pieces[0], pieces[2]));

            }
        }

        if (null == documentObjectHateoas) {
            documentObjectHateoas = new
                    DocumentObjectHateoas((List<INikitaEntity>) (List)
                    documentObjectService.findDocumentObjectByOwnerPaginated(top, skip));
        }
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(documentObjectHateoas);
    }

    // Get a file identified by systemID retrievable with referanseFile
    // GET [contextPath][api]/arkivstruktur/dokumentobjekt/{systemID}/referanseFil
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
    public void handleFileDownload(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the documentObject that has a file associated with it",
                    required = true)
            @PathVariable("systemID") final String documentObjectSystemId) throws IOException {
        DocumentObject documentObject = documentObjectService.findBySystemId(documentObjectSystemId);
        if (documentObject == null) {
            throw new NoarkEntityNotFoundException(documentObjectSystemId);
        }
        Resource fileResource = documentObjectService.loadAsResource(documentObject);
        String acceptType = request.getHeader(HttpHeaders.ACCEPT);
        if (acceptType != null &&
	    !acceptType.equalsIgnoreCase(documentObject.getMimeType())) {
            if (!acceptType.equals("*/*")) {
                throw new NoarkNotAcceptableException("The request [" + request.getRequestURI() + "] is not acceptable. "
                        + "You have issued an Accept: " + acceptType + ", while the mimeType you are trying to retrieve "
                        + "is [" + documentObject.getMimeType() + "].");
            }
        }
        response.setContentType(documentObject.getMimeType());
        response.setContentLength(documentObject.getFileSize().intValue());
        response.addHeader("Content-disposition", "inline; filename=" + documentObject.getOriginalFilename());
        response.addHeader("Content-Type", documentObject.getMimeType());

        InputStream filestream = fileResource.getInputStream();
        try {
            long bytesTotal = IOUtils.copyLarge(filestream,
                                                response.getOutputStream());
            filestream.close();
        } finally {
            try {
		// Try close without exceptions if copy() threw an
		// exception.  If close() is called twice, the second
		// close() should be ignored.
                filestream.close();
            } catch(IOException e) {
                // swallow any error to expose exceptions from
                // IOUtil.copy() if the second close() failed.
            }
        }
        response.flushBuffer();
    }

    // API - All POST Requests (CRUD - CREATE)

    // upload a file and associate it with a documentObject
    // POST [contextPath][api]/arkivstruktur/dokumentobjekt/{systemID}/referanseFil
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
                throw new NoarkEntityNotFoundException(documentObjectSystemId);
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
            documentObjectHateoasHandler.addLinks(documentObjectHateoas, new Authorisation());
            return new ResponseEntity<>(documentObjectHateoas, HttpStatus.OK);
        } catch (IOException e) {
            throw new StorageException(e.toString());
        }
    }

    // Delete a DocumentObject identified by systemID
    // DELETE [contextPath][api]/arkivstruktur/dokumentobjekt/{systemId}/
    @ApiOperation(value = "Deletes a single DocumentObject entity identified by systemID", response = HateoasNoarkObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parent entity (DocumentDescription or Record) returned", response = HateoasNoarkObject.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<HateoasNoarkObject> deleteDocumentObjectBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the documentObject to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        DocumentObject documentObject = documentObjectService.findBySystemId(systemID);
        NoarkEntity parentEntity = documentObject.chooseParent();
        documentObjectService.deleteEntity(systemID);
        HateoasNoarkObject hateoasNoarkObject;
        if (parentEntity instanceof DocumentDescription) {
            hateoasNoarkObject = new DocumentDescriptionHateoas(parentEntity);
            documentDescriptionHateoasHandler.addLinks(hateoasNoarkObject, new Authorisation());
        }
        else if (parentEntity instanceof Record) {
            hateoasNoarkObject = new RecordHateoas(parentEntity);
            recordHateoasHandler.addLinks(hateoasNoarkObject, new Authorisation());
        }
        else {
            throw new NikitaException("Internal error. Could process" + request.getRequestURI());
        }
        applicationEventPublisher.publishEvent(new AfterNoarkEntityDeletedEvent(this, documentObject));
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(hateoasNoarkObject);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a DocumentObject
    // PUT [contextPath][api]/arkivstruktur/dokumentobjekt/{systemID}
    @ApiOperation(value = "Updates a DocumentObject object", notes = "Returns the newly" +
            " update DocumentObject object after it is persisted to the database", response = DocumentObjectHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentObjectHateoas.class),
            @ApiResponse(code = 201, message = "DocumentObject " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentObjectHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type DocumentObject"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<DocumentObjectHateoas> updateDocumentObject(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of documentObject to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "documentObject",
                    value = "Incoming documentObject object",
                    required = true)
            @RequestBody DocumentObject documentObject) throws NikitaException {
        validateForUpdate(documentObject);

        DocumentObject updatedDocumentObject = documentObjectService.handleUpdate(systemID, parseETAG(request.getHeader(ETAG)), documentObject);
        DocumentObjectHateoas documentObjectHateoas = new DocumentObjectHateoas(updatedDocumentObject);
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedDocumentObject));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedDocumentObject.getVersion().toString())
                .body(documentObjectHateoas);
    }

}
/*

properties check
    public void checkForObligatoryDocumentObjectValues(DocumentObject documentObject) {
        if (documentObject.getVersionNumber() == null) {
            throw new NikitaMalformedInputDataException("The dokumentobjekt you tried to create is " +
                    "malformed. The versjonsnummer field is mandatory, and you have submitted an empty value.");
        }
        if (documentObject.getVariantFormat() == null) {
            throw new NikitaMalformedInputDataException("The dokumentobjekt you tried to create is " +
                    "malformed. The variantformat field is mandatory, and you have submitted an empty value.");
        }
        if (documentObject.getFormat() == null) {
            throw new NikitaMalformedInputDataException("The dokumentobjekt you tried to create is " +
                    "malformed. The format field is mandatory, and you have submitted an empty value.");
        }
    }


 */
