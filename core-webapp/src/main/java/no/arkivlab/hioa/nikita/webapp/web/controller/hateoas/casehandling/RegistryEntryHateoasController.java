package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.casehandling;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.casehandling.DocumentFlow;
import nikita.model.noark5.v4.casehandling.Precedence;
import nikita.model.noark5.v4.casehandling.RegistryEntry;
import nikita.model.noark5.v4.casehandling.secondary.*;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import nikita.model.noark5.v4.hateoas.casehandling.*;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.model.noark5.v4.secondary.SignOff;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRegistryEntryHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.secondary.ICorrespondencePartHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ICorrespondencePartTypeService;
import no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.NoarkController;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityDeletedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Set;

import static nikita.config.Constants.*;
import static nikita.config.MetadataConstants.CORRESPONDENCE_PART_CODE_EA;
import static nikita.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + REGISTRY_ENTRY,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class RegistryEntryHateoasController extends NoarkController {

    ICorrespondencePartTypeService correspondencePartTypeService;
    private IRegistryEntryService registryEntryService;
    private IRegistryEntryHateoasHandler registryEntryHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;
    private ICorrespondencePartHateoasHandler correspondencePartHateoasHandler;

    public RegistryEntryHateoasController(IRegistryEntryService registryEntryService,
                                          IRegistryEntryHateoasHandler registryEntryHateoasHandler,
                                          ApplicationEventPublisher applicationEventPublisher,
                                          ICorrespondencePartHateoasHandler correspondencePartHateoasHandler,
                                          ICorrespondencePartTypeService correspondencePartTypeService) {
        this.registryEntryService = registryEntryService;
        this.registryEntryHateoasHandler = registryEntryHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.correspondencePartHateoasHandler = correspondencePartHateoasHandler;
        this.correspondencePartTypeService = correspondencePartTypeService;
    }

    // API - All POST Requests (CRUD - CREATE)

    // Create a new CorrespondencePartPerson and associate it with the given journalpost
    // POST [contextPath][api]/casehandling/journalpost/{systemId}/ny-korrespondansepartperson
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-korrespondansepartperson/
    @ApiOperation(value = "Persists a CorrespondencePartPerson object associated with the given Record systemId",
            notes = "Returns the newly created CorrespondencePartPerson object after it was associated with a " +
                    "Record object and persisted to the database", response = CorrespondencePartPersonHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartPerson " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartPersonHateoas.class),
            @ApiResponse(code = 201, message = "CorrespondencePartPerson " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartPersonHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CorrespondencePartPerson"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_CORRESPONDENCE_PART_PERSON, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CorrespondencePartPersonHateoas> createCorrespondencePartPersonAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of record to associate the CorrespondencePartPerson with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "CorrespondencePartPerson",
                    value = "Incoming CorrespondencePartPerson object",
                    required = true)
            @RequestBody CorrespondencePartPerson CorrespondencePartPerson)
            throws NikitaException {

        CorrespondencePartPerson createdCorrespondencePartPerson =
                registryEntryService.createCorrespondencePartPersonAssociatedWithRegistryEntry(systemID, CorrespondencePartPerson);
        CorrespondencePartPersonHateoas correspondencePartPersonHateoas =
                new CorrespondencePartPersonHateoas(createdCorrespondencePartPerson);
        correspondencePartHateoasHandler.addLinks(correspondencePartPersonHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdCorrespondencePartPerson));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdCorrespondencePartPerson.getVersion().toString())
                .body(correspondencePartPersonHateoas);
    }

    // Create a new CorrespondencePartInternal and associate it with the given journalpost
    // POST [contextPath][api]/casehandling/journalpost/{systemId}/ny-korrespondansepartintern
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-korrespondansepartintern/
    @ApiOperation(value = "Persists a CorrespondencePartInternal object associated with the given Record systemId",
            notes = "Returns the newly created CorrespondencePartInternal object after it was associated with a " +
                    "Record object and persisted to the database", response = CorrespondencePartInternalHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartInternal " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartInternalHateoas.class),
            @ApiResponse(code = 201, message = "CorrespondencePartInternal " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartInternalHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CorrespondencePartInternal"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_CORRESPONDENCE_PART_INTERNAL, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CorrespondencePartInternalHateoas> createCorrespondencePartInternalAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of record to associate the CorrespondencePartInternal with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "CorrespondencePartInternal",
                    value = "Incoming CorrespondencePartInternal object",
                    required = true)
            @RequestBody CorrespondencePartInternal CorrespondencePartInternal)
            throws NikitaException {

        CorrespondencePartInternal createdCorrespondencePartInternal =
                registryEntryService.createCorrespondencePartInternalAssociatedWithRegistryEntry(systemID, CorrespondencePartInternal);
        CorrespondencePartInternalHateoas correspondencePartInternalHateoas =
                new CorrespondencePartInternalHateoas(createdCorrespondencePartInternal);
        correspondencePartHateoasHandler.addLinks(correspondencePartInternalHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdCorrespondencePartInternal));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdCorrespondencePartInternal.getVersion().toString())
                .body(correspondencePartInternalHateoas);
    }

    // Create a new CorrespondencePartUnit and associate it with the given journalpost
    // POST [contextPath][api]/casehandling/journalpost/{systemId}/ny-korrespondansepartenhet
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-korrespondansepartenhet/
    @ApiOperation(value = "Persists a CorrespondencePartUnit object associated with the given Record systemId",
            notes = "Returns the newly created CorrespondencePartUnit object after it was associated with a " +
                    "Record object and persisted to the database", response = CorrespondencePartUnitHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartUnit " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartUnitHateoas.class),
            @ApiResponse(code = 201, message = "CorrespondencePartUnit " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartUnitHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CorrespondencePartUnit"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_CORRESPONDENCE_PART_UNIT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CorrespondencePartUnitHateoas> createCorrespondencePartUnitAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of record to associate the CorrespondencePartUnit with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "CorrespondencePartUnit",
                    value = "Incoming CorrespondencePartUnit object",
                    required = true)
            @RequestBody CorrespondencePartUnit CorrespondencePartUnit)
            throws NikitaException {

        CorrespondencePartUnit createdCorrespondencePartUnit =
                registryEntryService.createCorrespondencePartUnitAssociatedWithRegistryEntry(systemID, CorrespondencePartUnit);
        CorrespondencePartUnitHateoas correspondencePartUnitHateoas =
                new CorrespondencePartUnitHateoas(createdCorrespondencePartUnit);
        correspondencePartHateoasHandler.addLinks(correspondencePartUnitHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdCorrespondencePartUnit));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdCorrespondencePartUnit.getVersion().toString())
                .body(correspondencePartUnitHateoas);
    }

    /*
    // Create a new Precedence and associate it with the given journalpost
    // POST [contextPath][api]/casehandling/journalpost/{systemId}/ny-presedens
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-presedens/
    @ApiOperation(value = "Persists a Precedence object associated with the given Record systemId",
            notes = "Returns the newly created Precedence object after it was associated with a " +
                    "Record object and persisted to the database", response = PrecedenceHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Precedence " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = PrecedenceHateoas.class),
            @ApiResponse(code = 201, message = "Precedence " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = PrecedenceHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Precedence"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_PRECEDENCE, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<PrecedenceHateoas> createPrecedenceAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of record to associate the Precedence with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "Precedence",
                    value = "Incoming Precedence object",
                    required = true)
            @RequestBody Precedence Precedence)
            throws NikitaException {

        Precedence createdPrecedence =
                registryEntryService.createPrecedenceAssociatedWithRecord(systemID, Precedence);
        PrecedenceHateoas precedenceHateoas =
                new PrecedenceHateoas(createdPrecedence);
        precedenceHateoasHandler.addLinks(PrecedenceHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdPrecedence));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
            .header(ETAG, .getVersion().toString())
                .body(precedenceHateoas);
    }


    // Create a new SignOff and associate it with the given journalpost
    // POST [contextPath][api]/casehandling/journalpost/{systemId}/ny-avskrivning
    //  http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-avskrivning/
    @ApiOperation(value = "Persists a SignOff object associated with the given Record systemId",
            notes = "Returns the newly created SignOff object after it was associated with a " +
                    "Record object and persisted to the database", response = SignOffHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SignOff " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SignOffHateoas.class),
            @ApiResponse(code = 201, message = "SignOff " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SignOffHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type SignOff"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_DOCUMENT_DESCRIPTION, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<SignOffHateoas>
    createSignOffAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of record to associate the signOff with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "signOff",
                    value = "Incoming signOff object",
                    required = true)
            @RequestBody SignOff signOff)
            throws NikitaException {

        SignOff createdSignOff =
                recordService.createSignOffAssociatedWithRecord(systemID, signOff);
        SignOffHateoas signOffHateoas =
                new SignOffHateoas(createdSignOff);
        signOffHateoasHandler.addLinks(signOffHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdSignOff));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
             .header(ETAG, .getVersion().toString())
                .body(signOffHateoas);
    }
   */

    // Create a new CorrespondencePart and associate it with the given RegistryEntry
    // POST [contextPath][api]/casehandling/journalpost/{systemId}/ny-korrespondansepart
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-korrespondansepart/
    @ApiOperation(value = "Persists a DocumentObject associated with the given Record systemId",
            notes = "Returns the newly created DocumentObject after it was associated with a " +
                    "Record and persisted to the database. A DocumentObject should not be associated with both a " +
                    "Record and a DocumentDescription. Choose one!", response = DocumentObjectHateoas.class)
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
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS
            + SLASH + NEW_DOCUMENT_OBJECT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> createDocumentObjectAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of record to associate the documentObject with.",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "documentObject",
                    value = "Incoming documentObject object",
                    required = true)
            @RequestBody DocumentObject documentObject)
            throws NikitaException {
        /*
        DocumentObjectHateoas documentObjectHateoas =
                new DocumentObjectHateoas(
                        recordService.createDocumentObjectAssociatedWithRecord(systemID,
                                documentObject));
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
           return ResponseEntity.status(HttpStatus.CREATED)
                .header(ETAG, .getVersion().toString())
                .body(documentObjectHateoas);
        */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all CorrespondencePartPerson associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/sakarkiv/journalpost/{systemId}/korrespondansepartperson
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/korrespondansepartperson/
    @ApiOperation(value = "Retrieves a list of CorrespondencePartPersons associated with a RegistryEntry",
            response = CorrespondencePartPersonHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartPerson returned",
                    response = CorrespondencePartPersonHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            CORRESPONDENCE_PART_PERSON, method = RequestMethod.GET)
    public ResponseEntity<CorrespondencePartPersonHateoas> findAllCorrespondencePartPersonAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        Set<CorrespondencePartPerson> correspondencePartPerson =
                registryEntryService.getCorrespondencePartPersonAssociatedWithRegistryEntry(systemID);
        CorrespondencePartPersonHateoas correspondencePartHateoas =
                new CorrespondencePartPersonHateoas(new ArrayList<>(correspondencePartPerson));
        correspondencePartHateoasHandler.addLinksOnTemplate(correspondencePartHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(correspondencePartHateoas);
    }

    // Retrieve all CorrespondencePartUnit associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/sakarkiv/journalpost/{systemId}/korrespondansepartperson
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/korrespondansepartperson/
    @ApiOperation(value = "Retrieves a list of CorrespondencePartUnits associated with a RegistryEntry",
            response = CorrespondencePartUnitHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartUnit returned",
                    response = CorrespondencePartUnitHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            CORRESPONDENCE_PART_UNIT, method = RequestMethod.GET)
    public ResponseEntity<CorrespondencePartUnitHateoas> findAllCorrespondencePartUnitAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        Set<CorrespondencePartUnit> correspondencePartUnit =
                registryEntryService.getCorrespondencePartUnitAssociatedWithRegistryEntry(systemID);
        CorrespondencePartUnitHateoas correspondencePartHateoas =
                new CorrespondencePartUnitHateoas(new ArrayList<>(correspondencePartUnit));
        correspondencePartHateoasHandler.addLinksOnTemplate(correspondencePartHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(correspondencePartHateoas);
    }

    // Retrieve all CorrespondencePartInternal associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/sakarkiv/journalpost/{systemId}/korrespondansepartperson
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/korrespondansepartperson/
    @ApiOperation(value = "Retrieves a list of CorrespondencePartInternals associated with a RegistryEntry",
            response = CorrespondencePartInternalHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartInternal returned",
                    response = CorrespondencePartInternalHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            CORRESPONDENCE_PART_INTERNAL, method = RequestMethod.GET)
    public ResponseEntity<CorrespondencePartInternalHateoas> findAllCorrespondencePartInternalAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        Set<CorrespondencePartInternal> correspondencePartInternal =
                registryEntryService.getCorrespondencePartInternalAssociatedWithRegistryEntry(systemID);
        CorrespondencePartInternalHateoas correspondencePartHateoas =
                new CorrespondencePartInternalHateoas(new ArrayList<>(correspondencePartInternal));
        correspondencePartHateoasHandler.addLinksOnTemplate(correspondencePartHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(correspondencePartHateoas);
    }


    // Create a suggested CorrespondencePartPerson (like a template) object with default values (nothing persisted)
    // GET [contextPath][api]/casehandling/journalpost/{systemId}/ny-korrespondansepartperson
    @ApiOperation(value = "Suggests the contents of a new CorrespondencePart object",
            notes = "Returns a pre-filled CorrespondencePart object" +
                    " with values relevant for the logged-in user", response = CorrespondencePartPersonHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePart " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartPersonHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = {SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_CORRESPONDENCE_PART_PERSON})
    public ResponseEntity<CorrespondencePartPersonHateoas> getCorrespondencePartPersonTemplate(
            HttpServletRequest request
    ) throws NikitaException {
        CorrespondencePartPerson suggestedCorrespondencePart = new CorrespondencePartPerson();

        CorrespondencePartType correspondencePartType =
                correspondencePartTypeService.findByCode(CORRESPONDENCE_PART_CODE_EA);
        if (correspondencePartType == null) {
            throw new NikitaException("Internal error, metadata missing. [" +
                    CORRESPONDENCE_PART_CODE_EA + "] returns no value");
        }
        suggestedCorrespondencePart.setCorrespondencePartType(correspondencePartType);

        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setAddressType(POSTAL_ADDRESS);
        postalAddress.setAddressLine1("ADRL1: 742 Evergreen Terrace");
        postalAddress.setAddressLine2("ADRL2: 742 Evergreen Terrace");
        postalAddress.setAddressLine3("ADRL3: 742 Evergreen Terrace");
        postalAddress.setCountryCode("US");
        postalAddress.setPostalNumber(new PostalNumber("12345"));
        postalAddress.setPostalTown("Springfield");
        suggestedCorrespondencePart.setPostalAddress(postalAddress);

        ResidingAddress residingAddress = new ResidingAddress();
        residingAddress.setAddressType(RESIDING_ADDRESS);
        residingAddress.setAddressLine1("ADRL1: 743 Evergreen Terrace");
        residingAddress.setAddressLine2("ADRL2: 743 Evergreen Terrace");
        residingAddress.setAddressLine3("ADRL3: 743 Evergreen Terrace");
        residingAddress.setCountryCode("US");
        residingAddress.setPostalNumber(new PostalNumber("12345"));
        residingAddress.setPostalTown("Springfield");
        suggestedCorrespondencePart.setResidingAddress(residingAddress);

        suggestedCorrespondencePart.setSocialSecurityNumber("09088512345");
        suggestedCorrespondencePart.setdNumber("dddddd1234");

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmailAddress("nikita@example.com");
        contactInformation.setMobileTelephoneNumber("123456789");
        contactInformation.setTelephoneNumber("987654321");
        suggestedCorrespondencePart.setContactInformation(contactInformation);

        suggestedCorrespondencePart.setName("Frank Grimes");

        CorrespondencePartPersonHateoas correspondencePartHateoas =
                new CorrespondencePartPersonHateoas(suggestedCorrespondencePart);
        correspondencePartHateoasHandler.addLinksOnTemplate(correspondencePartHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(correspondencePartHateoas);
    }

    // Create a suggested CorrespondencePartUnit (like a template) object with default values (nothing persisted)
    // GET [contextPath][api]/casehandling/journalpost/{systemId}/ny-korrespondansepartenhet
    @ApiOperation(value = "Suggests the contents of a new CorrespondencePart object",
            notes = "Returns a pre-filled CorrespondencePart object" +
                    " with values relevant for the logged-in user", response = CorrespondencePartUnitHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePart " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartUnitHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = {SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_CORRESPONDENCE_PART_UNIT})
    public ResponseEntity<CorrespondencePartUnitHateoas> getCorrespondencePartUnitTemplate(
            HttpServletRequest request
    ) throws NikitaException {
        CorrespondencePartUnit suggestedCorrespondencePart = new CorrespondencePartUnit();

        CorrespondencePartType correspondencePartType =
                correspondencePartTypeService.findByCode(CORRESPONDENCE_PART_CODE_EA);
        if (correspondencePartType == null) {
            throw new NikitaException("Internal error, metadata missing. [" +
                    CORRESPONDENCE_PART_CODE_EA + "] returns no value");
        }
        suggestedCorrespondencePart.setCorrespondencePartType(correspondencePartType);

        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setAddressType(POSTAL_ADDRESS);
        postalAddress.setAddressLine1("ADRL1: 744 Evergreen Terrace");
        postalAddress.setAddressLine2("ADRL2: 744 Evergreen Terrace");
        postalAddress.setAddressLine3("ADRL3: 744 Evergreen Terrace");
        postalAddress.setCountryCode("US");
        postalAddress.setPostalNumber(new PostalNumber("12345"));
        postalAddress.setPostalTown("Springfield");
        suggestedCorrespondencePart.setPostalAddress(postalAddress);

        BusinessAddress businessAddress = new BusinessAddress();
        businessAddress.setAddressType(BUSINESS_ADDRESS);
        businessAddress.setAddressLine1("ADRL1: 745 Evergreen Terrace");
        businessAddress.setAddressLine2("ADRL2: 745 Evergreen Terrace");
        businessAddress.setAddressLine3("ADRL3: 745 Evergreen Terrace");
        businessAddress.setCountryCode("US");
        businessAddress.setPostalNumber(new PostalNumber("12345"));
        businessAddress.setPostalTown("Springfield");
        suggestedCorrespondencePart.setBusinessAddress(businessAddress);

        suggestedCorrespondencePart.setContactPerson("Frank Contact Person Grimes");

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmailAddress("nikita@example.com");
        contactInformation.setMobileTelephoneNumber("123456789");
        contactInformation.setTelephoneNumber("987654321");
        suggestedCorrespondencePart.setContactInformation(contactInformation);

        suggestedCorrespondencePart.setName("Frank Grimes");

        CorrespondencePartUnitHateoas correspondencePartHateoas =
                new CorrespondencePartUnitHateoas(suggestedCorrespondencePart);
        correspondencePartHateoasHandler.addLinksOnTemplate(correspondencePartHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(correspondencePartHateoas);
    }

    // Create a suggested CorrespondencePartInternal (like a template) object with default values (nothing persisted)
    // GET [contextPath][api]/casehandling/journalpost/{systemId}/ny-korrespondansepartintern
    @ApiOperation(value = "Suggests the contents of a new CorrespondencePartInternal object",
            notes = "Returns a pre-filled CorrespondencePartInternal object" +
                    " with values relevant for the logged-in user", response = CorrespondencePartInternalHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePart " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartInternalHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = {SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_CORRESPONDENCE_PART_INTERNAL})
    public ResponseEntity<String> getCorrespondencePartInternalTemplate(
            HttpServletRequest request
    ) throws NikitaException {
        CorrespondencePartInternal suggestedCorrespondencePart = new CorrespondencePartInternal();

        CorrespondencePartType correspondencePartType =
                correspondencePartTypeService.findByCode(CORRESPONDENCE_PART_CODE_EA);
        if (correspondencePartType == null) {
            throw new NikitaException("Internal error, metadata missing. [" +
                    CORRESPONDENCE_PART_CODE_EA + "] returns no value");
        }
        suggestedCorrespondencePart.setCorrespondencePartType(correspondencePartType);

        // The reason this is not implemented is that we are missing AdministrativeUnit and multiple users
        CorrespondencePartInternalHateoas correspondencePartHateoas =
                new CorrespondencePartInternalHateoas(suggestedCorrespondencePart);
        correspondencePartHateoasHandler.addLinksOnTemplate(correspondencePartHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body("");
    }

    // Retrieve all SignOff associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/casehandling/journalpost/{systemId}/avskrivning
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/avskrivning/
    @ApiOperation(value = "Retrieves a list of SignOffs associated with a RegistryEntry",
            response = SignOff.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SignOff returned", response = SignOff.class), //SignOffHateoas
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            SIGN_OFF, method = RequestMethod.GET)
    public ResponseEntity<String> findAllSignOffAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*  Record record = recordService.findBySystemIdOrderBySystemId(systemID);
            if (record == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + systemID);
        }
        SignOffHateoas documentDescriptionHateoas = new
                SignOffHateoas(new ArrayList<>(record.getReferenceSignOff()));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(documentDescriptionHateoas);
                */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all Precedence associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/casehandling/journalpost/{systemId}/presedens
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/presedens/
    @ApiOperation(value = "Retrieves a list of Precedences associated with a RegistryEntry",
            response = Precedence.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Precedence returned", response = PrecedenceHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            PRECEDENCE, method = RequestMethod.GET)
    public ResponseEntity<String> findAllPrecedenceAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the registryEntry to retrieve associated Precedence",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*   Record record = recordService.findBySystemIdOrderBySystemId(systemID);
        if (record == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + systemID);
        }
        PrecedenceHateoas documentDescriptionHateoas = new
                PrecedenceHateoas(new ArrayList<>(record.getReferencePrecedence()));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(documentDescriptionHateoas);
                */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all DocumentFlow associated with a RegistryEntry identified by systemId
    // GET [contextPath][api]/casehandling/journalpost/{systemId}/dokumentflyt
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/dokumentflyt/
    @ApiOperation(value = "Retrieves a list of DocumentFlows associated with a RegistryEntry",
            response = DocumentFlow.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentFlow returned", response = DocumentFlow.class), //DocumentFlowHateoas
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            DOCUMENT_FLOW, method = RequestMethod.GET)
    public ResponseEntity<String> findAllDocumentFlowAssociatedWithRecord(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*  Record record = recordService.findBySystemIdOrderBySystemId(systemID);
            if (record == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + systemID);
        }
        DocumentFlowHateoas documentDescriptionHateoas = new
                DocumentFlowHateoas(new ArrayList<>(record.getReferenceDocumentFlow()));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(documentDescriptionHateoas);
                */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }


    // Retrieve a single registryEntry identified by systemId
    // GET [contextPath][api]/casehandling/journalpost/{systemID}
    @ApiOperation(value = "Retrieves a single RegistryEntry entity given a systemId", response = RegistryEntry.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry returned", response = RegistryEntry.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<RegistryEntryHateoas> findOneRegistryEntrybySystemId(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the registryEntry to retrieve",
                    required = true)
            @PathVariable("systemID") final String registryEntrySystemId) {
        RegistryEntry registryEntry = registryEntryService.findBySystemIdOrderBySystemId(registryEntrySystemId);

        RegistryEntryHateoas registryEntryHateoas = new
                RegistryEntryHateoas(registryEntry);
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(registryEntry.getVersion().toString())
                .body(registryEntryHateoas);
    }

    // Get all registryEntry
    // GET [contextPath][api]/casehandling/journalpost/
    // http://rel.kxml.no/noark5/v4/api/sakarkiv/journalpost/
    @ApiOperation(value = "Retrieves multiple RegistryEntry entities limited by ownership rights", notes = "The field skip" +
            "tells how many RegistryEntry rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = RegistryEntryHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry found",
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RegistryEntryHateoas> findAllRegistryEntry(
            HttpServletRequest request,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {
        RegistryEntryHateoas registryEntryHateoas = new
                RegistryEntryHateoas((ArrayList<INikitaEntity>) (ArrayList)
                registryEntryService.findRegistryEntryByOwnerPaginated(top, skip));
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(registryEntryHateoas);
    }

    // Delete a Record identified by systemID
    // DELETE [contextPath][api]/casehandling/journalpost/{systemId}/
    @ApiOperation(value = "Deletes a single RegistryEntry entity identified by systemID", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parent entity (DocumentDescription or Record) returned", response = String.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRecordBySystemId(HttpServletRequest request,
                                                         @ApiParam(name = "systemID",
                    value = "systemID of the record to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        RegistryEntry registryEntry = registryEntryService.findBySystemIdOrderBySystemId(systemID);
        registryEntryService.deleteEntity(systemID);
        applicationEventPublisher.publishEvent(new AfterNoarkEntityDeletedEvent(this, registryEntry));
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(CommonUtils.WebUtils.getSuccessStatusStringForDelete());
    }


    // Update a RegistryEntry with given values
    // PUT [contextPath][api]/casehandling/journalpost/{systemId}
    @ApiOperation(value = "Updates a RegistryEntry identified by a given systemId", notes = "Returns the newly updated registryEntry",
            response = RegistryEntryHateoas.class)
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
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<RegistryEntryHateoas> updateRegistryEntry(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of registryEntry to update",
                    required = true)
            @PathVariable("systemID") final String systemID,
            @ApiParam(name = "RegistryEntry",
                    value = "Incoming registryEntry object",
                    required = true)
            @RequestBody RegistryEntry registryEntry) throws NikitaException {
        validateForUpdate(registryEntry);

        RegistryEntry updatedRegistryEntry = registryEntryService.handleUpdate(systemID, parseETAG(request.getHeader(ETAG)), registryEntry);
        RegistryEntryHateoas registryEntryHateoas = new RegistryEntryHateoas(updatedRegistryEntry);
        registryEntryHateoasHandler.addLinks(registryEntryHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedRegistryEntry));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedRegistryEntry.getVersion().toString())
                .body(registryEntryHateoas);
    }
}
