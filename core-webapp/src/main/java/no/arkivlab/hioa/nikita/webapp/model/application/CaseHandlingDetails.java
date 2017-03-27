package no.arkivlab.hioa.nikita.webapp.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import no.arkivlab.hioa.nikita.webapp.util.serialisers.APIDetailsSerializer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CASE_FILE;
import static nikita.config.N5ResourceMappings.REGISTRY_ENTRY;

@JsonSerialize(using = APIDetailsSerializer.class)
public class CaseHandlingDetails extends APIDetails {

    public CaseHandlingDetails() {
        super();
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        // Add support for caeFile object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH,
                true
        ));

        // Add support for registryEntry object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + REGISTRY_ENTRY
                        + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_CASE_HANDLING_PATH + SLASH + REGISTRY_ENTRY + SLASH,
                true
        ));
    }
}
