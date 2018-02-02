package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.SeriesHateoasSerializer;

import java.util.AbstractCollection;

import static nikita.config.N5ResourceMappings.SERIES;

@JsonSerialize(using = SeriesHateoasSerializer.class)
public class SeriesHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public SeriesHateoas(INikitaEntity entity) {
        super(entity);
    }

    public SeriesHateoas(AbstractCollection<INikitaEntity> entityList) {
        super(entityList, SERIES);
    }
}
