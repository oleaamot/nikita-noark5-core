package nikita.common.util.deserialisers.casehandling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.casehandling.RegistryEntry;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming RegistryEntry JSON object.
 * <p>
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 * <p>
 * <p>
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
public class RegistryEntryDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public RegistryEntry deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();
        RegistryEntry registryEntry = new RegistryEntry();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general record properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(registryEntry, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(registryEntry, objectNode, errors);

        // Deserialize archivedBy
        JsonNode currentNode = objectNode.get(N5ResourceMappings.RECORD_ARCHIVED_BY);
        if (null != currentNode) {
            registryEntry.setArchivedBy(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.RECORD_ARCHIVED_BY);
        }
        // Deserialize archivedDate
        registryEntry.setArchivedDate(CommonUtils.Hateoas.Deserialize.deserializeDateTime(N5ResourceMappings.RECORD_ARCHIVED_DATE, objectNode, errors));
        // Deserialize general basicRecord properties
        // Deserialize recordId
        currentNode = objectNode.get(N5ResourceMappings.BASIC_RECORD_ID);
        if (null != currentNode) {
            registryEntry.setRecordId(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.BASIC_RECORD_ID);
        }
        // Deserialize title (not using utils to preserve order)
        currentNode = objectNode.get(N5ResourceMappings.TITLE);
        if (null != currentNode) {
            registryEntry.setTitle(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.TITLE);
        }
        // Deserialize  officialTitle
        currentNode = objectNode.get(N5ResourceMappings.FILE_PUBLIC_TITLE);
        if (null != currentNode) {
            registryEntry.setOfficialTitle(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.FILE_PUBLIC_TITLE);
        }
        // Deserialize description
        currentNode = objectNode.get(N5ResourceMappings.DESCRIPTION);
        if (null != currentNode) {
            registryEntry.setDescription(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DESCRIPTION);
        }
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(registryEntry, objectNode, errors);
        // Deserialize general registryEntry properties
        // Deserialize recordYear
        currentNode = objectNode.get(N5ResourceMappings.REGISTRY_ENTRY_YEAR);
        if (null != currentNode) {
            registryEntry.setRecordYear(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(N5ResourceMappings.REGISTRY_ENTRY_YEAR);
        }
        // Deserialize recordSequenceNumber
        currentNode = objectNode.get(N5ResourceMappings.REGISTRY_ENTRY_SEQUENCE_NUMBER);
        if (null != currentNode) {
            registryEntry.setRecordSequenceNumber(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(N5ResourceMappings.REGISTRY_ENTRY_SEQUENCE_NUMBER);
        }
        // Deserialize registryEntryNumber
        currentNode = objectNode.get(N5ResourceMappings.REGISTRY_ENTRY_NUMBER);
        if (null != currentNode) {
            registryEntry.setRegistryEntryNumber(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(N5ResourceMappings.REGISTRY_ENTRY_NUMBER);
        }
        // Deserialize registryEntryType
        currentNode = objectNode.get(N5ResourceMappings.REGISTRY_ENTRY_TYPE);
        if (null != currentNode) {
            registryEntry.setRegistryEntryType(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.REGISTRY_ENTRY_TYPE);
        } else {
            errors.append("The journalpost you tried to create is missing journalposttype. ");
        }
        // Deserialize recordStatus
        currentNode = objectNode.get(N5ResourceMappings.REGISTRY_ENTRY_STATUS);
        if (null != currentNode) {
            registryEntry.setRecordStatus(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.REGISTRY_ENTRY_STATUS);
        } else {
            errors.append("The journalpost you tried to create is missing journalstatus. ");
        }
        // Deserialize recordDate
        registryEntry.setRecordDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.REGISTRY_ENTRY_DATE, objectNode, errors, true));
        // Deserialize documentDate
        registryEntry.setDocumentDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.REGISTRY_ENTRY_DOCUMENT_DATE, objectNode, errors));

        // Deserialize receivedDate
        registryEntry.setReceivedDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.REGISTRY_ENTRY_RECEIVED_DATE, objectNode, errors));

        // Deserialize sentDate
        registryEntry.setSentDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.REGISTRY_ENTRY_SENT_DATE, objectNode, errors));

        // Deserialize dueDate
        registryEntry.setDueDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.REGISTRY_ENTRY_DUE_DATE, objectNode, errors));

        // Deserialize freedomAssessmentDate
        registryEntry.setFreedomAssessmentDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE,
                objectNode, errors));

        // Deserialize numberOfAttachments
        currentNode = objectNode.get(N5ResourceMappings.REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS);
        if (null != currentNode) {
            registryEntry.setNumberOfAttachments(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(N5ResourceMappings.REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS);
        }
        // Deserialize loanedDate
        registryEntry.setLoanedDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.CASE_LOANED_DATE, objectNode, errors));

        // Deserialize loanedTo
        currentNode = objectNode.get(N5ResourceMappings.CASE_LOANED_TO);
        if (null != currentNode) {
            registryEntry.setLoanedTo(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.CASE_LOANED_TO);
        }

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The journalpost you tried to create is malformed. The "
                    + "following fields are not recognised as journalpost fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }
        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());
        return registryEntry;
    }
}
