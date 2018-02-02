package nikita.model.noark5.v4.hateoas.casehandling;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.casehandling.CorrespondencePartPersonHateoasSerializer;

import java.util.AbstractCollection;

import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART_PERSON;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a CorrespondencePartPersonHateoas
 * object
 */
@JsonSerialize(using = CorrespondencePartPersonHateoasSerializer.class)
public class CorrespondencePartPersonHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public CorrespondencePartPersonHateoas(INikitaEntity entity) {
        super(entity);
    }

    public CorrespondencePartPersonHateoas(AbstractCollection<INikitaEntity> entityList) {
        super(entityList, CORRESPONDENCE_PART_PERSON);
    }
}
