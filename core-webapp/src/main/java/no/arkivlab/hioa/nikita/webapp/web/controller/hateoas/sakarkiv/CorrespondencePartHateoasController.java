package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.sakarkiv;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.casehandling.CorrespondencePart;
import nikita.model.noark5.v4.hateoas.secondary.CorrespondencePartHateoas;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.secondary.ICorrespondencePartHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.ICorrespondencePartService;
import no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.NoarkController;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 4/25/17.
 */

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + CORRESPONDENCE_PART,
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
    @ApiOperation(value = "Retrieves a single CorrespondencePart entity given a systemId", response = CorrespondencePart.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePart returned", response = CorrespondencePart.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<CorrespondencePartHateoas> findOneCorrespondencePartBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the correspondencePart to retrieve",
                    required = true)
            @PathVariable("systemID") final String correspondencePartSystemId) {
        CorrespondencePart correspondencePart = correspondencePartService.findBySystemId(correspondencePartSystemId);
        CorrespondencePartHateoas correspondencePartHateoas = new CorrespondencePartHateoas(correspondencePart);
        correspondencePartHateoasHandler.addLinks(correspondencePartHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(correspondencePart.getVersion().toString())
                .body(correspondencePartHateoas);
    }


    // Update a CorrespondencePart with given values
    // PUT [contextPath][api]/sakarkiv/saksmappe/{systemId}
    @ApiOperation(value = "Updates a CorrespondencePart identified by a given systemId", notes = "Returns the newly updated correspondencePart",
            response = CorrespondencePartHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePart " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartHateoas.class),
            @ApiResponse(code = 201, message = "CorrespondencePart " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CorrespondencePart"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.PUT, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CorrespondencePartHateoas> updateCorrespondencePart(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of correspondencePart to update",
                    required = true)
            @PathVariable("systemID") final String systemID,
            @ApiParam(name = "CorrespondencePart",
                    value = "Incoming correspondencePart object",
                    required = true)
            @RequestBody CorrespondencePart correspondencePart) throws NikitaException {
        validateForUpdate(correspondencePart);

        CorrespondencePart updatedCorrespondencePart = correspondencePartService.updateCorrespondencePart(systemID, parseETAG(request.getHeader(ETAG)), correspondencePart);
        CorrespondencePartHateoas correspondencePartHateoas = new CorrespondencePartHateoas(updatedCorrespondencePart);
        correspondencePartHateoasHandler.addLinks(correspondencePartHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedCorrespondencePart));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedCorrespondencePart.getVersion().toString())
                .body(correspondencePartHateoas);
    }
}
/*
properties check
    public void checkForObligatoryCorrespondencePartValues(CorrespondencePart correspondencePart) {

        if (correspondencePart.getCorrespondencePartType() == null) {
            throw new NikitaMalformedInputDataException("The korrespondansepart you tried to create is malformed. The "
                    + "korrespondanseparttype field is mandatory, and you have submitted an empty value.");
        }

 */
