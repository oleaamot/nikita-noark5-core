package nikita.common.util.serializers.noark5v4.hateoas.casehandling;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import nikita.common.model.noark5.v4.casehandling.CaseFile;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.serializers.noark5v4.hateoas.HateoasSerializer;
import nikita.common.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.common.config.N5ResourceMappings.*;
import static nikita.common.util.CommonUtils.Hateoas.Serialize;

/**
 * Serialise an outgoing CaseFile object as JSON.
 * <p>
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 * <p>
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 * <p>
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 * <p>
 * TODO: You are missing M209 referanseSekundaerKlassifikasjon
 */

public class CaseFileHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INikitaEntity noarkSystemIdEntity,
                                     HateoasNoarkObject caseFileHateoas, JsonGenerator jgen) throws IOException {

        CaseFile caseFile = (CaseFile) noarkSystemIdEntity;

        jgen.writeStartObject();
        if (caseFile.getSystemId() != null) {
            jgen.writeStringField(SYSTEM_ID, caseFile.getSystemId());
        }
        if (caseFile.getFileId() != null) {
            jgen.writeStringField(FILE_ID, caseFile.getFileId());
        }
        if (caseFile.getTitle() != null) {
            jgen.writeStringField(TITLE, caseFile.getTitle());
        }
        if (caseFile.getOfficialTitle() != null) {
            jgen.writeStringField(FILE_PUBLIC_TITLE, caseFile.getOfficialTitle());
        }
        if (caseFile.getDescription() != null) {
            jgen.writeStringField(DESCRIPTION, caseFile.getDescription());
        }
        CommonUtils.Hateoas.Serialize.printKeyword(jgen, caseFile);
        if (caseFile.getDocumentMedium() != null) {
            jgen.writeStringField(DOCUMENT_MEDIUM, caseFile.getDocumentMedium());
        }
        CommonUtils.Hateoas.Serialize.printStorageLocation(jgen, caseFile);
        if (caseFile.getCreatedDate() != null) {
            jgen.writeStringField(CREATED_DATE,
                    Serialize.formatDateTime(caseFile.getCreatedDate()));
        }
        if (caseFile.getCreatedBy() != null) {
            jgen.writeStringField(CREATED_BY, caseFile.getCreatedBy());
        }
        if (caseFile.getFinalisedDate() != null) {
            jgen.writeStringField(FINALISED_DATE,
                    Serialize.formatDateTime(caseFile.getFinalisedDate()));
        }
        if (caseFile.getFinalisedBy() != null) {
            jgen.writeStringField(FINALISED_BY, caseFile.getFinalisedBy());
        }
        if (caseFile.getReferenceSeries() != null && caseFile.getReferenceSeries().getSystemId() != null) {
            jgen.writeStringField(REFERENCE_SERIES, caseFile.getReferenceSeries().getSystemId());
        }
        if (caseFile.getCaseYear() != null) {
            jgen.writeNumberField(CASE_YEAR, caseFile.getCaseYear().intValue());
        }
        if (caseFile.getCaseSequenceNumber() != null) {
            jgen.writeNumberField(CASE_SEQUENCE_NUMBER, caseFile.getCaseSequenceNumber().intValue());
        }
        if (caseFile.getCaseDate() != null) {
            jgen.writeStringField(CASE_DATE,
                    Serialize.formatDate(caseFile.getCaseDate()));
        }
        if (caseFile.getAdministrativeUnit() != null) {
            jgen.writeStringField(ADMINISTRATIVE_UNIT, caseFile.getAdministrativeUnit());
        }
        if (caseFile.getCaseResponsible() != null) {
            jgen.writeStringField(CASE_RESPONSIBLE, caseFile.getCaseResponsible());
        }
        if (caseFile.getRecordsManagementUnit() != null) {
            jgen.writeStringField(CASE_RECORDS_MANAGEMENT_UNIT, caseFile.getRecordsManagementUnit());
        }
        if (caseFile.getCaseStatus() != null) {
            jgen.writeStringField(CASE_STATUS, caseFile.getCaseStatus());
        }
        if (caseFile.getLoanedDate() != null) {
            jgen.writeStringField(CASE_LOANED_DATE,
                    Serialize.formatDate(caseFile.getLoanedDate()));
        }
        if (caseFile.getLoanedTo() != null) {
            jgen.writeStringField(CASE_LOANED_TO, caseFile.getLoanedTo());
        }

        // TODO: Implement M209 referanseSekundaerKlassifikasjon
        //CommonCommonUtils.Hateoas.Serialize.printSecondaryClassification(jgen, caseFile);
        CommonUtils.Hateoas.Serialize.printCaseParty(jgen, caseFile);
        //CommonUtils.Hateoas.Serialize.printPrecedence(jgen, caseFile);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, caseFileHateoas.getLinks(caseFile));
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        throw new UnsupportedOperationException();
    }
}
