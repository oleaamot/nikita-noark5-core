package nikita.common.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.FondsHateoasSerializer;

import java.util.List;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a FondsHateoas object
 */
@JsonSerialize(using = FondsHateoasSerializer.class)
public class FondsHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public FondsHateoas(INikitaEntity entity) {
        super(entity);
    }

    public FondsHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.FONDS);
    }
}
