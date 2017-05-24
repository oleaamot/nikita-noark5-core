package nikita.util.deserialisers.admin;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.Fonds;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

import static nikita.config.N5ResourceMappings.FONDS_STATUS;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming Fonds JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *

 *
 * Note this implementation expects that the fonds object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not
 * enforce this and will deserialize a fonds object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in FondsController or FondsService
 *
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 */
public class UserDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Fonds deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {

        Fonds fonds = new Fonds();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // TODO : Are we deserialising parent? No, it's not done here or is it????

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkEntity(fonds, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(fonds, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseStorageLocation(fonds, objectNode);

        // Deserialize seriesStatus
        JsonNode currentNode = objectNode.get(FONDS_STATUS);
        if (currentNode != null) {
            fonds.setFondsStatus(currentNode.textValue());
            objectNode.remove(FONDS_STATUS);
        }

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The arkiv you tried to create is malformed. The "
                    + "following fields are not recognised as arkiv fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return fonds;
    }
}