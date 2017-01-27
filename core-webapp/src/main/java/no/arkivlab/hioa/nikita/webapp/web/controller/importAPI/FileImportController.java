package no.arkivlab.hioa.nikita.webapp.web.controller.importAPI;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;
import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.hateoas.BasicRecordHateoas;
import nikita.model.noark5.v4.hateoas.RecordHateoas;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IFileImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FILE;

/**
 * Created by tsodring on 23/11/16.
 *
 * An example request to create a file object looks like the following:
 *
 *  http://localhost:8092/noark5v4/import-api/arkivstruktur/arkivdel/eaa70f90-cd70-4c46-b341-fde3059d2ba5/ny-mappe"
 *
 * This will locate the series object with a systemId eaa70f90-cd70-4c46-b341-fde3059d2ba5 and create and associate a
 * file object detailed in the request body with the identified  series
 *
 * You have to know the systemId of the "parent" object so you can associate the object with the correct node
 * of the fonds structure
 */

@RestController
@RequestMapping(value = IMPORT_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE)
@Api(value = "File", description = "CRUD operations on file")
public class FileImportController {

    @Autowired
    IFileImportService fileImportService;

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
            SLASH + NEW_RECORD)
    public ResponseEntity<RecordHateoas> createRecordAssociatedWithFile(
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the record with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "Record",
                    value = "Incoming record object",
                    required = true)
            @RequestBody Record record) throws NikitaException {
        RecordHateoas recordHateoas =
                new RecordHateoas(fileImportService.createRecordAssociatedWithFile(fileSystemId, record));
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
            SLASH + NEW_BASIC_RECORD)
    public ResponseEntity<BasicRecordHateoas> createBasicRecordAssociatedWithFile(
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the basicRecord with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "BasicRecord",
                    value = "Incoming basicRecord object",
                    required = true)
            @RequestBody BasicRecord basicRecord) throws NikitaException {
        BasicRecordHateoas basicRecordHateoas =
                new BasicRecordHateoas(fileImportService.createBasicRecordAssociatedWithFile(
                        fileSystemId, basicRecord));
        return new ResponseEntity<>(basicRecordHateoas, HttpStatus.CREATED);
    }
}
