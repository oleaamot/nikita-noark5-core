package nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.Fonds;
import nikita.common.model.noark5.v4.FondsCreator;
import nikita.common.model.noark5.v4.Series;
import nikita.common.model.noark5.v4.hateoas.FondsCreatorHateoas;
import nikita.common.model.noark5.v4.hateoas.FondsHateoas;
import nikita.common.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.model.application.FondsStructureDetails;
import nikita.webapp.service.interfaces.IFondsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH +
        NOARK_FONDS_STRUCTURE_PATH + SLASH, produces =
        {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
// Note we are seeing intellij report bugs that contents in the PathVarible
// cannot be found in the RequestMapping.
//     https://youtrack.jetbrains.com/issue/IDEA-100240
// We are seeing similar false positives on bean inspection.
@SuppressWarnings({"MVCPathVariableInspection",
        "SpringJavaAutowiringInspection", "unchecked"})
// Ideally I do not want IDE related stuff in the code and think we can
// remove it later. For now it's handy as it helps me to keep track of potential
// issues.
public class FondsHateoasController extends NoarkController {

    private EntityManager entityManager;
    private IFondsService fondsService;


    public FondsHateoasController(EntityManager entityManager,
                                  IFondsService fondsService) {
        this.entityManager = entityManager;
        this.fondsService = fondsService;
    }

    // API - All POST Requests (CRUD - CREATE)

    // Create a Fonds
    // POST [contextPath][api]/arkivstruktur/arkiv
    @ApiOperation(
            value = "Persists a Fonds object",
            notes = "Returns the newly created Fonds object after it is " +
                    "persisted to the database",
            response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(
                    code = 201,
                    message = "Fonds " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsHateoas.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " +
                            "Fonds"),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.POST,
            value = NEW_FONDS,
            consumes = {NOARK5_V4_CONTENT_TYPE_JSON}
    )
    public ResponseEntity<FondsHateoas> createFonds(
            HttpServletRequest request,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds) throws NikitaException {
        validateForCreate(fonds);
        FondsHateoas fondsHateoas = fondsService.createNewFonds(fonds);
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(fonds.getVersion().toString())
                .body(fondsHateoas);
    }

    // Create a sub-fonds and associate it with the Fonds identified by systemId
    // POST [contextPath][api]/arkivstruktur/arkiv/{systemId}/ny-underarkiv
    @ApiOperation(
            value = "Persists a child Fonds object",
            notes = "Returns the newly created child Fonds object after it is" +
                    " persisted to the database",
            response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 201,
                    message = "Fonds " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsHateoas.class),
            @ApiResponse(code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404,
                    message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type" +
                            " Fonds"),
            @ApiResponse(code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501,
                    message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted

    @RequestMapping(
            method = RequestMethod.POST,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH + NEW_SUB_FONDS,
            consumes = {NOARK5_V4_CONTENT_TYPE_JSON})

    public ResponseEntity<FondsHateoas> createFondsAssociatedWithFonds(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of parent fonds to associate the fonds " +
                            "with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds)
            throws NikitaException {
        validateForCreate(fonds);
        FondsHateoas fondsHateoas = fondsService
                .createFondsAssociatedWithFonds(systemID, fonds);
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(fonds.getVersion().toString())
                .body(fondsHateoas);
    }

    // Create a Series and associate it with the Fonds identified by systemId
    // POST [contextPath][api]/arkivstruktur/arkiv/{systemId}/ny-arkivdel
    @ApiOperation(
            value = "Persists a Series object associated with the given Fonds" +
                    " systemId",
            notes = "Returns the newly created Series object after it was " +
                    "associated with a Fonds object and persisted to the " +
                    "database",
            response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Series " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesHateoas.class),
            @ApiResponse(
                    code = 201,
                    message = "Series" +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SeriesHateoas.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " +
                            "Series"),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(
                    code = 501,
                    message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted

    @RequestMapping(
            method = RequestMethod.POST,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH + NEW_SERIES,
            consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<SeriesHateoas> createSeriesAssociatedWithFonds(
            HttpServletRequest request,
            @ApiParam(
                    name = "systemID",
                    value = "systemId of fonds to associate the series with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(
                    name = "series",
                    value = "Incoming series object",
                    required = true)
            @RequestBody Series series)
            throws NikitaException {
        validateForCreate(series);
        SeriesHateoas seriesHateoas =
                fondsService.createSeriesAssociatedWithFonds(systemID, series);
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(seriesHateoas.getEntityVersion().toString())
                .body(seriesHateoas);
    }

    // Create a FondsCreator and associate it with the Fonds identified by systemId
    // API - All GET Requests (CRUD - READ)

    // POST [contextPath][api]/arkivstruktur/arkiv/{systemId}/ny-arkivskaper
    @ApiOperation(
            value = "Persists a FondsCreator associated with the given Fonds " +
                    "systemId",
            notes = "Returns the newly created FondsCreator after it was " +
                    "associated with a Fonds and persisted to the database",
            response = FondsCreatorHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FondsCreator " +
                            API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsCreatorHateoas.class),
            @ApiResponse(
                    code = 201,
                    message = "FondsCreator " +
                            API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsCreatorHateoas.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type " +
                            "FondsCreator"),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)
    })
    @Counted

    @RequestMapping(
            method = RequestMethod.POST,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH + NEW_FONDS_CREATOR,
            consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FondsCreatorHateoas>
    createFondsCreatorAssociatedWithFonds(
            HttpServletRequest request,
            @ApiParam(
                    name = "systemID",
                    value = "systemId of fonds to associate the series with.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(
                    name = "fondsCreator",
                    value = "Incoming fondsCreator object",
                    required = true)
            @RequestBody FondsCreator fondsCreator)
            throws NikitaException {

        validateForCreate(fondsCreator);
        FondsCreatorHateoas fondsCreatorHateoas = fondsService
                .createFondsCreatorAssociatedWithFonds(systemID, fondsCreator);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(fondsCreatorHateoas.getEntityVersion().toString())
                .body(fondsCreatorHateoas);
    }

    // GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/
    @ApiOperation(value = "Retrieves a single fonds entity given a systemId",
            response = Fonds.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Fonds returned",
                    response = Fonds.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS
    )
    public ResponseEntity<FondsHateoas> findOne(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to retrieve.",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        FondsHateoas fondsHateoas = fondsService.findSingleFonds(systemID);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(fondsHateoas.getEntityVersion().toString())
                .body(fondsHateoas);
    }
    // Get single fonds identified by systemId

    // Return a Series object with default values
    //GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/ny-arkivdel
    @ApiOperation(
            value = "Create a Series with default values",
            response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Series returned", response = Series.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH + NEW_SERIES
    )
    public ResponseEntity<SeriesHateoas> createDefaultSeries(
            HttpServletRequest request,
            @ApiParam(
                    name = "systemID",
                    value = "systemId of fonds to associate Series with.",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fondsService.generateDefaultSeries(systemID));
    }

    // Get all fondsCreators associated with fonds identified by systemId
    // GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/arkivskaper/
    @ApiOperation(value = "Retrieves the fondsCreators associated with a " +
            "Fonds identified by a systemId", response = FondsCreator.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "FondsCreator returned",
                    response = FondsCreator.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH + FONDS_CREATOR + SLASH
    )
    public ResponseEntity<FondsCreatorHateoas>
    findFondsCreatorAssociatedWithFonds(
            HttpServletRequest request,
            @ApiParam(
                    name = "systemID",
                    value = "systemId of fonds you want retrieve associated " +
                            "FondsCreator objects for.",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        FondsCreatorHateoas fondsCreatorHateoas = fondsService
                .findFondsCreatorAssociatedWithFonds(systemID);
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fondsCreatorHateoas);
    }

    // Get all Series associated with Fonds identified by systemId
    // GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/arkivdel/
    @ApiOperation(
            value = "Retrieves the Series associated with a Fonds identified " +
                    "by a systemId",
            response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Series returned", response = Series.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH + SERIES + SLASH
    )
    public ResponseEntity<SeriesHateoas>
    findSeriesAssociatedWithFonds(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of Fonds that has Series associated " +
                            "with it.",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fondsService.findSeriesAssociatedWithFonds(systemID));
    }

    // Get all Sub-fonds associated with a Fonds identified by systemId
    // GET [contextPath][api]/arkivstruktur/arkiv/{systemId}/underarkiv/
    @ApiOperation(
            value = "Retrieves the (sub)Fonds associated with a Fonds " +
                    "identified by a systemId",
            response = Fonds.class)
    @ApiResponses(value = {/*
            @ApiResponse(
                    code = 200,
                    message = "Fonds returned", response = Fonds.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR),*/
            @ApiResponse(
                    code = 501,
                    message = API_MESSAGE_NOT_IMPLEMENTED)
    })
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS + SLASH + SUB_FONDS + SLASH
    )
    public ResponseEntity<String> findSubfondsAssociatedWithFonds(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of parent Fonds.",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED,
                HttpStatus.NOT_IMPLEMENTED);
    }

    // Get all fonds
    // GET [contextPath][api]/arkivstruktur/arkiv/
    @ApiOperation(
            value = "Retrieves multiple Fonds entities limited by ownership  " +
                    "rights",
            notes = "The field skip tells how many Fonds rows of the result " +
                    "set to ignore (starting at 0), while  top tells how many" +
                    " rows after skip to return. Note if the value of top is " +
                    "greater than system value  nikita-noark5-core.pagination" +
                    ".maxPageSize, then nikita-noark5-core.pagination" +
                    ".maxPageSize  is used. ",
            response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Fonds found",
                    response = FondsHateoas.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)
    })
    @Counted

    @RequestMapping(method = RequestMethod.GET, value = FONDS)
    public ResponseEntity<FondsHateoas> findAllFonds(
            HttpServletRequest request,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        FondsHateoas fondsHateoas = fondsService.
                findFondsByOwnerPaginated(top, skip);

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fondsHateoas);
    }

    // Create a suggested Fonds (like a template) object with default values
    // (nothing persisted)
    // GET [contextPath][api]/arkivstruktur/{systemId}/ny-arkiv
    @ApiOperation(
            value = "Suggests the contents of a new Fonds object",
            notes = "Returns a pre-filled Fonds object with values relevant " +
                    "for the logged-in user",
            response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = FONDS_CREATOR + SLASH + LEFT_PARENTHESIS +
                    SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_FONDS
    )
    public ResponseEntity<FondsHateoas> getSubFondsTemplate(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to associate the series with.",
                    required = true)
            @PathVariable("systemID") String systemID
    ) throws NikitaException {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fondsService.generateDefaultFonds(systemID));
    }

    // Create a suggested sub-fonds (like a template) object with default values
    // (nothing persisted)
    // GET [contextPath][api]/arkivstruktur/ny-arkiv
    @ApiOperation(
            value = "Suggests the contents of a new Fonds object",
            notes = "Returns a pre-filled Fonds object with values relevant " +
                    "for the logged-in user",
            response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.GET,
            value = NEW_FONDS
    )
    public ResponseEntity<FondsHateoas> getFondsTemplate(
            HttpServletRequest request
    ) throws NikitaException {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fondsService.generateDefaultFonds(null));
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a Fonds
    // PUT [contextPath][api]/arkivstruktur/arkiv
    @ApiOperation(
            value = "Updates a Fonds object",
            notes = "Returns the newly updated Fonds object after it is " +
                    "persisted to the database",
            response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Fonds " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsHateoas.class),
            @ApiResponse(
                    code = 201,
                    message = "Fonds " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsHateoas.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 404,
                    message = API_MESSAGE_PARENT_DOES_NOT_EXIST +
                            " of type Fonds"),
            @ApiResponse(
                    code = 409,
                    message = API_MESSAGE_CONFLICT),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.PUT,
            value = FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS,
            consumes = {NOARK5_V4_CONTENT_TYPE_JSON}
    )
    public ResponseEntity<FondsHateoas> updateFonds(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "fonds",
                    value = "Incoming fonds object",
                    required = true)
            @RequestBody Fonds fonds) throws NikitaException {

        validateForUpdate(fonds);
        FondsHateoas fondsHateoas =
                fondsService.handleUpdate(systemID,
                        parseETAG(request.getHeader(ETAG)), fonds);

        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.
                        getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(fondsHateoas.getEntityVersion().toString())
                .body(fondsHateoas);
    }

    // Delete a Fonds identified by systemID
    // DELETE [contextPath][api]/arkivstruktur/arkiv/{systemId}/
    @ApiOperation(
            value = "Deletes a single Fonds entity identified by systemID",
            response = FondsStructureDetails.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Parent ApplicationDetails returned",
                    response = FondsStructureDetails.class),
            @ApiResponse(
                    code = 401,
                    message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(
                    code = 403,
                    message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(
                    code = 500,
                    message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = SLASH + FONDS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
                    RIGHT_PARENTHESIS
    )
    public ResponseEntity<String> deleteFondsBySystemId(
            @ApiParam(name = "systemID",
                    value = "systemID of the series to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        // TODO: When dealing with a fonds, if you have a parent fonds, then
        // you must return the parent fonds but if you don't have a parent
        // fonds you must return an applicationdetails. The only way to handle
        // this is a little mesy, but probably just create the JSON manually
        // here and return it as type string
        fondsService.deleteEntity(systemID);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\" : \"Success\"}");
    }
}
