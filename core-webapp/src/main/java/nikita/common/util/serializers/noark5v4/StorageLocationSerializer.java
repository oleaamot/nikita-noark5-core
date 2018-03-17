package nikita.common.util.serializers.noark5v4;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nikita.common.model.noark5.v4.secondary.StorageLocation;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.common.config.N5ResourceMappings.TITLE;

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
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        throw new UnsupportedOperationException();
    }
}
