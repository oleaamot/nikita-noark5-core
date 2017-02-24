package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.hateoas.BasicRecordHateoas;
import nikita.model.noark5.v4.hateoas.FileHateoas;
import nikita.model.noark5.v4.hateoas.RecordHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IBasicRecordHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFileHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRecordHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FILE;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE,
        produces = {NOARK5_V4_CONTENT_TYPE})
public class FileHateoasController {

    @Autowired
    IFileService fileService;

    @Autowired
    IFileHateoasHandler fileHateoasHandler;

    @Autowired
    IRecordHateoasHandler recordHateoasHandler;

    @Autowired
    IBasicRecordHateoasHandler basicRecordHateoasHandler;

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a Record object associated with the given Series systemId",
            notes = "Returns the newly created record object after it was associated with a File object and " +
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
    @RequestMapping(method = RequestMethod.POST, value = LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_RECORD, consumes = {NOARK5_V4_CONTENT_TYPE})
    public ResponseEntity<RecordHateoas> createRecordAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the record with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "Record",
                    value = "Incoming record object",
                    required = true)
            @RequestBody Record record) throws NikitaException {
        RecordHateoas recordHateoas =
                new RecordHateoas(fileService.createRecordAssociatedWithFile(fileSystemId, record));
        recordHateoasHandler.addLinks(recordHateoas, request, new Authorisation());
        return new ResponseEntity<>(recordHateoas, HttpStatus.CREATED);
    }
    @ApiOperation(value = "Persists a BasicRecord object associated with the given Series systemId",
            notes = "Returns the newly created basicRecord object after it was associated with a File object and " +
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
    @RequestMapping(method = RequestMethod.POST, value = LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_BASIC_RECORD, consumes = {NOARK5_V4_CONTENT_TYPE})
    public ResponseEntity<BasicRecordHateoas> createBasicRecordAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the basicRecord with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "BasicRecord",
                    value = "Incoming basicRecord object",
                    required = true)
            @RequestBody BasicRecord basicRecord) throws NikitaException {
        BasicRecordHateoas basicRecordHateoas =
                new BasicRecordHateoas(fileService.createBasicRecordAssociatedWithFile(fileSystemId, basicRecord));
        basicRecordHateoasHandler.addLinks(basicRecordHateoas, request, new Authorisation());
        return new ResponseEntity<>(basicRecordHateoas, HttpStatus.CREATED);
    }
    
    // API - All GET Requests (CRUD - READ)

    // Create a Record object with default values
    //GET [contextPath][api]/arkivstruktur/mappe/SYSTEM_ID/ny-registrering
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

    // Create a BasicRecord object with default values
    //GET [contextPath][api]/arkivstruktur/mappe/SYSTEM_ID/ny-basisregistrering
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

    @ApiOperation(value = "Retrieves a single File entity given a systemId", response = File.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File returned", response = File.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<FileHateoas> findOneFilebySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the file to retrieve",
                    required = true)
            @PathVariable("systemID") final String fileSystemId) {
        FileHateoas fileHateoas = new
                FileHateoas(fileService.findBySystemId(fileSystemId));
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return new ResponseEntity<>(fileHateoas, HttpStatus.CREATED);
    }

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
    public ResponseEntity<FileHateoas> findAllFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        FileHateoas fileHateoas = new
                FileHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                fileService.findFileByOwnerPaginated(top, skip));
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return new ResponseEntity<>(fileHateoas, HttpStatus.OK);
    }
}
