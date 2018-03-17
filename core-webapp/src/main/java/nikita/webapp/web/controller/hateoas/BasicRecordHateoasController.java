package nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.*;
import nikita.common.model.noark5.v4.Class;
import nikita.common.model.noark5.v4.hateoas.*;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.webapp.hateoas.interfaces.IBasicRecordHateoasHandler;
import nikita.webapp.hateoas.interfaces.IClassHateoasHandler;
import nikita.webapp.hateoas.interfaces.IFileHateoasHandler;
import nikita.webapp.hateoas.interfaces.ISeriesHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.interfaces.IBasicRecordService;
import nikita.webapp.web.events.AfterNoarkEntityDeletedEvent;
import nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.BASIC_RECORD;
import static nikita.common.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + BASIC_RECORD,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class BasicRecordHateoasController extends NoarkController {

    private IBasicRecordService basicRecordService;
    private IBasicRecordHateoasHandler basicRecordHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;
    private ISeriesHateoasHandler seriesHateoasHandler;
    private IFileHateoasHandler fileHateoasHandler;
    private IClassHateoasHandler classHateoasHandler;

    public BasicRecordHateoasController(IBasicRecordService basicRecordService,
                                        IBasicRecordHateoasHandler basicRecordHateoasHandler,
                                        ApplicationEventPublisher applicationEventPublisher,
                                        ISeriesHateoasHandler seriesHateoasHandler,
                                        IFileHateoasHandler fileHateoasHandler,
                                        IClassHateoasHandler classHateoasHandler) {
        this.basicRecordService = basicRecordService;
        this.basicRecordHateoasHandler = basicRecordHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.seriesHateoasHandler = seriesHateoasHandler;
        this.fileHateoasHandler = fileHateoasHandler;
        this.classHateoasHandler = classHateoasHandler;
    }

    // API - All GET Requests (CRUD - READ)

    @ApiOperation(value = "Retrieves a single BasicRecord entity given a systemId", response = BasicRecord.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord returned", response = BasicRecord.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<BasicRecordHateoas> findOneBasicRecordBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the basicRecord to retrieve",
                    required = true)
            @PathVariable("systemID") final String basicRecordSystemId) {
        BasicRecord createdBasicRecord = basicRecordService.findBySystemId(basicRecordSystemId);
        BasicRecordHateoas basicRecordHateoas = new BasicRecordHateoas(createdBasicRecord);
        basicRecordHateoasHandler.addLinks(basicRecordHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdBasicRecord.getVersion().toString())
                .body(basicRecordHateoas);
    }

    @ApiOperation(value = "Retrieves multiple BasicRecord entities limited by ownership rights", notes = "The field skip" +
            "tells how many BasicRecord rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = BasicRecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord list found",
                    response = BasicRecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BasicRecordHateoas> findAllBasicRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        BasicRecordHateoas basicRecordHateoas = new
                BasicRecordHateoas((List<INikitaEntity>) (List)
                basicRecordService.findBasicRecordByOwnerPaginated(top, skip));
        basicRecordHateoasHandler.addLinks(basicRecordHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(basicRecordHateoas);
    }

    // Delete a Record identified by systemID
    // DELETE [contextPath][api]/arkivstruktur/registrering/{systemId}/
    @ApiOperation(value = "Deletes a single Record entity identified by systemID", response = HateoasNoarkObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parent entity (DocumentDescription or Record) returned", response = HateoasNoarkObject.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<HateoasNoarkObject> deleteRecordBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the record to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        BasicRecord basicRecord = basicRecordService.findBySystemId(systemID);
        NoarkEntity parentEntity = basicRecord.chooseParent();
        HateoasNoarkObject hateoasNoarkObject;
        if (parentEntity instanceof Series) {
            hateoasNoarkObject = new SeriesHateoas(parentEntity);
            seriesHateoasHandler.addLinks(hateoasNoarkObject, new Authorisation());
        }
        else if (parentEntity instanceof File) {
            hateoasNoarkObject = new FileHateoas(parentEntity);
            fileHateoasHandler.addLinks(hateoasNoarkObject, new Authorisation());
        }
        else if (parentEntity instanceof Class) {
            hateoasNoarkObject = new ClassHateoas(parentEntity);
            classHateoasHandler.addLinks(hateoasNoarkObject, new Authorisation());
        }
        else {
            throw new NikitaException("Internal error. Could not process"
                    + request.getRequestURI());
        }
        basicRecordService.deleteEntity(systemID);
        applicationEventPublisher.publishEvent(new AfterNoarkEntityDeletedEvent(this, basicRecord));
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(hateoasNoarkObject);
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a BasicRecord
    // PUT [contextPath][api]/arkivstruktur/basisregistrering/{systemID}
    @ApiOperation(value = "Updates a BasicRecord object", notes = "Returns the newly" +
            " update BasicRecord object after it is persisted to the database", response = BasicRecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = BasicRecordHateoas.class),
            @ApiResponse(code = 201, message = "BasicRecord " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = BasicRecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type BasicRecord"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<BasicRecordHateoas> updateBasicRecord(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of basicRecord to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "basicRecord",
                    value = "Incoming basicRecord object",
                    required = true)
            @RequestBody BasicRecord basicRecord) throws NikitaException {
        validateForUpdate(basicRecord);

        BasicRecord updatedBasicRecord = basicRecordService.handleUpdate(systemID, parseETAG(request.getHeader(ETAG)), basicRecord);
        BasicRecordHateoas basicRecordHateoas = new BasicRecordHateoas(updatedBasicRecord);
        basicRecordHateoasHandler.addLinks(basicRecordHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedBasicRecord));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedBasicRecord.getVersion().toString())
                .body(basicRecordHateoas);
    }
}
