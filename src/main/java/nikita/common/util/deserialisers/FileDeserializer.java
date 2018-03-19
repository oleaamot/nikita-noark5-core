package nikita.common.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.File;
import nikita.common.model.noark5.v4.Series;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming File JSON object.
 * <p>
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 * <p>
 * <p>
 * Note this implementation expects that the File object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not
 * enforce this and will deserialize a file object correctly. This is because e.g the import interface will require
 * such functionality.
 * <p>
 * - Testing of compliance of properties is handled by the core, either in FileController or FileService
 * <p>
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 */
public class FileDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public File deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();
        File file = new File();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkEntity(file, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(file, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseStorageLocation(file, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseKeyword(file, objectNode, errors);

        // Deserialize fileId
        JsonNode currentNode = objectNode.get(N5ResourceMappings.FILE_ID);
        if (null != currentNode) {
            file.setFileId(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.FILE_ID);
        }
        // Deserialize officialTitle
        currentNode = objectNode.get(N5ResourceMappings.FILE_PUBLIC_TITLE);
        if (null != currentNode) {
            file.setOfficialTitle(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.FILE_PUBLIC_TITLE);
        }
        // TODO: FIX THIS CommonCommonUtils.Hateoas.Deserialize.deserialiseCrossReference(file, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseComments(file, objectNode, errors);
        file.setReferenceDisposal(CommonUtils.Hateoas.Deserialize.deserialiseDisposal(objectNode, errors));
        file.setReferenceScreening(CommonUtils.Hateoas.Deserialize.deserialiseScreening(objectNode, errors));
        file.setReferenceClassified(CommonUtils.Hateoas.Deserialize.deserialiseClassified(objectNode, errors));

        // Deserialize referenceSeries
        currentNode = objectNode.get(N5ResourceMappings.REFERENCE_SERIES);
        if (null != currentNode) {
            Series series = new Series();
            String systemID = currentNode.textValue();
            if (systemID != null) {
                series.setSystemId(systemID);
            }
            file.setReferenceSeries(series);
            objectNode.remove(N5ResourceMappings.REFERENCE_SERIES);
        }

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The mappe you tried to create is malformed. The " +
                    "following fields are not recognised as mappe fields  [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());

        return file;
    }
}
