package nikita.util.deserialisers.casehandling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming CorrespondencePart JSON object.
 * <p>
 * Detect if the CorrespondencePart is CorrespondencePartPerson, CorrespondencePartInternal or
 * CorrespondencePartInternal and returns an object the appropriate type.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 */
public class CorrespondencePartInternalDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public CorrespondencePartInternal deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {

        CorrespondencePartInternal correspondencePartInternal = new CorrespondencePartInternal();
        ObjectNode objectNode = mapper.readTree(jsonParser);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(correspondencePartInternal, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseCorrespondencePartInternalEntity(correspondencePartInternal, objectNode);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The korrespondansepartintern you tried to create is malformed. The "
                    + "following fields are not recognised as korrespondansepartintern fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return correspondencePartInternal;
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
                    throw new NikitaMalformedInputDataException("The korrespondansepart you are trying to create " +
                            "is malformed. kode under korrespondanseparttype has a non-recognised value. The value " +
                            "you set is " + correspondencePartTypeCode);
                }
            }
            else {
                throw new NikitaMalformedInputDataException("The korrespondansepart you are trying to create " +
                        "is malformed. kode under korrespondanseparttype has no value ");
            }
        }
        else {
            throw new NikitaMalformedInputDataException("The korrespondansepart you are trying to create is malformed" +
                    ". It is missing korrespondanseparttype. It is not possible to construct a valid " +
                    " korrespondansepart without this value being set");
        }
*/
