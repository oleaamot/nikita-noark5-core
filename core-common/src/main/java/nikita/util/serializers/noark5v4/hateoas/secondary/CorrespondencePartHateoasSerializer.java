package nikita.util.serializers.noark5v4.hateoas.secondary;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.casehandling.CorrespondencePart;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.HateoasSerializer;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

/**
 *
 * Serialise an outgoing CorrespondencePart object as JSON.
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
public class CorrespondencePartHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INikitaEntity noarkSystemIdEntity,
                                     HateoasNoarkObject correspondencePartHateoas, JsonGenerator jgen) throws IOException {
        CorrespondencePart correspondencePart = (CorrespondencePart) noarkSystemIdEntity;

        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printCorrespondencePart(jgen, correspondencePart);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, correspondencePartHateoas.getLinks(correspondencePart));
        jgen.writeEndObject();
    }
}
