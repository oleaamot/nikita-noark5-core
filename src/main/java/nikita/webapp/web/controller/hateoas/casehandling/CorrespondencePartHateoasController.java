package nikita.webapp.web.controller.hateoas.casehandling;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;
import nikita.common.model.noark5.v4.hateoas.casehandling.CorrespondencePartInternalHateoas;
import nikita.common.model.noark5.v4.hateoas.casehandling.CorrespondencePartPersonHateoas;
import nikita.common.model.noark5.v4.hateoas.casehandling.CorrespondencePartUnitHateoas;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.hateoas.interfaces.secondary.ICorrespondencePartHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.interfaces.secondary.ICorrespondencePartService;
import nikita.webapp.web.controller.hateoas.NoarkController;
import nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 4/25/17.
 */

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class CorrespondencePartHateoasController extends NoarkController {

    private ICorrespondencePartHateoasHandler correspondencePartHateoasHandler;
    private ICorrespondencePartService correspondencePartService;
    private ApplicationEventPublisher applicationEventPublisher;

    public CorrespondencePartHateoasController(ICorrespondencePartHateoasHandler correspondencePartHateoasHandler,
                                               ICorrespondencePartService correspondencePartService,
                                               ApplicationEventPublisher applicationEventPublisher) {
        this.correspondencePartHateoasHandler = correspondencePartHateoasHandler;
        this.correspondencePartService = correspondencePartService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All GET Requests (CRUD - READ)
    // Get a CorrespondencePartPerson identified by systemID
    // GET [contextPath][api]/casehandling/korrespondansepartperson/{systemId}
    @ApiOperation(value = "Retrieves a single CorrespondencePartPerson entity given a systemId",
            response = CorrespondencePartPerson.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartPerson returned",
                    response = CorrespondencePartPerson.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_PERSON + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<CorrespondencePartPersonHateoas> findOneCorrespondencePartPersonBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the correspondencePartPerson to retrieve",
                    required = true)
            @PathVariable("systemID") final String correspondencePartPersonSystemId) {
        /*CorrespondencePartPerson correspondencePartPerson =
                (CorrespondencePartPerson) correspondencePartService.findBySystemId(correspondencePartPersonSystemId);
        CorrespondencePartPersonHateoas correspondencePartPersonHateoas =
                new CorrespondencePartPersonHateoas(correspondencePartPerson);
        correspondencePartHateoasHandler.addLinks(correspondencePartPersonHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(correspondencePartPerson.getVersion().toString())
                .body(correspondencePartPersonHateoas);
                */
        return null;
    }

    // Get a CorrespondencePartInternal identified by systemID
    // GET [contextPath][api]/casehandling/korrespondansepartintern/{systemId}
    @ApiOperation(value = "Retrieves a single CorrespondencePartInternal entity given a systemId",
            response = CorrespondencePartInternal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartInternal returned",
                    response = CorrespondencePartInternal.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_INTERNAL + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<CorrespondencePartInternalHateoas> findOneCorrespondencePartInternalBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the correspondencePartInternal to retrieve",
                    required = true)
            @PathVariable("systemID") final String correspondencePartInternalSystemId) {
        CorrespondencePartInternal correspondencePartInternal =
                (CorrespondencePartInternal) correspondencePartService.findBySystemId(correspondencePartInternalSystemId);
        CorrespondencePartInternalHateoas correspondencePartInternalHateoas =
                new CorrespondencePartInternalHateoas(correspondencePartInternal);
        correspondencePartHateoasHandler.addLinks(correspondencePartInternalHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(correspondencePartInternal.getVersion().toString())
                .body(correspondencePartInternalHateoas);
    }

    // Get a CorrespondencePartPerson identified by systemID
    // GET [contextPath][api]/casehandling/korrespondansepartenhet/{systemId}
    @ApiOperation(value = "Retrieves a single CorrespondencePartUnit entity given a systemId",
            response = CorrespondencePartUnit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartUnit returned",
                    response = CorrespondencePartUnit.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_UNIT + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<CorrespondencePartUnitHateoas> findOneCorrespondencePartUnitBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the correspondencePartUnit to retrieve",
                    required = true)
            @PathVariable("systemID") final String correspondencePartUnitSystemId) {
        CorrespondencePartUnit correspondencePartUnit =
                (CorrespondencePartUnit) correspondencePartService.findBySystemId(correspondencePartUnitSystemId);
        CorrespondencePartUnitHateoas correspondencePartUnitHateoas = new CorrespondencePartUnitHateoas(correspondencePartUnit);
        correspondencePartHateoasHandler.addLinks(correspondencePartUnitHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(correspondencePartUnit.getVersion().toString())
                .body(correspondencePartUnitHateoas);
    }

    // Update a CorrespondencePartUnit with given values
    // PUT [contextPath][api]/casehandling/korrespondansepartenhet/{systemId}
    @ApiOperation(value = "Updates a CorrespondencePartUnit identified by a given systemId",
            notes = "Returns the newly updated correspondencePartUnit",
            response = CorrespondencePartUnitHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartUnit " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartUnitHateoas.class),
            @ApiResponse(code = 201, message = "CorrespondencePartUnit " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartUnitHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CorrespondencePartUnit"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_UNIT + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CorrespondencePartUnitHateoas> updateCorrespondencePartUnit(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of correspondencePartUnit to update",
                    required = true)
            @PathVariable("systemID") final String systemID,
            @ApiParam(name = "CorrespondencePartUnit",
                    value = "Incoming correspondencePartUnit object",
                    required = true)
            @RequestBody CorrespondencePartUnit correspondencePartUnit) throws NikitaException {
        validateForUpdate(correspondencePartUnit);

        CorrespondencePartUnit updatedCorrespondencePartUnit =
                correspondencePartService.updateCorrespondencePartUnit(systemID, parseETAG(request.getHeader(ETAG)),
                        correspondencePartUnit);
        CorrespondencePartUnitHateoas correspondencePartUnitHateoas = new
                CorrespondencePartUnitHateoas(updatedCorrespondencePartUnit);
        correspondencePartHateoasHandler.addLinks(correspondencePartUnitHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedCorrespondencePartUnit));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedCorrespondencePartUnit.getVersion().toString())
                .body(correspondencePartUnitHateoas);
    }

    // Update a CorrespondencePartPerson with given values
    // PUT [contextPath][api]/casehandling/korrespondansepartperson/{systemId}
    @ApiOperation(value = "Updates a CorrespondencePartPerson identified by a given systemId",
            notes = "Returns the newly updated correspondencePartPerson",
            response = CorrespondencePartPersonHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartPerson " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartPersonHateoas.class),
            @ApiResponse(code = 201, message = "CorrespondencePartPerson " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartPersonHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CorrespondencePartPerson"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_PERSON + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CorrespondencePartPersonHateoas> updateCorrespondencePartPerson(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of correspondencePartPerson to update",
                    required = true)
            @PathVariable("systemID") final String systemID,
            @ApiParam(name = "CorrespondencePartPerson",
                    value = "Incoming correspondencePartPerson object",
                    required = true)
            @RequestBody CorrespondencePartPerson correspondencePartPerson) throws NikitaException {
        validateForUpdate(correspondencePartPerson);

        CorrespondencePartPerson updatedCorrespondencePartPerson =
                correspondencePartService.updateCorrespondencePartPerson(systemID,
                        parseETAG(request.getHeader(ETAG)), correspondencePartPerson);
        CorrespondencePartPersonHateoas correspondencePartPersonHateoas =
                new CorrespondencePartPersonHateoas(updatedCorrespondencePartPerson);
        correspondencePartHateoasHandler.addLinks(correspondencePartPersonHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedCorrespondencePartPerson));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedCorrespondencePartPerson.getVersion().toString())
                .body(correspondencePartPersonHateoas);
    }

    // Update a CorrespondencePartInternal with given values
    // PUT [contextPath][api]/casehandling/korrespondansepartintern/{systemId}
    @ApiOperation(value = "Updates a CorrespondencePartInternal identified by a given systemId",
            notes = "Returns the newly updated correspondencePartInternal",
            response = CorrespondencePartInternalHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartInternal " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartInternalHateoas.class),
            @ApiResponse(code = 201, message = "CorrespondencePartInternal " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartInternalHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CorrespondencePartInternal"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_INTERNAL + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CorrespondencePartInternalHateoas> updateCorrespondencePartInternal(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of correspondencePartInternal to update",
                    required = true)
            @PathVariable("systemID") final String systemID,
            @ApiParam(name = "CorrespondencePartInternal",
                    value = "Incoming correspondencePartInternal object",
                    required = true)
            @RequestBody CorrespondencePartInternal correspondencePartInternal) throws NikitaException {
        validateForUpdate(correspondencePartInternal);

        CorrespondencePartInternal updatedCorrespondencePartInternal =
                correspondencePartService.updateCorrespondencePartInternal(systemID,
                        parseETAG(request.getHeader(ETAG)), correspondencePartInternal);
        CorrespondencePartInternalHateoas correspondencePartInternalHateoas =
                new CorrespondencePartInternalHateoas(updatedCorrespondencePartInternal);
        correspondencePartHateoasHandler.addLinks(correspondencePartInternalHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedCorrespondencePartInternal));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedCorrespondencePartInternal.getVersion().toString())
                .body(correspondencePartInternalHateoas);
    }

    // Delete a correspondencePartUnit identified by kode
    // DELETE [contextPath][api]/sakarkiv/korrespondansepartenhet/{kode}/
    @ApiOperation(value = "Deletes a single CorrespondencePartUnit entity identified by kode")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartUnit deleted"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_UNIT + SLASH + LEFT_PARENTHESIS + CODE + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCorrespondencePartUnit(
            @ApiParam(name = "kode",
                    value = "kode of the correspondencePartUnit to delete",
                    required = true)
            @PathVariable("kode") final String kode) {
        correspondencePartService.deleteCorrespondencePartUnit(kode);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\" : \"Success\"}");
    }

    // Delete a correspondencePartPerson identified by kode
    // DELETE [contextPath][api]/sakarkiv/korrespondansepartperson/{kode}/
    @ApiOperation(value = "Deletes a single CorrespondencePartPerson entity identified by kode")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartPerson deleted"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_PERSON + SLASH + LEFT_PARENTHESIS + CODE + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCorrespondencePartPerson(
            @ApiParam(name = "kode",
                    value = "kode of the correspondencePartPerson to delete",
                    required = true)
            @PathVariable("kode") final String kode) {
        correspondencePartService.deleteCorrespondencePartPerson(kode);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\" : \"Success\"}");
    }

    // Delete a correspondencePartInternal identified by kode
    // DELETE [contextPath][api]/sakarkiv/korrespondansepartintern/{kode}/
    @ApiOperation(value = "Deletes a single CorrespondencePartInternal entity identified by kode")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartInternal deleted"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CORRESPONDENCE_PART_INTERNAL + SLASH + LEFT_PARENTHESIS + CODE + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCorrespondencePartInternal(
            @ApiParam(name = "kode",
                    value = "kode of the correspondencePartInternal to delete",
                    required = true)
            @PathVariable("kode") final String kode) {
        correspondencePartService.deleteCorrespondencePartInternal(kode);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\" : \"Success\"}");
    }

}
