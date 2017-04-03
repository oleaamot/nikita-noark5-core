package nikita.util.serializers.noark5v4.hateoas.metadata;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.IMetadataEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import nikita.util.serializers.noark5v4.hateoas.HateoasSerializer;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

import static nikita.config.N5ResourceMappings.CODE;
import static nikita.config.N5ResourceMappings.DESCRIPTION;

/**
 * Serialise an outgoing Metadata entity as JSON.
 * <p>
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 * <p>
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 */
public class MetadataHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INoarkSystemIdEntity noarkSystemIdEntity,
                                     HateoasNoarkObject metadataHateoas, JsonGenerator jgen) throws IOException {

        if (!(noarkSystemIdEntity instanceof nikita.model.noark5.v4.interfaces.entities.IMetadataEntity)) {
            throw new NikitaException("Internal error when serialising " + noarkSystemIdEntity +
                    " Not castable to nikita.model.noark5.v4.interfaces.entities.IMetadataEntity");
        }

        IMetadataEntity metadataEntity = (IMetadataEntity) noarkSystemIdEntity;

        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, metadataEntity);
        if (metadataEntity.getCode() != null) {
            jgen.writeStringField(CODE, metadataEntity.getCode());
        }
        if (metadataEntity.getDescription() != null) {
            jgen.writeStringField(DESCRIPTION, metadataEntity.getDescription());
        }
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, metadataHateoas.getLinks(metadataEntity));
        jgen.writeEndObject();
    }
}