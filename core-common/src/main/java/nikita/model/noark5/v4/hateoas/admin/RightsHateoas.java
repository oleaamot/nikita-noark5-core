package nikita.model.noark5.v4.hateoas.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.admin.AdministrativeUnitHateoasSerializer;

import java.util.AbstractCollection;

import static nikita.config.N5ResourceMappings.RIGHT;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a AdministrativeUnitHateoas object
 */
@JsonSerialize(using = AdministrativeUnitHateoasSerializer.class)
public class RightsHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public RightsHateoas(INikitaEntity entity) {
        super(entity);
    }

    public RightsHateoas(AbstractCollection<INikitaEntity> entityList) {
        super(entityList, RIGHT);
    }
}
