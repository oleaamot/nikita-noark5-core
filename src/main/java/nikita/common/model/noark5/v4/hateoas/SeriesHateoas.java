package nikita.common.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.SeriesHateoasSerializer;

import java.util.List;

@JsonSerialize(using = SeriesHateoasSerializer.class)
public class SeriesHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public SeriesHateoas(INikitaEntity entity) {
        super(entity);
    }

    public SeriesHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.SERIES);
    }
}
