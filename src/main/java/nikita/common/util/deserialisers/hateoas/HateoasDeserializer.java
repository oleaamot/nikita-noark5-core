package nikita.common.util.deserialisers.hateoas;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.model.noark5.v4.NoarkGeneralEntity;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.util.CommonUtils;

import java.io.IOException;

public class HateoasDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public HateoasNoarkObject deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();

        NoarkGeneralEntity entity = new NoarkGeneralEntity();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkEntity(entity, objectNode, errors);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The arkiv you tried to create is malformed. The " +
                    "following fields are not recognised as arkiv fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        //if (0 < errors.length())
        //    throw new NikitaMalformedInputDataException(errors.toString());

        HateoasNoarkObject hateoasNoarkObject = new HateoasNoarkObject(entity);

        return hateoasNoarkObject;
    }
}
