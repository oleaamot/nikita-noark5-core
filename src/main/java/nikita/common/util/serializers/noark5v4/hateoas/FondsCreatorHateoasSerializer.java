package nikita.common.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.common.model.noark5.v4.FondsCreator;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

/**
 * Serialise an outgoing FondsCreator object as JSON.
 * <p>
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 * <p>
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 */
public class FondsCreatorHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INikitaEntity noarkSystemIdEntity,
                                     HateoasNoarkObject fondsCreatorHateoas, JsonGenerator jgen) throws IOException {

        FondsCreator fondsCreator = (FondsCreator) noarkSystemIdEntity;
        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printFondsCreator(jgen, fondsCreator);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, fondsCreatorHateoas.getLinks(fondsCreator));
        jgen.writeEndObject();
    }
}
