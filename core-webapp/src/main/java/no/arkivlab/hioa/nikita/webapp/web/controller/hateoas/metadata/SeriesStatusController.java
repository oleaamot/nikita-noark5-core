package no.arkivdellab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.SeriesStatus;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ISeriesStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FONDS_STATUS;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class SeriesStatusController {

    private ISeriesStatusService seriesStatusService;
    private IMetadataHateoasHandler metadataHateoasHandler;

    public SeriesStatusController(ISeriesStatusService seriesStatusService,
                                  IMetadataHateoasHandler metadataHateoasHandler) {
        this.seriesStatusService = seriesStatusService;
        this.metadataHateoasHandler = metadataHateoasHandler;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new arkivdelstatus
    // POST [contextPath][api]/metadata/arkivdelstatus/ny-arkivdelstatus
    @ApiOperation(value = "Persists a new SeriesStatus object", notes = "Returns the newly" +
            " created SeriesStatus object after it is persisted to the database", response = SeriesStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SeriesStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesStatus.class),
            @ApiResponse(code = 201, message = "SeriesStatus " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SeriesStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = FONDS_STATUS + SLASH + NEW_FONDS_STATUS)
    public ResponseEntity<MetadataHateoas> createSeriesStatus(
            HttpServletRequest request,
            @RequestBody SeriesStatus seriesStatus)
            throws NikitaException {
        seriesStatusService.createNewSeriesStatus(seriesStatus);
        MetadataHateoas metadataHateoas = new MetadataHateoas(seriesStatus);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(seriesStatus.getVersion().toString())
                .body(metadataHateoas);
    }

    // API - All GET Requests (CRUD - READ)
    // Retrieves all seriesStatus
    // GET [contextPath][api]/metadata/arkivdelstatus/
    @ApiOperation(value = "Retrieves all SeriesStatus ", response = SeriesStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SeriesStatus codes found",
                    response = SeriesStatus.class),
            @ApiResponse(code = 404, message = "No SeriesStatus found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = FONDS_STATUS)
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        return null;
        /*MetadataHateoas metadataHateoas = new MetadataHateoas(new
                List<>(seriesStatusService.findAllAsList()),
                FONDS_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);*/
    }

    // Retrieves a given seriesStatus identified by a systemId
    // GET [contextPath][api]/metadata/arkivdelstatus/{systemId}/
    @ApiOperation(value = "Gets seriesStatus identified by its systemId", notes = "Returns the requested " +
            " seriesStatus object", response = SeriesStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SeriesStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesStatus.class),
            @ApiResponse(code = 201, message = "SeriesStatus " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SeriesStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(value = FONDS_STATUS + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH, method = RequestMethod.GET)
    public ResponseEntity<MetadataHateoas> findBySystemId(@PathVariable("systemID") final String systemId,
                                                          HttpServletRequest request) {
        SeriesStatus seriesStatus = seriesStatusService.findBySystemId(systemId);
        MetadataHateoas metadataHateoas = new MetadataHateoas(seriesStatus);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(seriesStatus.getVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested seriesStatus(like a template) with default values (nothing persisted)
    // GET [contextPath][api]/metadata/ny-arkivdelstatus
    @ApiOperation(value = "Creates a suggested SeriesStatus", response = SeriesStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SeriesStatus codes found",
                    response = SeriesStatus.class),
            @ApiResponse(code = 404, message = "No SeriesStatus found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = NEW_FONDS_STATUS)
    public ResponseEntity<MetadataHateoas> getSeriesStatusTemplate(HttpServletRequest request) {
        SeriesStatus seriesStatus = new SeriesStatus();
        seriesStatus.setCode(TEMPLATE_FONDS_STATUS_CODE);
        seriesStatus.setDescription(TEMPLATE_FONDS_STATUS_DESCRIPTION);
        MetadataHateoas metadataHateoas = new MetadataHateoas(seriesStatus);
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a arkivdelstatus
    // PUT [contextPath][api]/metatdata/arkivdelstatus/
    @ApiOperation(value = "Updates a SeriesStatus object", notes = "Returns the newly" +
            " updated SeriesStatus object after it is persisted to the database", response = SeriesStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SeriesStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = FONDS_STATUS + SLASH + FONDS_STATUS)
    public ResponseEntity<MetadataHateoas> updateSeriesStatus(@RequestBody SeriesStatus seriesStatus,
                                                              HttpServletRequest request)
            throws NikitaException {
        seriesStatusService.update(seriesStatus);
        MetadataHateoas metadataHateoas = new MetadataHateoas(seriesStatus);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }
}
