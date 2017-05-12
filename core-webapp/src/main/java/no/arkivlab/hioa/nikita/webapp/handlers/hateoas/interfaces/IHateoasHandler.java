package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
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

    void addLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject,
                          HttpServletRequest request, IAuthorisation authorisation);

    void addLinksOnTemplate(IHateoasNoarkObject hateoasNoarkObject,
                            HttpServletRequest request, IAuthorisation authorisationt);

    void addLinksOnRead(IHateoasNoarkObject hateoasNoarkObject,
                        HttpServletRequest request, IAuthorisation authorisation);

    void addLinksOnUpdate(IHateoasNoarkObject hateoasNoarkObject,
                          HttpServletRequest request, IAuthorisation authorisation);

    void addLinksOnDelete(IHateoasNoarkObject hateoasNoarkObject,
                          HttpServletRequest request, IAuthorisation authorisation);

    void addLinksOnNew(IHateoasNoarkObject hateoasNoarkObject,
                       HttpServletRequest request, IAuthorisation authorisation);

    void addSelfLink(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinks(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnCreate(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnTemplate(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnRead(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addEntityLinksOnNew(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentMedium(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);



}
