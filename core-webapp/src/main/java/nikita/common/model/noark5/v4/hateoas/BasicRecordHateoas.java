package nikita.common.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.BasicRecordHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 */
@JsonSerialize(using = BasicRecordHateoasSerializer.class)
public class BasicRecordHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public BasicRecordHateoas(INikitaEntity entity) {
        super(entity);
    }

    public BasicRecordHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.BASIC_RECORD);
    }
}
