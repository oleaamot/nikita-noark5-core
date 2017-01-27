package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;
import nikita.config.HATEOASConstants;
import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.hateoas.CaseFileHateoas;
import nikita.model.noark5.v4.hateoas.FileHateoas;
import nikita.util.exceptions.NikitaException;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.hateoas.SeriesHateoas;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CASE_FILE;
import static nikita.config.N5ResourceMappings.SERIES;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES)
@Api(value = "SeriesController", description = "Contains CRUD operations for Series. Create operations are only for " +
        "entities that can be associated with a series e.g. File / ClassificationSystem. Update and delete operations" +
        " are on individual series entities identified by systemId. Read operations are either on individual series" +
        "entities or pageable iterable sets of series")
public class SeriesHateoasController {

    @Autowired
    ISeriesService seriesService;

//    @Value("${nikita-noark5-core.pagination.maxPageSize}")
  //  Integer maxPageSize;

    String uri;
    String hrefSelf;

    public SeriesHateoasController() {

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
        FileHateoas fileHateoas = new FileHateoas(seriesService.createFileAssociatedWithSeries(seriesSystemId, file));
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
                CaseFileHateoas(seriesService.createCaseFileAssociatedWithSeries(seriesSystemId, caseFile));

        // Looking at adding in the Hateoas links. Just proof of concept to have a reference point will be reimplemented
        ArrayList <Link> links  = (ArrayList <Link>) caseFileHateoas.getLinks();
        uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        hrefSelf = uri + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH +
                SLASH;
        String href = hrefSelf + CASE_FILE + SLASH + caseFile.getSystemId();
        links.add(new Link(HATEOASConstants.SELF, href, true));
        return new ResponseEntity<>(caseFileHateoas, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieves multiple Series entities limited by ownership rights", notes = "The field skip" +
            "tells how many Series rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = SeriesHateoas.class)
    @ApiResponses(value = {
            // Probably going to be a SeriesHateoasIterator or similar
            @ApiResponse(code = 200, message = "File " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<SeriesHateoas> findAllSeries(
            @RequestParam("top") final int top,
            @RequestParam("skip") final int skip) {

        SeriesHateoas seriesHateoas = new
                SeriesHateoas(seriesService.findSeriesByOwnerPaginated(top, skip));

        /* We need some kind of class to handle odata requirements in a more generic fashion
          This is just temp to get an idea of what it looks like.
        if (top > maxPageSize) {

        }
        else {

        }
        */
        return new ResponseEntity<>(seriesHateoas, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a single Series entity given a systemId", response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = Series.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = "/{systemID}", method = RequestMethod.GET)
    public ResponseEntity<SeriesHateoas> findOneSeriesbySystemId(
            @ApiParam(name = "systemID",
                    value = "systemID of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String seriesSystemId) {
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(seriesService.findBySystemId(seriesSystemId));
        return new ResponseEntity<>(seriesHateoas, HttpStatus.CREATED);
    }
}
