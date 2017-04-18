package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.FondsHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.FONDS;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a FondsHateoas object
 */
@JsonSerialize(using = FondsHateoasSerializer.class)
public class FondsHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public FondsHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public FondsHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, FONDS);
    }
}
