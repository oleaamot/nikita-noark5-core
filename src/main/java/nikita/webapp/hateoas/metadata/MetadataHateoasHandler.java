package nikita.webapp.handlers.hateoas.metadata;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.hateoas.Link;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.webapp.hateoas.HateoasHandler;
import nikita.webapp.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.common.config.Constants.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add hateoas links for metadata entities with specific information
 */
@Component()
public class MetadataHateoasHandler extends HateoasHandler implements IMetadataHateoasHandler {

    @Override
    public void addEntityLinks(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addCode(entity, hateoasNoarkObject);
    }

    @Override
    public void addCode(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + entity.getBaseTypeName() + SLASH + entity.getSystemId() + SLASH,
                REL_METADATA + entity.getBaseTypeName() + SLASH, false));
    }
    @Override
    public void addNewCode(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + NEW + DASH + entity.getBaseTypeName(),
                NIKITA_CONFORMANCE_REL + NEW + DASH + entity.getBaseTypeName() + SLASH, false));
    }
}
