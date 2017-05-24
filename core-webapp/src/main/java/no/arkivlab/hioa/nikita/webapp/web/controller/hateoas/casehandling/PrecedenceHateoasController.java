package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.casehandling;

import nikita.config.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART;

//import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.IPrecedenceService;

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
        Precedence precedence = precedenceService.findBySystemIdOrderBySystemId(precedenceSystemId);
        PrecedenceHateoas precedenceHateoas = new PrecedenceHateoas(precedence);
        precedenceHateoasHandler.addLinks(precedenceHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(precedence.getVersion().toString())
                .body(precedenceHateoas);
    }
*/
}
