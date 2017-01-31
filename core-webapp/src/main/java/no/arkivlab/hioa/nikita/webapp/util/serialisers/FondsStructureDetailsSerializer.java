package no.arkivlab.hioa.nikita.webapp.util.serialisers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import no.arkivlab.hioa.nikita.webapp.model.application.FondsStructureDetail;
import no.arkivlab.hioa.nikita.webapp.model.application.FondsStructureDetails;

import java.io.IOException;
import java.lang.reflect.Type;

public class FondsStructureDetailsSerializer extends StdSerializer<FondsStructureDetails> {

    public FondsStructureDetailsSerializer() {
        super(FondsStructureDetails.class);
    }

    @Override
    public void serialize(FondsStructureDetails fondsStructureDetails, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeArrayFieldStart("_links");

        for (FondsStructureDetail fondsStructureDetail : fondsStructureDetails.getFondsStructureDetails()) {
            jgen.writeStartObject();
            jgen.writeStringField("href", fondsStructureDetail.getHref());
            jgen.writeStringField("rel", fondsStructureDetail.getRel());
            jgen.writeBooleanField("templated", fondsStructureDetail.getTemplated());
            jgen.writeBooleanField("reltemplatedSpecified", fondsStructureDetail.getTemplatedSpecified());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
