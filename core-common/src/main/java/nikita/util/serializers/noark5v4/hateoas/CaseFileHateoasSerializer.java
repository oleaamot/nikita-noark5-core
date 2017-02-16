package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.Constants.DATE_FORMAT;
import static nikita.config.Constants.DATE_TIME_FORMAT;
import static nikita.config.N5ResourceMappings.*;

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
 *
 *  TODO: You are missing M209 referanseSekundaerKlassifikasjon
 */

public class CaseFileHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INoarkSystemIdEntity noarkSystemIdEntity,
                                     HateoasNoarkObject caseFileHateoas, JsonGenerator jgen) throws IOException {

        CaseFile caseFile = (CaseFile) noarkSystemIdEntity;

        jgen.writeStartObject();
        if (caseFile.getSystemId() != null) {
            jgen.writeStringField(SYSTEM_ID, caseFile.getSystemId());
        }
        if (caseFile.getFileId()!= null) {
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
            jgen.writeStringField(CREATED_DATE, DATE_TIME_FORMAT.format(caseFile.getCreatedDate()));
        }
        if (caseFile.getCreatedBy() != null) {
            jgen.writeStringField(CREATED_BY, caseFile.getCreatedBy());
        }
        if (caseFile.getFinalisedDate() != null) {
            jgen.writeStringField(FINALISED_DATE, DATE_TIME_FORMAT.format(caseFile.getFinalisedDate()));
        }
        if (caseFile.getFinalisedBy() != null) {
            jgen.writeStringField(FINALISED_BY, caseFile.getFinalisedBy());
        }
        if (caseFile.getReferenceSeries() != null && caseFile.getReferenceSeries().getSystemId() != null) {
            jgen.writeStringField(REFERENCE_SERIES, caseFile.getReferenceSeries().getSystemId());
        }
        if (caseFile.getCaseYear() != null) {
            jgen.writeStringField(CASE_YEAR, caseFile.getCaseYear().toString());
        }
        if (caseFile.getCaseSequenceNumber() != null) {
            jgen.writeStringField(CASE_SEQUENCE_NUMBER, caseFile.getCaseSequenceNumber().toString());
        }
        if (caseFile.getCaseDate() != null) {
            jgen.writeStringField(CASE_DATE, DATE_FORMAT.format(caseFile.getCaseDate()));
        }
        if (caseFile.getAdministrativeUnit() != null) {
            jgen.writeStringField(CASE_ADMINISTRATIVE_UNIT, caseFile.getAdministrativeUnit());
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
            jgen.writeStringField(CASE_LOANED_DATE, DATE_FORMAT.format(caseFile.getLoanedDate()));
        }
        if (caseFile.getLoanedTo() != null) {
            jgen.writeStringField(CASE_LOANED_TO, caseFile.getLoanedTo());
        }

        // TODO: Implement M209 referanseSekundaerKlassifikasjon
        //CommonCommonUtils.Hateoas.Serialize.printSecondaryClassification(jgen, caseFile);
        CommonUtils.Hateoas.Serialize.printCaseParty(jgen, caseFile);
        CommonUtils.Hateoas.Serialize.printPrecedence(jgen, caseFile);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, caseFileHateoas.getLinks(caseFile));
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
