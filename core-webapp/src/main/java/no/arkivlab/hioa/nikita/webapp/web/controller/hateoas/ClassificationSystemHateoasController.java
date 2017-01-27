package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.util.exceptions.NikitaException;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.ClassificationSystem;
import nikita.model.noark5.v4.hateoas.ClassHateoas;
import nikita.model.noark5.v4.hateoas.ClassificationSystemHateoas;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IClassificationSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CLASSIFICATION_SYSTEM;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH)
public class ClassificationSystemHateoasController {

    @Autowired
    IClassificationSystemService classificationSystemService;

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
    @RequestMapping(method = RequestMethod.POST, value = NEW_CLASSIFICATION_SYSTEM)
    public ResponseEntity<ClassificationSystemHateoas> createClassificationSystemAssociatedWithFile(
            @ApiParam(name = "classificationSystem",
                    value = "Incoming classificationSystem object",
                    required = true)
            @RequestBody ClassificationSystem classificationSystem)  throws NikitaException {
        ClassificationSystemHateoas classificationSystemHateoas = new ClassificationSystemHateoas(
                classificationSystemService.createNewClassificationSystem(classificationSystem));
        return new ResponseEntity<> (classificationSystemHateoas, HttpStatus.CREATED);
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
    @RequestMapping(method = RequestMethod.POST, value = CLASSIFICATION_SYSTEM + SLASH +  LEFT_PARENTHESIS +
            "classificationSystemSystemId" + RIGHT_PARENTHESIS + SLASH + NEW_RECORD)
    public ResponseEntity<ClassHateoas> createClassAssociatedWithClassificationSystem(
            @ApiParam(name = "classificationSystemSystemId",
                    value = "systemId of classificationSystem to associate the klass with.",
                    required = true)
            @PathVariable String classificationSystemSystemId,
            @ApiParam(name = "klass",
                    value = "Incoming class object",
                    required = true)
            @RequestBody Class klass)  throws NikitaException {
        ClassHateoas classHateoas = new ClassHateoas(
                classificationSystemService.createClassAssociatedWithClassificationSystem
                (classificationSystemSystemId, klass));
        return new ResponseEntity<> (classHateoas, HttpStatus.CREATED);
    }
    // API - All GET Requests (CRUD - READ)

    @RequestMapping(value = SLASH + "{id}", method = RequestMethod.GET)
    public ResponseEntity<ClassificationSystemHateoas> findOne(
            @ApiParam(name = "classificationSystemSystemId",
                    value = "systemId of classificationSystem to retrieve.",
                    required = true)
            @PathVariable("id") final Long id) {
        ClassificationSystem classificationSystem = classificationSystemService.findById(id);
        ClassificationSystemHateoas classificationSystemHateoas = new ClassificationSystemHateoas(classificationSystem);
        return new ResponseEntity<> (classificationSystemHateoas, HttpStatus.CREATED);
    }
}
