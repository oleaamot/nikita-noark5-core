package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.SeriesHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.SERIES;

@JsonSerialize(using = SeriesHateoasSerializer.class)
public class SeriesHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public SeriesHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public SeriesHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, SERIES);
    }
}
