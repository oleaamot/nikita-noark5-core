package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.IAuthorisation;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.*;
import static nikita.config.HATEOASConstants.SELF;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add Hateoas links with information
 * <p>
 * Note that the contextServletPath is set when addLinks is called
 */
@Component("hateoasHandler")
public class HateoasHandler implements IHateoasHandler {

    protected String contextServletPath = "";
    protected String servletPath = "";
    protected String contextPath = "";
    IAuthorisation authorisation;

    @Override
    public void addLinks(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request,
                         IAuthorisation authorisation) {
        setParameters(request);
        this.authorisation = authorisation;

        Iterable<INoarkSystemIdEntity> entities = hateoasNoarkObject.getList();
        for (INoarkSystemIdEntity entity : entities) {
            addSelfLink(entity, hateoasNoarkObject);
            addEntityLinks(entity, hateoasNoarkObject);
        }
        // If hateoasNoarkObject is a list add a self link.
        // { "entity": [], "_links": [] }
        if (!hateoasNoarkObject.isSingleEntity() && hateoasNoarkObject.getList().size() > 0) {
            StringBuffer url = request.getRequestURL();
            Link selfLink = new Link(url.toString(), getRelSelfLink(), false);
            hateoasNoarkObject.addSelfLink(selfLink);
        }
    }

    @Override
    public void addLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request,
                                 IAuthorisation authorisation) {
        addLinks(hateoasNoarkObject, request, authorisation);
    }

    @Override
    public void addLinksOnNew(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request,
                              IAuthorisation authorisation) {
        setParameters(request);
        this.authorisation = authorisation;

        Iterable<INoarkSystemIdEntity> entities = hateoasNoarkObject.getList();
        for (INoarkSystemIdEntity entity : entities) {
            addEntityLinksOnNew(entity, hateoasNoarkObject);
        }
    }

    @Override
    public void addLinksOnRead(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request,
                               IAuthorisation authorisation) {
        addLinks(hateoasNoarkObject, request, authorisation);
    }

    @Override
    public void addLinksOnUpdate(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request,
                                 IAuthorisation authorisation) {
        addLinks(hateoasNoarkObject, request, authorisation);
    }

    @Override
    public void addLinksOnDelete(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request,
                                 IAuthorisation authorisation) {
        addLinks(hateoasNoarkObject, request, authorisation);
    }

    @Override
    public void addSelfLink(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        String systemId = entity.getSystemId();
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + entity.getBaseTypeName() + SLASH + systemId + SLASH,
                    getRelSelfLink(), false));
    }

    @Override
    public void addDocumentMedium(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH
                + SLASH + DOCUMENT_MEDIUM, REL_METADATA_DOCUMENT_MEDIUM, false));
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinksOnCreate(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinksOnNew(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
    }

    // Sub class should handle this, empty links otherwise!
    @Override
    public void addEntityLinksOnRead(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    protected String getRelSelfLink() {
        return SELF;
    }

    protected void setParameters(HttpServletRequest request) {
        this.servletPath = request.getServletPath();
        this.contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        this.contextServletPath = contextPath + servletPath + SLASH;
        this.servletPath += SLASH;
        this.contextPath += SLASH;
    }
}

