package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.ClassificationSystem;
import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.hateoas.ClassHateoas;
import nikita.model.noark5.v4.hateoas.ClassificationSystemHateoas;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaEntityNotFoundException;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IClassHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IClassificationSystemHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IClassService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityDeletedEvent;
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

    private IClassService classService;
    private IClassHateoasHandler classHateoasHandler;
    private IClassificationSystemHateoasHandler classificationSystemHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public ClassHateoasController(IClassService classService,
                                  IClassHateoasHandler classHateoasHandler,
                                  IClassificationSystemHateoasHandler classificationSystemHateoasHandler,
                                  ApplicationEventPublisher applicationEventPublisher) {
        this.classService = classService;
        this.classHateoasHandler = classHateoasHandler;
        this.classificationSystemHateoasHandler = classificationSystemHateoasHandler;
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
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
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
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
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
                ClassHateoas((ArrayList<INikitaEntity>) (ArrayList)
                classService.findClassByOwnerPaginated(top, skip));
        classHateoasHandler.addLinks(classHateoas, request, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(classHateoas);
    }

    // Delete a Class identified by systemID
    // DELETE [contextPath][api]/arkivstruktur/dokumentobjekt/{systemId}/
    @ApiOperation(value = "Deletes a single Class entity identified by systemID", response = HateoasNoarkObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parent entity (DocumentDescription or Record) returned", response = HateoasNoarkObject.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<HateoasNoarkObject> deleteClassBySystemId(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response,
            @ApiParam(name = "systemID",
                    value = "systemID of the class to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        Class klass = classService.findBySystemId(systemID);
        NoarkEntity parentEntity = klass.chooseParent();
        classService.deleteEntity(systemID);
        HateoasNoarkObject hateoasNoarkObject;
        if (parentEntity instanceof Class) {
            hateoasNoarkObject = new ClassHateoas(parentEntity);
            classHateoasHandler.addLinks(hateoasNoarkObject, request, new Authorisation());
        }
        else if (parentEntity instanceof ClassificationSystem) {
            hateoasNoarkObject = new ClassificationSystemHateoas(parentEntity);
            classificationSystemHateoasHandler.addLinks(hateoasNoarkObject, request, new Authorisation());
        }
        else {
            throw new NikitaException("Internal error. Could process" + request.getRequestURI());
        }
        applicationEventPublisher.publishEvent(new AfterNoarkEntityDeletedEvent(this, klass));
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(hateoasNoarkObject);
    }
}
