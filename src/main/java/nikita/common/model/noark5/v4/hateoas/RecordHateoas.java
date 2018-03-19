package nikita.common.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.RecordHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 * <p>
 * Using composition rather than inheritance. Although this class is really only a placeholder for the Record object
 * along with the hateoas links. It's not intended that you will manipulate the Record object from here.
 */
@JsonSerialize(using = RecordHateoasSerializer.class)
public class RecordHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public RecordHateoas(INikitaEntity entity) {
        super(entity);
    }

    public RecordHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.REGISTRATION);
    }

}
