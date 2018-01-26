package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.admin;

import nikita.config.Constants;
import no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.NoarkController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static nikita.config.Constants.*;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class UserController extends NoarkController {
/*
    private IUserService userService;
    private IUserHateoasHandler userHateoasHandler;

    public UserController(IUserService userService,
                          IUserHateoasHandler userHateoasHandler) {
        this.userService = userService;
        this.userHateoasHandler = userHateoasHandler;
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
            @RequestBody User user)
            throws NikitaException {
        userService.createNewUser(user);
        UserHateoas userHateoas = new UserHateoas(user);
        userHateoasHandler.addLinks(userHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(user.getVersion().toString())
                .body(userHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all user
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
        UserHateoas userHateoas = new UserHateoas(
                (ArrayList<INikitaEntity>) (ArrayList) userService.findAll());
        userHateoasHandler.addLinks(userHateoas, request, new Authorisation());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(userHateoas);
    }

    // Retrieves a given user identified by a systemId
    // GET [contextPath][api]/admin/administrativtenhet/{systemId}/
    @ApiOperation(value = "Gets user identified by its systemId", notes = "Returns the requested " +
            " user object", response = User.class)
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
        User user = userService.findBySystemId(systemId);
        UserHateoas userHateoas = new UserHateoas(user);
        userHateoasHandler.addLinks(userHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(user.getVersion().toString())
                .body(userHateoas);
    }

    // Create a suggested user(like a template) with default values (nothing persisted)
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
        User user = new User();
        user.setShortName("kortnavn på administrativtenhet");
        user.setUserName("Formell navn på administrativtenhet");
        UserHateoas userHateoas = new UserHateoas(user);
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(userHateoas);
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
                                                                              @ApiParam(name = "user",
                                                                                      value = "Incoming user object",
                                                                                      required = true)
                                                                              @RequestBody User user)
            throws NikitaException {
        User newUser = userService.update(systemID,
                parseETAG(request.getHeader(ETAG)), user);
        UserHateoas userHateoas = new UserHateoas(user);
        userHateoasHandler.addLinks(userHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(userHateoas);
    }
    */
}
