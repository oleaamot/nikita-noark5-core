package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.util.exceptions.NikitaMalformedInputDataException;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static nikita.config.Constants.NOARK_DATE_FORMAT_PATTERN;
import static nikita.config.N5ResourceMappings.*;
import static nikita.util.CommonUtils.Hateoas.Deserialize;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming RegistryEntry JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the RegistryEntry object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a registryEntry object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in RegistryEntryController or RegistryEntryService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 *  - RegistryEntry has no obligatory values required to be present at instantiation time
 */
public class RegistryEntryDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public RegistryEntry deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        RegistryEntry registryEntry = new RegistryEntry();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general record properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity (registryEntry, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(registryEntry, objectNode);

        // Deserialize archivedBy
        JsonNode currentNode = objectNode.get(RECORD_ARCHIVED_BY);
        String key = RECORD_ARCHIVED_BY;
        if (currentNode == null) {
            currentNode = objectNode.get(RECORD_ARCHIVED_BY_EN);
            key = RECORD_ARCHIVED_BY_EN;
        }
        if (currentNode != null) {
            registryEntry.setArchivedBy(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize archivedDate
        currentNode = objectNode.get(RECORD_ARCHIVED_DATE);
        key = RECORD_ARCHIVED_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(RECORD_ARCHIVED_DATE_EN);
            key = RECORD_ARCHIVED_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = Deserialize.dateTimeFormat.parse(currentNode.textValue());
                registryEntry.setArchivedDate(parsedDate);
            }
            catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The RegistryEntry object you tried to create " +
                        "has a malformed arkivertDato/archivedDate. Make sure format is " +
                        NOARK_DATE_FORMAT_PATTERN);
            }
            objectNode.remove(key);
        }

        // Deserialize general basicRecord properties

        // Deserialize recordId
        currentNode = objectNode.get(BASIC_RECORD_ID);
        key = BASIC_RECORD_ID;
        if (currentNode == null) {
            currentNode = objectNode.get(BASIC_RECORD_ID);
            key = BASIC_RECORD_ID;
        }
        if (currentNode != null) {
            registryEntry.setRecordId(currentNode.textValue());
            objectNode.remove(key);
        }
        // Deserialize title (not using utils to preserve order)
        currentNode = objectNode.get(TITLE);
        key = TITLE;
        if (currentNode == null) {
            currentNode = objectNode.get(TITLE_EN);
            key = TITLE_EN;
        }
        if (currentNode != null) {
            registryEntry.setTitle(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize  officialTitle
        currentNode = objectNode.get(FILE_PUBLIC_TITLE);
        key = FILE_PUBLIC_TITLE;
        if (currentNode == null) {
            currentNode = objectNode.get(FILE_PUBLIC_TITLE_EN);
            key = FILE_PUBLIC_TITLE_EN;
        }
        if (currentNode != null) {
            registryEntry.setOfficialTitle(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize description
        currentNode = objectNode.get(DESCRIPTION);
        key = DESCRIPTION;
        if (currentNode == null) {
            currentNode = objectNode.get(DESCRIPTION_EN);
            key = DESCRIPTION_EN;
        }
        if (currentNode != null) {
            registryEntry.setDescription(currentNode.textValue());
            objectNode.remove(key);
        }
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(registryEntry, objectNode);

        // Deserialize general registryEntry properties

        // Deserialize recordYear
        currentNode = objectNode.get(REGISTRY_ENTRY_YEAR);
        key = REGISTRY_ENTRY_YEAR;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_YEAR_EN);
            key = REGISTRY_ENTRY_YEAR_EN;
        }
        if (currentNode != null) {
            registryEntry.setRecordYear(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(key);
        }

        // Deserialize recordSequenceNumber
        currentNode = objectNode.get(REGISTRY_ENTRY_SEQUENCE_NUMBER);
        key = REGISTRY_ENTRY_SEQUENCE_NUMBER;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_SEQUENCE_NUMBER_EN);
            key = REGISTRY_ENTRY_SEQUENCE_NUMBER_EN;
        }
        if (currentNode != null) {
            registryEntry.setRecordSequenceNumber(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(key);
        }

        // Deserialize registryEntryNumber
        currentNode = objectNode.get(REGISTRY_ENTRY_NUMBER);
        key = REGISTRY_ENTRY_NUMBER;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_NUMBER_EN);
            key = REGISTRY_ENTRY_NUMBER_EN;
        }
        if (currentNode != null) {
            registryEntry.setRegistryEntryNumber(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(key);
        }

        // Deserialize registryEntryType
        currentNode = objectNode.get(REGISTRY_ENTRY_TYPE);
        key = REGISTRY_ENTRY_TYPE;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_TYPE_EN);
            key = REGISTRY_ENTRY_TYPE_EN;
        }
        if (currentNode != null) {
            registryEntry.setRegistryEntryType(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize recordStatus
        currentNode = objectNode.get(REGISTRY_ENTRY_STATUS);
        key = REGISTRY_ENTRY_STATUS;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_STATUS_EN);
            key = REGISTRY_ENTRY_STATUS_EN;
        }
        if (currentNode != null) {
            registryEntry.setRecordStatus(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize recordDate
        currentNode = objectNode.get(REGISTRY_ENTRY_DATE);
        key = REGISTRY_ENTRY_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_DATE_EN);
            key = REGISTRY_ENTRY_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setRecordDate(parsedDate);
            }
            catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The registryEntry object you tried to create " +
                        "has a malformed journaldato/recordDate. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);

            }
            objectNode.remove(key);
        }


        // Deserialize documentDate
        currentNode = objectNode.get(REGISTRY_ENTRY_DOCUMENT_DATE);
        key = REGISTRY_ENTRY_DOCUMENT_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_DOCUMENT_DATE_EN);
            key = REGISTRY_ENTRY_DOCUMENT_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setDocumentDate(parsedDate);
            }
            catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The registryEntry object you tried to create " +
                        "has a malformed dokumentetsDato/documentDate. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);

            }
            objectNode.remove(key);
        }

                        // Deserialize receivedDate
                        currentNode = objectNode.get(REGISTRY_ENTRY_RECEIVED_DATE);
        key = REGISTRY_ENTRY_RECEIVED_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_RECEIVED_DATE_EN);
            key = REGISTRY_ENTRY_RECEIVED_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setReceivedDate(parsedDate);
            }
            catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The registryEntry object you tried to create " +
                        "has a malformed mottattDato/receivedDate. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);

            }
            objectNode.remove(key);
        }

                // Deserialize sentDate
                currentNode = objectNode.get(REGISTRY_ENTRY_SENT_DATE);
        key = REGISTRY_ENTRY_SENT_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_SENT_DATE_EN);
            key = REGISTRY_ENTRY_SENT_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setSentDate(parsedDate);
            }
            catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The registryEntry object you tried to create " +
                        "has a malformed sendtDate/sentDate. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);

            }
            objectNode.remove(key);
        }

                        // Deserialize dueDate
                        currentNode = objectNode.get(REGISTRY_ENTRY_DUE_DATE);
        key = REGISTRY_ENTRY_DUE_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_DUE_DATE_EN);
            key = REGISTRY_ENTRY_DUE_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setDueDate(parsedDate);
            }
            catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The registryEntry object you tried to create " +
                        "has a malformed forfallsdato/dueDate. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);

            }
            objectNode.remove(key);
        }

                // Deserialize freedomAssessmentDate
                currentNode = objectNode.get(REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE);
        key = REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_FREEDOM_ASSESSMENT_DATE_EN);
            key = REGISTRY_ENTRY_FREEDOM_ASSESSMENT_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setFreedomAssessmentDate(parsedDate);
            }
            catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The registryEntry object you tried to create " +
                        "has a malformed /. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);

            }
            objectNode.remove(key);
        }

        // Deserialize numberOfAttachments
        currentNode = objectNode.get(REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS);
        key = REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS;
        if (currentNode == null) {
            currentNode = objectNode.get(REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS_EN);
            key = REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS_EN;
        }
        if (currentNode != null) {
            registryEntry.setNumberOfAttachments(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(key);
        }

        // Deserialize loanedDate
        currentNode = objectNode.get(CASE_LOANED_DATE);
        key = CASE_LOANED_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(CASE_LOANED_DATE_EN);
            key = CASE_LOANED_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setLoanedDate(parsedDate);
            }
            catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The registryEntry object you tried to create " +
                        "has a malformed utlaantDato/loanedDate. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);

            }
            objectNode.remove(key);
        }

        // Deserialize loanedTo
        currentNode = objectNode.get(CASE_LOANED_TO);
        key = CASE_LOANED_TO;
        if (currentNode == null) {
            currentNode = objectNode.get(CASE_LOANED_TO_EN);
            key = CASE_LOANED_TO_EN;
        }
        if (currentNode != null) {
            registryEntry.setLoanedTo(currentNode.textValue());
            objectNode.remove(key);
        }

        // Check that all obligatory values are present
        checkForObligatoryRegistryEntryValues(registryEntry);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The RegistryEntry object you tried to create is malformed. The "
                    + "following objects are not recognised as RegistryEntry properties [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return registryEntry;
    }

    @Override
    /**
     *
     *  RegistryEntry has no obligatory Noark values
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {
    }

    public void checkForObligatoryRegistryEntryValues(RegistryEntry registryEntry) {
    }
}
