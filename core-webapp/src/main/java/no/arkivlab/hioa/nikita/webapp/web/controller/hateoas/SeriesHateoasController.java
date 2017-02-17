package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;
import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.hateoas.CaseFileHateoas;
import nikita.model.noark5.v4.hateoas.FileHateoas;
import nikita.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.ICaseFileHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFileHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.ISeriesHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.SERIES;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController

@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES,
        produces = {NOARK5_V4_CONTENT_TYPE})
@Api(value = "SeriesController", description = "Contains CRUD operations for Series. Create operations are only for " +
        "entities that can be associated with a series e.g. File / ClassificationSystem. Update and delete operations" +
        " are on individual series entities identified by systemId. Read operations are either on individual series" +
        "entities or pageable iterable sets of series")
public class SeriesHateoasController {

    @Autowired
    ISeriesService seriesService;

    @Autowired
    ISeriesHateoasHandler seriesHateoasHandler;

    @Autowired
    ICaseFileHateoasHandler caseFileHateoasHandler;

    @Autowired
    IFileHateoasHandler fileHateoasHandler;

//    @Value("${nikita-noark5-core.pagination.maxPageSize}")
  //  Integer maxPageSize;

    String uri;
    String hrefSelf;

    public SeriesHateoasController() {

    }

    // API - All POST Requests (CRUD - CREATE)

    // ny-mappe / new-file
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
            SLASH + NEW_FILE, consumes = {NOARK5_V4_CONTENT_TYPE})
    public ResponseEntity<FileHateoas> createFileAssociatedWithSeries(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "seriesSystemId",
                    value = "systemId of series to associate the caseFile with",
                    required = true)
            @PathVariable String seriesSystemId,
            @ApiParam(name = "File",
                    value = "Incoming file object",
                    required = true)
            @RequestBody File file) throws NikitaException {
        FileHateoas fileHateoas = new FileHateoas(seriesService.createFileAssociatedWithSeries(seriesSystemId, file));
        fileHateoasHandler.addLinks(fileHateoas, request, new Authorisation());
        return new ResponseEntity<>(fileHateoas, HttpStatus.CREATED);
    }

    // ny-saksmappe / new-casefile
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
            RIGHT_PARENTHESIS + SLASH + NEW_CASE_FILE, consumes = {NOARK5_V4_CONTENT_TYPE})
    public ResponseEntity<CaseFileHateoas>
    createCaseFileAssociatedWithSeries(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "seriesSystemId",
                    value = "systemId of series to associate the caseFile with",
                    required = true)
            @PathVariable String seriesSystemId,
            @ApiParam(name = "caseFile",
                    value = "Incoming caseFile object",
                    required = true)
            @RequestBody CaseFile caseFile) throws NikitaException {

        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas(seriesService.createCaseFileAssociatedWithSeries(seriesSystemId, caseFile));
        caseFileHateoasHandler.addLinks(caseFileHateoas, request, new Authorisation());
        return new ResponseEntity<>(caseFileHateoas, HttpStatus.CREATED);
    }

    // associate series with reference to successor
    @ApiOperation(value = "Associates a Series object as successor Series systemId", notes = "Returns ", response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 201, message = "File " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CaseFile"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = LEFT_PARENTHESIS + "seriesSystemId" +
            RIGHT_PARENTHESIS + SLASH + SERIES_ASSOCIATE_AS_SUCCESSOR, consumes = {NOARK5_V4_CONTENT_TYPE})
    public ResponseEntity<String> associateSeriesWithSeriesSuccessor(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "seriesSystemId",
                    value = "systemId of series to associate the caseFile with",
                    required = true)
            @PathVariable String seriesSystemId,
            @ApiParam(name = "series",
                    value = "Incoming series object",
                    required = true)
            @RequestBody Series seriesSuccessor) throws NikitaException {

//        SeriesHateoas seriesHateoas = new
//                SeriesHateoas(seriesService.associateSeriesWithSeriesSuccessor(seriesSystemId, caseFile));
//        caseFileHateoasHandler.addLinks(seriesHateoas, request, new Authorisation());
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }



    // API - All GET Requests (CRUD - READ)
    @ApiOperation(value = "Retrieves a single Series entity given a systemId", response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = Series.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<SeriesHateoas> findOneSeriesbySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String seriesSystemId) {
        Series series = seriesService.findBySystemId(seriesSystemId);
        if (series == null) {
            throw new NoarkEntityNotFoundException("Could not find series object with systemID " + seriesSystemId);
        }
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(series);
        seriesHateoasHandler.addLinks(seriesHateoas, request, new Authorisation());
        return new ResponseEntity<>(seriesHateoas, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieves multiple Series entities limited by ownership rights", notes = "The field skip" +
            "tells how many Series rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series list found",
                    response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<SeriesHateoas> findAllSeries(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        SeriesHateoas seriesHateoas = new
                SeriesHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                seriesService.findSeriesByOwnerPaginated(top, skip));
        seriesHateoasHandler.addLinksOnRead(seriesHateoas, request, new Authorisation());
        return new ResponseEntity<>(seriesHateoas, HttpStatus.OK);
    }
}
