package no.arkivlab.hioa.nikita.webapp.web.controller.importAPI;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.hateoas.RegistryEntryHateoas;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.ICaseFileImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.Constants.API_MESSAGE_INTERNAL_SERVER_ERROR;
import static nikita.config.Constants.API_MESSAGE_UNAUTHORISED_FOR_USER;
import static nikita.config.N5ResourceMappings.CASE_FILE;


@RestController
@RequestMapping(value = IMPORT_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + CASE_FILE)
public class CaseFileImportController {

    @Autowired
    ICaseFileImportService caseFileImportService;

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a RegistryEntry object associated with the given Series systemId",
            notes = "Returns the newly created record object after it was associated with a File object and " +
                    "persisted to the database", response = RegistryEntryHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 201, message = "RegistryEntry " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type RegistryEntry"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS + SLASH
            + NEW_REGISTRY_ENTRY)
    public ResponseEntity<RegistryEntryHateoas> createRegistryEntryAssociatedWithFile(
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the record with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "RegistryEntry",
                    value = "Incoming registryEntry object",
                    required = true)
            @RequestBody RegistryEntry registryEntry)  throws NikitaException {
        RegistryEntryHateoas registryEntryHateoas =
                new RegistryEntryHateoas(caseFileImportService.createRegistryEntryAssociatedWithCaseFile(
                        fileSystemId, registryEntry));
        return new ResponseEntity<> (registryEntryHateoas, HttpStatus.CREATED);
    }
}
