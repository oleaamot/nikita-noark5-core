package no.arkivlab.hioa.nikita.webapp.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import no.arkivlab.hioa.nikita.webapp.util.serialisers.APIDetailsSerializer;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CASE_FILE;
import static nikita.config.N5ResourceMappings.REGISTRY_ENTRY;

@JsonSerialize(using = APIDetailsSerializer.class)
@Component
public class CaseHandlingDetails extends APIDetails {

    public CaseHandlingDetails(String publicUrlPath) {
        super();
        // Add support for caeFile object
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE,
                NOARK_CONFORMANCE_REL + NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH,
                true
        ));

        // Add support for registryEntry object
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH + REGISTRY_ENTRY,
                NOARK_CONFORMANCE_REL + NOARK_CASE_HANDLING_PATH + SLASH + REGISTRY_ENTRY + SLASH,
                true
        ));
    }
}
