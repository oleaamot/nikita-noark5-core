package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.metadata;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.HateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.IAuthorisation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static nikita.config.Constants.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add hateoas links for metadata entities with specific information
 */
@Component()
public class MetadataHateoasHandler extends HateoasHandler implements IMetadataHateoasHandler {

    @Override
    public void addLinks(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request,
                         IAuthorisation authorisation) {
        super.addLinks(hateoasNoarkObject, request, authorisation);

        // As this is a top-domain entity, we need to support ny-
        // { "entity": [], "_links": [] }
        if (!hateoasNoarkObject.isSingleEntity()) {
            List<INikitaEntity> entities = hateoasNoarkObject.getList();
            INikitaEntity entity = entities.get(0);
            if (entity == null) {
                throw new NikitaException("Internal error, could not create metadata new object");
            }
            String rel = NIKITA_CONFORMANCE_REL + NEW + DASH + entity.getBaseTypeName() + SLASH;
            Link newCodeLink = new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_METADATA_PATH + SLASH + NEW + DASH + entity.getBaseTypeName(),
                    rel, false);
            hateoasNoarkObject.addLink(newCodeLink);
        }
    }

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
