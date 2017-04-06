package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.*;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.hateoas.*;
import nikita.model.noark5.v4.interfaces.entities.ICrossReferenceEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IBasicRecordHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFileHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRecordHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFileService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
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
import static nikita.config.N5ResourceMappings.*;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class FileHateoasController {

    private IFileService fileService;
    private IFileHateoasHandler fileHateoasHandler;
    private IRecordHateoasHandler recordHateoasHandler;
    private IBasicRecordHateoasHandler basicRecordHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public FileHateoasController(IFileService fileService,
                                 IFileHateoasHandler fileHateoasHandler,
                                 IRecordHateoasHandler recordHateoasHandler,
                                 IBasicRecordHateoasHandler basicRecordHateoasHandler,
                                 ApplicationEventPublisher applicationEventPublisher) {
        this.fileService = fileService;
        this.fileHateoasHandler = fileHateoasHandler;
        this.recordHateoasHandler = recordHateoasHandler;
        this.basicRecordHateoasHandler = basicRecordHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All POST Requests (CRUD - CREATE)

    // Create a Record
    // POST [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-registrering
    // REL http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-registrering/
    @ApiOperation(value = "Persists a Record associated with the given Series systemId",
            notes = "Returns the newly created record after it was associated with a File and " +
                    "persisted to the database", response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Record " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 201, message = "Record " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Record"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_RECORD, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<RecordHateoas> createRecordAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the record with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "Record",
                    value = "Incoming record",
                    required = true)
            @RequestBody Record record) throws NikitaException {
        Record createdRecord = fileService.createRecordAssociatedWithFile(fileSystemId, record);
        RecordHateoas recordHateoas = new RecordHateoas(createdRecord);
        recordHateoasHandler.addLinks(recordHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdRecord));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdRecord.getVersion().toString())
                .body(recordHateoas);
    }

    // Create a BasicRecord
    // POST [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-basisregistrering
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-basisregistrering/
    @ApiOperation(value = "Persists a BasicRecord associated with the given Series systemId",
            notes = "Returns the newly created basicRecord after it was associated with a File and " +
                    "persisted to the database", response = BasicRecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = BasicRecordHateoas.class),
            @ApiResponse(code = 201, message = "BasicRecord " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = BasicRecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type BasicRecord"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_BASIC_RECORD, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<BasicRecordHateoas> createBasicRecordAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the basicRecord with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "BasicRecord",
                    value = "Incoming basicRecord",
                    required = true)
            @RequestBody BasicRecord basicRecord) throws NikitaException {
        BasicRecord createdBasicRecord = fileService.createBasicRecordAssociatedWithFile(fileSystemId, basicRecord);
        BasicRecordHateoas basicRecordHateoas = new BasicRecordHateoas(createdBasicRecord);
        basicRecordHateoasHandler.addLinks(basicRecordHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdBasicRecord));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdBasicRecord.getVersion().toString())
                .body(basicRecordHateoas);
    }

    // Create a CrossReference
    // POST [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-kryssreferanse
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-kryssreferanse/
    @ApiOperation(value = "Persists a BasicRecord associated with the given Series systemId",
            notes = "Returns the newly created basicRecord after it was associated with a File and " +
                    "persisted to the database", response = BasicRecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = BasicRecordHateoas.class),
            @ApiResponse(code = 201, message = "BasicRecord " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = BasicRecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type BasicRecord"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_CROSS_REFERENCE, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> createCrossReferenceAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the basicRecord with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "crossReferenceEntity",
                    value = "Noark entity that support cross reference functionality",
                    required = true)
            @RequestBody ICrossReferenceEntity crossReferenceEntity) throws NikitaException {

        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        //return ResponseEntity.status(HttpStatus.CREATED)
        //        .eTag(crossReference.getVersion().toString())
        //        .body(crossReferenceHateoas);
        // Think about how to handle if cross reference is to Record or class. Do we need
        // to specify this in the URL
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Create a (sub) File
    // POST [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-undermappe
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-undermappe/
    @ApiOperation(value = "Create a new File and associate it, with the given File identified by systemId, as a " +
            "(sub)File", notes = "Returns the newly created (sub)File after it was associated with a File and " +
            "persisted to the database", response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = FILE + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileHateoas.class),
            @ApiResponse(code = 201, message = FILE + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + FILE),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_SUB_FILE, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> createSubFileAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of the parent file",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "File",
                    value = "File to be (sub)File",
                    required = true)
            @RequestBody File file) throws NikitaException {
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        //return ResponseEntity.status(HttpStatus.CREATED)
        //        .eTag(createdFile.getVersion().toString())
        //        .body(fileHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a Comment to a File
    // POST [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-merknad
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-merknad/
    @ApiOperation(value = "Associates a Comment with a File identified by fileSystemId",
            notes = "Returns the File with the comment associated with it", response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = COMMENT + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileHateoas.class),
            @ApiResponse(code = 201, message = COMMENT + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + COMMENT),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_COMMENT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addCommentToFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of File to associate the Comment with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "Comment",
                    value = "comment",
                    required = true)
            @RequestBody Comment comment) throws NikitaException {
        //TODO: What do we return here? File + comment? comment?
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        //        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(comment.getVersion().toString())
//                .body(commentHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a Class to a File
    // POST [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-klasse
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-klasse/
    @ApiOperation(value = "Associates a Class with a File identified by fileSystemId",
            notes = "Returns the File with the Class associated with it", response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = CLASS + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileHateoas.class),
            @ApiResponse(code = 201, message = CLASS + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + CLASS),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_CLASS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addClassToFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of File to associate the Class with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "klass",
                    value = "Class",
                    required = true)
            @RequestBody Class klass) throws NikitaException {
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        //        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(createdClass.getVersion().toString())
//                .body(classHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a reference to a secondary Series associated with the File
    // POST [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-referanseArkivdel
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-referanseArkivdel/
    @ApiOperation(value = "Associates a Secondary Series with a File identified by fileSystemId",
            notes = "Returns the File after the secondary Series is successfully associated with it." +
                    "Note a secondary series allows a File to be associated with another Series.",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = CLASS + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileHateoas.class),
            @ApiResponse(code = 201, message = CLASS + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + CLASS),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_REFERENCE_SERIES, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addReferenceSeriesToFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of File to associate the secondary Series with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "Series",
                    value = "series",
                    required = true)
            @RequestBody Series series) throws NikitaException {
        //TODO: What do we return here? File ? maybe just 200 OK
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        //return ResponseEntity.status(HttpStatus.CREATED)
        //        .eTag(file.getVersion().toString())
        //       .body(fileHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Add a secondary Class to a File
    // POST [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-sekundaerklassifikasjon
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-sekundaerklassifikasjon/
    @ApiOperation(value = "Associates a Class with a File identified by fileSystemId as secondary Class",
            notes = "Returns the File with the Class associated with it. Note a File can only have one Class " +
                    "associated with it, but can have multiple secondary Class associated with it. An example" +
                    "is the use of K-Koder on case-handling and a secondary classification of person",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = COMMENT + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileHateoas.class),
            @ApiResponse(code = 201, message = COMMENT + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " + CLASS),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_SECONDARY_CLASSIFICATION, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> addReferenceToSecondaryClassToFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of File to associate the secondary Class with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "klass",
                    value = "Class",
                    required = true)
            @RequestBody Class klass) throws NikitaException {
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // API - All GET Requests (CRUD - READ)

    // Retrieve all Records associated with File identified by systemId
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/registrering
    // REL http://rel.kxml.no/noark5/v4/api/arkivstruktur/registrering/
    @ApiOperation(value = "Retrieve all Record associated with a File identified by systemId",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Record returned", response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + REGISTRATION,
            method = RequestMethod.GET)
    public ResponseEntity<RecordHateoas> findAllRecordsAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated Record",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {

        File file = fileService.findBySystemId(fileSystemId);
        if (file == null) {
            throw new NoarkEntityNotFoundException("Could not find File object with systemID " + fileSystemId);
        }
        RecordHateoas recordHateoas = new
                RecordHateoas(new ArrayList<> (file.getReferenceRecord()));
        recordHateoasHandler.addLinks(recordHateoas, request, new Authorisation());
        return new ResponseEntity<>(recordHateoas, HttpStatus.OK);
    }

    // Retrieve all BasicRecords associated with File identified by systemId
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/basisregistrering
    // REL http://rel.kxml.no/noark5/v4/api/arkivstruktur/basisregistrering/
    @ApiOperation(value = "Retrieve all BasicRecord associated with a File identified by systemId",
            response = BasicRecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord returned", response = BasicRecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + BASIC_RECORD,
            method = RequestMethod.GET)
    public ResponseEntity<String> findAllBasicRecordsAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated BasicRecord",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all BasicRecords associated with File identified by systemId
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/undermappe
    // REL http://rel.kxml.no/noark5/v4/api/arkivstruktur/undermappe/
    @ApiOperation(value = "Retrieve all (sub) File associated with a File identified by systemId",
            response = BasicRecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord returned", response = BasicRecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + SUB_FILE,
            method = RequestMethod.GET)
    public ResponseEntity<String> findAllSubFileAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve associated (sub)File",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Create a Record with default values
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-registrering
    @ApiOperation(value = "Create a Record with default values", response = Record.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Record returned", response = Record.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            NEW_RECORD, method = RequestMethod.GET)
    public ResponseEntity<RecordHateoas> createDefaultRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {

        Record defaultRecord = new Record();
        defaultRecord.setArchivedBy(TEST_USER_CASE_HANDLER_2);
        defaultRecord.setArchivedDate(new Date());
        RecordHateoas recordHateoas = new
                RecordHateoas(defaultRecord);
        recordHateoasHandler.addLinksOnNew(recordHateoas, request, new Authorisation());
        return new ResponseEntity<>(recordHateoas, HttpStatus.OK);
    }

    // Create a BasicRecord with default values
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/ny-basisregistrering
    @ApiOperation(value = "Create a BasicRecord with default values", response = BasicRecord.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord returned", response = BasicRecord.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            NEW_BASIC_RECORD, method = RequestMethod.GET)
    public ResponseEntity<BasicRecordHateoas> createDefaultBasicRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {

        BasicRecord defaultBasicRecord = new BasicRecord();
        defaultBasicRecord.setArchivedBy(TEST_USER_CASE_HANDLER_2);
        defaultBasicRecord.setArchivedDate(new Date());
        BasicRecordHateoas basicRecordHateoas = new
                BasicRecordHateoas(defaultBasicRecord);
        basicRecordHateoasHandler.addLinksOnNew(basicRecordHateoas, request, new Authorisation());
        return new ResponseEntity<>(basicRecordHateoas, HttpStatus.OK);
    }

    // Retrieve a file identified by a systemId
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}
    @ApiOperation(value = "Retrieves a single File entity given a systemId", response = File.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File returned", response = File.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<FileHateoas> findOneFileBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        File file = fileService.findBySystemId(fileSystemId);
        // TODO: If null return not found exception
        FileHateoas fileHateoas = new FileHateoas(file);
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(file.getVersion().toString())
                .body(fileHateoas);
    }

    // Retrieves all files
    // GET [contextPath][api]/arkivstruktur/mappe
    @ApiOperation(value = "Retrieves multiple File entities limited by ownership rights", notes = "The field skip" +
            "tells how many File rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File list found",
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<FileHateoas> findAllFiles(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        FileHateoas fileHateoas = new
                FileHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                fileService.findFileByOwnerPaginated(top, skip));
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return new ResponseEntity<>(fileHateoas, HttpStatus.OK);
    }

    // Retrieve all Comments associated with a File
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/merknad
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/merknad/
    @ApiOperation(value = "Retrieves all Comments associated with a File identified by a systemId",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File returned", response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + COMMENT,
            method = RequestMethod.GET)
    public ResponseEntity<String> findAllCommentsAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve comments for",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        //return new ResponseEntity<>(commentHateoas, HttpStatus.OK);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all CrossReference associated with a File
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/kryssreferanse
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/kryssreferanse/
    @ApiOperation(value = "Retrieves all CrossReference associated with a File identified by a systemId",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File returned", response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + CROSS_REFERENCE,
            method = RequestMethod.GET)
    public ResponseEntity<String> findAllCrossReferenceAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the File to retrieve CrossReferences for",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve the Class associated with a File
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/klasse
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/klasse/
    @ApiOperation(value = "Retrieves the Class associated with a File identified by a systemId",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File returned", response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + CLASS,
            method = RequestMethod.GET)
    public ResponseEntity<String> findClassAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the File to retrieve a Class for",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve the Series associated with a File
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/arkivdel
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkivdel/
    @ApiOperation(value = "Retrieves the Series associated with a File identified by a systemId",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File returned", response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + SERIES,
            method = RequestMethod.GET)
    public ResponseEntity<String> findSeriesAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the File to retrieve a Class for",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        //return ResponseEntity.status(HttpStatus.CREATED)
        //        .eTag(series.getVersion().toString())
        //        .body(seriesHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all Class associated with a File as secondary classification
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/sekundaerklassifikasjon
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/sekundaerklassifikasjon/
    @ApiOperation(value = "Retrieves all secondary Class associated with a File identified by a systemId",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File returned", response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + SECONDARY_CLASSIFICATION,
            method = RequestMethod.GET)
    public ResponseEntity<String> findSecondaryClassAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the File to retrieve secondary Class for",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        //return ResponseEntity.status(HttpStatus.CREATED)
        //        .eTag(klass.getVersion().toString())
        //        .body(classHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all secondary Series associated with a File
    // GET [contextPath][api]/arkivstruktur/mappe/{systemId}/referanseArkivdel
    // http://rel.kxml.no/noark5/v4/api/arkivstruktur/referanseArkivdel/
    @ApiOperation(value = "Retrieves all secondary Series associated with a File identified by a systemId",
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
    public ResponseEntity<String> findSecondarySeriesAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the File to retrieve secondary Class for",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // API - All PUT Requests (CRUD - UPDATE)

    // Update a File with given values
    // PUT [contextPath][api]/arkivstruktur/mappe/{systemId}
    @ApiOperation(value = "Updates a File identified by a given systemId", notes = "Returns the newly updated file",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileHateoas.class),
            @ApiResponse(code = 201, message = "File " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type File"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + FILE, method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> updateFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to update",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "File",
                    value = "Incoming file object",
                    required = true)
            @RequestBody File file) throws NikitaException {
        /*FileHateoas fileHateoas = new FileHateoas(fileService.updateFile(fileSystemId, file));
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return new ResponseEntity<>(fileHateoas, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(updatedFile.getVersion().toString())
                .body(fileHateoas);
        */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Finalise a File
    // PUT [contextPath][api]/arkivstruktur/mappe/{systemId}/avslutt-mappe
    // REL http://rel.kxml.no/noark5/v4/api/arkivstruktur/avslutt-mappe/
    @ApiOperation(value = "Updates a File identified by a given systemId", notes = "Returns the newly updated file",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileHateoas.class),
            @ApiResponse(code = 201, message = "File " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type File"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + FILE_END)
    public ResponseEntity<String> finaliseFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to update",
                    required = true)
            @PathVariable String fileSystemId) throws NikitaException {

        /*
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, ));
        FileHateoas fileHateoas = new FileHateoas(fileService.updateFile(fileSystemId, file));
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return new ResponseEntity<>(fileHateoas, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(finalisedFile.getVersion().toString())
                .body(fileHateoas);*/
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Expand a File to a CaseFile
    // PUT [contextPath][api]/arkivstruktur/mappe/{systemId}/utvid-til-saksmappe
    // REL http://rel.kxml.no/noark5/v4/api/arkivstruktur/utvid-til-saksmappe/
    @ApiOperation(value = "Expands a File identified by a systemId to a CaseFile", notes = "Returns the newly updated " +
            "CaseFile",
            response = CaseFileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 201, message = "CaseFile " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type File"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + FILE_EXPAND_TO_CASE_FILE)
    public ResponseEntity<String> expandFileToCaseFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to expand",
                    required = true)
            @PathVariable String fileSystemId) throws NikitaException {
        /*
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, ));
        FileHateoas fileHateoas = new FileHateoas(fileService.updateFile(fileSystemId, file));
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return new ResponseEntity<>(fileHateoas, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(expandedFile.getVersion().toString())
                .body(caseFileHateoas);
        */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Expand a File to a MeetingFile
    // PUT [contextPath][api]/arkivstruktur/mappe/{systemId}/utvid-til-moetemappe
    // REL http://rel.kxml.no/noark5/v4/api/arkivstruktur/utvid-til-moetemappe/
    // TODO: At implementation time, we are missing MeetingFileHateoas. Leaving as CaseFileHateoas
    // just to allow continued compilation
    @ApiOperation(value = "Expands a File identified by a systemId to a MeetingFile", notes = "Returns the newly " +
            "updated MeetingFile. Note TODO in FileHateoasController. Fix this before swagger is published",
            response = CaseFileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 201, message = "CaseFile " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type File"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + FILE_EXPAND_TO_MEETING_FILE)
    public ResponseEntity<String> expandFileToMeetingFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to expand",
                    required = true)
            @PathVariable String fileSystemId) throws NikitaException {
        /* applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, ));
        FileHateoas fileHateoas = new FileHateoas(fileService.updateFile(fileSystemId, file));
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return new ResponseEntity<>(fileHateoas, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(expandedFile.getVersion().toString())
                .body(meetingFileHateoas);
        */
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }
}
