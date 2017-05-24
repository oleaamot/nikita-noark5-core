package nikita.util.serializers.noark5v4.hateoas.admin;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.HateoasSerializer;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

import static nikita.config.N5ResourceMappings.FONDS_STATUS;

/**
 * Serialise an outgoing Fonds object as JSON.
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
public class UserHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INikitaEntity noarkSystemIdEntity,
                                     HateoasNoarkObject fondsHateoas, JsonGenerator jgen) throws IOException {
        Fonds fonds = (Fonds) noarkSystemIdEntity;

        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, fonds);
        CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, fonds);
        if (fonds.getFondsStatus() != null) {
            jgen.writeStringField(FONDS_STATUS, fonds.getFondsStatus());
        }
        CommonUtils.Hateoas.Serialize.printDocumentMedium(jgen, fonds);
        CommonUtils.Hateoas.Serialize.printStorageLocation(jgen, fonds);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, fonds);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, fonds);
        CommonUtils.Hateoas.Serialize.printFondsCreators(jgen, fonds);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, fondsHateoas.getLinks(fonds));
        jgen.writeEndObject();
    }
}
