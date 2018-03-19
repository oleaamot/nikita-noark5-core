package nikita.common.model.noark5.v4.hateoas.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.admin.AdministrativeUnitHateoasSerializer;

import java.util.List;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a AdministrativeUnitHateoas object
 */
@JsonSerialize(using = AdministrativeUnitHateoasSerializer.class)
public class RightsHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public RightsHateoas(INikitaEntity entity) {
        super(entity);
    }

    public RightsHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.RIGHT);
    }
}
