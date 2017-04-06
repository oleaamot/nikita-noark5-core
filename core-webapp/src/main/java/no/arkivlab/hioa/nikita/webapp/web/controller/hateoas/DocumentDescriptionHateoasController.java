package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.hateoas.DocumentDescriptionHateoas;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaEntityNotFoundException;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentDescriptionHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentObjectHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentDescriptionService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class DocumentDescriptionHateoasController {

    private IDocumentDescriptionService documentDescriptionService;
    private IDocumentDescriptionHateoasHandler documentDescriptionHateoasHandler;
    private IDocumentObjectHateoasHandler documentObjectHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public DocumentDescriptionHateoasController(IDocumentDescriptionService documentDescriptionService,
                                                IDocumentDescriptionHateoasHandler documentDescriptionHateoasHandler,
                                                IDocumentObjectHateoasHandler documentObjectHateoasHandler,
                                                ApplicationEventPublisher applicationEventPublisher) {
        this.documentDescriptionService = documentDescriptionService;
        this.documentDescriptionHateoasHandler = documentDescriptionHateoasHandler;
        this.documentObjectHateoasHandler = documentObjectHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a DocumentObject object associated with the given DocumentDescription systemId",
            notes = "Returns the newly created documentObject after it was associated with a DocumentDescription" +
                    " object and persisted to the database", response = DocumentDescriptionHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 201, message = "DocumentObject " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type DocumentObject"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "documentDescriptionSystemId" +
            RIGHT_PARENTHESIS + SLASH + NEW_DOCUMENT_OBJECT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<DocumentObjectHateoas>
    createDocumentObjectAssociatedWithDocumentDescription(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "documentDescriptionSystemId",
                    value = "systemId of documentDescription to associate the documentObject with.",
                    required = true)
            @PathVariable String documentDescriptionSystemId,
            @ApiParam(name = "documentObject",
                    value = "Incoming documentObject object",
                    required = true)
            @RequestBody DocumentObject documentObject)
            throws NikitaException {
        DocumentObject createdDocumentObject = documentDescriptionService.createDocumentObjectAssociatedWithDocumentDescription(
                documentDescriptionSystemId, documentObject);
        DocumentObjectHateoas documentObjectHateoas = new DocumentObjectHateoas(documentObject);
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdDocumentObject));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdDocumentObject.getVersion().toString())
                .body(documentObjectHateoas);
    }

    // API - All GET Requests (CRUD - READ)

    @ApiOperation(value = "Retrieves a single DocumentDescription entity given a systemId", response =
            DocumentDescription.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentDescription returned", response = DocumentDescription.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<DocumentDescriptionHateoas> findOneDocumentDescriptionBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the documentDescription to retrieve",
                    required = true)
            @PathVariable("systemID") final String documentDescriptionSystemId) {
        DocumentDescription documentDescription = documentDescriptionService.findBySystemId(documentDescriptionSystemId);
        if (documentDescription == null) {
            throw new NikitaEntityNotFoundException(documentDescriptionSystemId);
        }
        DocumentDescriptionHateoas documentDescriptionHateoas = new
                DocumentDescriptionHateoas(documentDescription);
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(documentDescription.getVersion().toString())
                .body(documentDescriptionHateoas);
    }

    @ApiOperation(value = "Retrieves multiple DocumentDescription entities limited by ownership rights", notes = "The field skip" +
            "tells how many DocumentDescription rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = DocumentDescriptionHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentDescription list found",
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DocumentDescriptionHateoas> findAllDocumentDescription(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        DocumentDescriptionHateoas documentDescriptionHateoas = new
                DocumentDescriptionHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                documentDescriptionService.findDocumentDescriptionByOwnerPaginated(top, skip));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentDescriptionHateoas, HttpStatus.OK);
    }

    // Create a DocumentObject with default values
    // GET [contextPath][api]/arkivstruktur/dokumentbeskrivelse/{systemId}/ny-dokumentobjekt
    @ApiOperation(value = "Create a DocumentObject with default values", response = DocumentObjectHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject returned", response = DocumentObjectHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            NEW_DOCUMENT_OBJECT, method = RequestMethod.GET)
    public ResponseEntity<DocumentObjectHateoas> createDefaultDocumentObject(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {

        DocumentObject defaultDocumentObject = new DocumentObject();
        // This is just temporary code as this will have to be replaced if this ever goes into production
        defaultDocumentObject.setMimeType(MediaType.APPLICATION_XML.toString());
        defaultDocumentObject.setVariantFormat(PRODUCTION_VERSION);
        defaultDocumentObject.setFormat("XML");
        defaultDocumentObject.setVersionNumber(1);

        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas(defaultDocumentObject);
        documentObjectHateoasHandler.addLinksOnNew(documentObjectHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentObjectHateoas, HttpStatus.OK);
    }

    // Retrieve all DocumentObjects associated with a DocumentDescription identified by systemId
    // GET [contextPath][api]/arkivstruktur/dokumentbeskrivelse/{systemId}/dokumentobjekt
    @ApiOperation(value = "Retrieves a list of DocumentObjects associated with a DocumentDescription",
            response = DocumentObjectHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject returned", response = DocumentObjectHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            DOCUMENT_OBJECT, method = RequestMethod.GET)
    public ResponseEntity<DocumentObjectHateoas> findAllDocumentDescriptionAssociatedWithRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String documentDescriptionSystemId) {

        DocumentDescription documentDescription = documentDescriptionService.findBySystemId(documentDescriptionSystemId);
        if (documentDescription == null) {
            throw new NoarkEntityNotFoundException("Could not find DocumentDescription object with systemID " +
                    documentDescriptionSystemId);
        }
        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas(new ArrayList<>(documentDescription.getReferenceDocumentObject()));
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentObjectHateoas, HttpStatus.OK);
    }
}
