package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.FileHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.FILE;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the File object
 * along with the hateoas links. It's not intended that you will manipulate the file object from here.
 *
 */
@JsonSerialize(using = FileHateoasSerializer.class)
public class FileHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public FileHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public FileHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, FILE);
    }

}