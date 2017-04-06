package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.hateoas.CaseFileHateoas;
import nikita.model.noark5.v4.hateoas.RegistryEntryHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaEntityNotFoundException;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.ICaseFileHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRegistryEntryHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ICaseFileService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CASE_FILE;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class CaseFileHateoasController {

    private ICaseFileService caseFileService;
    private ICaseFileHateoasHandler caseFileHateoasHandler;
    private IRegistryEntryHateoasHandler registryEntryHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public CaseFileHateoasController(ICaseFileService caseFileService,
                                     ICaseFileHateoasHandler caseFileHateoasHandler,
                                     IRegistryEntryHateoasHandler registryEntryHateoasHandler,
                                     ApplicationEventPublisher applicationEventPublisher) {
        this.caseFileService = caseFileService;
        this.caseFileHateoasHandler = caseFileHateoasHandler;
        this.registryEntryHateoasHandler = registryEntryHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a RegistryEntry object associated with the given Series systemId",
            notes = "Returns the newly created record object after it was associated with a File object and " +
                    "persisted to the database", response = RegistryEntryHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 201, message = "RegistryEntry " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type RegistryEntry"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS
            + SLASH + NEW_REGISTRY_ENTRY, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<RegistryEntryHateoas> createRegistryEntryAssociatedWithFile(
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the record with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "RegistryEntry",
                    value = "Incoming registryEntry object",
                    required = true)
            @RequestBody RegistryEntry registryEntry)  throws NikitaException {
        RegistryEntry createdRegistryEntry = caseFileService.createRegistryEntryAssociatedWithCaseFile(fileSystemId,
                registryEntry);
        RegistryEntryHateoas registryEntryHateoas = new RegistryEntryHateoas(createdRegistryEntry);
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdRegistryEntry));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdRegistryEntry.getVersion().toString())
                .body(registryEntryHateoas);
    }

    // API - All GET Requests (CRUD - READ)

    // Create a RegistryEntry object with default values
    //GET [contextPath][api]/arkivstruktur/mappe/SYSTEM_ID/ny-basisregistrering
    @ApiOperation(value = "Create a RegistryEntry with default values", response = RegistryEntry.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry returned", response = RegistryEntry.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            NEW_REGISTRY_ENTRY, method = RequestMethod.GET)
    public ResponseEntity<RegistryEntryHateoas> createDefaultRegistryEntry(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {

        RegistryEntry defaultRegistryEntry = new RegistryEntry();
        defaultRegistryEntry.setDescription(TEST_DESCRIPTION);
        defaultRegistryEntry.setTitle(TEST_TITLE);
        defaultRegistryEntry.setDocumentDate(new Date());
        RegistryEntryHateoas registryEntryHateoas = new
                RegistryEntryHateoas(defaultRegistryEntry);
        registryEntryHateoasHandler.addLinksOnNew(registryEntryHateoas, request, new Authorisation());
        return new ResponseEntity<>(registryEntryHateoas, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a single CaseFile entity given a systemId", response = CaseFile.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile returned", response = CaseFile.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<CaseFileHateoas> findOneCaseFilebySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the caseFile to retrieve",
                    required = true)
            @PathVariable("systemID") final String caseFileSystemId) {
        CaseFile caseFile = caseFileService.findBySystemId(caseFileSystemId);
        if (caseFile == null) {
            throw new NikitaEntityNotFoundException(caseFileSystemId);
        }
        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas(caseFile);
        caseFileHateoasHandler.addLinks(caseFileHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(caseFile.getVersion().toString())
                .body(caseFileHateoas);
    }

    @ApiOperation(value = "Retrieves multiple CaseFile entities limited by ownership rights", notes = "The field skip" +
            "tells how many CaseFile rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = CaseFileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile list found",
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CaseFileHateoas> findAllCaseFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                caseFileService.findCaseFileByOwnerPaginated(top, skip));

        caseFileHateoasHandler.addLinks(caseFileHateoas, request, new Authorisation());
        return new ResponseEntity<>(caseFileHateoas, HttpStatus.OK);
    }
}
