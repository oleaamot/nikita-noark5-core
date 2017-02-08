package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.hateoas.FondsHateoas;
import nikita.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.elasticsearch.ElasticsearchQueries;
import org.hibernate.search.query.engine.spi.QueryDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FONDS;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE})
public class FondsHateoasController {

    EntityManager entityManager;
    IFondsService fondsService;
    ISeriesService seriesService;
    IFondsHateoasHandler fondsHateoasHandler;

    @Autowired
    public FondsHateoasController(EntityManager entityManager, IFondsService fondsService,
                                  ISeriesService seriesService, IFondsHateoasHandler fondsHateoasHandler) {
        this.entityManager = entityManager;
        this.fondsService = fondsService;
        this.seriesService = seriesService;
        this.fondsHateoasHandler = fondsHateoasHandler;
    }

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
    @RequestMapping(method = RequestMethod.POST, value = NEW_FONDS, consumes = {NOARK5_V4_CONTENT_TYPE})
    public ResponseEntity<FondsHateoas> createFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds)  throws NikitaException {
        //Authorisation authorisation = new Authorisation();
        //SecurityContextHolder.getContext().getAuthentication().getName();
        Fonds createdFonds = fondsService.createNewFonds(fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(createdFonds);
        fondsHateoasHandler.addLinksOnCreate(fondsHateoas, request, new Authorisation());
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
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMTNED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = FONDS + SLASH + LEFT_PARENTHESIS +
            "fondsSystemId" + RIGHT_PARENTHESIS + SLASH + NEW_FONDS, consumes = {NOARK5_V4_CONTENT_TYPE})
    public ResponseEntity<FondsHateoas> createFondsAssociatedWithFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "parentFondsSystemId",
                    value = "systemId of parent fonds to associate the fonds with.",
                    required = true)
            @PathVariable String parentFondsSystemId,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds)
            throws NikitaException {
        Fonds createdFonds = fondsService.createFondsAssociatedWithFonds(parentFondsSystemId, fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(createdFonds);
        fondsHateoasHandler.addLinksOnCreate(fondsHateoas, request, new Authorisation());

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
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMTNED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = FONDS + SLASH + LEFT_PARENTHESIS +
            "fondsSystemId" + RIGHT_PARENTHESIS + SLASH + NEW_SERIES, consumes = {NOARK5_V4_CONTENT_TYPE})
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
        Series seriesCreated = fondsService.createSeriesAssociatedWithFonds(fondsSystemId, series);
        SeriesHateoas seriesHateoas = new SeriesHateoas(seriesCreated);
        return new ResponseEntity<> (seriesHateoas, HttpStatus.CREATED);
    }

    // API - All GET Requests (CRUD - READ)
    @ApiOperation(value = "Retrieves a single fonds entity given a systemId", response = Fonds.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds returned", response = Fonds.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<FondsHateoas> findOne(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of fonds to retrieve.",
                    required = true)
            @PathVariable("systemID") final String fondsSystemId) {
        Fonds fonds = fondsService.findBySystemId(fondsSystemId);
        if (fonds == null) {
            throw new NoarkEntityNotFoundException("Could not find fonds object with systemID " + fondsSystemId);
        }
        FondsHateoas fondsHateoas = new FondsHateoas(fonds);
        fondsHateoasHandler.addLinksOnRead(fondsHateoas, request, new Authorisation());
        return new ResponseEntity<> (fondsHateoas, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieves multiple Fonds entities limited by ownership rights", notes = "The field skip" +
            "tells how many Fonds rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds found",
                    response = FondsHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = FONDS + SLASH)
    public ResponseEntity<FondsHateoas> findAllFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {
        FondsHateoas fondsHateoas = new
                FondsHateoas(fondsService.findFondsByOwnerPaginated(top, skip));
        fondsHateoasHandler.addLinksOnRead(fondsHateoas, request, new Authorisation());

        return new ResponseEntity<>(fondsHateoas, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = FONDS + SLASH + "all" + SLASH)
    public ResponseEntity<FondsHateoas> findAllFonds(
            @RequestParam(name = "filter", required = false) String filter) {

        Session session = entityManager.unwrap(Session.class);

        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryDescriptor query = ElasticsearchQueries.fromQueryString("title:test fonds");
        List<Fonds> result = fullTextSession.createFullTextQuery(query, Fonds.class).list();

        FondsHateoas fondsHateoas = new
                FondsHateoas(result);
        return new ResponseEntity<>(fondsHateoas, HttpStatus.OK);
    }
}
