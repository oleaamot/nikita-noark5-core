package nikita.model.noark5.v4.hateoas.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.admin.AdministrativeUnitHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.ADMINISTRATIVE_UNIT;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a AdministrativeUnitHateoas object
 */
@JsonSerialize(using = AdministrativeUnitHateoasSerializer.class)
public class AdministrativeUnitHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public AdministrativeUnitHateoas(INikitaEntity entity) {
        super(entity);
    }

    public AdministrativeUnitHateoas(List<INikitaEntity> entityList) {
        super(entityList, ADMINISTRATIVE_UNIT);
    }
}
