package nikita.webapp.web.controller.hateoas.admin;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.admin.AdministrativeUnit;
import nikita.common.model.noark5.v4.hateoas.admin.AdministrativeUnitHateoas;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.hateoas.interfaces.admin.IAdministrativeUnitHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.interfaces.admin.IAdministrativeUnitService;
import nikita.webapp.web.controller.hateoas.NoarkController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class AdministrativeUnitController extends NoarkController {

    private IAdministrativeUnitService administrativeUnitService;
    private IAdministrativeUnitHateoasHandler administrativeUnitHateoasHandler;

    public AdministrativeUnitController(IAdministrativeUnitService administrativeUnitService,
                                        IAdministrativeUnitHateoasHandler administrativeUnitHateoasHandler) {
        this.administrativeUnitService = administrativeUnitService;
        this.administrativeUnitHateoasHandler = administrativeUnitHateoasHandler;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new administrativtenhet
    // POST [contextPath][api]/admin/ny-administrativtenhet
    @ApiOperation(value = "Persists a new AdministrativeUnit object", notes = "Returns the newly" +
            " created AdministrativeUnit object after it is persisted to the database",
            response = AdministrativeUnit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdministrativeUnit " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = AdministrativeUnit.class),
            @ApiResponse(code = 201, message = "AdministrativeUnit " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = AdministrativeUnit.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted

    @RequestMapping(method = RequestMethod.POST, value = NEW_ADMINISTRATIVE_UNIT)
    public ResponseEntity<AdministrativeUnitHateoas> createAdministrativeUnit(
            HttpServletRequest request,
            @RequestBody AdministrativeUnit administrativeUnit)
            throws NikitaException {
        administrativeUnitService.createNewAdministrativeUnit(administrativeUnit);
        AdministrativeUnitHateoas adminHateoas = new AdministrativeUnitHateoas(administrativeUnit);
        administrativeUnitHateoasHandler.addLinks(adminHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(administrativeUnit.getVersion().toString())
                .body(adminHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all administrativeUnit
    // GET [contextPath][api]/admin/administrativtenhet/
    @ApiOperation(value = "Retrieves all AdministrativeUnit ", response = AdministrativeUnit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdministrativeUnit found",
                    response = AdministrativeUnit.class),
            @ApiResponse(code = 404, message = "No AdministrativeUnit found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(method = RequestMethod.GET, value = ADMINISTRATIVE_UNIT)
    public ResponseEntity<AdministrativeUnitHateoas> findAll(HttpServletRequest request) {
        AdministrativeUnitHateoas adminHateoas = new AdministrativeUnitHateoas(
                (List<INikitaEntity>) (List) administrativeUnitService.findAll());
        administrativeUnitHateoasHandler.addLinks(adminHateoas, new Authorisation());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(adminHateoas);
    }

    // Retrieves a given administrativeUnit identified by a systemId
    // GET [contextPath][api]/admin/administrativtenhet/{systemId}/
    @ApiOperation(value = "Gets administrativeUnit identified by its systemId", notes = "Returns the requested " +
            " administrativeUnit object", response = AdministrativeUnit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdministrativeUnit " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = AdministrativeUnit.class),
            @ApiResponse(code = 201, message = "AdministrativeUnit " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = AdministrativeUnit.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted

    @RequestMapping(value = ADMINISTRATIVE_UNIT + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH,
            method = RequestMethod.GET)
    public ResponseEntity<AdministrativeUnitHateoas> findBySystemId(@PathVariable("systemID") final String systemId,
                                                                    HttpServletRequest request) {
        AdministrativeUnit administrativeUnit = administrativeUnitService.findBySystemId(systemId);
        AdministrativeUnitHateoas adminHateoas = new AdministrativeUnitHateoas(administrativeUnit);
        administrativeUnitHateoasHandler.addLinks(adminHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(administrativeUnit.getVersion().toString())
                .body(adminHateoas);
    }

    // Create a suggested administrativeUnit(like a template) with default values (nothing persisted)
    // GET [contextPath][api]/admin/ny-administrativtenhet
    @ApiOperation(value = "Creates a suggested AdministrativeUnit", response = AdministrativeUnit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdministrativeUnit codes found",
                    response = AdministrativeUnit.class),
            @ApiResponse(code = 404, message = "No AdministrativeUnit found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(method = RequestMethod.GET, value = NEW_ADMINISTRATIVE_UNIT)
    public ResponseEntity<AdministrativeUnitHateoas> getAdministrativeUnitTemplate(HttpServletRequest request) {
        AdministrativeUnit administrativeUnit = new AdministrativeUnit();
        administrativeUnit.setShortName("kortnavn på administrativtenhet");
        administrativeUnit.setAdministrativeUnitName("Formell navn på administrativtenhet");
        AdministrativeUnitHateoas adminHateoas = new AdministrativeUnitHateoas(administrativeUnit);
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(adminHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a administrativtenhet
    // PUT [contextPath][api]/metatdata/administrativtenhet/{systemID}
    @ApiOperation(value = "Updates a AdministrativeUnit object", notes = "Returns the newly" +
            " updated AdministrativeUnit object after it is persisted to the database",
            response = AdministrativeUnit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdministrativeUnit " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = AdministrativeUnit.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(method = RequestMethod.PUT, value = ADMINISTRATIVE_UNIT + SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS)
    public ResponseEntity<AdministrativeUnitHateoas> updateAdministrativeUnit(HttpServletRequest request,
                                                                              @ApiParam(name = "systemID",
                                                                                      value = "systemID of documentDescription to update.",
                                                                                      required = true)
                                                                              @PathVariable("systemID") String systemID,
                                                                              @ApiParam(name = "administrativeUnit",
                                                                                      value = "Incoming administrativeUnit object",
                                                                                      required = true)
                                                                              @RequestBody AdministrativeUnit administrativeUnit)
            throws NikitaException {
        administrativeUnitService.update(systemID,
                parseETAG(request.getHeader(ETAG)), administrativeUnit);
        AdministrativeUnitHateoas adminHateoas = new AdministrativeUnitHateoas(administrativeUnit);
        administrativeUnitHateoasHandler.addLinks(adminHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(adminHateoas);
    }
}
