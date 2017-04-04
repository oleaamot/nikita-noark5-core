package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.metadata;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.CommonUtils;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.HateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add hateoas links for metadata entities with specific information
 */
@Component()
public class MetadataHateoasHandler extends HateoasHandler implements IMetadataHateoasHandler {

    @Override
    public void addSelfLink(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        String systemId = entity.getSystemId();
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + detectType(entity) + SLASH + systemId + SLASH,
                getRelSelfLink(), false));
    }

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addCode(entity, hateoasNoarkObject);
        addNewCode(entity, hateoasNoarkObject);
    }

    @Override
    public void addCode(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + detectType(entity) + SLASH + entity.getSystemId() + SLASH,
                REL_METADATA + detectType(entity) + SLASH, false));
    }

    @Override
    public void addNewCode(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + NEW + DASH + detectType(entity),
                REL_METADATA + NEW + DASH + detectType(entity) + SLASH, false));
    }

    private String detectType(INoarkSystemIdEntity entity) {
        return CommonUtils.Hateoas.getMetatdatEntityType(entity.getClass().getSimpleName());
    }
}
