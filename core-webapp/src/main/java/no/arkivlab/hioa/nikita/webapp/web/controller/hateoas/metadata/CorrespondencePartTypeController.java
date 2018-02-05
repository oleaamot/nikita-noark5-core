package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ICorrespondencePartTypeService;
import no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.NoarkController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class CorrespondencePartTypeController extends NoarkController {

    private ICorrespondencePartTypeService correspondencePartTypeService;
    private IMetadataHateoasHandler metadataHateoasHandler;

    public CorrespondencePartTypeController(ICorrespondencePartTypeService correspondencePartTypeService,
                                            IMetadataHateoasHandler metadataHateoasHandler) {
        this.correspondencePartTypeService = correspondencePartTypeService;
        this.metadataHateoasHandler = metadataHateoasHandler;
    }

    // API - All POST Requests (CRUD - CREATE)
    // Creates a new korrespondanseparttype
    // POST [contextPath][api]/metadata/korrespondanseparttype/ny-korrespondanseparttype
    @ApiOperation(value = "Persists a new CorrespondencePartType object", notes = "Returns the newly" +
            " created CorrespondencePartType object after it is persisted to the database", response = CorrespondencePartType.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartType " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartType.class),
            @ApiResponse(code = 201, message = "CorrespondencePartType " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartType.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = CORRESPONDENCE_PART_TYPE + SLASH + NEW_CORRESPONDENCE_PART_TYPE)
    public ResponseEntity<MetadataHateoas> createCorrespondencePartType(
            HttpServletRequest request,
            @RequestBody CorrespondencePartType correspondencePartType)
            throws NikitaException {
        correspondencePartTypeService.createNewCorrespondencePartType(correspondencePartType);
        MetadataHateoas metadataHateoas = new MetadataHateoas(correspondencePartType);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(correspondencePartType.getVersion().toString())
                .body(metadataHateoas);
    }
    // API - All GET Requests (CRUD - READ)
    // Retrieves all correspondencePartType
    // GET [contextPath][api]/metadata/korrespondanseparttype/
    @ApiOperation(value = "Retrieves all CorrespondencePartType ", response = CorrespondencePartType.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartType codes found",
                    response = CorrespondencePartType.class),
            @ApiResponse(code = 404, message = "No CorrespondencePartType found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = CORRESPONDENCE_PART_TYPE)
    public ResponseEntity<MetadataHateoas> findAll(HttpServletRequest request) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>)
                        (List) correspondencePartTypeService.findAll(),
                CORRESPONDENCE_PART_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // Retrieves a given correspondencePartType identified by a systemId
    // GET [contextPath][api]/metadata/korrespondanseparttype/{systemId}
    @ApiOperation(value = "Gets correspondencePartType identified by its systemId", notes = "Returns the requested " +
            " correspondencePartType object", response = CorrespondencePartType.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartType " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartType.class),
            @ApiResponse(code = 201, message = "CorrespondencePartType " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CorrespondencePartType.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMENTED)})
    @Counted
    @Timed
    @RequestMapping(value = CORRESPONDENCE_PART_TYPE + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<MetadataHateoas> findBySystemId(@PathVariable("systemID") final String systemId,
                                                          HttpServletRequest request) {
        CorrespondencePartType correspondencePartType = correspondencePartTypeService.findBySystemId(systemId);
        MetadataHateoas metadataHateoas = new MetadataHateoas(correspondencePartType);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(correspondencePartType.getVersion().toString())
                .body(metadataHateoas);
    }

    // Create a suggested correspondencePartType(like a template) with default values (nothing persisted)
    // GET [contextPath][api]/metadata/ny-korrespondanseparttype
    @ApiOperation(value = "Creates a suggested CorrespondencePartType", response = CorrespondencePartType.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartType codes found",
                    response = CorrespondencePartType.class),
            @ApiResponse(code = 404, message = "No CorrespondencePartType found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = NEW_CORRESPONDENCE_PART_TYPE)
    public ResponseEntity<MetadataHateoas> getCorrespondencePartTypeTemplate(HttpServletRequest request) {
        CorrespondencePartType correspondencePartType = new CorrespondencePartType();
        correspondencePartType.setCode(TEMPLATE_FONDS_STATUS_CODE);
        correspondencePartType.setDescription(TEMPLATE_FONDS_STATUS_DESCRIPTION);
        MetadataHateoas metadataHateoas = new MetadataHateoas(correspondencePartType);
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(metadataHateoas);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a korrespondanseparttype
    // PUT [contextPath][api]/metatdata/korrespondanseparttype/
    @ApiOperation(value = "Updates a CorrespondencePartType object", notes = "Returns the newly" +
            " updated CorrespondencePartType object after it is persisted to the database", response = CorrespondencePartType.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartType " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CorrespondencePartType.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = CORRESPONDENCE_PART_TYPE + UNIT + SLASH + LEFT_PARENTHESIS +
            SYSTEM_ID + RIGHT_PARENTHESIS)
    public ResponseEntity<MetadataHateoas> updateCorrespondencePartTypeUnit(
            @RequestBody CorrespondencePartType correspondencePartType,
            HttpServletRequest request)
            throws NikitaException {
        CorrespondencePartType updatedCorrespondencePartType = correspondencePartTypeService.update(correspondencePartType);
        MetadataHateoas metadataHateoas = new MetadataHateoas(updatedCorrespondencePartType);
        metadataHateoasHandler.addLinks(metadataHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(correspondencePartType.getVersion().toString())
                .body(metadataHateoas);
    }

    // Delete a correspondencePartType identified by systemID
    // DELETE [contextPath][api]/sakarkiv/korrespondanseparttype/{kode}/
    @ApiOperation(value = "Deletes a single CorrespondencePartType entity identified by kode")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CorrespondencePartType deleted"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + CODE + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deletecorrespondencePartTypeByCode(
            @ApiParam(name = "kode",
                    value = "kode of the correspondencePartType to delete",
                    required = true)
            @PathVariable("kode") final String kode) {
        correspondencePartTypeService.deleteEntity(kode);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\" : \"Success\"}");
    }
}
