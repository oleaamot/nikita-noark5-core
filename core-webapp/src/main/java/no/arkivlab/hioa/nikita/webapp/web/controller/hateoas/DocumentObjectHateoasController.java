package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_OBJECT;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT)
public class DocumentObjectHateoasController {

    @Autowired
    IDocumentObjectService documentObjectService;

    // API - All GET Requests (CRUD - READ)

    @ApiOperation(value = "Retrieves a single DocumentObject entity given a systemId", response = DocumentObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "DocumentObject returned", response = DocumentObject.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<DocumentObjectHateoas> findOneDocumentObjectBySystemId(
            @ApiParam(name = "systemID",
                    value = "systemID of the documentObject to retrieve",
                    required = true)
            @PathVariable("systemID") final String documentObjectSystemId) {
        DocumentObjectHateoas documentObjectHateoas = new
                DocumentObjectHateoas(documentObjectService.findBySystemId(documentObjectSystemId));
        return new ResponseEntity<>(documentObjectHateoas, HttpStatus.CREATED);
    }
}
