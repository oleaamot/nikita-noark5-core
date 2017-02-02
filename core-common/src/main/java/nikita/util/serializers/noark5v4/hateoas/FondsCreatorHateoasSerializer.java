package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.hateoas.FondsCreatorHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.N5ResourceMappings.*;

/**
 * Serialise an outgoing FondsCreator object as JSON.
 * <p>
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 * <p>
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 * <p>
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 */
public class FondsCreatorHateoasSerializer extends StdSerializer<FondsCreatorHateoas> {

    public FondsCreatorHateoasSerializer() {
        super(FondsCreatorHateoas.class);
    }

    @Override
    public void serialize(FondsCreatorHateoas fondsCreatorHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        Iterable<FondsCreator> fondsIterable = fondsCreatorHateoas.getFondsCreatorIterable();
        if (fondsIterable != null && Iterables.size(fondsIterable) > 0) {
            jgen.writeStartObject();
            jgen.writeFieldName("");
            jgen.writeStartArray();
            for (FondsCreator fondsCreator : fondsIterable) {
                serializeFonds(fondsCreator, fondsCreatorHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        } else if (fondsCreatorHateoas.getFondsCreator() != null) {
            serializeFonds(fondsCreatorHateoas.getFondsCreator(), fondsCreatorHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links _links : []
        else {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
            jgen.writeEndObject();
        }
    }

    private void serializeFonds(FondsCreator fondsCreator, FondsCreatorHateoas fondsCreatorHateoas,
                                JsonGenerator jgen, SerializerProvider provider) throws IOException {

        if (fondsCreator != null) {
            jgen.writeStartObject();
            if (fondsCreator.getFondsCreatorId() != null) {
                jgen.writeStringField(FONDS_CREATOR_ID, fondsCreator.getFondsCreatorId());
            }
            if (fondsCreator.getFondsCreatorName() != null) {
                jgen.writeStringField(FONDS_CREATOR_NAME, fondsCreator.getFondsCreatorName());
            }
            if (fondsCreator.getDescription() != null) {
                jgen.writeStringField(DESCRIPTION, fondsCreator.getDescription());
            }
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, fondsCreatorHateoas.getLinks());
            jgen.writeEndObject();
        }
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
