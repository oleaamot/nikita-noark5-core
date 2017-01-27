package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.util.exceptions.NikitaMalformedInputDataException;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.CommonUtils;

import java.io.IOException;

import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming DocumentObject JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the DocumentObject object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a documentObject object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in DocumentObjectController or DocumentObjectService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 *  - DocumentObject has no obligatory values required to be present at instantiation time
 */
public class DocumentObjectDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public DocumentObject deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        DocumentObject documentObject = new DocumentObject();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general DocumentObject properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity (documentObject, objectNode);
        // Deserialize versionNumber
        JsonNode currentNode = objectNode.get(DOCUMENT_OBJECT_VERSION_NUMBER);
        String key = DOCUMENT_OBJECT_VERSION_NUMBER;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_OBJECT_VERSION_NUMBER_EN);
            key = DOCUMENT_OBJECT_VERSION_NUMBER_EN;
        }
        if (currentNode != null) {
            documentObject.setVersionNumber(Integer.valueOf(currentNode.intValue()));
            objectNode.remove(key);
        }

        // Deserialize variantFormat
        currentNode = objectNode.get(DOCUMENT_OBJECT_VARIANT_FORMAT);
        key = DOCUMENT_OBJECT_VARIANT_FORMAT;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_OBJECT_VARIANT_FORMAT_EN);
            key = DOCUMENT_OBJECT_VARIANT_FORMAT_EN;
        }
        if (currentNode != null) {
            documentObject.setVariantFormat(currentNode.textValue());
            objectNode.remove(key);
        }
        // Deserialize format
        currentNode = objectNode.get(DOCUMENT_OBJECT_FORMAT);
        key = DOCUMENT_OBJECT_FORMAT;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_OBJECT_FORMAT_EN);
            key = DOCUMENT_OBJECT_FORMAT_EN;
        }
        if (currentNode != null) {
            documentObject.setFormat(currentNode.textValue());
            objectNode.remove(key);
        }
        // Deserialize formatDetails
        currentNode = objectNode.get(DOCUMENT_OBJECT_FORMAT_DETAILS);
        key = DOCUMENT_OBJECT_FORMAT_DETAILS;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_OBJECT_FORMAT_DETAILS_EN);
            key = DOCUMENT_OBJECT_FORMAT_DETAILS_EN;
        }
        if (currentNode != null) {
            documentObject.setFormatDetails(currentNode.textValue());
            objectNode.remove(key);
        }
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(documentObject, objectNode);
        // Deserialize referenceDocumentFile
        currentNode = objectNode.get(DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE);
        key = DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE_EN);
            key = DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE_EN;
        }
        if (currentNode != null) {
            documentObject.setReferenceDocumentFile(currentNode.textValue());
            objectNode.remove(key);
        }
        // Deserialize checksum
        currentNode = objectNode.get(DOCUMENT_OBJECT_CHECKSUM);
        key = DOCUMENT_OBJECT_CHECKSUM;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_OBJECT_CHECKSUM_EN);
            key = DOCUMENT_OBJECT_CHECKSUM_EN;
        }
        if (currentNode != null) {
            documentObject.setChecksum(currentNode.textValue());
            objectNode.remove(key);
        }
        // Deserialize checksumAlgorithm
        currentNode = objectNode.get(DOCUMENT_OBJECT_CHECKSUM_ALGORITHM);
        key = DOCUMENT_OBJECT_CHECKSUM_ALGORITHM;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_OBJECT_CHECKSUM_ALGORITHM_EN);
            key = DOCUMENT_OBJECT_CHECKSUM_ALGORITHM_EN;
        }
        if (currentNode != null) {
            documentObject.setChecksumAlgorithm(currentNode.textValue());
            objectNode.remove(key);
        }
        // Deserialize fileSize
        currentNode = objectNode.get(DOCUMENT_OBJECT_FILE_SIZE);
        key = DOCUMENT_OBJECT_FILE_SIZE;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_OBJECT_FILE_SIZE_EN);
            key = DOCUMENT_OBJECT_FILE_SIZE_EN;
        }
        if (currentNode != null) {
            documentObject.setFileSize(Long.getLong(currentNode.textValue()));
            objectNode.remove(key);
        }

        checkForObligatoryDocumentObjectValues(documentObject);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The DocumentObject object you tried to create is malformed. The "
                    + "following objects are not recognised as DocumentObject properties [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return documentObject;
    }

    @Override
    /**
     *
     *  DocumentObject is not a INoarkGeneralEntity
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {
    }

    public void checkForObligatoryDocumentObjectValues(DocumentObject documentObject) {
        if (documentObject.getVersionNumber() == null) {
            throw new NikitaMalformedInputDataException("The DocumentObject object you tried to create is " +
                    "malformed. The versionNumber field is mandatory, and you have submitted an empty value.");
        }
        if (documentObject.getVariantFormat() == null) {
            throw new NikitaMalformedInputDataException("The DocumentObject object you tried to create is " +
                    "malformed. The variantFormat field is mandatory, and you have submitted an empty value.");
        }
        if (documentObject.getFormat() == null) {
            throw new NikitaMalformedInputDataException("The DocumentObject object you tried to create is " +
                    "malformed. The format field is mandatory, and you have submitted an empty value.");
        }
    }
}
