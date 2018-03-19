package nikita.common.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.DocumentObject;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming DocumentObject JSON object.
 * <p>
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 * <p>
 * <p>
 * Note this implementation expects that the DocumentObject object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not
 * enforce this and will deserialize a documentObject object correctly. This is because e.g the import interface will require
 * such functionality.
 * <p>
 * - Testing of compliance of properties is handled by the core, either in DocumentObjectController or DocumentObjectService
 * <p>
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 * - DocumentObject has no obligatory values required to be present at instantiation time
 */
public class DocumentObjectDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public DocumentObject deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();
        DocumentObject documentObject = new DocumentObject();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general DocumentObject properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(documentObject, objectNode, errors);
        // Deserialize versionNumber
        JsonNode currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_VERSION_NUMBER);
        if (null != currentNode) {
            documentObject.setVersionNumber(new Integer(currentNode.intValue()));
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_VERSION_NUMBER);
        }
        // Deserialize variantFormat
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_VARIANT_FORMAT);
        if (null != currentNode) {
            documentObject.setVariantFormat(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_VARIANT_FORMAT);
        }
        // Deserialize format
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_FORMAT);
        if (null != currentNode) {
            documentObject.setFormat(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_FORMAT);
        }
        // Deserialize formatDetails
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_FORMAT_DETAILS);
        if (null != currentNode) {
            documentObject.setFormatDetails(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_FORMAT_DETAILS);
        }
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(documentObject, objectNode, errors);
        // Deserialize referenceDocumentFile
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE);
        if (null != currentNode) {
            documentObject.setReferenceDocumentFile(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE);
        }
        // Deserialize checksum
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_CHECKSUM);
        if (null != currentNode) {
            documentObject.setChecksum(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_CHECKSUM);
        }
        // Deserialize checksumAlgorithm
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_CHECKSUM_ALGORITHM);
        if (null != currentNode) {
            documentObject.setChecksumAlgorithm(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_CHECKSUM_ALGORITHM);
        }
        // Deserialize fileSize
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_FILE_SIZE);
        if (null != currentNode) {
            documentObject.setFileSize(currentNode.asLong());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_FILE_SIZE);
        }
        // Deserialize filename
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_FILE_NAME);
        if (null != currentNode) {
            documentObject.setOriginalFilename(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_FILE_NAME);
        }
        // Deserialize mimeType
        currentNode = objectNode.get(N5ResourceMappings.DOCUMENT_OBJECT_MIME_TYPE);
        if (null != currentNode) {
            documentObject.setMimeType(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DOCUMENT_OBJECT_MIME_TYPE);
        }

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The dokumentobjekt you tried to create is malformed. The " +
                    "following fields are not recognised as dokumentobjekt fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());

        return documentObject;
    }
}
