package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.DocumentObjectHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.DOCUMENT_OBJECT;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the DocumentObject object
 * along with the hateoas links. It's not intended that you will manipulate the DocumentObject object from here.
 *
 */
@JsonSerialize(using = DocumentObjectHateoasSerializer.class)
public class DocumentObjectHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public DocumentObjectHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public DocumentObjectHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, DOCUMENT_OBJECT);
    }
}