package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.CommonUtils;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static nikita.config.Constants.NOARK_DATE_FORMAT_PATTERN;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming CaseFile JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
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
public class CaseFileDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public CaseFile deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        CaseFile caseFile = new CaseFile();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise properties for File
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkEntity(caseFile, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(caseFile, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseStorageLocation(caseFile, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseKeyword(caseFile, objectNode);

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
        caseFile.setReferenceCrossReference(CommonUtils.Hateoas.Deserialize.deserialiseCrossReferences(objectNode));
        CommonUtils.Hateoas.Deserialize.deserialiseComments(caseFile, objectNode);
        caseFile.setReferenceDisposal(CommonUtils.Hateoas.Deserialize.deserialiseDisposal(objectNode));
        caseFile.setReferenceScreening(CommonUtils.Hateoas.Deserialize.deserialiseScreening(objectNode));
        caseFile.setReferenceClassified(CommonUtils.Hateoas.Deserialize.deserialiseClassified(objectNode));

        // Deserialise general properties for CaseFile
        // Deserialize caseYear
        currentNode = objectNode.get(CASE_YEAR);
        if (null != currentNode) {
            caseFile.setCaseYear(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(CASE_YEAR);
        }
        // Deserialize caseSequenceNumber
        currentNode = objectNode.get(CASE_SEQUENCE_NUMBER);
        if (null != currentNode) {
            caseFile.setCaseSequenceNumber(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(CASE_SEQUENCE_NUMBER);
        }
        // Deserialize caseDate
        currentNode = objectNode.get(CASE_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                caseFile.setCaseDate(parsedDate);
                objectNode.remove(CASE_DATE);
            }
            catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The saksmappe you tried to create " +
                        "has a malformed saksDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize administrativeUnit
        currentNode = objectNode.get(CASE_ADMINISTRATIVE_UNIT);
        if (null != currentNode) {
            caseFile.setAdministrativeUnit(currentNode.textValue());
            objectNode.remove(CASE_ADMINISTRATIVE_UNIT);
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
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                caseFile.setLoanedDate(parsedDate);
                objectNode.remove(CASE_LOANED_DATE);
            }
            catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The saksmappe you tried to create " +
                        "has a malformed utlaantDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize loanedTo
        currentNode = objectNode.get(CASE_LOANED_TO);
        if (null != currentNode) {
            caseFile.setLoanedTo(currentNode.textValue());
            objectNode.remove(CASE_LOANED_TO);
        }
        // Check that all obligatory values are present
        checkForObligatoryNoarkValues(caseFile);
        checkForObligatoryCaseFileValues(caseFile);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The saksmappe object you tried to create is malformed. The "
                    + "following fields are not recognised as saksmappe fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }

        caseFile.setReferenceCaseParty(CommonUtils.Hateoas.Deserialize.deserialiseCaseParties(objectNode));
        caseFile.setReferencePrecedence(CommonUtils.Hateoas.Deserialize.deserialisePrecedences(objectNode));

        return caseFile;
    }

    @Override
    /**
     *
     * The only field that is mandatory, according to arkivstruktur.xsd, when creating the object is 'title'
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {
        if (noarkEntity.getTitle() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "tittel field is mandatory, and you have submitted an empty value.");
        }
    }

    // TODO: Consider gathering all the missing fields as a string and returning all in one go
    // But the developer of the client should know what's required!
    public void checkForObligatoryCaseFileValues(CaseFile caseFile) {

        if (caseFile.getFileId() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "mappeID field is mandatory, and you have submitted an empty value.");
        }
        if (caseFile.getCaseDate() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "saksDato field is mandatory, and you have submitted an empty value.");
        }
        if (caseFile.getAdministrativeUnit() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "field administrativEnhet is mandatory, and you have submitted an empty value.");
        }
        if (caseFile.getCaseResponsible() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe  you tried to create is malformed. The "
                    + "saksansvarlig field is mandatory, and you have submitted an empty value.");
        }
        if (caseFile.getCaseStatus() == null) {
            throw new NikitaMalformedInputDataException("The saksmappe you tried to create is malformed. The "
                    + "saksstatus field is mandatory, and you have submitted an empty value.");
        }
    }
}
