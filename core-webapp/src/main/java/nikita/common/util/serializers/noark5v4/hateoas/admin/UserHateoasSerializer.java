package nikita.common.util.serializers.noark5v4.hateoas.admin;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.common.model.noark5.v4.admin.User;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.serializers.noark5v4.hateoas.HateoasSerializer;

import java.io.IOException;

import static nikita.common.config.N5ResourceMappings.USER_NAME;

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
public class UserHateoasSerializer extends HateoasSerializer implements nikita.common.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INikitaEntity noarkSystemIdEntity,
                                     HateoasNoarkObject userHateoas, JsonGenerator jgen) throws IOException {
        User user = (User) noarkSystemIdEntity;

        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, user);
        if (user.getUsername() != null) {
            jgen.writeStringField(USER_NAME, user.getUsername());
        }
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, user);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, user);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, userHateoas.getLinks(user));
        jgen.writeEndObject();
    }
}
