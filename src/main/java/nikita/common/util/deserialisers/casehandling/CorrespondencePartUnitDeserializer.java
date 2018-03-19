package nikita.common.util.deserialisers.casehandling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming CorrespondencePart JSON object.
 * <p>
 * Detect if the CorrespondencePart is CorrespondencePartPerson, CorrespondencePartInternal or
 * CorrespondencePartUnit and returns an object the appropriate type.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 */
public class CorrespondencePartUnitDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public CorrespondencePartUnit deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();

        CorrespondencePartUnit correspondencePartUnit = new CorrespondencePartUnit();
        ObjectNode objectNode = mapper.readTree(jsonParser);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(correspondencePartUnit, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseCorrespondencePartUnitEntity(correspondencePartUnit, objectNode, errors);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The korrespondansepartenhet you tried to create is malformed. The " +
                    "following fields are not recognised as korrespondansepartenhet fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());

        return correspondencePartUnit;
    }
}
/*
        if (null != currentNode) {
            ObjectNode correspondencePartTypeObjectNode = currentNode.deepCopy();
            correspondencePartType = new CorrespondencePartType();
            //
            JsonNode correspondencePartTypeNode = correspondencePartTypeObjectNode.get(CODE);

            if (null != correspondencePartTypeNode) {
                String correspondencePartTypeCode = correspondencePartTypeNode.asText();

                if (correspondencePartTypeCode.equalsIgnoreCase()) {

                }
                else if (correspondencePartTypeCode.equalsIgnoreCase()) {

                }
                else if (correspondencePartTypeCode.equalsIgnoreCase()) {

                }
                else {
                    errors.append("The korrespondansepart you are trying to create " +
                            "is malformed. kode under korrespondanseparttype has a non-recognised value. The value " +
                            "you set is " + correspondencePartTypeCode + ". ");
                }
            }
            else {
                errors.append("The korrespondansepart you are trying to create " +
                        "is malformed. kode under korrespondanseparttype has no value. ");
            }
        }
        else {
            errors.append("The korrespondansepart you are trying to create is malformed" +
                    ". It is missing korrespondanseparttype. It is not possible to construct a valid " +
                    " korrespondansepart without this value being set. ");
        }
*/
