package nikita.common.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.BasicRecord;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming BasicRecord JSON object.
 * <p>
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 * <p>
 * <p>
 * Note this implementation expects that the BasicRecord object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not
 * enforce this and will deserialize a basicRecord object correctly. This is because e.g the import interface will require
 * such functionality.
 * <p>
 * - Testing of compliance of properties is handled by the core, either in BasicRecordController or BasicRecordService
 * <p>
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 * - BasicRecord has no obligatory values required to be present at instantiation time
 */
public class BasicRecordDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public BasicRecord deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();
        BasicRecord basicRecord = new BasicRecord();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general record properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(basicRecord, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(basicRecord, objectNode, errors);

        // Deserialize archivedBy
        JsonNode currentNode = objectNode.get(N5ResourceMappings.RECORD_ARCHIVED_BY);
        if (null != currentNode) {
            basicRecord.setArchivedBy(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.RECORD_ARCHIVED_BY);
        }
        // Deserialize archivedDate
        basicRecord.setArchivedDate(CommonUtils.Hateoas.Deserialize.deserializeDateTime(N5ResourceMappings.RECORD_ARCHIVED_DATE, objectNode, errors));
        // Deserialize general basicRecord properties
        // Deserialize recordId
        currentNode = objectNode.get(N5ResourceMappings.BASIC_RECORD_ID);
        if (null != currentNode) {
            basicRecord.setRecordId(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.BASIC_RECORD_ID);
        }
        // Deserialize title (not using utils to preserve order)
        currentNode = objectNode.get(N5ResourceMappings.TITLE);
        if (null != currentNode) {
            basicRecord.setTitle(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.TITLE);
        }
        // Deserialize  officialTitle
        currentNode = objectNode.get(N5ResourceMappings.FILE_PUBLIC_TITLE);
        if (null != currentNode) {
            basicRecord.setOfficialTitle(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.FILE_PUBLIC_TITLE);
        }
        // Deserialize description
        currentNode = objectNode.get(N5ResourceMappings.DESCRIPTION);
        if (null != currentNode) {
            basicRecord.setDescription(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.DESCRIPTION);
        }
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(basicRecord, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseAuthor(basicRecord, objectNode, errors);
        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The basisregistrering you tried to create is malformed. The " +
                    "following fields are not recognised as basisregistrering fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());

        return basicRecord;
    }
}
