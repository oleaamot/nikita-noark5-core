package nikita.model.noark5.v4.hateoas.casehandling;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.casehandling.CorrespondencePartInternalHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART_INTERNAL;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a
 * CorrespondencePartInternalHateoas object
 */
@JsonSerialize(using = CorrespondencePartInternalHateoasSerializer.class)
public class CorrespondencePartInternalHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public CorrespondencePartInternalHateoas(INikitaEntity entity) {
        super(entity);
    }

    public CorrespondencePartInternalHateoas(List<INikitaEntity> entityList) {
        super(entityList, CORRESPONDENCE_PART_INTERNAL);
    }
}
