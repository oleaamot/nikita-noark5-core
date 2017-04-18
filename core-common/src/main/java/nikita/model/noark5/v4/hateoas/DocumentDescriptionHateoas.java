package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.DocumentDescriptionHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.DOCUMENT_DESCRIPTION;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the DocumentDescription object
 * along with the hateoas links. It's not intended that you will manipulate the DocumentDescription object from here.
 *
 */
@JsonSerialize(using = DocumentDescriptionHateoasSerializer.class)
public class DocumentDescriptionHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public DocumentDescriptionHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public DocumentDescriptionHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, DOCUMENT_DESCRIPTION);
    }
}