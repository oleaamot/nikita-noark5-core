package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.CommonUtils;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static nikita.config.Constants.NOARK_DATE_FORMAT_PATTERN;
import static nikita.config.N5ResourceMappings.RECORD_ARCHIVED_BY;
import static nikita.config.N5ResourceMappings.RECORD_ARCHIVED_DATE;
import static nikita.util.CommonUtils.Hateoas.Deserialize;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming Record JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the Record object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a record object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in RecordController or RecordService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 *  - Record has no obligatory values required to be present at instantiation time
 */
public class RecordDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Record deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        Record record = new Record();

        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkSystemIdEntity (record, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkCreateEntity(record, objectNode);
        // Deserialize archivedBy
        JsonNode currentNode = objectNode.get(RECORD_ARCHIVED_BY);
        if (currentNode != null) {
            record.setArchivedBy(currentNode.textValue());
            objectNode.remove(RECORD_ARCHIVED_BY);
        }
        // Deserialize archivedDate
        currentNode = objectNode.get(RECORD_ARCHIVED_DATE);
        if (currentNode != null) {
            try {
                Date parsedDate = Deserialize.dateTimeFormat.parse(currentNode.textValue());
                record.setArchivedDate(parsedDate);
                objectNode.remove(RECORD_ARCHIVED_DATE);
            }
            catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The registrering you tried to create " +
                        "has a malformed arkivertDato. Make sure format is " + NOARK_DATE_FORMAT_PATTERN);
            }
        }

        // TODO: Handle deserialize of referanseArkivdel
        // You need a minor change to the domain model to handle this
        // Something like referenceSecondarySeries
        // You have the main fonds structure you link it to, but you also need to be able to link it to other
        // series objects
        //CommonCommonUtils.Hateoas.Deserialize.deserialiseReferenceMultipleSeries(objectNode);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The registrering you tried to create is malformed. The "
                    + "following fields are not recognised as registrering fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return record;
    }

    @Override
    /**
     *
     *  Record has no obligatory values required to be present at instantiation time
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {
    }
}
