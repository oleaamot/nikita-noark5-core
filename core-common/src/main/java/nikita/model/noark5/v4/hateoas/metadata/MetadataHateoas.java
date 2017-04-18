package nikita.model.noark5.v4.hateoas.metadata;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.metadata.MetadataHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 4/3/17.
 */
@JsonSerialize(using = MetadataHateoasSerializer.class)
public class MetadataHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public MetadataHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public MetadataHateoas(List<INoarkSystemIdEntity> entityList, String entityType) {
        super(entityList, entityType);
    }
}

