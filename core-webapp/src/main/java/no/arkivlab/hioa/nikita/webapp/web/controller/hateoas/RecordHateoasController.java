package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.*;
import nikita.model.noark5.v4.hateoas.DocumentDescriptionHateoas;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import nikita.model.noark5.v4.hateoas.RecordHateoas;
import nikita.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentDescriptionHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentObjectHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRecordHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRecordService;
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
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class RecordHateoasController {

    private IRecordService recordService;
    private IDocumentDescriptionHateoasHandler documentDescriptionHateoasHandler;
    private IDocumentObjectHateoasHandler documentObjectHateoasHandler;
    private IRecordHateoasHandler recordHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public RecordHateoasController(IRecordService recordService,
                                   IDocumentDescriptionHateoasHandler documentDescriptionHateoasHandler,
                                   IDocumentObjectHateoasHandler documentObjectHateoasHandler,
                                   IRecordHateoasHandler recordHateoasHandler,
                                   ApplicationEventPublisher applicationEventPublisher) {
        this.recordService = recordService;
        this.documentDescriptionHateoasHandler = documentDescriptionHateoasHandler;
        this.documentObjectHateoasHandler = documentObjectHateoasHandler;
        this.recordHateoasHandler = recordHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    // API - All POST Requests (CRUD - CREATE)

    // Create a new DocumentDescription and associate it with the given Record
    // POST [contextPath][api]/arkivstruktur/registregin/{systemId}/ny-dokumentobjekt
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-dokumentobjekt/
    @ApiOperation(value = "Persists a DocumentDescription object associated with the given Record systemId",
            notes = "Returns the newly created DocumentDescription object after it was associated with a " +
                    "Record object and persisted to the database", response = DocumentDescriptionHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentDescription " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 201, message = "DocumentDescription " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type DocumentDescription"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_DOCUMENT_DESCRIPTION, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<DocumentDescriptionHateoas>
    createDocumentDescriptionAssociatedWithRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of record to associate the documentDescription with.",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "documentDescription",
                    value = "Incoming documentDescription object",
                    required = true)
            @RequestBody DocumentDescription documentDescription)
            throws NikitaException {

        DocumentDescription createdDocumentDescription =
                recordService.createDocumentDescriptionAssociatedWithRecord(recordSystemId, documentDescription);
        DocumentDescriptionHateoas documentDescriptionHateoas =
                new DocumentDescriptionHateoas(createdDocumentDescription);
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdDocumentDescription));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdDocumentDescription.getVersion().toString())
                .body(documentDescriptionHateoas);
    }

    // Create a new DocumentObject and associate it with the given Record
    // POST [contextPath][api]/arkivstruktur/registregin/{systemId}/ny-dokumentobjekt
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-dokumentobjekt/
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
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS
            + SLASH + NEW_DOCUMENT_OBJECT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> createDocumentObjectAssociatedWithRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of record to associate the documentObject with.",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "documentObject",
                    value = "Incoming documentObject object",
                    required = true)
            @RequestBody DocumentObject documentObject)
            throws NikitaException {
        /*
        DocumentObjectHateoas documentObjectHateoas =
                new DocumentObjectHateoas(
                        recordService.createDocumentObjectAssociatedWithRecord(recordSystemId,
                                documentObject));
        documentObjectHateoasHandler.addLinks(documentObjectHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
           return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdDocumentObject.getVersion().toString())
                .body(documentObjectHateoas);
        */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a reference to a secondary Series associated with the Record
    // POST [contextPath][api]/arkivstruktur/registrering/{systemId}/ny-referanseArkivdel
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-referanseArkivdel/
    @ApiOperation(value = "Associates a secondary Series with a Record identified by recordSystemId",
            notes = "Returns the Record after the secondary Series is successfully associated with it." +
                    "Note a secondary series allows a Record to be associated with another Series.",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = CLASS + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 201, message = CLASS + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + CLASS),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS
            + SLASH + NEW_REFERENCE_SERIES, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addReferenceSeriesToRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of Record to associate the secondary Series with",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "Series",
                    value = "series",
                    required = true)
            @RequestBody Series series) throws NikitaException {
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(series.getVersion().toString())
//                .body(seriesHateoas);

        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a Classified (gradering) to a Record
    // POST [contextPath][api]/arkivstruktur/registrering/{systemId}/ny-gradering
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-gradering/
    @ApiOperation(value = "Associates a Classified with a Record identified by recordSystemId",
            notes = "Returns the Record after the Classified is successfully associated with it." +
                    "Note a Record can only have one Classified. Update via PUT",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = CLASSIFIED + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 201, message = CLASSIFIED + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + CLASSIFIED),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS
            + SLASH + NEW_CLASSIFIED, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addNewClassifiedToRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of Record to associate the Classified with",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "Classified",
                    value = "classified",
                    required = true)
            @RequestBody Classified classified) throws NikitaException {
        //applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(classified.getVersion().toString())
//                .body(classifiedHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a Disposal to a Record
    // POST [contextPath][api]/arkivstruktur/registrering/{systemId}/ny-kassasjon
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-kassasjon/
    @ApiOperation(value = "Associates a Disposal with a Record identified by recordSystemId",
            notes = "Returns the Record after the Disposal is successfully associated with it." +
                    "Note a Record can only have one Disposal. Update via PUT",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DISPOSAL + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 201, message = DISPOSAL + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + DISPOSAL),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS
            + SLASH + NEW_DISPOSAL, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addNewDisposalToRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of Record to associate the Disposal with",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "Disposal",
                    value = "disposal",
                    required = true)
            @RequestBody Disposal disposal) throws NikitaException {
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        //TODO: What do we return here? Record + comment? comment?
        //        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(comment.getVersion().toString())
//                .body(commentHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a screening (skjerming) to a Record
    // POST [contextPath][api]/arkivstruktur/registrering/{systemId}/ny-skjerming
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-skjerming/
    @ApiOperation(value = "Associates a Screening with a Record identified by recordSystemId",
            notes = "Returns the Record after the Screening is successfully associated with it." +
                    "Note a Record can only have one Screening. Update via PUT",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SCREENING + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 201, message = SCREENING + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + SCREENING),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS
            + SLASH + NEW_SCREENING, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addNewScreeningToRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of Record to associate the Screening with",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "Screening",
                    value = "screening",
                    required = true)
            @RequestBody Screening screening) throws NikitaException {
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
//        TODO: What do we return here? Record + screening? screening?
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(screening.getVersion().toString())
//                .body(screeningHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a disposalUndertaken (utfoertKassasjon) to a Record
    // POST [contextPath][api]/arkivstruktur/registrering/{systemId}/ny-utfoertKassasjon
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-utfoertKassasjon/
    @ApiOperation(value = "Associates a DisposalUndertaken with a Record identified by recordSystemId",
            notes = "Returns the Record after the DisposalUndertaken is successfully associated with it." +
                    "Note a Record can only have one DisposalUndertaken. Update via PUT",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DISPOSAL_UNDERTAKEN + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 201, message = DISPOSAL_UNDERTAKEN + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + DISPOSAL_UNDERTAKEN),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS
            + SLASH + NEW_DISPOSAL_UNDERTAKEN, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addNewDisposalUndertakenToRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of Record to associate the DisposalUndertaken with",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "DisposalUndertaken",
                    value = "disposalUndertaken",
                    required = true)
            @RequestBody DisposalUndertaken disposalUndertaken) throws NikitaException {
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        //TODO: What do we return here? Record + disposalUndertaken? disposalUndertaken?
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(disposalUndertaken.getVersion().toString())
//                .body(disposalUndertakenHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a Deletion  to a Record
    // POST [contextPath][api]/arkivstruktur/registrering/{systemId}/ny-sletting
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-sletting/
    @ApiOperation(value = "Associates a Deletion with a Record identified by recordSystemId",
            notes = "Returns the Record after the Deletion is successfully associated with it." +
                    "Note a Record can only have one Deletion. Update via PUT",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DELETION + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 201, message = DELETION + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + DELETION),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS
            + SLASH + NEW_DELETION, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addNewDeletionToRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "recordSystemId",
                    value = "systemId of Record to associate the Deletion with",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "Deletion",
                    value = "deletion",
                    required = true)
            @RequestBody Deletion deletion) throws NikitaException {
        //TODO: This is more to carry out a deletion of files. You don't just add a deletion to Record
        //applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(deletion.getVersion().toString())
//                .body(deletionHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // API - All GET Requests (CRUD - READ)

    // Retrieve a Record identified by a systemId
    // GET [contextPath][api]/arkivstruktur/registrering/{systemId}
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/registrering/
    @ApiOperation(value = "Retrieves a single Record entity given a systemId", response = Record.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Record returned", response = Record.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<RecordHateoas> findOneRecordbySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the record to retrieve",
                    required = true)
            @PathVariable("systemID") final String recordSystemId) {
        RecordHateoas recordHateoas = new
                RecordHateoas(recordService.findBySystemId(recordSystemId));
        recordHateoasHandler.addLinks(recordHateoas, request, new Authorisation());
        return new ResponseEntity<>(recordHateoas, HttpStatus.CREATED);
    }

    // Retrieve all Records
    // GET [contextPath][api]/arkivstruktur/registrering
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/registrering/
    @ApiOperation(value = "Retrieves multiple Record entities limited by ownership rights",
            notes = "The field skip tells how many Record rows of the result set to ignore (starting at 0), " +
                    "while top tells how many rows after skip to return. Note if the value of top is greater than " +
                    "system value nikita-noark5-core.pagination.maxPageSize, then " +
                    "nikita-noark5-core.pagination.maxPageSize is used.",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RecordHateoas found", response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RecordHateoas> findAllRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        RecordHateoas recordHateoas = new RecordHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                recordService.findRecordByOwnerPaginated(top, skip));
        recordHateoasHandler.addLinks(recordHateoas, request, new Authorisation());
        return new ResponseEntity<>(recordHateoas, HttpStatus.OK);
    }

    // Retrieve all secondary Series associated with a Record
    // GET [contextPath][api]/arkivstruktur/registrering/{systemId}/referanseArkivdel
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/referanseArkivdel/
    @ApiOperation(value = "Retrieves all secondary Series associated with a Record identified by a systemId",
            response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + REFERENCE_SERIES,
            method = RequestMethod.GET)
    public ResponseEntity<String> findSecondarySeriesAssociatedWithRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the Record to retrieve secondary Class for",
                    required = true)
            @PathVariable("systemID") final String recordSystemId) {
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Create a DocumentDescription with default values
    // GET [contextPath][api]/arkivstruktur/resgistrering/{systemId}/ny-dokumentbeskrivelse
    @ApiOperation(value = "Create a DocumentDescription with default values", response = DocumentDescriptionHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentDescription returned", response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            NEW_DOCUMENT_DESCRIPTION, method = RequestMethod.GET)
    public ResponseEntity<DocumentDescriptionHateoas> createDefaultDocumentDescription(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {

        DocumentDescription defaultDocumentDescription = new DocumentDescription();

        defaultDocumentDescription.setAssociatedWithRecordAs(MAIN_DOCUMENT);
        defaultDocumentDescription.setTitle(TEST_TITLE);
        defaultDocumentDescription.setDocumentType(LETTER);
        defaultDocumentDescription.setDocumentStatus(DOCUMENT_STATUS_FINALISED);
        defaultDocumentDescription.setDescription(TEST_DESCRIPTION);

        DocumentDescriptionHateoas documentDescriptionHateoas = new
                DocumentDescriptionHateoas(defaultDocumentDescription);
        documentDescriptionHateoasHandler.addLinksOnNew(documentDescriptionHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentDescriptionHateoas, HttpStatus.OK);
    }

    // Create a DocumentObject with default values
    // GET [contextPath][api]/arkivstruktur/resgistrering/{systemId}/ny-dokumentobjekt
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

        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas(defaultDocumentObject);
        documentObjectHateoasHandler.addLinksOnNew(documentObjectHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentObjectHateoas, HttpStatus.OK);
    }

    // Retrieve all DocumentDescriptions associated with a Record identified by systemId
    // GET [contextPath][api]/arkivstruktur/resgistrering/{systemId}/dokumentbeskrivelse
    @ApiOperation(value = "Retrieves a lit of DocumentDescriptions associated with a Record",
            response = DocumentDescriptionHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentDescription returned", response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            DOCUMENT_DESCRIPTION, method = RequestMethod.GET)
    public ResponseEntity<DocumentDescriptionHateoas> findAllDocumentDescriptionAssociatedWithRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String recordSystemId) {
        Record record = recordService.findBySystemId(recordSystemId);
        if (record == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + recordSystemId);
        }
        DocumentDescriptionHateoas documentDescriptionHateoas = new
                DocumentDescriptionHateoas(new ArrayList<>(record.getReferenceDocumentDescription()));
        documentDescriptionHateoasHandler.addLinks(documentDescriptionHateoas, request, new Authorisation());
        return new ResponseEntity<>(documentDescriptionHateoas, HttpStatus.OK);
    }
}
