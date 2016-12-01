package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.Fonds;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import no.arkivlab.hioa.nikita.webapp.web.model.hateoas.FondsResource;
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
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + N5ResourceMappings.CLASS)
public class ClassHateoasController {

    @Autowired
    IFondsService fondsService;



    // API - All POST Requests (CRUD - CREATE) {"title": "Test tittel", "description": "Test description", "documentMedium":"Elektronisk arkiv"}
    @ApiOperation(value = "Creates a new fonds object", notes = "Returns a complete list of users details with a date of last modification.", response = Fonds.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Fonds object successfully created", response = Fonds.class),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public Fonds save(/* @ApiParam(name = "userName", value = "Alphanumeric login to the application", required = true) */@RequestBody Fonds fonds) {
        return fondsService.save(fonds);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Fonds> findAll(final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<Fonds> fonds = fondsService.findByOwnedBy(loggedInUser);

        /*
        PersonResourceAssembler assembler = new PersonResourceAssembler();
        List<PersonResource> resources = assembler.toResources(people);
        // Resources allows to add links once for the entire list
        // provides the list as content attribute
        Resources<FondsResource> wrapped = new Resources<FondsResource>(resources, linkTo(methodOn(FondsController.class, fonds)).withSelfRel()
*/

        return fonds;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FondsResource findOne(@PathVariable("id") final Long id) {
        Fonds fonds = fondsService.findById(id);

        // Handle null content!!!
        FondsResource fondsResource = new FondsResource (fonds);
/*
        if (fondsService.hasChildren(fonds)==true) {
            // add links to children
        }
        if (fondsService.hasSeries(fonds)==true) {
            // add links to children
        }
*/
        fondsResource.add(
                linkTo(
                        methodOn(ClassHateoasController.class, fonds).findOne(fonds.getId())
                ).withSelfRel()//.withRel()
        );


        // Look at https://github.com/opencredo/spring-hateoas-sample/blob/master/src/main/java/com/opencredo/demo/hateoas/api/AuthorResourceAssembler.java
        //https://opencredo.com/hal-hypermedia-api-spring-hateoas/
        // HEre yo could add links to fondsCreator, parentFonds , associated series
        //Resources<FondsResource> wrapped = new Resources<FondsResource>(resources, linkTo(methodOn(FondsController.class, fonds)).withSelfRel()

        return fondsResource;
    }
}
