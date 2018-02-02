package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.BasicRecordHateoasSerializer;

import java.util.AbstractCollection;

import static nikita.config.N5ResourceMappings.BASIC_RECORD;

/**
 * Created by tsodring on 12/9/16.
 *
 */
@JsonSerialize(using = BasicRecordHateoasSerializer.class)
public class BasicRecordHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public BasicRecordHateoas(INikitaEntity entity) {
        super(entity);
    }

    public BasicRecordHateoas(AbstractCollection<INikitaEntity> entityList) {
        super(entityList, BASIC_RECORD);
    }
}
