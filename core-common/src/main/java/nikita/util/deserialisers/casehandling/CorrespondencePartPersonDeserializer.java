package nikita.util.deserialisers.casehandling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming CorrespondencePartPerson JSON object.
 * <p>
 * Detect if the CorrespondencePart is CorrespondencePartPerson, CorrespondencePartInternal or
 * CorrespondencePartUnit and returns an object the appropriate type.
 */
public class CorrespondencePartPersonDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public CorrespondencePartPerson deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {

        CorrespondencePartPerson correspondencePart = new CorrespondencePartPerson();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(correspondencePart, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseCorrespondencePartPersonEntity(correspondencePart, objectNode);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The korrespondansepartperson you tried to create is malformed. The "
                    + "following fields are not recognised as korrespondansepartperson fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return correspondencePart;
    }
}
