package no.arkivlab.hioa.nikita.webapp.web.hateoas.assemblers;

import nikita.config.Constants;
import nikita.model.application.ApplicationDetails;
import no.arkivlab.hioa.nikita.webapp.web.controller.ApplicationController;
import no.arkivlab.hioa.nikita.webapp.web.model.hateoas.ApplicationDetailsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ApplicationDetailsResourceAssembler extends ResourceAssemblerSupport<ApplicationDetails, ApplicationDetailsResource> {

    @Autowired
    Environment env;

    ApplicationDetailsResourceAssembler() {
        super(ApplicationDetails.class, ApplicationDetailsResource.class);
    }

    // TODO: Think about how you will handle this is exception occurs or some values are set to ""
    // TODO: Maybe test coverage is enough
    @Override
    public ApplicationDetailsResource toResource(ApplicationDetails applicationDetails) {

        /*
        //resource.add(linkTo(methodOn(ApplicationController.class).getApplicationDetailsBooks(ApplicationDetails.getApplicationDetailsId())).withRel("books"));

        resource.add(linkTo(applicationDetails).withRel(Constants.NOARK_CONFORMANCE_FONDS_STRUCTURE_REL));
        resource.add(new Link(linkFondsStructure).withRel(Constants.NOARK_CONFORMANCE_FONDS_STRUCTURE_REL));
        resource.add(new Link(linkCaseHandling).withRel(Constants.NOARK_CONFORMANCE_CASE_HANDLING_REL));

        return resource;

        */
        return null;
    }

    @Override
    protected ApplicationDetailsResource instantiateResource(ApplicationDetails applicationDetails) {
        return new ApplicationDetailsResource(applicationDetails);
    }

}