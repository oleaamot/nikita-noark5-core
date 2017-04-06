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
import nikita.model.noark5.v4.hateoas.FondsCreatorHateoas;
import nikita.model.noark5.v4.hateoas.FondsHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsCreatorHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsCreatorService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class FondsCreatorHateoasController {

    private IFondsCreatorService fondsCreatorService;
    private IFondsService fondsService;
    private IFondsCreatorHateoasHandler fondsCreatorHateoasHandler;
    private IFondsHateoasHandler fondsHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public FondsCreatorHateoasController(IFondsCreatorService fondsCreatorService,
                                         IFondsService fondsService,
                                         IFondsCreatorHateoasHandler fondsCreatorHateoasHandler,
                                         IFondsHateoasHandler fondsHateoasHandler,
                                         ApplicationEventPublisher applicationEventPublisher) {
        this.fondsCreatorService = fondsCreatorService;
        this.fondsService = fondsService;
        this.fondsCreatorHateoasHandler = fondsCreatorHateoasHandler;
        this.fondsHateoasHandler = fondsHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All POST Requests (CRUD - CREATE)

    // Create a new FondsCreator
    // POST [contextPath][api]/arkivstruktur/ny-arkivskaper
    @ApiOperation(value = "Persists a FondsCreator object", notes = "Returns the newly" +
            " created FondsCreator object after it is persisted to the database", response = FondsCreatorHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsCreator " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsCreatorHateoas.class),
            @ApiResponse(code = 201, message = "FondsCreator " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsCreatorHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type FondsCreator"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = NEW_FONDS_CREATOR, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsCreatorHateoas> createFondsCreator(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "FondsCreator",
                    value = "Incoming FondsCreator object",
                    required = true)
            @RequestBody FondsCreator fondsCreator) throws NikitaException {
        fondsCreatorService.createNewFondsCreator(fondsCreator);
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas(fondsCreator);
        fondsCreatorHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, fondsCreator));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(fondsCreator.getVersion().toString())
                .body(fondsCreatorHateoas);
    }

    // Create a new fonds
    // POST [contextPath][api]/arkivstruktur/arkivskaper/{systemID}/ny-arkiv
    @ApiOperation(value = "Persists a new Fonds associated with a FondsCreator", notes = "Returns the newly" +
            " created Fonds after it is associated with the Fonds and persisted to the database",
            response = FondsCreatorHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 201, message = "Fonds " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = FONDS_CREATOR + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS + SLASH + NEW_FONDS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsHateoas> createFondsAssociatedWithFondsCreator(
            HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of FondsCreator to associate the Fonds with.",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds) throws NikitaException {
        fondsCreatorService.createFondsAssociatedWithFondsCreator(systemID, fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(fonds);
        fondsHateoasHandler.addLinks(fondsHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, fonds));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(fonds.getVersion().toString())
                .body(fondsHateoas);
    }

    // API - All GET Requests (CRUD - READ)

    // Get a FondsCreator identified by a systemId
    // GET [contextPath][api]/arkivstruktur/arkivskaper/{systemId}
    @ApiOperation(value = "Retrieves a single FondsCreator entity given a systemId", response = FondsCreator.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsCreator returned", response = FondsCreator.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = FONDS_CREATOR + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsCreatorHateoas> findOne(HttpServletRequest request,
                                                       @ApiParam(name = "systemId",
                    value = "systemId of FondsCreator to retrieve.",
                    required = true)
            @PathVariable("systemID") final String fondsCreatorSystemId) {
        FondsCreator fondsCreator = fondsCreatorService.findBySystemId(fondsCreatorSystemId);
        if (fondsCreator == null) {
            throw new NoarkEntityNotFoundException("Could not find FondsCreator object with systemID " +
                    fondsCreatorSystemId);
        }
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas(fondsCreator);
        fondsCreatorHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(fondsCreator.getVersion().toString())
                .body(fondsCreatorHateoas);
    }

    // Get all FondsCreator
    // GET [contextPath][api]/arkivstruktur/arkivskaper/
    @ApiOperation(value = "Retrieves multiple FondsCreator entities limited by ownership rights", notes = "The field skip" +
            "tells how many FondsCreator rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = FondsCreatorHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsCreator found",
                    response = FondsCreatorHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = FONDS_CREATOR)
    public ResponseEntity<FondsCreatorHateoas> findAllFondsCreator(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {
        FondsCreatorHateoas fondsCreatorHateoas = new
                FondsCreatorHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                fondsCreatorService.findFondsCreatorByOwnerPaginated(top, skip));
        fondsCreatorHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());
        return new ResponseEntity<>(fondsCreatorHateoas, HttpStatus.OK);
    }

    // API - All PUT Requests (CRUD - UPDATE)

    // Updates a FondsCreator identified by a systemId
    // PUT [contextPath][api]/arkivstruktur/arkivskaper/{systemId}
    @ApiOperation(value = "Updates a FondsCreator identified by a systemId with new values",
            response = FondsCreator.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsCreator updated", response = FondsCreator.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = FONDS_CREATOR + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsCreatorHateoas> updateFondsCreator(HttpServletRequest request,
                                                                  @ApiParam(name = "fondsCreator",
                                                                          value = "Incoming fondsCreator object",
                    required = true)
                                                                  @RequestBody FondsCreator fondsCreator) {
        FondsCreator createdFonds = fondsCreatorService.updateFondsCreator(fondsCreator);
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, createdFonds));
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas(createdFonds);
        fondsCreatorHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(createdFonds.getVersion().toString())
                .body(fondsCreatorHateoas);
    }

    // Create a suggested FondsCreator (like a template) object with default values (nothing persisted)
    // GET [contextPath][api]/arkivstruktur/arkiv/{systemID}/ny-arkivskaper
    // GET [contextPath][api]/arkivstruktur/ny-arkivskaper
    @ApiOperation(value = "Suggests the contents of a new FondsCreator", notes = "Returns a pre-filled FondsCreator" +
            " with values relevant for the logged-in user", response = FondsCreatorHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsCreator " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsCreatorHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = {NEW_FONDS_CREATOR, FONDS + SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_FONDS_CREATOR})
    public ResponseEntity<FondsCreatorHateoas> getFondsCreatorTemplate(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response
    ) throws NikitaException {
        FondsCreator suggestedFondsCreator = new FondsCreator();
        // TODO: This should be replaced with configurable data based on whoever is logged in
        //       Currently just returns the test values
        suggestedFondsCreator.setFondsCreatorId("123456789");
        suggestedFondsCreator.setDescription("Eksempel kommune");
        suggestedFondsCreator.setFondsCreatorName("Eksempel kommune ligger i eksempel fylke nord for nord");
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas(suggestedFondsCreator);
        fondsHateoasHandler.addLinksOnNew(fondsCreatorHateoas, request, new Authorisation());
        return new ResponseEntity<>(fondsCreatorHateoas, HttpStatus.OK);
    }

}
