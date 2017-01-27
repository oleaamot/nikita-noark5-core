package no.arkivlab.hioa.nikita.webapp.util.serialisers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import no.arkivlab.hioa.nikita.webapp.model.application.ApplicationDetails;
import no.arkivlab.hioa.nikita.webapp.model.application.ConformityLevel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class ApplicationDetailsSerializer extends StdSerializer<ApplicationDetails> {

    public ApplicationDetailsSerializer() {
        super(ApplicationDetails.class);
    }

    // TODO: Add some error handling, if application details is empty  etc
    @Override
    public void serialize(ApplicationDetails applicationDetails, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeArrayFieldStart("_links");

        ArrayList <ConformityLevel> conformityLevels = (ArrayList) applicationDetails.getConformityLevels();
        Iterator<ConformityLevel> iterator = conformityLevels.iterator();

        while(iterator.hasNext()){
            ConformityLevel conformityLevel = iterator.next();
            jgen.writeStartObject();
            jgen.writeStringField("href", conformityLevel.getHref());
            jgen.writeStringField("rel", conformityLevel.getRel());
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
