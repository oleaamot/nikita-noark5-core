package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.DocumentDescription;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentDescriptionService;
import no.arkivlab.hioa.nikita.webapp.web.model.hateoas.DocumentDescriptionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + "/" + N5ResourceMappings.DOCUMENT_DESCRIPTION)
public class DocumentDescriptionHateoasController {

    @Autowired
    IDocumentDescriptionService documentDescriptionService;



    // API - All POST Requests (CRUD - CREATE) {"title": "Test tittel", "description": "Test description", "documentMedium":"Elektronisk arkiv"}
    @ApiOperation(value = "Creates a new documentDescription object", notes = "Returns a complete list of users details with a date of last modification.", response = DocumentDescription.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "DocumentDescription object successfully created", response = DocumentDescription.class),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public DocumentDescription save(/* @ApiParam(name = "userName", value = "Alphanumeric login to the application", required = true) */@RequestBody DocumentDescription documentDescription) {
        return documentDescriptionService.save(documentDescription);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<DocumentDescription> findAll(final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<DocumentDescription> documentDescription = documentDescriptionService.findByOwnedBy(loggedInUser);

        /*
        PersonResourceAssembler assembler = new PersonResourceAssembler();
        List<PersonResource> resources = assembler.toResources(people);
        // Resources allows to add links once for the entire list
        // provides the list as content attribute
        Resources<DocumentDescriptionResource> wrapped = new Resources<DocumentDescriptionResource>(resources, linkTo(methodOn(DocumentDescriptionController.class, documentDescription)).withSelfRel()
*/

        return documentDescription;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DocumentDescriptionResource findOne(@PathVariable("id") final Long id) {
        DocumentDescription documentDescription = documentDescriptionService.findById(id);

        // Handle null content!!!
        DocumentDescriptionResource documentDescriptionResource = new DocumentDescriptionResource (documentDescription);
/*
        if (documentDescriptionService.hasChildren(documentDescription)==true) {
            // add links to children
        }
        if (documentDescriptionService.hasSeries(documentDescription)==true) {
            // add links to children
        }
*/
        documentDescriptionResource.add(
                linkTo(
                        methodOn(DocumentDescriptionHateoasController.class, documentDescription).findOne(documentDescription.getId())
                ).withSelfRel()//.withRel()
        );


        // Look at https://github.com/opencredo/spring-hateoas-sample/blob/master/src/main/java/com/opencredo/demo/hateoas/api/AuthorResourceAssembler.java
        //https://opencredo.com/hal-hypermedia-api-spring-hateoas/
        // HEre yo could add links to documentDescriptionCreator, parentDocumentDescription , associated series
        //Resources<DocumentDescriptionResource> wrapped = new Resources<DocumentDescriptionResource>(resources, linkTo(methodOn(DocumentDescriptionController.class, documentDescription)).withSelfRel()

        return documentDescriptionResource;
    }
}
