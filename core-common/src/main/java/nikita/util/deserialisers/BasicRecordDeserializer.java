package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.BasicRecord;
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
 *
 * Deserialise an incoming BasicRecord JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the BasicRecord object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a basicRecord object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in BasicRecordController or BasicRecordService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 *  - BasicRecord has no obligatory values required to be present at instantiation time
 */
public class BasicRecordDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public BasicRecord deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        BasicRecord basicRecord = new BasicRecord();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general record properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity (basicRecord, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(basicRecord, objectNode);

        // Deserialize archivedBy
        JsonNode currentNode = objectNode.get(RECORD_ARCHIVED_BY);
        if (null != currentNode) {
            basicRecord.setArchivedBy(currentNode.textValue());
            objectNode.remove(RECORD_ARCHIVED_BY);
        }
        // Deserialize archivedDate
        currentNode = objectNode.get(RECORD_ARCHIVED_DATE);
        if (null != currentNode) {
            try {
                Date parsedDate = Deserialize.dateTimeFormat.parse(currentNode.textValue());
                basicRecord.setArchivedDate(parsedDate);
                objectNode.remove(RECORD_ARCHIVED_DATE);
            }
            catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The basisregistrering you tried to create " +
                        "has a malformed arkivertDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }
        // Deserialize general basicRecord properties
        // Deserialize recordId
        currentNode = objectNode.get(BASIC_RECORD_ID);
        if (null != currentNode) {
            basicRecord.setRecordId(currentNode.textValue());
            objectNode.remove(BASIC_RECORD_ID);
        }
        // Deserialize title (not using utils to preserve order)
        currentNode = objectNode.get(TITLE);
        if (null != currentNode) {
            basicRecord.setTitle(currentNode.textValue());
            objectNode.remove(TITLE);
        }
        // Deserialize  officialTitle
        currentNode = objectNode.get(FILE_PUBLIC_TITLE);
        if (null != currentNode) {
            basicRecord.setOfficialTitle(currentNode.textValue());
            objectNode.remove(FILE_PUBLIC_TITLE);
        }
        // Deserialize description
        currentNode = objectNode.get(DESCRIPTION);
        if (null != currentNode) {
            basicRecord.setDescription(currentNode.textValue());
            objectNode.remove(DESCRIPTION);
        }
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(basicRecord, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseAuthor(basicRecord, objectNode);
        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The basisregistrering you tried to create is malformed. The "
                    + "following fields are not recognised as basisregistrering fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return basicRecord;
    }

    @Override
    /**
     *
     *  BasicRecord has no obligatory values required to be present at instantiation time
     *  TODO: This is a lie, read the standard ... muppet!
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {
    }
}
