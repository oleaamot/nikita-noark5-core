package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.FondsCreatorHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.FONDS_CREATOR;

@JsonSerialize(using = FondsCreatorHateoasSerializer.class)
public class FondsCreatorHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public FondsCreatorHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public FondsCreatorHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, FONDS_CREATOR);
    }
}