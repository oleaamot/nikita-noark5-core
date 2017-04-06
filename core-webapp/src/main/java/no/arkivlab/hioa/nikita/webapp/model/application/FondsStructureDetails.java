package no.arkivlab.hioa.nikita.webapp.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import no.arkivlab.hioa.nikita.webapp.util.serialisers.APIDetailsSerializer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

@JsonSerialize(using = APIDetailsSerializer.class)
public class FondsStructureDetails extends APIDetails {

    public FondsStructureDetails() {
        super();

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        // Add support for fonds object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS + SLASH,
                true
        ));

        // Add support for new-fonds
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + NEW_FONDS,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + NEW_FONDS + SLASH,
                false
        ));

        // Add support for fondsCreator object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS_CREATOR,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS_CREATOR + SLASH,
                true
        ));

        // Add support for series object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH,
                true
        ));

        // Add support for classification_system object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH +
                        CLASSIFICATION_SYSTEM,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASSIFICATION_SYSTEM + SLASH,
                true
        ));

        // Add support for class object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH,
                true
        ));

        // Add support for file object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH,
                true
        ));

        // Add support for registration object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH,
                true
        ));

        // Add support for basic_registration object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + BASIC_RECORD,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + BASIC_RECORD + SLASH,
                true
        ));

        // Add support for document_description object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH +
                        DOCUMENT_DESCRIPTION,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH,
                true
        ));

        // Add support for document_object object
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH,
                true
        ));
    }
}
