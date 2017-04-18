package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.ClassHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.CLASS;

@JsonSerialize(using = ClassHateoasSerializer.class)
public class ClassHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public ClassHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public ClassHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, CLASS);
    }
}