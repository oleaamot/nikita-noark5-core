package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.application.ApplicationDetails;
import org.springframework.hateoas.Resource;

public class ApplicationDetailsResource extends Resource<ApplicationDetails> {

    public ApplicationDetailsResource(ApplicationDetails applicationDetails) {
        super(applicationDetails);
    }
}
