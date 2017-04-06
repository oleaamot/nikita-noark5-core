package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.hateoas.DocumentDescriptionHateoas;
import nikita.model.noark5.v4.hateoas.RegistryEntryHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentDescriptionHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentObjectHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRegistryEntryHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.REGISTRY_ENTRY;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + REGISTRY_ENTRY,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class RegistryEntryHateoasController {

    private IRegistryEntryService registryEntryService;
    private IDocumentDescriptionHateoasHandler documentDescriptionHateoasHandler;
    private IDocumentObjectHateoasHandler documentObjectHateoasHandler;
    private IRegistryEntryHateoasHandler registryEntryHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;


    public RegistryEntryHateoasController(IRegistryEntryService registryEntryService,
                                          IDocumentDescriptionHateoasHandler documentDescriptionHateoasHandler,
                                          IDocumentObjectHateoasHandler documentObjectHateoasHandler,
                                          IRegistryEntryHateoasHandler registryEntryHateoasHandler,
                                          ApplicationEventPublisher applicationEventPublisher) {
        this.registryEntryService = registryEntryService;
        this.documentDescriptionHateoasHandler = documentDescriptionHateoasHandler;
        this.documentObjectHateoasHandler = documentObjectHateoasHandler;
        this.registryEntryHateoasHandler = registryEntryHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All POST Requests (CRUD - CREATE)
    // POST [contextPath][api]/arkivstruktur/journalpost/{systemId}/ny-dokumentbeskrivelse/
    @ApiOperation(value = "Persists a DocumentDescription object associated with the given RegistryEntry systemId",
            notes = "Returns the newly created documentDescription object after it was associated with a " +
                    "RegistryEntry object and persisted to the database", response = DocumentDescriptionHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentDescription " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 201, message = "DocumentDescription " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type RegistryEntry"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_DOCUMENT_DESCRIPTION, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<DocumentDescriptionHateoas> createDocumentDescriptionAssociatedWithRegistryEntry(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of record/registryEntry to associate the documentDescription with.",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "documentDescription",
                    value = "Incoming documentDescription object",
                    required = true)
            @RequestBody DocumentDescription documentDescription)
            throws NikitaException {
        DocumentDescription createdDocumentDescription = registryEntryService.createDocumentDescriptionAssociatedWithRegistryEntry(recordSystemId,
                documentDescription);
        DocumentDescriptionHateoas documentDescriptionHateoas =
                new DocumentDescriptionHateoas(documentDescription);
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdDocumentDescription));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdDocumentDescription.getVersion().toString())
                .body(documentDescriptionHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // GET [contextPath][api]/arkivstruktur/journalpost/
    @ApiOperation(value = "Retrieves multiple RegistryEntry entities limited by ownership rights",
            notes = "The field skip tells how many RegistryEntry rows of the result set to ignore (starting at 0), " +
                    "while top tells how many rows after skip to return. Note if the value of top is greater than " +
                    "system value nikita-noark5-core.pagination.maxPageSize, then " +
                    "nikita-noark5-core.pagination.maxPageSize is used.",
            response = RegistryEntryHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntryHateoas list found", response = RegistryEntryHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RegistryEntryHateoas> findAllRegistryEntry(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        RegistryEntryHateoas registryEntryHateoas = new RegistryEntryHateoas(
                (ArrayList<INoarkSystemIdEntity>) (ArrayList)
                        registryEntryService.findRegistryEntryByOwnerPaginated(top, skip));
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, request, new Authorisation());
        return new ResponseEntity<>(registryEntryHateoas, HttpStatus.OK);
    }
}
