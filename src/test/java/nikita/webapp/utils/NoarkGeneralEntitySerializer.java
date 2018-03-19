package nikita.webapp.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nikita.common.model.noark5.v4.NoarkGeneralEntity;
import nikita.common.util.CommonUtils;

import java.io.IOException;

/**
 * Created by tsodring on 17/03/17.
 * <p>
 * Serialise a NoarkEntity as a JSON object.
 * <p>
 * Originally added to aid testing as we need to be able generate valid JSON
 * with Norwegian names
 */
public class NoarkGeneralEntitySerializer extends StdSerializer<NoarkGeneralEntity> {

    public NoarkGeneralEntitySerializer() {
        super(NoarkGeneralEntity.class);
    }

    @Override
    public void serialize(NoarkGeneralEntity noarkEntity,
                          JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, noarkEntity);
        CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, noarkEntity);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, noarkEntity);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, noarkEntity);
        jgen.writeEndObject();
    }
}
