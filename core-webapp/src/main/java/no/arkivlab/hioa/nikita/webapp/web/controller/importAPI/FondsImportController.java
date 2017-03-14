package no.arkivlab.hioa.nikita.webapp.web.controller.importAPI;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.hateoas.FondsHateoas;
import nikita.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IFondsImportService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.ISeriesImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FONDS;

@RestController
@RequestMapping(value = IMPORT_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class FondsImportController {

    @Autowired
    IFondsImportService fondsImportService;

    @Autowired
    ISeriesImportService seriesImportService;

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a Fonds object", notes = "Returns the newly" +
            " created Fonds object after it is persisted to the database", response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 201, message = "Fonds " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Fonds"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = NEW_FONDS)
    public ResponseEntity<FondsHateoas> createFonds(
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds)  throws NikitaException {
        Fonds createdFonds = fondsImportService.createNewFonds(fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(createdFonds);
        return new ResponseEntity<> (fondsHateoas, HttpStatus.CREATED);
    }

    // TODO: Doublecheck this logic
    @ApiOperation(value = "Persists a child Fonds object", notes = "Returns the newly" +
            " created child Fonds object after it is persisted to the database", response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 201, message = "Fonds " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Fonds"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = FONDS + SLASH + LEFT_PARENTHESIS +
            "fondsSystemId" + RIGHT_PARENTHESIS + SLASH
            + NEW_FONDS)
    public ResponseEntity<FondsHateoas> createFondsAssociatedWithFonds(
            @ApiParam(name = "parentFondsSystemId",
                    value = "systemId of parent fonds to associate the fonds with.",
                    required = true)
            @PathVariable String parentFondsSystemId,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds)
            throws NikitaException {
        Fonds createdFonds = fondsImportService.createFondsAssociatedWithFonds(parentFondsSystemId, fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(createdFonds);
        return new ResponseEntity<> (fondsHateoas, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Persists a Series object associated with the given Fonds systemId", notes = "Returns" +
            " the newly created Series object after it was associated with a Fonds object and persisted to the " +
            "database", response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 201, message = "Fonds " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Series"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = FONDS + SLASH + LEFT_PARENTHESIS +
            "fondsSystemId" + RIGHT_PARENTHESIS + SLASH
            + NEW_SERIES)
    public ResponseEntity<SeriesHateoas> createSeriesAssociatedWithFonds(
            @ApiParam(name = "fondsSystemId",
                    value = "systemId of fonds to associate the series with.",
                    required = true)
            @PathVariable String fondsSystemId,
            @ApiParam(name = "series",
                    value = "Incoming series object",
                    required = true)
            @RequestBody Series series)
            throws NikitaException {
        Series seriesCreated = fondsImportService.createSeriesAssociatedWithFonds(fondsSystemId, series);
        SeriesHateoas seriesHateoas = new SeriesHateoas(seriesCreated);
        return new ResponseEntity<> (seriesHateoas, HttpStatus.CREATED);
    }
}
