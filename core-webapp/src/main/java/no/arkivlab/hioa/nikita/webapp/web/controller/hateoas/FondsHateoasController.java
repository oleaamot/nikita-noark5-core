package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.hateoas.FondsCreatorHateoas;
import nikita.model.noark5.v4.hateoas.FondsHateoas;
import nikita.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsCreatorHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.ISeriesHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.elasticsearch.ElasticsearchQueries;
import org.hibernate.search.query.engine.spi.QueryDescriptor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@SuppressWarnings("unchecked")
public class FondsHateoasController {

    private EntityManager entityManager;
    private IFondsService fondsService;
    private ISeriesService seriesService;
    private IFondsHateoasHandler fondsHateoasHandler;
    private IFondsCreatorHateoasHandler fondsCreatorHateoasHandler;
    private ISeriesHateoasHandler seriesHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public FondsHateoasController(EntityManager entityManager,
                                  IFondsService fondsService,
                                  ISeriesService seriesService,
                                  IFondsHateoasHandler fondsHateoasHandler,
                                  IFondsCreatorHateoasHandler fondsCreatorHateoasHandler,
                                  ISeriesHateoasHandler seriesHateoasHandler,
                                  ApplicationEventPublisher applicationEventPublisher) {
        this.entityManager = entityManager;
        this.fondsService = fondsService;
        this.seriesService = seriesService;
        this.fondsHateoasHandler = fondsHateoasHandler;
        this.fondsCreatorHateoasHandler = fondsCreatorHateoasHandler;
        this.seriesHateoasHandler = seriesHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

// API - All POST Requests (CRUD - CREATE)

    // Create a Fonds
    // POST [contextPath][api]/arkivstruktur/arkiv
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
    @RequestMapping(method = RequestMethod.POST, value = NEW_FONDS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsHateoas> createFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds) throws NikitaException {
        Fonds createdFonds = fondsService.createNewFonds(fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(createdFonds);
        fondsHateoasHandler.addLinks(fondsHateoas, request, new Authorisation());
        response.setHeader(ETAG, fonds.getVersion().toString());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdFonds));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdFonds.getVersion().toString())
                .body(fondsHateoas);
    }

    // Create a sub-fonds and associate it with the Fonds identified by systemId
    // POST [contextPath][api]/arkivstruktur/arkiv/{systemId}/ny-underarkiv
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
            "fondsSystemId" + RIGHT_PARENTHESIS + SLASH + NEW_SUB_FONDS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsHateoas> createFondsAssociatedWithFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fondsSystemId",
                    value = "systemId of parent fonds to associate the fonds with.",
                    required = true)
            @PathVariable String fondsSystemId,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds)
            throws NikitaException {
        Fonds createdFonds = fondsService.createFondsAssociatedWithFonds(fondsSystemId, fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(createdFonds);
        fondsHateoasHandler.addLinks(fondsHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdFonds));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdFonds.getVersion().toString())
                .body(fondsHateoas);
    }

    // Create a Series and associate it with the Fonds identified by systemId
    // POST [contextPath][api]/arkivstruktur/arkiv/{systemId}/ny-arkivdel
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
            "fondsSystemId" + RIGHT_PARENTHESIS + SLASH + NEW_SERIES, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<SeriesHateoas> createSeriesAssociatedWithFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
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
        seriesHateoasHandler.addLinks(seriesHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, seriesCreated));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(seriesCreated.getVersion().toString())
                .body(seriesHateoas);
    }

    // Create a FondsCreator and associate it with the Fonds identified by systemId
    // POST [contextPath][api]/arkivstruktur/arkiv/{systemId}/ny-arkivskaper
    @ApiOperation(value = "Persists a FondsCreator associated with the given Fonds systemId", notes = "Returns" +
            " the newly created FondsCreator after it was associated with a Fonds and persisted to the " +
            "database", response = FondsCreatorHateoas.class)
    @SuppressWarnings("unchecked")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsCreator " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsCreatorHateoas.class),
            @ApiResponse(code = 201, message = "FondsCreator " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsCreatorHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type FondsCreator"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = FONDS + SLASH + LEFT_PARENTHESIS +
            "fondsSystemId" + RIGHT_PARENTHESIS + SLASH + NEW_FONDS_CREATOR, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsCreatorHateoas> createFondsCreatorAssociatedWithFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "fondsSystemId",
                    value = "systemId of fonds to associate the series with.",
                    required = true)
            @PathVariable String fondsSystemId,
            @ApiParam(name = "fondsCreator",
                    value = "Incoming fondsCreator object",
                    required = true)
            @RequestBody FondsCreator fondsCreator)
            throws NikitaException {
        fondsService.createFondsCreatorAssociatedWithFonds(fondsSystemId, fondsCreator);
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas(fondsCreator);
        fondsCreatorHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());
        response.setHeader(ETAG, fondsCreator.getVersion().toString());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, fondsCreator));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(fondsCreator.getVersion().toString())
                .body(fondsCreatorHateoas);

    }

    // API - All GET Requests (CRUD - READ)
    // Get single fonds identified by systemId
    // GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/
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
    @SuppressWarnings("unchecked")
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
        fondsHateoasHandler.addLinks(fondsHateoas, request, new Authorisation());

        return ResponseEntity.status(HttpStatus.OK)
                .eTag(fonds.getVersion().toString())
                .body(fondsHateoas);
    }

    // Create a Series object with default values
    //GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/ny-arkivdel
    @ApiOperation(value = "Create a Series with default values", response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = Series.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH +
            NEW_SERIES, method = RequestMethod.GET)
    public ResponseEntity<SeriesHateoas> createDefaultSeries(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {

        Series defaultSeries = new Series();
        defaultSeries.setTitle(TEST_TITLE);
        defaultSeries.setDescription(TEST_DESCRIPTION);
        defaultSeries.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(defaultSeries);
        seriesHateoasHandler.addLinksOnNew(seriesHateoas, request, new Authorisation());
        return new ResponseEntity<>(seriesHateoas, HttpStatus.OK);
    }

    // Get all fondsCreators associated with fonds identified by systemId
    // GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/arkivskaper/
    @ApiOperation(value = "Retrieves the fondsCreators associated with a Fonds identified by a systemId",
            response = FondsCreator.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsCreator returned", response = FondsCreator.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + FONDS_CREATOR +
            SLASH, method = RequestMethod.GET)
    public ResponseEntity<String> findFondsCreatorAssociatedWithFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of fonds to retrieve.",
                    required = true)
            @PathVariable("systemID") final String fondsSystemId) {

        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
        //return ResponseEntity.status(HttpStatus.OK)
        //        .eTag(fonds.getVersion().toString())
        //        .body(fondsHateoas);
    }

    // Get all Series associated with Fonds identified by systemId
    // GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/arkivdel/
    @ApiOperation(value = "Retrieves the Series associated with a Fonds identified by a systemId",
            response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = Series.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + SERIES +
            SLASH, method = RequestMethod.GET)
    public ResponseEntity<SeriesHateoas> findSeriesAssociatedWithFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of Fonds that has Series associated with it.",
                    required = true)
            @PathVariable("systemID") final String fondsSystemId) {

        Fonds fonds = fondsService.findBySystemId(fondsSystemId);
        if (fonds == null) {
            throw new NoarkEntityNotFoundException("Could not find series object with systemID " + fondsSystemId);
        }
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(new ArrayList<>(fonds.getReferenceSeries()));
        seriesHateoasHandler.addLinks(seriesHateoas, request, new Authorisation());
        return new ResponseEntity<>(seriesHateoas, HttpStatus.OK);
    }

    // Get all Sub-fonds associated with a Fonds identified by systemId
    // GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/underarkiv/
    @ApiOperation(value = "Retrieves the (sub)Fonds associated with a Fonds identified by a systemId",
            response = Fonds.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds returned", response = Fonds.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + SUB_FONDS +
            SLASH, method = RequestMethod.GET)
    public ResponseEntity<String> findSubfondsAssociatedWithFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of parent Fonds.",
                    required = true)
            @PathVariable("systemID") final String fondsSystemId) {

        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
        //return new ResponseEntity<> (fondsHateoas, HttpStatus.CREATED);
    }

    // Get all fonds
    // GET [contextPath][api]/arkivstruktur/arkiv/
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
    @RequestMapping(method = RequestMethod.GET, value = FONDS)
    public ResponseEntity<FondsHateoas> findAllFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {
        FondsHateoas fondsHateoas = new
                FondsHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                fondsService.findFondsByOwnerPaginated(top, skip));
        fondsHateoasHandler.addLinks(fondsHateoas, request, new Authorisation());

        return new ResponseEntity<>(fondsHateoas, HttpStatus.OK);
    }

    // Find all fonds using elasticsearch ... This is experimental and not part of the standard
    // No swagger documentation on this. If we decide to drop db for es, then all re
    // GET [contextPath][api]/arkivstruktur/arkiv/all/
    @RequestMapping(method = RequestMethod.GET, value = FONDS + SLASH + "all" + SLASH)
    public ResponseEntity<FondsHateoas> findAllFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "filter", required = false) String filter) {

        Session session = entityManager.unwrap(Session.class);

        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryDescriptor query = ElasticsearchQueries.fromQueryString("title:test fonds");
        List<Fonds> result = fullTextSession.createFullTextQuery(query, Fonds.class).list();

        FondsHateoas fondsHateoas = new
                FondsHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList) result);
        fondsHateoasHandler.addLinks(fondsHateoas, request, new Authorisation());
        return new ResponseEntity<>(fondsHateoas, HttpStatus.OK);
    }

    // Create a suggested Fonds (like a template) object with default values (nothing persisted)
    // GET [contextPath][api]/arkivstruktur/ny-arkiv
    @ApiOperation(value = "Suggests the contents of a new Fonds object", notes = "Returns a pre-filled Fonds object" +
            " with values relevant for the logged-in user", response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = {NEW_FONDS, FONDS_CREATOR + SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_FONDS})
    public ResponseEntity<FondsHateoas> getFondsTemplate(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response
    ) throws NikitaException {
        Fonds suggestedFonds = new Fonds();
        // TODO: This should be replaced with configurable data based on whoever is logged in
        //       Currently just returns the test values
        suggestedFonds.setTitle(TEST_TITLE);
        suggestedFonds.setDescription(TEST_DESCRIPTION);
        suggestedFonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);
        FondsHateoas fondsHateoas = new FondsHateoas(suggestedFonds);
        fondsHateoasHandler.addLinksOnNew(fondsHateoas, request, new Authorisation());
        return new ResponseEntity<>(fondsHateoas, HttpStatus.OK);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a Fonds
    // PUT [contextPath][api]/arkivstruktur/arkiv
    @ApiOperation(value = "Updates a Fonds object", notes = "Returns the newly" +
            " update Fonds object after it is persisted to the database", response = FondsHateoas.class)
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
    @RequestMapping(method = RequestMethod.PUT, value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsHateoas> updateFonds(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of parent fonds to associate the fonds with.",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds) throws NikitaException {
        Fonds updatedFonds = fondsService.handleUpdate(systemID, Long.parseLong(request.getHeader(ETAG)), fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(updatedFonds);
        fondsHateoasHandler.addLinks(fondsHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedFonds));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(updatedFonds.getVersion().toString())
                .body(fondsHateoas);
    }
}
