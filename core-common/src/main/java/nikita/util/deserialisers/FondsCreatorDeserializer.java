package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.CommonUtils;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming FondsCreator JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the fondsCreator object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a fondsCreator object correctly. This is because e.g the import interface will require 
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in FondsCreatorController or FondsCreatorService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 */
public class FondsCreatorDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public FondsCreator deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {

        FondsCreator fondsCreator = new FondsCreator();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseFondsCreator(fondsCreator, objectNode);

        // Check that all obligatory values are present
        checkForObligatoryFondsCreatorValues(fondsCreator);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The arkivskaper you tried to create is malformed. The "
                    + "following fields are not recognised as arkivskaper fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }

        return fondsCreator;
    }

    @Override
    /**
     *
     * FondsCreator is not a INoarkGeneralEntity
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {}

    /**
     *
     * FondsCreator is not a INoarkGeneralEntity
     */
    public void checkForObligatoryFondsCreatorValues(FondsCreator fondsCreator) {

        if (fondsCreator.getFondsCreatorId() == null) {
            throw new NikitaMalformedInputDataException("The arkivskaper you tried to create is malformed. The "
                    + "arkivskaperID field is mandatory, and you have submitted an empty value.");
        }
        if (fondsCreator.getFondsCreatorName() == null) {
            throw new NikitaMalformedInputDataException("The arkivskaper you tried to create is malformed. The "
                    + "arkivskaperNavn field is mandatory, and you have submitted an empty value.");
        }
    }

}