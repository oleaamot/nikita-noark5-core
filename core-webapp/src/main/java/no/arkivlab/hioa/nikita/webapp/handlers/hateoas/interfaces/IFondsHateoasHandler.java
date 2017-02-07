package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IFondsHateoasHandler extends IHateoasHandler {

    void addDocumentMedium(IHateoasNoarkObject hateoasNoarkObject);

    void addFondsCreator(IHateoasNoarkObject hateoasNoarkObject);

    void addSeries(IHateoasNoarkObject hateoasNoarkObject);

    void addFonds(IHateoasNoarkObject hateoasNoarkObject);

    void addNewFondsCreator(IHateoasNoarkObject hateoasNoarkObject);

    void addSubFonds(IHateoasNoarkObject hateoasNoarkObject);

    void addFondsStatus(IHateoasNoarkObject hateoasNoarkObject);

    void addNewFonds(IHateoasNoarkObject hateoasNoarkObject);

    void addNewSeries(IHateoasNoarkObject hateoasNoarkObject);

}
