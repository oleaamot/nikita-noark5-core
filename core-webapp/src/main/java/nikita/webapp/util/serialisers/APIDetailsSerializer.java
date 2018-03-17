package nikita.webapp.util.serialisers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nikita.webapp.application.APIDetail;
import nikita.webapp.application.APIDetails;

import java.io.IOException;
import java.lang.reflect.Type;

public class APIDetailsSerializer extends StdSerializer<APIDetails> {

    public APIDetailsSerializer() {
        super(APIDetails.class);
    }

    @Override
    public void serialize(APIDetails apiDetails, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeArrayFieldStart("_links");

        for (APIDetail apiDetail : apiDetails.getApiDetails()) {
            jgen.writeStartObject();
            jgen.writeStringField("href", apiDetail.getHref());
            jgen.writeStringField("rel", apiDetail.getRel());
            jgen.writeBooleanField("templated", apiDetail.getTemplated());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        throw new UnsupportedOperationException();
    }
}
