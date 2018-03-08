package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.IAuthorisation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.HATEOASConstants.SELF;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add Hateoas links with information
 *
 */
@Component
public class HateoasHandler implements IHateoasHandler {

    protected IAuthorisation authorisation;

    @Value("${hateoas.publicAddress}")
    protected String contextPath;

    @Override
    public void addLinks(IHateoasNoarkObject hateoasNoarkObject,
                         IAuthorisation authorisation) {
        this.authorisation = authorisation;

        Iterable<INikitaEntity> entities = hateoasNoarkObject.getList();
        for (INikitaEntity entity : entities) {
            addSelfLink(entity, hateoasNoarkObject);
            addEntityLinks(entity, hateoasNoarkObject);
        }
        // If hateoasNoarkObject is a list add a self link.
        // { "entity": [], "_links": [] }
        if (!hateoasNoarkObject.isSingleEntity()) {
            String url = this.contextPath;
            Link selfLink = new Link(url, getRelSelfLink(), false);
            hateoasNoarkObject.addSelfLink(selfLink);
        }
    }

    @Override
    public void addLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject,
                                 IAuthorisation authorisation) {
        addLinks(hateoasNoarkObject, authorisation);
    }

    @Override
    public void addLinksOnNew(IHateoasNoarkObject hateoasNoarkObject,
                              IAuthorisation authorisation) {
        this.authorisation = authorisation;

        Iterable<INikitaEntity> entities = hateoasNoarkObject.getList();
        for (INikitaEntity entity : entities) {
            addEntityLinksOnNew(entity, hateoasNoarkObject);
        }
    }

    @Override
    public void addLinksOnRead(IHateoasNoarkObject hateoasNoarkObject,
                               IAuthorisation authorisation) {
        addLinks(hateoasNoarkObject, authorisation);
    }

    @Override
    public void addLinksOnUpdate(IHateoasNoarkObject hateoasNoarkObject,
                                 IAuthorisation authorisation) {
        addLinks(hateoasNoarkObject, authorisation);
    }

    @Override
    public void addLinksOnDelete(IHateoasNoarkObject hateoasNoarkObject,
                                 IAuthorisation authorisation) {
        addLinks(hateoasNoarkObject, authorisation);
    }

    @Override
    public void addLinksOnTemplate(IHateoasNoarkObject hateoasNoarkObject,
                                   IAuthorisation authorisation) {
        this.authorisation = authorisation;

        Iterable<INikitaEntity> entities = hateoasNoarkObject.getList();
        for (INikitaEntity entity : entities) {
            addEntityLinksOnTemplate(entity, hateoasNoarkObject);
        }
    }

    @Override
    public void addSelfLink(INikitaEntity entity,
                            IHateoasNoarkObject hateoasNoarkObject) {
        String systemId = entity.getSystemId();
        hateoasNoarkObject.addLink(entity, new Link(contextPath +
                HATEOAS_API_PATH + SLASH + entity.getFunctionalTypeName() +
                SLASH + entity.getBaseTypeName() + SLASH + systemId + SLASH,
                getRelSelfLink(), false));
    }

    @Override
    public void addDocumentMedium(INikitaEntity entity,
                                  IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath +
                HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH +
                DOCUMENT_MEDIUM, REL_METADATA_DOCUMENT_MEDIUM, false));
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinks(INikitaEntity entity,
                               IHateoasNoarkObject hateoasNoarkObject) {
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinksOnCreate(INikitaEntity entity,
                                       IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinksOnTemplate(INikitaEntity entity,
                                         IHateoasNoarkObject hateoasNoarkObject) {
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinksOnNew(INikitaEntity entity,
                                    IHateoasNoarkObject hateoasNoarkObject) {
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinksOnRead(INikitaEntity entity,
                                     IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    protected String getRelSelfLink() {
        return SELF;
    }

}

