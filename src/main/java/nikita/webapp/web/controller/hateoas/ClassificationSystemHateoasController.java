package nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.common.model.noark5.v4.Class;
import nikita.common.model.noark5.v4.ClassificationSystem;
import nikita.common.model.noark5.v4.hateoas.ClassHateoas;
import nikita.common.model.noark5.v4.hateoas.ClassificationSystemHateoas;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaException;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.application.FondsStructureDetails;
import nikita.webapp.hateoas.interfaces.IClassHateoasHandler;
import nikita.webapp.hateoas.interfaces.IClassificationSystemHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.application.ApplicationService;
import nikita.webapp.service.interfaces.IClassificationSystemService;
import nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
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
import static nikita.common.config.N5ResourceMappings.CLASSIFICATION_SYSTEM;
import static nikita.common.config.N5ResourceMappings.SYSTEM_ID;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class ClassificationSystemHateoasController extends NoarkController {

    private IClassificationSystemService classificationSystemService;
    private IClassificationSystemHateoasHandler classificationSystemHateoasHandler;
    private IClassHateoasHandler classHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;
    private ApplicationService applicationService;

    public ClassificationSystemHateoasController(IClassificationSystemService classificationSystemService,
                                                 IClassificationSystemHateoasHandler classificationSystemHateoasHandler,
                                                 IClassHateoasHandler classHateoasHandler,
                                                 ApplicationEventPublisher applicationEventPublisher,
                                                 ApplicationService applicationService) {

        this.classificationSystemService = classificationSystemService;
        this.classificationSystemHateoasHandler = classificationSystemHateoasHandler;
        this.classHateoasHandler = classHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
        this.applicationService = applicationService;
    }

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a ClassificationSystem object", notes = "Returns the newly created " +
            "classificationSystem object after it was persisted to the database",
            response = ClassificationSystemHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ClassificationSystem " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ClassificationSystemHateoas.class),
            @ApiResponse(code = 201, message = "ClassificationSystem " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = ClassificationSystemHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type ClassificationSystem"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(method = RequestMethod.POST, value = NEW_CLASSIFICATION_SYSTEM,
            consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<ClassificationSystemHateoas> createClassificationSystemAssociatedWithFile(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "classificationSystem",
                    value = "Incoming classificationSystem object",
                    required = true)
            @RequestBody ClassificationSystem classificationSystem) throws NikitaException {
        ClassificationSystem createdClassificationSystem =
                classificationSystemService.createNewClassificationSystem(classificationSystem);
        ClassificationSystemHateoas classificationSystemHateoas = new
                ClassificationSystemHateoas(createdClassificationSystem);
        classificationSystemHateoasHandler.addLinks(classificationSystemHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdClassificationSystem));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdClassificationSystem.getVersion().toString())
                .body(classificationSystemHateoas);
    }

    @ApiOperation(value = "Persists a Class object associated with the given ClassificationSystem systemId",
            notes = "Returns the newly created class object after it was associated with a classificationSystem " +
                    "object and persisted to the database", response = ClassHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Class " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = Class.class),
            @ApiResponse(code = 201, message = "Class " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = Class.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Class"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(method = RequestMethod.POST, value = CLASSIFICATION_SYSTEM + SLASH + LEFT_PARENTHESIS +
            "classificationSystemSystemId" + RIGHT_PARENTHESIS + SLASH + NEW_RECORD,
            consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<ClassHateoas> createClassAssociatedWithClassificationSystem(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "classificationSystemSystemId",
                    value = "systemId of classificationSystem to associate the klass with.",
                    required = true)
            @PathVariable String classificationSystemSystemId,
            @ApiParam(name = "klass",
                    value = "Incoming class object",
                    required = true)
            @RequestBody Class klass) throws NikitaException {
        Class createdClass =
                classificationSystemService.createClassAssociatedWithClassificationSystem(classificationSystemSystemId,
                        klass);
        ClassHateoas classHateoas = new ClassHateoas(createdClass);
        classHateoasHandler.addLinks(classHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdClass));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdClass.getVersion().toString())
                .body(classHateoas);
    }
    // API - All GET Requests (CRUD - READ)

    @RequestMapping(value = CLASSIFICATION_SYSTEM + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<ClassificationSystemHateoas> findOne(
            HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of classificationSystem to retrieve.",
                    required = true)
            @PathVariable("systemID") final String classificationSystemId) {
        ClassificationSystem classificationSystem = classificationSystemService.findBySystemId(classificationSystemId);
        if (classificationSystem == null) {
            throw new NoarkEntityNotFoundException(classificationSystemId);
        }
        ClassificationSystemHateoas classificationSystemHateoas = new ClassificationSystemHateoas(classificationSystem);
        classificationSystemHateoasHandler.addLinks(classificationSystemHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(classificationSystem.getVersion().toString())
                .body(classificationSystemHateoas);
    }

    @ApiOperation(value = "Retrieves multiple ClassificationSystem entities limited by ownership rights", notes = "The field skip" +
            "tells how many ClassificationSystem rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = ClassificationSystemHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ClassificationSystem list found",
                    response = ClassificationSystemHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = CLASSIFICATION_SYSTEM, method = RequestMethod.GET)
    public ResponseEntity<ClassificationSystemHateoas> findAllClassificationSystem(
            HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        ClassificationSystemHateoas classificationSystemHateoas = new
                ClassificationSystemHateoas((List<INikitaEntity>) (List)
                classificationSystemService.findClassificationSystemByOwnerPaginated(top, skip));
        classificationSystemHateoasHandler.addLinks(classificationSystemHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(classificationSystemHateoas);
    }

    // Delete a ClassificationSystem identified by systemID
    // DELETE [contextPath][api]/arkivstruktur/klassifikasjonsystem/{systemId}/
    @ApiOperation(value = "Deletes a single ClassificationSystem entity identified by systemID", response = FondsStructureDetails.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parent ApplicationDetails returned", response = FondsStructureDetails.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(value = SLASH + CLASSIFICATION_SYSTEM + SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<FondsStructureDetails> deleteClassificationSystemBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the ClassificationSystem to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        ClassificationSystem classificationSystem = classificationSystemService.findBySystemId(systemID);

        classificationSystemService.deleteEntity(systemID);
        applicationEventPublisher.publishEvent(new AfterNoarkEntityDeletedEvent(this, classificationSystem));
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(applicationService.getFondsStructureDetails());
    }

    // API - All PUT Requests (CRUD - UPDATE)
    // Update a ClassificationSystem
    // PUT [contextPath][api]/arkivstruktur/klassifikasjonsystem/{systemID}
    @ApiOperation(value = "Updates a ClassificationSystem object", notes = "Returns the newly" +
            " update ClassificationSystem object after it is persisted to the database", response = ClassificationSystemHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ClassificationSystem " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ClassificationSystemHateoas.class),
            @ApiResponse(code = 201, message = "ClassificationSystem " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = ClassificationSystemHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type ClassificationSystem"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted

    @RequestMapping(method = RequestMethod.PUT, value = CLASSIFICATION_SYSTEM + SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<ClassificationSystemHateoas> updateClassificationSystem(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemId of classificationSystem to update.",
                    required = true)
            @PathVariable("systemID") String systemID,
            @ApiParam(name = "classificationSystem",
                    value = "Incoming classificationSystem object",
                    required = true)
            @RequestBody ClassificationSystem classificationSystem) throws NikitaException {
        validateForUpdate(classificationSystem);

        ClassificationSystem updatedClassificationSystem = classificationSystemService.handleUpdate(systemID, parseETAG(request.getHeader(ETAG)), classificationSystem);
        ClassificationSystemHateoas classificationSystemHateoas = new ClassificationSystemHateoas(updatedClassificationSystem);
        classificationSystemHateoasHandler.addLinks(classificationSystemHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedClassificationSystem));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedClassificationSystem.getVersion().toString())
                .body(classificationSystemHateoas);
    }
}
