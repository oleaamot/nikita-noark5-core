package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.hateoas.FondsCreatorHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsCreatorService;
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
import static nikita.config.N5ResourceMappings.FONDS_CREATOR;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class FondsCreatorHateoasController {

    private IFondsCreatorService fondsCreatorService;
    private IFondsHateoasHandler fondsHateoasHandler;

    @Autowired
    public FondsCreatorHateoasController(IFondsCreatorService fondsCreatorService,
                                         IFondsHateoasHandler fondsHateoasHandler) {
        this.fondsCreatorService = fondsCreatorService;
        this.fondsHateoasHandler = fondsHateoasHandler;

    }

    // API - All POST Requests (CRUD - CREATE)

    // Create a new FondsCreator
    // POST [contextPath][api]/arkivstruktur/arkivskaper
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
            @RequestBody FondsCreator FondsCreator) throws NikitaException {
        FondsCreator createdFondsCreator = fondsCreatorService.createNewFondsCreator(FondsCreator);
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas(createdFondsCreator);
        fondsHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());
        return new ResponseEntity<>(fondsCreatorHateoas, HttpStatus.CREATED);
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
    public ResponseEntity<FondsCreatorHateoas> findOne(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of FondsCreator to retrieve.",
                    required = true)
            @PathVariable("systemID") final String FondsCreatorSystemId) {
        FondsCreator FondsCreator = fondsCreatorService.findBySystemId(FondsCreatorSystemId);
        if (FondsCreator == null) {
            throw new NoarkEntityNotFoundException("Could not find FondsCreator object with systemID " +
                    FondsCreatorSystemId);
        }
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas(FondsCreator);
        fondsHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());
        return new ResponseEntity<>(fondsCreatorHateoas, HttpStatus.CREATED);
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
    @RequestMapping(method = RequestMethod.GET, value = FONDS_CREATOR + SLASH)
    public ResponseEntity<FondsCreatorHateoas> findAllFondsCreator(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {
        FondsCreatorHateoas fondsCreatorHateoas = new
                FondsCreatorHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                fondsCreatorService.findFondsCreatorByOwnerPaginated(top, skip));
        fondsHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());
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
    public ResponseEntity<String> updateFondsCreator(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of FondsCreator to retrieve.",
                    required = true)
            @PathVariable("systemID") final String FondsCreatorSystemId) {
        /*FondsCreator FondsCreator = fondsCreatorService.findBySystemId(FondsCreatorSystemId);
        if (FondsCreator == null) {
            throw new NoarkEntityNotFoundException("Could not find FondsCreator object with systemID " + FondsCreatorSystemId);
        }
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas(FondsCreator);
        fondsHateoasHandler.addLinks(fondsCreatorHateoas, request, new Authorisation());*/
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }
}
