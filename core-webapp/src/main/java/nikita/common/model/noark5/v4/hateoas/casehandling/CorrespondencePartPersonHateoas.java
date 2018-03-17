package nikita.common.model.noark5.v4.hateoas.casehandling;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.casehandling.CorrespondencePartPersonHateoasSerializer;

import java.util.List;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a CorrespondencePartPersonHateoas
 * object
 */
@JsonSerialize(using = CorrespondencePartPersonHateoasSerializer.class)
public class CorrespondencePartPersonHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public CorrespondencePartPersonHateoas(INikitaEntity entity) {
        super(entity);
    }

    public CorrespondencePartPersonHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.CORRESPONDENCE_PART_PERSON);
    }
}
