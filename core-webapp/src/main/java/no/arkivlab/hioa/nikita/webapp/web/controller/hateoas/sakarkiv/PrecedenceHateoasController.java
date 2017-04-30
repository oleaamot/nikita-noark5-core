package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.sakarkiv;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.secondary.PrecedenceHateoas;
import nikita.model.noark5.v4.secondary.Precedence;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.secondary.IPrecedenceHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
//import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.IPrecedenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

/**
 * Created by tsodring on 4/25/17.
 */

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + CORRESPONDENCE_PART,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class PrecedenceHateoasController {
/*
    IPrecedenceHateoasHandler precedenceHateoasHandler;
    IPrecedenceService precedenceService;

    public PrecedenceHateoasController(IPrecedenceHateoasHandler precedenceHateoasHandler,
                                       IPrecedenceService precedenceService) {
        this.precedenceHateoasHandler = precedenceHateoasHandler;
        this.precedenceService = precedenceService;
    }

    // API - All GET Requests (CRUD - READ)
    @ApiOperation(value = "Retrieves a single Precedence entity given a systemId", response = Precedence.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Precedence returned", response = Precedence.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<PrecedenceHateoas> findOnePrecedenceBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the precedence to retrieve",
                    required = true)
            @PathVariable("systemID") final String precedenceSystemId) {
        Precedence precedence = precedenceService.findBySystemId(precedenceSystemId);
        PrecedenceHateoas precedenceHateoas = new PrecedenceHateoas(precedence);
        precedenceHateoasHandler.addLinks(precedenceHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(precedence.getVersion().toString())
                .body(precedenceHateoas);
    }
*/
}
