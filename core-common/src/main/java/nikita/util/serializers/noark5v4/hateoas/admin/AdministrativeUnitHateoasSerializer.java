package nikita.util.serializers.noark5v4.hateoas.admin;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.admin.AdministrativeUnit;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.HateoasSerializer;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

import static nikita.config.N5ResourceMappings.*;

/**
 * Serialise an outgoing AdministrativeUnit object as JSON.
 * <p>
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 * <p>
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 */
public class AdministrativeUnitHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INikitaEntity noarkSystemIdEntity,
                                     HateoasNoarkObject administrativeUnitHateoas, JsonGenerator jgen) throws IOException {
        AdministrativeUnit administrativeUnit = (AdministrativeUnit) noarkSystemIdEntity;

        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, administrativeUnit);
        if (administrativeUnit.getAdministrativeUnitName() != null) {
            jgen.writeStringField(ADMINISTRATIVE_UNIT_NAME, administrativeUnit.getAdministrativeUnitName());
        }
        if (administrativeUnit.getShortName() != null) {
            jgen.writeStringField(SHORT_NAME, administrativeUnit.getShortName());
        }
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, administrativeUnit);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, administrativeUnit);
        if (administrativeUnit.getAdministrativeUnitStatus() != null) {
            jgen.writeStringField(ADMINISTRATIVE_UNIT_STATUS, administrativeUnit.getAdministrativeUnitStatus());
        }
        if (administrativeUnit.getParentAdministrativeUnit() != null) {
            jgen.writeStringField(ADMINISTRATIVE_UNIT_PARENT_REFERENCE,
                    administrativeUnit.getParentAdministrativeUnit().getSystemId());
        }
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, administrativeUnitHateoas.getLinks(administrativeUnit));
        jgen.writeEndObject();
    }
}
