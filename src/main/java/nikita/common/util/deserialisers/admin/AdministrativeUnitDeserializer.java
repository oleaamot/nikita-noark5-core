package nikita.common.util.deserialisers.admin;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.admin.AdministrativeUnit;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming AdministrativeUnit JSON object.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 */
public class AdministrativeUnitDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public AdministrativeUnit deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();

        AdministrativeUnit administrativeUnit = new AdministrativeUnit();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity(administrativeUnit, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(administrativeUnit, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkFinaliseEntity(administrativeUnit, objectNode, errors);

        // Deserialize administrativeUnitStatus
        JsonNode currentNode = objectNode.get(N5ResourceMappings.ADMINISTRATIVE_UNIT_STATUS);
        if (currentNode != null) {
            administrativeUnit.setAdministrativeUnitStatus(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.ADMINISTRATIVE_UNIT_STATUS);
        }

        // Deserialize administrativeUnitName
        currentNode = objectNode.get(N5ResourceMappings.ADMINISTRATIVE_UNIT_NAME);
        if (currentNode != null) {
            administrativeUnit.setAdministrativeUnitName(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.ADMINISTRATIVE_UNIT_NAME);
        }

        // Deserialize shortName
        currentNode = objectNode.get(N5ResourceMappings.SHORT_NAME);
        if (currentNode != null) {
            administrativeUnit.setShortName(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.SHORT_NAME);
        }

        // Deserialize referenceToParent
        currentNode = objectNode.get(N5ResourceMappings.ADMINISTRATIVE_UNIT_PARENT_REFERENCE);
        if (currentNode != null) {
            AdministrativeUnit parentAdministrativeUnit = administrativeUnit.getParentAdministrativeUnit();
            // Will it not always be null??
            if (parentAdministrativeUnit == null) {
                parentAdministrativeUnit = new AdministrativeUnit();
                parentAdministrativeUnit.setSystemId(currentNode.textValue());
                parentAdministrativeUnit.getReferenceChildAdministrativeUnit().add(administrativeUnit);
            }
            objectNode.remove(N5ResourceMappings.ADMINISTRATIVE_UNIT_PARENT_REFERENCE);
        }

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The administrativEnhet you tried to create is malformed. The " +
                    "following fields are not recognised as administrativEnhet fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());

        return administrativeUnit;
    }
}

