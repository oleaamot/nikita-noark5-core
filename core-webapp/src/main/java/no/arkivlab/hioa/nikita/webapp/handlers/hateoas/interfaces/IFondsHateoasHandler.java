package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IFondsHateoasHandler extends IHateoasHandler {

    void addFondsCreator(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSubFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFondsCreator(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSubFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFondsStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
