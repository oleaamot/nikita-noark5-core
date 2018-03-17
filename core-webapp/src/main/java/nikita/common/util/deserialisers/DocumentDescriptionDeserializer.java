package nikita.common.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming DocumentDescription JSON object.
 * <p>
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 * <p>
 * <p>
 * <p>
 * Note this implementation expects that the DocumentDescription object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not
 * enforce this and will deserialize a documentDescription object correctly. This is because e.g the import interface will require
 * such functionality.
 * <p>
 * - Testing of compliance of properties is handled by the core, either in DocumentDescriptionController or DocumentDescriptionService
 * <p>
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 * - DocumentDescription has no obligatory values required to be present at instantiation time
 */
public class DocumentDescriptionDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public DocumentDescription deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();
        DocumentDescription documentDescription = new DocumentDescription();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general record properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(documentDescription, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(documentDescription, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkTitleDescriptionEntity(documentDescription, objectNode, errors);

        // Deserialize documentType
        JsonNode currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_DESCRIPTION_DOCUMENT_TYPE);
        if (null != currentNode) {
            documentDescription.setDocumentType(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_DESCRIPTION_DOCUMENT_TYPE);
        }
        // Deserialize documentStatus
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_DESCRIPTION_STATUS);
        if (null != currentNode) {
            documentDescription.setDocumentStatus(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_DESCRIPTION_STATUS);
        }
        // Deserialize associatedWithRecordAs
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS);
        if (null != currentNode) {
            documentDescription.setAssociatedWithRecordAs(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS);
        }
        // Deserialize documentNumber
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER);
        if (null != currentNode) {
            documentDescription.setDocumentNumber(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(N5ResourceMappings.DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER);
        }
        // Deserialize associationDate
        documentDescription.setAssociationDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.DOCUMENT_DESCRIPTION_ASSOCIATION_DATE,
                objectNode, errors));

        // Deserialize associatedBy
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_DESCRIPTION_ASSOCIATED_BY);
        if (null != currentNode) {
            documentDescription.setAssociatedBy(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_DESCRIPTION_ASSOCIATED_BY);
        }

        // Deserialize storageLocation
        currentNode = objectNode.get(N5ResourceMappings.STORAGE_LOCATION);
        if (null != currentNode) {
            documentDescription.setStorageLocation(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.STORAGE_LOCATION);
        }

        // Deserialize general documentDescription properties
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(documentDescription, objectNode, errors);
        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The dokumentbeskrivelse you tried to create is malformed. The " +
                    "following fields are not recognised as dokumentbeskrivelse fields[" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());

        return documentDescription;
    }
}
