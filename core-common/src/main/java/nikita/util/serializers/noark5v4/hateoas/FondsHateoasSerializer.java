package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.hateoas.FondsHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.N5ResourceMappings.FONDS_STATUS;

/**
 *
 * Serialise an outgoing Fonds object as JSON.
 *
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 *
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 *
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 *
 */
public class FondsHateoasSerializer extends StdSerializer<FondsHateoas> {

    public FondsHateoasSerializer() {
        super(FondsHateoas.class);
    }

    @Override
    public void serialize(FondsHateoas fondsHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        Fonds fonds = fondsHateoas.getFonds();
        if (fonds != null) {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, fonds);
            CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, fonds);
            if (fonds.getFondsStatus() != null) {
                jgen.writeStringField(FONDS_STATUS, fonds.getFondsStatus());
            }
            CommonUtils.Hateoas.Serialize.printDocumentMedium(jgen, fonds);
            CommonUtils.Hateoas.Serialize.printStorageLocation(jgen, fonds);
            CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, fonds);
            CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, fonds);
            CommonUtils.Hateoas.Serialize.printFondsCreators(jgen, fonds);
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, fondsHateoas.getLinks());
            jgen.writeEndObject();
        }
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
