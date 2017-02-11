package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

/**
 *
 * Serialise an outgoing Class object as JSON.
 *
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 *
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 *
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 *
 */
public class ClassHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INoarkSystemIdEntity noarkSystemIdEntity,
                                     HateoasNoarkObject classHateoas, JsonGenerator jgen) throws IOException {

        Class klass = (Class) noarkSystemIdEntity;

        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, klass);
        CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, klass);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, klass);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, klass);
        // TODO: Fix this ! CommonCommonUtils.Hateoas.Serialize.printCrossReference(jgen, klass);
        CommonUtils.Hateoas.Serialize.printDisposal(jgen, klass);
        CommonUtils.Hateoas.Serialize.printScreening(jgen, klass);
        CommonUtils.Hateoas.Serialize.printClassified(jgen, klass);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, classHateoas.getLinks(klass));
        jgen.writeEndObject();
    }
}
