package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.CommonUtils;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static nikita.config.Constants.NOARK_DATE_FORMAT_PATTERN;
import static nikita.config.N5ResourceMappings.*;
import static nikita.util.CommonUtils.Hateoas.Deserialize;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming RegistryEntry JSON object.
 * <p>
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 * <p>
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 * <p>
 * Note this implementation expects that the RegistryEntry object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not
 * enforce this and will deserialize a registryEntry object correctly. This is because e.g the import interface will require
 * such functionality.
 * <p>
 * - Testing of compliance of properties is handled by the core, either in RegistryEntryController or RegistryEntryService
 * <p>
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 * - RegistryEntry has no obligatory values required to be present at instantiation time
 */
public class RegistryEntryDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public RegistryEntry deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        RegistryEntry registryEntry = new RegistryEntry();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general record properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(registryEntry, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(registryEntry, objectNode);

        // Deserialize archivedBy
        JsonNode currentNode = objectNode.get(RECORD_ARCHIVED_BY);
        if (null != currentNode) {
            registryEntry.setArchivedBy(currentNode.textValue());
            objectNode.remove(RECORD_ARCHIVED_BY);
        }
        // Deserialize archivedDate
        currentNode = objectNode.get(RECORD_ARCHIVED_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = Deserialize.dateTimeFormat.parse(currentNode.textValue());
                registryEntry.setArchivedDate(parsedDate);
                objectNode.remove(RECORD_ARCHIVED_DATE);
            } catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The journalpost object you tried to create " +
                        "has a malformed arkivertDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize general basicRecord properties
        // Deserialize recordId
        currentNode = objectNode.get(BASIC_RECORD_ID);
        if (null != currentNode) {
            registryEntry.setRecordId(currentNode.textValue());
            objectNode.remove(BASIC_RECORD_ID);
        }
        // Deserialize title (not using utils to preserve order)
        currentNode = objectNode.get(TITLE);
        if (null != currentNode) {
            registryEntry.setTitle(currentNode.textValue());
            objectNode.remove(TITLE);
        }
        // Deserialize  officialTitle
        currentNode = objectNode.get(FILE_PUBLIC_TITLE);
        if (null != currentNode) {
            registryEntry.setOfficialTitle(currentNode.textValue());
            objectNode.remove(FILE_PUBLIC_TITLE);
        }
        // Deserialize description
        currentNode = objectNode.get(DESCRIPTION);
        if (null != currentNode) {
            registryEntry.setDescription(currentNode.textValue());
            objectNode.remove(DESCRIPTION);
        }
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(registryEntry, objectNode);
        // Deserialize general registryEntry properties
        // Deserialize recordYear
        currentNode = objectNode.get(REGISTRY_ENTRY_YEAR);
        if (null != currentNode) {
            registryEntry.setRecordYear(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(REGISTRY_ENTRY_YEAR);
        }
        // Deserialize recordSequenceNumber
        currentNode = objectNode.get(REGISTRY_ENTRY_SEQUENCE_NUMBER);
        if (null != currentNode) {
            registryEntry.setRecordSequenceNumber(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(REGISTRY_ENTRY_SEQUENCE_NUMBER);
        }
        // Deserialize registryEntryNumber
        currentNode = objectNode.get(REGISTRY_ENTRY_NUMBER);
        if (null != currentNode) {
            registryEntry.setRegistryEntryNumber(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(REGISTRY_ENTRY_NUMBER);
        }
        // Deserialize registryEntryType
        currentNode = objectNode.get(REGISTRY_ENTRY_TYPE);
        if (null != currentNode) {
            registryEntry.setRegistryEntryType(currentNode.textValue());
            objectNode.remove(REGISTRY_ENTRY_TYPE);
        }
        // Deserialize recordStatus
        currentNode = objectNode.get(REGISTRY_ENTRY_STATUS);
        if (null != currentNode) {
            registryEntry.setRecordStatus(currentNode.textValue());
            objectNode.remove(REGISTRY_ENTRY_STATUS);
        }
        // Deserialize recordDate
        currentNode = objectNode.get(REGISTRY_ENTRY_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setRecordDate(parsedDate);
                objectNode.remove(REGISTRY_ENTRY_DATE);
            } catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The journalpost you tried to create " +
                        "has a malformed journaldato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize documentDate
        currentNode = objectNode.get(REGISTRY_ENTRY_DOCUMENT_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setDocumentDate(parsedDate);
                objectNode.remove(REGISTRY_ENTRY_DOCUMENT_DATE);
            } catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The journalpost you tried to create " +
                        "has a malformed dokumentetsDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }

        // Deserialize receivedDate
        currentNode = objectNode.get(REGISTRY_ENTRY_RECEIVED_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setReceivedDate(parsedDate);
                objectNode.remove(REGISTRY_ENTRY_RECEIVED_DATE);
            } catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The journalpost you tried to create " +
                        "has a malformed mottattDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }

        // Deserialize sentDate
        currentNode = objectNode.get(REGISTRY_ENTRY_SENT_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setSentDate(parsedDate);
                objectNode.remove(REGISTRY_ENTRY_SENT_DATE);
            } catch (ParseException e) {

                throw new NikitaMalformedInputDataException("The journalpost you tried to create " +
                        "has a malformed sendtDate. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize dueDate
        currentNode = objectNode.get(REGISTRY_ENTRY_DUE_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setDueDate(parsedDate);
                objectNode.remove(REGISTRY_ENTRY_DUE_DATE);
            } catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The journalpost you tried to create " +
                        "has a malformed forfallsdato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize freedomAssessmentDate
        currentNode = objectNode.get(REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setFreedomAssessmentDate(parsedDate);
                objectNode.remove(REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE);
            } catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The journalpost you tried to create " +
                        "has a malformed offentlighetsvurdertDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize numberOfAttachments
        currentNode = objectNode.get(REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS);
        if (null != currentNode) {
            registryEntry.setNumberOfAttachments(Integer.getInteger(currentNode.textValue()));
            objectNode.remove(REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS);
        }
        // Deserialize loanedDate
        currentNode = objectNode.get(CASE_LOANED_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = CommonUtils.Hateoas.Deserialize.dateFormat.parse(currentNode.textValue());
                registryEntry.setLoanedDate(parsedDate);
                objectNode.remove(CASE_LOANED_DATE);
            } catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The journalpost you tried to create " +
                        "has a malformed utlaantDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize loanedTo
        currentNode = objectNode.get(CASE_LOANED_TO);
        if (null != currentNode) {
            registryEntry.setLoanedTo(currentNode.textValue());
            objectNode.remove(CASE_LOANED_TO);
        }
        // Check that all obligatory values are present
        checkForObligatoryRegistryEntryValues(registryEntry);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The journalpost you tried to create is malformed. The "
                    + "following fields are not recognised as journalpost fields [" +
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
