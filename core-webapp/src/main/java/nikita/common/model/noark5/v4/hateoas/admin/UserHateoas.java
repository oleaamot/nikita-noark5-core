package nikita.common.model.noark5.v4.hateoas.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.admin.UserHateoasSerializer;

import java.util.List;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a AdministrativeUnitHateoas object
 */
@JsonSerialize(using = UserHateoasSerializer.class)
public class UserHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public UserHateoas(INikitaEntity entity) {
        super(entity);
    }

    public UserHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.USER);
    }
}
