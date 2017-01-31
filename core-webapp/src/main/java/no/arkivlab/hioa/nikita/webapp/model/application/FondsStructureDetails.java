package no.arkivlab.hioa.nikita.webapp.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import no.arkivlab.hioa.nikita.webapp.util.serialisers.FondsStructureDetailsSerializer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.Set;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

@JsonSerialize(using = FondsStructureDetailsSerializer.class)
public class FondsStructureDetails {


    private Set<FondsStructureDetail> fondsStructureDetails = new HashSet<>();

    public FondsStructureDetails() {

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        // Add support for fonds object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS,
                true,
                true
        ));

        // Add support for new-fonds
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + NEW_FONDS,
                NOARK_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + NEW_FONDS,
                false,
                true
        ));

        // Add support for fondsCreator object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS_CREATOR
                        + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS_CREATOR,
                true,
                true
        ));

        // Add support for series object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES,
                true,
                true
        ));

        // Add support for classification_system object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH +
                        CLASSIFICATION_SYSTEM + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASSIFICATION_SYSTEM,
                true,
                true
        ));

        // Add support for class object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS,
                true,
                true
        ));

        // Add support for file object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE,
                true,
                true
        ));

        // Add support for registration object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION
                        + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION,
                true,
                true
        ));

        // Add support for basic_registration object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + BASIC_RECORD +
                        SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + BASIC_RECORD,
                true,
                true
        ));

        // Add support for document_description object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH +
                        DOCUMENT_DESCRIPTION + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + DOCUMENT_DESCRIPTION,
                true,
                true
        ));

        // Add support for document_object object
        fondsStructureDetails.add(new FondsStructureDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT
                        + SLASH,
                NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + DOCUMENT_OBJECT,
                true,
                true
        ));
    }

    public Set<FondsStructureDetail> getFondsStructureDetails() {
        return fondsStructureDetails;
    }
}
