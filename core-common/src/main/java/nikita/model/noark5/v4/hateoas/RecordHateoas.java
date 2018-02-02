package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.RecordHateoasSerializer;

import java.util.AbstractCollection;

import static nikita.config.N5ResourceMappings.REGISTRATION;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the Record object
 * along with the hateoas links. It's not intended that you will manipulate the Record object from here.
 *
 */
@JsonSerialize(using = RecordHateoasSerializer.class)
public class RecordHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public RecordHateoas(INikitaEntity entity) {
        super(entity);
    }

    public RecordHateoas(AbstractCollection<INikitaEntity> entityList) {
        super(entityList, REGISTRATION);
    }

}
