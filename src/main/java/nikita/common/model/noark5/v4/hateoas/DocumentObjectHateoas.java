package nikita.common.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.serializers.noark5v4.hateoas.DocumentObjectHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 * <p>
 * Using composition rather than inheritance. Although this class is really only a placeholder for the DocumentObject object
 * along with the hateoas links. It's not intended that you will manipulate the DocumentObject object from here.
 */
@JsonSerialize(using = DocumentObjectHateoasSerializer.class)
public class DocumentObjectHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public DocumentObjectHateoas(INikitaEntity entity) {
        super(entity);
    }

    public DocumentObjectHateoas(List<INikitaEntity> entityList) {
        super(entityList, N5ResourceMappings.DOCUMENT_OBJECT);
    }
}
