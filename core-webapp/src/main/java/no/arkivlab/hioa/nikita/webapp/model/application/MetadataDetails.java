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
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + DOCUMENT_MEDIUM,
                publicUrlPath + NOARK_METADATA_PATH + SLASH +
                        DOCUMENT_MEDIUM + SLASH,
                true
        ));

        // Add support for new DocumentMedium
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_DOCUMENT_MEDIUM,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_DOCUMENT_MEDIUM + SLASH,
                true
        ));

        // Add support for FondsStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + FONDS_STATUS,
                REL_METADATA_FONDS_STATUS,
                true
        ));

        // Add support for new FondsStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_FONDS_STATUS,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_FONDS_STATUS + SLASH,
                true
        ));

        // Add support for DocumentStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + DOCUMENT_STATUS,
                REL_METADATA_DOCUMENT_STATUS,
                true
        ));

        // Add support for DocumentType
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + DOCUMENT_TYPE,
                REL_METADATA_DOCUMENT_TYPE,
                true
        ));

        // Add support for new DocumentStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_DOCUMENT_STATUS,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_DOCUMENT_STATUS + SLASH,
                true
        ));

        // Add support for RegistryEntryStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + REGISTRY_ENTRY_STATUS,
                REL_METADATA_REGISTRY_ENTRY_STATUS,
                true
        ));

        // Add support for new RegistryEntryStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_REGISTRY_ENTRY_STATUS,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_REGISTRY_ENTRY_STATUS + SLASH,
                true
        ));

        // Add support for precedenceStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + PRECEDENCE_STATUS,
                REL_METADATA_PRECEDENCE_STATUS,
                true
        ));

        // Add support for new precedenceStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_PRECEDENCE_STATUS,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_PRECEDENCE_STATUS + SLASH,
                true
        ));

        // Add support for CorrespondencePart
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + CORRESPONDENCE_PART_TYPE,
                REL_METADATA_CORRESPONDENCE_PART_TYPE,
                true
        ));

        // Add support for new CorrespondencePart
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_CORRESPONDENCE_PART_TYPE,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_CORRESPONDENCE_PART_TYPE + SLASH,
                true
        ));

        // Add support for SignOffMethod
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + SIGN_OFF_METHOD,
                REL_METADATA_SIGN_OFF_METHOD,
                true
        ));

        // Add support for new SignOffMethod
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_SIGN_OFF_METHOD,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_SIGN_OFF_METHOD + SLASH,
                true
        ));

        // Add support for ElectronicSignatureSecurityLevel
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH +
                        ELECTRONIC_SIGNATURE_SECURITY_LEVEL,
                REL_METADATA_ELECTRONIC_SIGNATURE_SECURITY_LEVEL,
                true
        ));

        // Add support for new ElectronicSignatureSecurityLevel
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH +
                        NEW_ELECTRONIC_SIGNATURE_SECURITY_LEVEL,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_ELECTRONIC_SIGNATURE_SECURITY_LEVEL + SLASH,
                true
        ));

        // Add support for ElectronicSignatureVerified
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH +
                        ELECTRONIC_SIGNATURE_VERIFIED,
                REL_METADATA_ELECTRONIC_SIGNATURE_VERIFIED,
                true
        ));

        // Add support for new ElectronicSignatureVerified
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH +
                        NEW_ELECTRONIC_SIGNATURE_VERIFIED,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_ELECTRONIC_SIGNATURE_VERIFIED + SLASH,
                true
        ));

        // Add support for Format
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + FORMAT,
                REL_METADATA_FORMAT,
                true
        ));

        // Add support for new Format
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_FORMAT,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_FORMAT + SLASH,
                true
        ));

        // Add support for FlowStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + FLOW_STATUS,
                REL_METADATA_FLOW_STATUS,
                true
        ));

        // Add support for new FlowStatus
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_FLOW_STATUS,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_FLOW_STATUS + SLASH,
                true
        ));

        // Add support for RegistryEntryType
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + REGISTRY_ENTRY_TYPE,
                REL_METADATA_REGISTRY_ENTRY_TYPE,
                true
        ));

        // Add support for new RegistryEntryType
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_REGISTRY_ENTRY_TYPE,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_REGISTRY_ENTRY_TYPE + SLASH,
                true
        ));

        // Add support for CasePartyRole
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + CASE_PARTY_ROLE,
                REL_METADATA_CASE_PARTY_ROLE,
                true
        ));

        // Add support for new CasePartyRole
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_CASE_PARTY_ROLE,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_CASE_PARTY_ROLE + SLASH,
                true
        ));

        // Add support for FileType
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + FILE_TYPE,
                REL_METADATA_FILE_TYPE,
                true
        ));

        // Add support for new FileType
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH +
                        NOARK_METADATA_PATH + SLASH + NEW_FILE_TYPE,
                NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH +
                        NEW_FILE_TYPE + SLASH,
                true
        ));
    }
}
