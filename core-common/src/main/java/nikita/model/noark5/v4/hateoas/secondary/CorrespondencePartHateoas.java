package nikita.model.noark5.v4.hateoas.secondary;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.secondary.CorrespondencePartHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a CorrespondencePartHateoas object
 */
@JsonSerialize(using = CorrespondencePartHateoasSerializer.class)
public class CorrespondencePartHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public CorrespondencePartHateoas(INikitaEntity entity) {
        super(entity);
    }

    public CorrespondencePartHateoas(List<INikitaEntity> entityList) {
        super(entityList, CORRESPONDENCE_PART);
    }
}
