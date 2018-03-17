package nikita.common.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.ClassHateoasSerializer;

import java.util.List;

@JsonSerialize(using = ClassHateoasSerializer.class)
public class ClassHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public ClassHateoas(INikitaEntity entity) {
        super(entity);
    }

    public ClassHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.CLASS);
    }
}
