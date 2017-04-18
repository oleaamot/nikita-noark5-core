package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.CaseFileHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.CASE_FILE;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the File object
 * along with the hateoas links. It's not intended that you will manipulate the file object from here.
 *
 */
@JsonSerialize(using = CaseFileHateoasSerializer.class)
public class CaseFileHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public CaseFileHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public CaseFileHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, CASE_FILE);
    }
}