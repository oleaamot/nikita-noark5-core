package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.hateoas.ClassHateoas;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.exceptions.NikitaEntityNotFoundException;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IClassHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IClassService;
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
import static nikita.config.N5ResourceMappings.CLASS;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
public class ClassHateoasController {

    IClassService classService;
    IClassHateoasHandler classHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public ClassHateoasController(IClassService classService,
                                  IClassHateoasHandler classHateoasHandler,
                                  ApplicationEventPublisher applicationEventPublisher) {
        this.classService = classService;
        this.classHateoasHandler = classHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All POST Requests (CRUD - CREATE)
    // POST [contextPath][api]/arkivstruktur/klassifikasjonsystem/{systemID}/ny-underklass
    @ApiOperation(value = "Persists a Class object associated with the (other) given Class systemId",
            notes = "Returns the newly created class object after it was associated with a class" +
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
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + "classificationSystemSystemId" +
            RIGHT_PARENTHESIS + SLASH + NEW_SUB_CLASS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<ClassHateoas> createClassAssociatedWithClassificationSystem(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "classificationSystemSystemId",
                    value = "systemId of classificationSystem to associate the klass with.",
                    required = true)
            @PathVariable String classSystemId,
            @ApiParam(name = "klass",
                    value = "Incoming class object",
                    required = true)
            @RequestBody Class klass) throws NikitaException {
        Class createdClass = classService.createClassAssociatedWithClass(classSystemId, klass);
        ClassHateoas classHateoas = new ClassHateoas(createdClass);
        classHateoasHandler.addLinks(classHateoas, request, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdClass));
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(createdClass.getVersion().toString())
                .body(classHateoas);
    }

    // API - All GET Requests (CRUD - READ)

    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<ClassHateoas> findOne(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemId",
                    value = "systemId of class to retrieve.",
                    required = true)
            @PathVariable("systemID") final String classSystemId) {
        Class klass = classService.findBySystemId(classSystemId);
        if (klass == null) {
            throw new NikitaEntityNotFoundException(classSystemId);
        }
        ClassHateoas classHateoas = new ClassHateoas(klass);
        classHateoasHandler.addLinks(classHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(klass.getVersion().toString())
                .body(classHateoas);
    }

    @ApiOperation(value = "Retrieves multiple Class entities limited by ownership rights", notes = "The field skip" +
            "tells how many Class rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = ClassHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Class list found",
                    response = ClassHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ClassHateoas> findAllClass(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        ClassHateoas classHateoas = new
                ClassHateoas((ArrayList<INoarkSystemIdEntity>) (ArrayList)
                classService.findClassByOwnerPaginated(top, skip));
        classHateoasHandler.addLinks(classHateoas, request, new Authorisation());
        return new ResponseEntity<>(classHateoas, HttpStatus.OK);
    }
}
