package nikita.common.util.deserialisers.casehandling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.Series;
import nikita.common.model.noark5.v4.casehandling.CaseFile;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming CaseFile JSON object.
 * <p>
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 * <p>
 * <p>
 * <p>
 * Note this implementation expects that the CaseFile object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not
 * enforce this and will deserialize a caseFile object correctly. This is because e.g the import interface will require
 * such functionality.
 * <p>
 * - Testing of compliance of properties is handled by the core, either in CaseFileController or CaseFileService
 * <p>
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 */
public class CaseFileDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public CaseFile deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();
        CaseFile caseFile = new CaseFile();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise properties for File
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkEntity(caseFile, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(caseFile, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseStorageLocation(caseFile, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseKeyword(caseFile, objectNode, errors);

        // Deserialize fileId
        JsonNode currentNode = objectNode.get(N5ResourceMappings.FILE_ID);
        if (null != currentNode) {
            caseFile.setFileId(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.FILE_ID);
        }
        // Deserialize officialTitle
        currentNode = objectNode.get(N5ResourceMappings.FILE_PUBLIC_TITLE);
        if (null != currentNode) {
            caseFile.setOfficialTitle(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.FILE_PUBLIC_TITLE);
        }
        caseFile.setReferenceCrossReference(CommonUtils.Hateoas.Deserialize.deserialiseCrossReferences(objectNode, errors));
        CommonUtils.Hateoas.Deserialize.deserialiseComments(caseFile, objectNode, errors);
        caseFile.setReferenceDisposal(CommonUtils.Hateoas.Deserialize.deserialiseDisposal(objectNode, errors));
        caseFile.setReferenceScreening(CommonUtils.Hateoas.Deserialize.deserialiseScreening(objectNode, errors));
        caseFile.setReferenceClassified(CommonUtils.Hateoas.Deserialize.deserialiseClassified(objectNode, errors));

        // Deserialise general properties for CaseFile
        // Deserialize caseYear
        currentNode = objectNode.get(N5ResourceMappings.CASE_YEAR);
        if (null != currentNode) {
            caseFile.setCaseYear(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(N5ResourceMappings.CASE_YEAR);
        }
        // Deserialize caseSequenceNumber
        currentNode = objectNode.get(N5ResourceMappings.CASE_SEQUENCE_NUMBER);
        if (null != currentNode) {
            caseFile.setCaseSequenceNumber(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(N5ResourceMappings.CASE_SEQUENCE_NUMBER);
        }
        // Deserialize caseDate
        caseFile.setCaseDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.CASE_DATE, objectNode, errors));
        // Deserialize administrativeUnit
        currentNode = objectNode.get(N5ResourceMappings.ADMINISTRATIVE_UNIT);
        if (null != currentNode) {
            caseFile.setAdministrativeUnit(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.ADMINISTRATIVE_UNIT);
        }
        // Deserialize caseResponsible
        currentNode = objectNode.get(N5ResourceMappings.CASE_RESPONSIBLE);
        if (null != currentNode) {
            caseFile.setCaseResponsible(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.CASE_RESPONSIBLE);
        }
        // Deserialize recordsManagementUnit
        currentNode = objectNode.get(N5ResourceMappings.CASE_RECORDS_MANAGEMENT_UNIT);
        if (null != currentNode) {
            caseFile.setRecordsManagementUnit(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.CASE_RECORDS_MANAGEMENT_UNIT);
        }
        // Deserialize caseStatus
        currentNode = objectNode.get(N5ResourceMappings.CASE_STATUS);
        if (null != currentNode) {
            caseFile.setCaseStatus(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.CASE_STATUS);
        }
        // Deserialize loanedDate
        caseFile.setLoanedDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.CASE_LOANED_DATE, objectNode, errors));
        // Deserialize loanedTo
        currentNode = objectNode.get(N5ResourceMappings.CASE_LOANED_TO);
        if (null != currentNode) {
            caseFile.setLoanedTo(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.CASE_LOANED_TO);
        }
        // Deserialize referenceSeries
        currentNode = objectNode.get(N5ResourceMappings.REFERENCE_SERIES);
        if (null != currentNode) {
            Series series = new Series();
            String systemID = currentNode.textValue();
            if (systemID != null) {
                series.setSystemId(systemID);
            }
            caseFile.setReferenceSeries(series);
            objectNode.remove(N5ResourceMappings.REFERENCE_SERIES);
        }

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The saksmappe object you tried to create is malformed. The " +
                    "following fields are not recognised as saksmappe fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        caseFile.setReferenceCaseParty(CommonUtils.Hateoas.Deserialize.deserialiseCaseParties(objectNode, errors));
        caseFile.setReferencePrecedence(CommonUtils.Hateoas.Deserialize.deserialisePrecedences(objectNode, errors));

        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());

        return caseFile;
    }
}
