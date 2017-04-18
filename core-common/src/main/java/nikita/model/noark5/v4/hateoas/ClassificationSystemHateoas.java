package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.ClassificationSystemHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.CLASSIFICATION_SYSTEM;

@JsonSerialize(using = ClassificationSystemHateoasSerializer.class)
public class ClassificationSystemHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public ClassificationSystemHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public ClassificationSystemHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, CLASSIFICATION_SYSTEM);
    }
}