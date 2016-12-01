package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.DocumentObject;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentObjectService;
import no.arkivlab.hioa.nikita.webapp.web.model.hateoas.DocumentObjectResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nikita.config.Constants.NOARK_FONDS_STRUCTURE_PATH;
import static nikita.config.Constants.SLASH;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + N5ResourceMappings.DOCUMENT_OBJECT)
public class DocumentObjectHateoasController {

    @Autowired
    IDocumentObjectService documentObjectService;



    // API - All POST Requests (CRUD - CREATE) {"title": "Test tittel", "description": "Test description", "documentMedium":"Elektronisk arkiv"}
    @ApiOperation(value = "Creates a new documentObject object", notes = "Returns a complete list of users details with a date of last modification.", response = DocumentObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "DocumentObject object successfully created", response = DocumentObject.class),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public DocumentObject save(/* @ApiParam(name = "userName", value = "Alphanumeric login to the application", required = true) */@RequestBody DocumentObject documentObject) {
        return documentObjectService.save(documentObject);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<DocumentObject> findAll(final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<DocumentObject> documentObject = documentObjectService.findByOwnedBy(loggedInUser);

        /*
        PersonResourceAssembler assembler = new PersonResourceAssembler();
        List<PersonResource> resources = assembler.toResources(people);
        // Resources allows to add links once for the entire list
        // provides the list as content attribute
        Resources<DocumentObjectResource> wrapped = new Resources<DocumentObjectResource>(resources, linkTo(methodOn(DocumentObjectController.class, documentObject)).withSelfRel()
*/

        return documentObject;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DocumentObjectResource findOne(@PathVariable("id") final Long id) {
        DocumentObject documentObject = documentObjectService.findById(id);

        // Handle null content!!!
        DocumentObjectResource documentObjectResource = new DocumentObjectResource (documentObject);
/*
        if (documentObjectService.hasChildren(documentObject)==true) {
            // add links to children
        }
        if (documentObjectService.hasSeries(documentObject)==true) {
            // add links to children
        }
*/
        documentObjectResource.add(
                linkTo(
                        methodOn(DocumentObjectHateoasController.class, documentObject).findOne(documentObject.getId())
                ).withSelfRel()//.withRel()
        );


        // Look at https://github.com/opencredo/spring-hateoas-sample/blob/master/src/main/java/com/opencredo/demo/hateoas/api/AuthorResourceAssembler.java
        //https://opencredo.com/hal-hypermedia-api-spring-hateoas/
        // HEre yo could add links to documentObjectCreator, parentDocumentObject , associated series
        //Resources<DocumentObjectResource> wrapped = new Resources<DocumentObjectResource>(resources, linkTo(methodOn(DocumentObjectController.class, documentObject)).withSelfRel()

        return documentObjectResource;
    }
}
