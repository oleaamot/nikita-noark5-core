package nikita.model.noark5.v4.hateoas.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.admin.UserHateoasSerializer;

import java.util.AbstractCollection;

import static nikita.config.N5ResourceMappings.USER;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a AdministrativeUnitHateoas object
 */
@JsonSerialize(using = UserHateoasSerializer.class)
public class UserHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public UserHateoas(INikitaEntity entity) {
        super(entity);
    }

    public UserHateoas(AbstractCollection<INikitaEntity> entityList) {
        super(entityList, USER);
    }
}
