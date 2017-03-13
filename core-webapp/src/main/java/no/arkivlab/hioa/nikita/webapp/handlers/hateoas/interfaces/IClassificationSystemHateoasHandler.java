package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for ClassificationSystem
 */
public interface IClassificationSystemHateoasHandler extends IHateoasHandler {

    void addSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSecondaryClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSecondaryClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassificationType(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
