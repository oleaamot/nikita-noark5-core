package no.arkivlab.hioa.nikita.webapp.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import no.arkivlab.hioa.nikita.webapp.util.serialisers.APIDetailsSerializer;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;
import static nikita.config.N5ResourceMappings.NEW_DOCUMENT_MEDIUM;

@JsonSerialize(using = APIDetailsSerializer.class)
public class MetadataDetails extends APIDetails {

    public MetadataDetails(String publicUrlPath) {
        super();
        // Add support for DocumentMedium
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + DOCUMENT_MEDIUM,
                publicUrlPath + NOARK_METADATA_PATH + SLASH + DOCUMENT_MEDIUM + SLASH,
                true
        ));

        // Add support for new DocumentMedium
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + NEW_DOCUMENT_MEDIUM,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH + NEW_DOCUMENT_MEDIUM + SLASH,
                true
        ));

        // Add support for FondsStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + FONDS_STATUS,
                REL_METADATA_FONDS_STATUS,
                true
        ));

        // Add support for new FondsStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + NEW_FONDS_STATUS,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH + NEW_FONDS_STATUS + SLASH,
                true
        ));

        // Add support for CorrespondencePart
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + CORRESPONDENCE_PART_TYPE,
                REL_METADATA_CORRESPONDENCE_PART_TYPE,
                true
        ));

        // Add support for new CorrespondencePart
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + NEW_CORRESPONDENCE_PART_TYPE,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH + NEW_CORRESPONDENCE_PART_TYPE + SLASH,
                true
        ));
    }
}
