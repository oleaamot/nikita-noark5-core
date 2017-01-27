package nikita.util.serializers.noark5v4;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nikita.model.noark5.v4.StorageLocation;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 1/9/17.
 */
public class StorageLocationSerializer extends StdSerializer<StorageLocation> {

    public StorageLocationSerializer() {
        super(StorageLocation.class);
    }

    @Override
    public void serialize(StorageLocation storageLocation, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField(TITLE, storageLocation.getStorageLocation());

        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
