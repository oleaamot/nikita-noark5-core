package no.arkivlab.hioa.nikita.webapp.web.controller.importAPI;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.hateoas.DocumentDescriptionHateoas;
import nikita.model.noark5.v4.hateoas.RegistryEntryHateoas;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IRegistryEntryImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.REGISTRY_ENTRY;

@RestController
@RequestMapping(value = IMPORT_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY)
public class RegistryEntryImportController {

    @Autowired
    IRegistryEntryImportService registryEntryImportService;

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a DocumentDescription object associated with the given RegistryEntry systemId",
            notes = "Returns the newly created documentDescription object after it was associated with a " +
                    "RegistryEntry object and persisted to the database", response = DocumentDescriptionHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentDescription " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 201, message = "DocumentDescription " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = DocumentDescriptionHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type RegistryEntry"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = LEFT_PARENTHESIS + "recordSystemId" + RIGHT_PARENTHESIS +
            SLASH + NEW_DOCUMENT_DESCRIPTION)
    public ResponseEntity<DocumentDescriptionHateoas>
    createDocumentDescriptionAssociatedWithRegistryEntry(
            @ApiParam(name = "recordSystemId",
                    value = "systemId of record/registryEntry to associate the documentDescription with.",
                    required = true)
            @PathVariable String recordSystemId,
            @ApiParam(name = "documentDescription",
                    value = "Incoming documentDescription object",
                    required = true)
            @RequestBody DocumentDescription documentDescription)
            throws NikitaException {
        DocumentDescriptionHateoas documentDescriptionHateoas =
                new DocumentDescriptionHateoas(
                        registryEntryImportService.createDocumentDescriptionAssociatedWithRegistryEntry(recordSystemId,
                                documentDescription));
        return new ResponseEntity<>(documentDescriptionHateoas, HttpStatus.CREATED);
    }
}
