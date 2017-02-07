package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IHateoasHandler {

    void addLinks(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request);

    void addSelfLink(IHateoasNoarkObject hateoasNoarkObject);

    void addGeneralLinks(INoarkGeneralEntity noarkGeneralEntity);

    void addEntityLinks(IHateoasNoarkObject hateoasNoarkObject);

    void addLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request);

    void addLinksOnRead(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request);
}
