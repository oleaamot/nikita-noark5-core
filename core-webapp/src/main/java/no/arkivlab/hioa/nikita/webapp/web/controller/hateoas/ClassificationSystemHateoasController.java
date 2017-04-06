package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.ClassificationSystem;
import nikita.model.noark5.v4.hateoas.ClassHateoas;
import nikita.model.noark5.v4.hateoas.ClassificationSystemHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaEntityNotFoundException;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IClassHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IClassificationSystemHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IClassificationSystemService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CLASSIFICATION_SYSTEM;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class ClassificationSystemHateoasController {

    private IClassificationSystemService classificationSystemService;
    private IClassificationSystemHateoasHandler classificationSystemHateoasHandler;
    private IClassHateoasHandler classHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public ClassificationSystemHateoasController(IClassificationSystemService classificationSystemService,
                                                 IClassificationSystemHateoasHandler classificationSystemHateoasHandler,
                                                 IClassHateoasHandler classHateoasHandler,
                                                 ApplicationEventPublisher applicationEventPublisher) {
        this.classificationSystemService = classificationSystemService;
        this.classificationSystemHateoasHandler = classificationSystemHateoasHandler;
        this.classHateoasHandler = classHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
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
    @Timed
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
        classificationSystemHateoasHandler.addLinks(classificationSystemHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdClassificationSystem));
        return ResponseEntity.status(HttpStatus.CREATED)
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
    @Timed
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
        classHateoasHandler.addLinks(classHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdClass));
        return ResponseEntity.status(HttpStatus.CREATED)
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
            throw new NikitaEntityNotFoundException(classificationSystemId);
        }
        ClassificationSystemHateoas classificationSystemHateoas = new ClassificationSystemHateoas(classificationSystem);
        classificationSystemHateoasHandler.addLinks(classificationSystemHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
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
    @Timed
    @RequestMapping(value = CLASSIFICATION_SYSTEM, method = RequestMethod.GET)
    public ResponseEntity<ClassificationSystemHateoas> findAllClassificationSystem(
            HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        ClassificationSystemHateoas classificationSystemHateoas = new
                ClassificationSystemHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                classificationSystemService.findClassificationSystemByOwnerPaginated(top, skip));
        classificationSystemHateoasHandler.addLinks(classificationSystemHateoas, request, new Authorisation());
        return new ResponseEntity<>(classificationSystemHateoas, HttpStatus.OK);
    }
}
