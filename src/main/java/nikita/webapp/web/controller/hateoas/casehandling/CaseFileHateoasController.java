package nikita.webapp.web.controller.hateoas.casehandling;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.Class;
import nikita.common.model.noark5.v4.NoarkEntity;
import nikita.common.model.noark5.v4.Series;
import nikita.common.model.noark5.v4.casehandling.CaseFile;
import nikita.common.model.noark5.v4.casehandling.RegistryEntry;
import nikita.common.model.noark5.v4.hateoas.ClassHateoas;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.common.model.noark5.v4.hateoas.casehandling.CaseFileHateoas;
import nikita.common.model.noark5.v4.hateoas.casehandling.RegistryEntryHateoas;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.hateoas.interfaces.ICaseFileHateoasHandler;
import nikita.webapp.hateoas.interfaces.IClassHateoasHandler;
import nikita.webapp.hateoas.interfaces.IRegistryEntryHateoasHandler;
import nikita.webapp.hateoas.interfaces.ISeriesHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.interfaces.ICaseFileService;
import nikita.webapp.web.controller.hateoas.NoarkController;
import nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import nikita.webapp.web.events.AfterNoarkEntityDeletedEvent;
import nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class CaseFileHateoasController extends NoarkController {

    private ICaseFileService caseFileService;
    private ICaseFileHateoasHandler caseFileHateoasHandler;
    private IRegistryEntryHateoasHandler registryEntryHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;
    private ISeriesHateoasHandler seriesHateoasHandler;
    private IClassHateoasHandler classHateoasHandler;

    public CaseFileHateoasController(ICaseFileService caseFileService,
                                     ICaseFileHateoasHandler caseFileHateoasHandler,
                                     IRegistryEntryHateoasHandler registryEntryHateoasHandler,
                                     ApplicationEventPublisher applicationEventPublisher,
                                     ISeriesHateoasHandler seriesHateoasHandler,
                                     IClassHateoasHandler classHateoasHandler) {
        this.caseFileService = caseFileService;
        this.caseFileHateoasHandler = caseFileHateoasHandler;
        this.registryEntryHateoasHandler = registryEntryHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.seriesHateoasHandler = seriesHateoasHandler;
        this.classHateoasHandler = classHateoasHandler;
    }

    // API - All POST Requests (CRUD - CREATE)

    // Create a RegistryEntry entity
    // POST [contextPath][api]/casehandling/{systemId}/ny-journalpost
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

    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS
            + SLASH + NEW_REGISTRY_ENTRY, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<RegistryEntryHateoas> createRegistryEntryAssociatedWithFile(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of file to associate the record with",
                    required = true)
            @PathVariable("systemID") final String systemID,
            @ApiParam(name = "RegistryEntry",
                    value = "Incoming registryEntry object",
                    required = true)
            @RequestBody RegistryEntry registryEntry)  throws NikitaException {
        RegistryEntry createdRegistryEntry = caseFileService.createRegistryEntryAssociatedWithCaseFile(systemID,
                registryEntry);
        RegistryEntryHateoas registryEntryHateoas = new RegistryEntryHateoas(createdRegistryEntry);
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdRegistryEntry));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdRegistryEntry.getVersion().toString())
                .body(registryEntryHateoas);
    }

    // API - All GET Requests (CRUD - READ)

    // Create a RegistryEntry object with default values
    // GET [contextPath][api]/casehandling/mappe/SYSTEM_ID/ny-journalpost
    @ApiOperation(value = "Create a RegistryEntry with default values", response = RegistryEntry.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry returned", response = RegistryEntry.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            NEW_REGISTRY_ENTRY, method = RequestMethod.GET)
    public ResponseEntity<RegistryEntryHateoas> createDefaultRegistryEntry(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {

        RegistryEntry defaultRegistryEntry = new RegistryEntry();

        // TODO consider using Calendar date = new GregorianCalendar()
        Date now = new Date();

        // TODO figure out good defaults to return
        defaultRegistryEntry.setRecordDate(now);
        defaultRegistryEntry.setDocumentDate(now);
        defaultRegistryEntry.setRecordStatus(TEST_RECORD_STATUS);
        defaultRegistryEntry.setRegistryEntryType(TEST_REGISTRY_ENTRY_TYPE);
        defaultRegistryEntry.setRecordYear(Calendar.getInstance().get(Calendar.YEAR));
        // TODO generate these
        //defaultRegistryEntry.setRecordSequenceNumber(201701011);
        //defaultRegistryEntry.setRegistryEntryNumber(201701);

        RegistryEntryHateoas registryEntryHateoas = new
                RegistryEntryHateoas(defaultRegistryEntry);
        registryEntryHateoasHandler.addLinksOnNew(registryEntryHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(registryEntryHateoas);
    }

    // Retrieve a single casefile identified by systemId
    // GET [contextPath][api]/casehandling/saksmappe/{systemID}
    @ApiOperation(value = "Retrieves a single CaseFile entity given a systemId", response = CaseFile.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile returned", response = CaseFile.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<CaseFileHateoas> findOneCaseFilebySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the caseFile to retrieve",
                    required = true)
            @PathVariable("systemID") final String caseFileSystemId) {
        CaseFile caseFile = caseFileService.findBySystemId(caseFileSystemId);
        if (caseFile == null) {
            throw new NoarkEntityNotFoundException(caseFileSystemId);
        }
        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas(caseFile);
        caseFileHateoasHandler.addLinks(caseFileHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(caseFile.getVersion().toString())
                .body(caseFileHateoas);
    }

    // Retrieve all RegistryEntry associated with a casefile identified by systemId
    // GET [contextPath][api]/casehandling/saksmappe/{systemID}/journalpost
    @ApiOperation(value = "Retrieves all RegistryEntry associated with a CaseFile identified by systemId",
            response = RegistryEntry.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry list returned", response = RegistryEntryHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + REGISTRY_ENTRY,
            method = RequestMethod.GET)
    public ResponseEntity<RegistryEntryHateoas> findRegistryEntryAssociatedWithCaseFileBySystemId(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the caseFile to retrieve",
                    required = true)
            @PathVariable("systemID") final String caseFileSystemId) {
        CaseFile caseFile = caseFileService.findBySystemId(caseFileSystemId);
        if (caseFile == null) {
            throw new NoarkEntityNotFoundException(caseFileSystemId);
        }
        RegistryEntryHateoas registryEntryHateoas = new
                RegistryEntryHateoas((List<INikitaEntity>)
                (List) (caseFile.getReferenceRecord()));
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(registryEntryHateoas);
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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CaseFileHateoas> findAllCaseFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas((List<INikitaEntity>) (List)
                caseFileService.findCaseFileByOwnerPaginated(top, skip));

        caseFileHateoasHandler.addLinks(caseFileHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(caseFileHateoas);
    }
    
    // Delete a CaseFile identified by systemID
    // DELETE [contextPath][api]/casehandling/saksmappe/{systemId}/
    @ApiOperation(value = "Deletes a single CaseFile entity identified by systemID", response = HateoasNoarkObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parent entity (DocumentDescription or CaseFile) returned", response = HateoasNoarkObject.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<HateoasNoarkObject> deleteCaseFileBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the caseFile to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        CaseFile caseFile = caseFileService.findBySystemId(systemID);
        NoarkEntity parentEntity = caseFile.chooseParent();
        HateoasNoarkObject hateoasNoarkObject;
        if (parentEntity instanceof Series) {
            hateoasNoarkObject = new SeriesHateoas(parentEntity);
            seriesHateoasHandler.addLinks(hateoasNoarkObject, new Authorisation());
        }
        // TODO: Can a casefile have a Class as parent???
        else if (parentEntity instanceof Class) {
            hateoasNoarkObject = new ClassHateoas(parentEntity);
            classHateoasHandler.addLinks(hateoasNoarkObject, new Authorisation());
        }
        else {
            throw new NikitaException("Internal error. Could not process " + request.getRequestURI());
        }
        caseFileService.deleteEntity(systemID);
        applicationEventPublisher.publishEvent(new AfterNoarkEntityDeletedEvent(this, caseFile));
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(hateoasNoarkObject);
    }

    // Update a CaseFile with given values
    // PUT [contextPath][api]/casehandling/saksmappe/{systemId}
    @ApiOperation(value = "Updates a CaseFile identified by a given systemId", notes = "Returns the newly updated caseFile",
            response = CaseFileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 201, message = "CaseFile " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CaseFile"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CaseFileHateoas> updateCaseFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of caseFile to update",
                    required = true)
            @PathVariable("systemID") final String systemID,
            @ApiParam(name = "CaseFile",
                    value = "Incoming caseFile object",
                    required = true)
            @RequestBody CaseFile caseFile) throws NikitaException {
        validateForUpdate(caseFile);

        CaseFile updatedCaseFile = caseFileService.handleUpdate(systemID, parseETAG(request.getHeader(ETAG)), caseFile);
        CaseFileHateoas caseFileHateoas = new CaseFileHateoas(updatedCaseFile);
        caseFileHateoasHandler.addLinks(caseFileHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedCaseFile));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedCaseFile.getVersion().toString())
                .body(caseFileHateoas);
    }
}
/*

    // TODO: Consider gathering all the missing fields as a string and returning all in one go
    // But the developer of the client should know what's required!
    public void checkForObligatoryCaseFileValues(CaseFile caseFile) {

        if (caseFile.getFileId() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "mappeID field is mandatory, and you have submitted an empty value.");
        }
        if (caseFile.getCaseDate() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "saksDato field is mandatory, and you have submitted an empty value.");
        }
        if (caseFile.getAdministrativeUnit() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "field administrativEnhet is mandatory, and you have submitted an empty value.");
        }
        if (caseFile.getCaseResponsible() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe  you tried to create is malformed. The "
                    + "saksansvarlig field is mandatory, and you have submitted an empty value.");
        }
        if (caseFile.getCaseStatus() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "saksstatus field is mandatory, and you have submitted an empty value.");
        }
    }
 @Override

public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {
    if (noarkEntity.getTitle() == null) {
        throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                + "tittel field is mandatory, and you have submitted an empty value.");
    }
}
 */
