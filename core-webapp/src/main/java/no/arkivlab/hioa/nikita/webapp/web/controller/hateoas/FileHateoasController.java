package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.File;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFileService;
import no.arkivlab.hioa.nikita.webapp.web.model.hateoas.FileResource;
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
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + N5ResourceMappings.FILE)
public class FileHateoasController {

    @Autowired
    IFileService seriesService;

    // API - All POST Requests (CRUD - CREATE) {"title": "Test tittel", "description": "Test description", "documentMedium":"Elektronisk arkiv"}
    @ApiOperation(value = "Creates a new File object", notes = "Returns a complete list of users details with a date of last modification.", response = File.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "File object successfully created", response = File.class),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public File save(/* @ApiParam(name = "userName", value = "Alphanumeric login to the application", required = true) */@RequestBody File series) {
        return seriesService.save(series);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<File> findAll(final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<File> series = seriesService.findByOwnedBy(loggedInUser);

        /*
        PersonResourceAssembler assembler = new PersonResourceAssembler();
        List<PersonResource> resources = assembler.toResources(people);
        // Resources allows to add links once for the entire list
        // provides the list as content attribute
        Resources<FileResource> wrapped = new Resources<FileResource>(resources, linkTo(methodOn(FileController.class, series)).withSelfRel()
*/

        return series;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FileResource findOne(@PathVariable("id") final Long id) {
        File series = seriesService.findById(id);

        // Handle null content!!!
        FileResource seriesResource = new FileResource (series);

        /*
        if (seriesService.hasChildren(series)==true) {
            // add links to children
        }
        if (seriesService.hasFile(series)==true) {
            // add links to children
        }
        */

        seriesResource.add(
                linkTo(
                        methodOn(FileHateoasController.class, series).findOne(series.getId())
                ).withSelfRel()//.withRel()
        );


        // Look at https://github.com/opencredo/spring-hateoas-sample/blob/master/src/main/java/com/opencredo/demo/hateoas/api/AuthorResourceAssembler.java
        //https://opencredo.com/hal-hypermedia-api-spring-hateoas/
        // HEre yo could add links to seriesCreator, parentFile , associated series
        //Resources<FileResource> wrapped = new Resources<FileResource>(resources, linkTo(methodOn(FileController.class, series)).withSelfRel()

        return seriesResource;
    }
}
