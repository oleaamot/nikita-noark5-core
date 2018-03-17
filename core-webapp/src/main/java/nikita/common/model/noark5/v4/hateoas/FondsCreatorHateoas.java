package nikita.common.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.FondsCreatorHateoasSerializer;

import java.util.List;

@JsonSerialize(using = FondsCreatorHateoasSerializer.class)
public class FondsCreatorHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public FondsCreatorHateoas(INikitaEntity entity) {
        super(entity);
    }

    public FondsCreatorHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.FONDS_CREATOR);
    }
}
