package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.Record;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRecordService;
import no.arkivlab.hioa.nikita.webapp.web.model.hateoas.RecordResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + "/" + N5ResourceMappings.REGISTRATION)
public class RecordHateoasController {

    @Autowired
    IRecordService recordService;



    // API - All POST Requests (CRUD - CREATE) {"title": "Test tittel", "description": "Test description", "documentMedium":"Elektronisk arkiv"}
    @ApiOperation(value = "Creates a new record object", notes = "Returns a complete list of users details with a date of last modification.", response = Record.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Record object successfully created", response = Record.class),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public Record save(/* @ApiParam(name = "userName", value = "Alphanumeric login to the application", required = true) */@RequestBody Record record) {
        return recordService.save(record);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Record> findAll(final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<Record> record = recordService.findByOwnedBy(loggedInUser);

        /*
        PersonResourceAssembler assembler = new PersonResourceAssembler();
        List<PersonResource> resources = assembler.toResources(people);
        // Resources allows to add links once for the entire list
        // provides the list as content attribute
        Resources<RecordResource> wrapped = new Resources<RecordResource>(resources, linkTo(methodOn(RecordController.class, record)).withSelfRel()
*/

        return record;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RecordResource findOne(@PathVariable("id") final Long id) {
        Record record = recordService.findById(id);

        // Handle null content!!!
        RecordResource recordResource = new RecordResource (record);
/*
        if (recordService.hasChildren(record)==true) {
            // add links to children
        }
        if (recordService.hasSeries(record)==true) {
            // add links to children
        }
*/
        recordResource.add(
                linkTo(
                        methodOn(RecordHateoasController.class, record).findOne(record.getId())
                ).withSelfRel()//.withRel()
        );


        // Look at https://github.com/opencredo/spring-hateoas-sample/blob/master/src/main/java/com/opencredo/demo/hateoas/api/AuthorResourceAssembler.java
        //https://opencredo.com/hal-hypermedia-api-spring-hateoas/
        // HEre yo could add links to recordCreator, parentRecord , associated series
        //Resources<RecordResource> wrapped = new Resources<RecordResource>(resources, linkTo(methodOn(RecordController.class, record)).withSelfRel()

        return recordResource;
    }
}
