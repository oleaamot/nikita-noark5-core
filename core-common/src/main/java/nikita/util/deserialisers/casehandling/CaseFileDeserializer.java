package nikita.util.deserialisers.casehandling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.casehandling.CaseFile;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static nikita.config.Constants.NOARK_DATE_FORMAT_PATTERN;
import static nikita.config.N5ResourceMappings.*;
import static nikita.util.CommonUtils.Hateoas.Deserialize;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming CaseFile JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *

 *
 * Note this implementation expects that the CaseFile object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a caseFile object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in CaseFileController or CaseFileService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
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
        JsonNode currentNode = objectNode.get(FILE_ID);
        if (null != currentNode) {
            caseFile.setFileId(currentNode.textValue());
            objectNode.remove(FILE_ID);
        }
        // Deserialize officialTitle
        currentNode = objectNode.get(FILE_PUBLIC_TITLE);
        if (null != currentNode) {
            caseFile.setOfficialTitle(currentNode.textValue());
            objectNode.remove(FILE_PUBLIC_TITLE);
        }
        caseFile.setReferenceCrossReference(CommonUtils.Hateoas.Deserialize.deserialiseCrossReferences(objectNode, errors));
        CommonUtils.Hateoas.Deserialize.deserialiseComments(caseFile, objectNode, errors);
        caseFile.setReferenceDisposal(CommonUtils.Hateoas.Deserialize.deserialiseDisposal(objectNode, errors));
        caseFile.setReferenceScreening(CommonUtils.Hateoas.Deserialize.deserialiseScreening(objectNode, errors));
        caseFile.setReferenceClassified(CommonUtils.Hateoas.Deserialize.deserialiseClassified(objectNode, errors));

        // Deserialise general properties for CaseFile
        // Deserialize caseYear
        currentNode = objectNode.get(CASE_YEAR);
        if (null != currentNode) {
            caseFile.setCaseYear(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(CASE_YEAR);
        }
        // Deserialize caseSequenceNumber
        currentNode = objectNode.get(CASE_SEQUENCE_NUMBER);
        if (null != currentNode) {
            caseFile.setCaseSequenceNumber(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(CASE_SEQUENCE_NUMBER);
        }
        // Deserialize caseDate
        currentNode = objectNode.get(CASE_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = Deserialize.parseDateFormat(currentNode.textValue());
                caseFile.setCaseDate(parsedDate);
                objectNode.remove(CASE_DATE);
            }
            catch (ParseException e) {
                errors.append("The saksmappe you tried to create " +
			      "has a malformed saksDato. Make sure format is " +
			      NOARK_DATE_FORMAT_PATTERN + ". ");
            }
        }
        // Deserialize administrativeUnit
        currentNode = objectNode.get(ADMINISTRATIVE_UNIT);
        if (null != currentNode) {
            caseFile.setAdministrativeUnit(currentNode.textValue());
            objectNode.remove(ADMINISTRATIVE_UNIT);
        }
        // Deserialize caseResponsible
        currentNode = objectNode.get(CASE_RESPONSIBLE);
        if (null != currentNode) {
            caseFile.setCaseResponsible(currentNode.textValue());
            objectNode.remove(CASE_RESPONSIBLE);
        }
        // Deserialize recordsManagementUnit
        currentNode = objectNode.get(CASE_RECORDS_MANAGEMENT_UNIT);
        if (null != currentNode) {
            caseFile.setRecordsManagementUnit(currentNode.textValue());
            objectNode.remove(CASE_RECORDS_MANAGEMENT_UNIT);
        }
        // Deserialize caseStatus
        currentNode = objectNode.get(CASE_STATUS);
        if (null != currentNode) {
            caseFile.setCaseStatus(currentNode.textValue());
            objectNode.remove(CASE_STATUS);
        }
        // Deserialize loanedDate
        currentNode = objectNode.get(CASE_LOANED_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = Deserialize.parseDateFormat(currentNode.textValue());
                caseFile.setLoanedDate(parsedDate);
                objectNode.remove(CASE_LOANED_DATE);
            }
            catch (ParseException e) {
                errors.append("The saksmappe you tried to create " +
			      "has a malformed utlaantDato. Make sure format is " +
			      NOARK_DATE_FORMAT_PATTERN + ". ");
            }
        }
        // Deserialize loanedTo
        currentNode = objectNode.get(CASE_LOANED_TO);
        if (null != currentNode) {
            caseFile.setLoanedTo(currentNode.textValue());
            objectNode.remove(CASE_LOANED_TO);
        }
        // Deserialize referenceSeries
        currentNode = objectNode.get(REFERENCE_SERIES);
        if (null != currentNode) {
            Series series = new Series();
            String systemID = currentNode.textValue();
            if (systemID != null) {
                series.setSystemId(systemID);
            }
            caseFile.setReferenceSeries(series);
            objectNode.remove(REFERENCE_SERIES);
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
