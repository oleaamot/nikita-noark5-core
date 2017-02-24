package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.security.IAuthorisation;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IHateoasHandler {

    void addLinks(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request,
                  IAuthorisation authorisation);

    void addSelfLink(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentMedium(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject,
                          HttpServletRequest request, IAuthorisation authorisation);

    void addLinksOnRead(IHateoasNoarkObject hateoasNoarkObject,
                        HttpServletRequest request, IAuthorisation authorisation);

    void addLinksOnUpdate(IHateoasNoarkObject hateoasNoarkObject,
                          HttpServletRequest request, IAuthorisation authorisation);

    void addLinksOnDelete(IHateoasNoarkObject hateoasNoarkObject,
                          HttpServletRequest request, IAuthorisation authorisation);

    void addLinksOnNew(IHateoasNoarkObject hateoasNoarkObject,
                       HttpServletRequest request, IAuthorisation authorisation);

    // In many ways, these should be private
    void addEntityLinksOnCreate(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnRead(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnNew(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);


}
