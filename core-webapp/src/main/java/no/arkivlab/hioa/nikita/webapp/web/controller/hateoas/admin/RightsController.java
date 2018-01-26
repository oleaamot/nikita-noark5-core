package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.admin;

import nikita.config.Constants;
import no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.NoarkController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static nikita.config.Constants.*;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class RightsController extends NoarkController {
/*
    private IUserService administrativeUnitService;
    private IUserHateoasHandler administrativeUnitHateoasHandler;

    public RightsController(IUserService administrativeUnitService,
                            IUserHateoasHandler administrativeUnitHateoasHandler) {
        this.administrativeUnitService = administrativeUnitService;
        this.administrativeUnitHateoasHandler = administrativeUnitHateoasHandler;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new administrativtenhet
    // POST [contextPath][api]/admin/ny-administrativtenhet
    @ApiOperation(value = "Persists a new User object", notes = "Returns the newly" +
            " created User object after it is persisted to the database",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = User.class),
            @ApiResponse(code = 201, message = "User " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = User.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = NEW_ADMINISTRATIVE_UNIT)
    public ResponseEntity<UserHateoas> createUser(
            HttpServletRequest request,
            @RequestBody User administrativeUnit)
            throws NikitaException {
        administrativeUnitService.createNewUser(administrativeUnit);
        UserHateoas adminHateoas = new UserHateoas(administrativeUnit);
        administrativeUnitHateoasHandler.addLinks(adminHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(administrativeUnit.getVersion().toString())
                .body(adminHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all administrativeUnit
    // GET [contextPath][api]/admin/administrativtenhet/
    @ApiOperation(value = "Retrieves all User ", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found",
                    response = User.class),
            @ApiResponse(code = 404, message = "No User found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = ADMINISTRATIVE_UNIT)
    public ResponseEntity<UserHateoas> findAll(HttpServletRequest request) {
        UserHateoas adminHateoas = new UserHateoas(
                (ArrayList<INikitaEntity>) (ArrayList) administrativeUnitService.findAll());
        administrativeUnitHateoasHandler.addLinks(adminHateoas, request, new Authorisation());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(adminHateoas);
    }

    // Retrieves a given administrativeUnit identified by a systemId
    // GET [contextPath][api]/admin/administrativtenhet/{systemId}/
    @ApiOperation(value = "Gets administrativeUnit identified by its systemId", notes = "Returns the requested " +
            " administrativeUnit object", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = User.class),
            @ApiResponse(code = 201, message = "User " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = User.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(value = ADMINISTRATIVE_UNIT + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET)
    public ResponseEntity<UserHateoas> findBySystemId(@PathVariable("systemID") final String systemId,
                                                                                   HttpServletRequest request) {
        User administrativeUnit = administrativeUnitService.findBySystemId(systemId);
        UserHateoas adminHateoas = new UserHateoas(administrativeUnit);
        administrativeUnitHateoasHandler.addLinks(adminHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(administrativeUnit.getVersion().toString())
                .body(adminHateoas);
    }

    // Create a suggested administrativeUnit(like a template) with default values (nothing persisted)
    // GET [contextPath][api]/admin/ny-administrativtenhet
    @ApiOperation(value = "Creates a suggested User", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User codes found",
                    response = User.class),
            @ApiResponse(code = 404, message = "No User found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = NEW_ADMINISTRATIVE_UNIT)
    public ResponseEntity<UserHateoas> getUserTemplate(HttpServletRequest request) {
        User administrativeUnit = new User();
        administrativeUnit.setShortName("kortnavn på administrativtenhet");
        administrativeUnit.setUserName("Formell navn på administrativtenhet");
        UserHateoas adminHateoas = new UserHateoas(administrativeUnit);
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(adminHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a administrativtenhet
    // PUT [contextPath][api]/metatdata/administrativtenhet/{systemID}
    @ApiOperation(value = "Updates a User object", notes = "Returns the newly" +
            " updated User object after it is persisted to the database",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = User.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = ADMINISTRATIVE_UNIT + SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS)
    public ResponseEntity<UserHateoas> updateUser(HttpServletRequest request,
                                                                              @ApiParam(name = "systemID",
                                                                                      value = "systemID of documentDescription to update.",
                                                                                      required = true)
                                                                              @PathVariable("systemID") String systemID,
                                                                              @ApiParam(name = "administrativeUnit",
                                                                                      value = "Incoming administrativeUnit object",
                                                                                      required = true)
                                                                              @RequestBody User administrativeUnit)
            throws NikitaException {
        User newUser = administrativeUnitService.update(systemID,
                parseETAG(request.getHeader(ETAG)), administrativeUnit);
        UserHateoas adminHateoas = new UserHateoas(administrativeUnit);
        administrativeUnitHateoasHandler.addLinks(adminHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(adminHateoas);
    }*/
}
