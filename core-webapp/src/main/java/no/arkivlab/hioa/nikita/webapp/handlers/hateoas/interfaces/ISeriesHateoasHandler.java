package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for Series
 */
public interface ISeriesHateoasHandler extends IHateoasHandler {

    void addNewSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addArkiv(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeriesStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
