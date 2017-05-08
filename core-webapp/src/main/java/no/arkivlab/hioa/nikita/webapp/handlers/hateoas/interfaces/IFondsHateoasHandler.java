package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IFondsHateoasHandler extends IHateoasHandler {

    void addFondsCreator(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeries(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSubFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFondsCreator(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSubFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFondsStatus(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSeries(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
