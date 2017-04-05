package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

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
public class FondsCreatorHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INoarkSystemIdEntity noarkSystemIdEntity,
                                     HateoasNoarkObject fondsCreatorHateoas, JsonGenerator jgen) throws IOException {

        FondsCreator fondsCreator = (FondsCreator) noarkSystemIdEntity;

        jgen.writeStartObject();
        if (fondsCreator.getSystemId() != null) {
            jgen.writeStringField(SYSTEM_ID, fondsCreator.getSystemId());
        }
        if (fondsCreator.getFondsCreatorId() != null) {
            jgen.writeStringField(FONDS_CREATOR_ID, fondsCreator.getFondsCreatorId());
        }
        if (fondsCreator.getFondsCreatorName() != null) {
            jgen.writeStringField(FONDS_CREATOR_NAME, fondsCreator.getFondsCreatorName());
        }
        if (fondsCreator.getDescription() != null) {
            jgen.writeStringField(DESCRIPTION, fondsCreator.getDescription());
        }
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, fondsCreatorHateoas.getLinks(fondsCreator));
        jgen.writeEndObject();
    }
}
