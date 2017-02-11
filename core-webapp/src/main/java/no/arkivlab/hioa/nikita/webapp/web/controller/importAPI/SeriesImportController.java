package no.arkivlab.hioa.nikita.webapp.web.controller.importAPI;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;
import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.hateoas.CaseFileHateoas;
import nikita.model.noark5.v4.hateoas.FileHateoas;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.ISeriesImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.SERIES;

@RestController
@RequestMapping(value = IMPORT_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES)
@Api(value = "SeriesController", description = "Contains CRUD operations for Series. Create operations are only for " +
        "entities that can be associated with a series e.g. File / ClassificationSystem. Update and delete operations" +
        " are on individual series entities identified by systemId. Read operations are either on individual series" +
        "entities or pageable iterable sets of series")
public class SeriesImportController {

    @Autowired
    ISeriesImportService seriesImportService;

    // TODO: Trying to get this to get a value, but it's not working. It just throws an exception
    // Revisit this!
    //    @Value("${nikita-noark5-core.pagination.maxPageSize}")
    //  Integer maxPageSize;

    String uri;
    String hrefSelf;

    public SeriesImportController() {

    }

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a File object associated with the given Series systemId", notes = "Returns the " +
            "newly created file object after it was associated with a Series object and persisted to the database",
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
    @RequestMapping(method = RequestMethod.POST, value = LEFT_PARENTHESIS + "seriesSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_FILE)
    public ResponseEntity<FileHateoas> createFileAssociatedWithSeries(
            @ApiParam(name = "seriesSystemId",
                    value = "systemId of series to associate the caseFile with",
                    required = true)
            @PathVariable String seriesSystemId,
            @ApiParam(name = "File",
                    value = "Incoming file object",
                    required = true)
            @RequestBody File file) throws NikitaException {
        FileHateoas fileHateoas = new FileHateoas(seriesImportService.createFileAssociatedWithSeries(seriesSystemId, file));
        return new ResponseEntity<>(fileHateoas, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Persists a CaseFile object associated with the given Series systemId", notes = "Returns " +
            "the newly created caseFile object after it was associated with a Series object and persisted to " +
            "the database", response = CaseFileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 201, message = "File " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CaseFile"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = LEFT_PARENTHESIS + "seriesSystemId" +
            RIGHT_PARENTHESIS + SLASH + NEW_CASE_FILE)
    public ResponseEntity<CaseFileHateoas>
    createCaseFileAssociatedWithSeries(
            @ApiParam(name = "seriesSystemId",
                    value = "systemId of series to associate the caseFile with",
                    required = true)
            @PathVariable String seriesSystemId,
            @ApiParam(name = "caseFile",
                    value = "Incoming caseFile object",
                    required = true)
            @RequestBody CaseFile caseFile) throws NikitaException {

        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas(seriesImportService.createCaseFileAssociatedWithSeries(seriesSystemId, caseFile));

        // Looking at adding in the Hateoas links. Just proof of concept to have a reference point will be reimplemented


        return new ResponseEntity<>(caseFileHateoas, HttpStatus.CREATED);
    }
}
