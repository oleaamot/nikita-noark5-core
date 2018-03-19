package nikita.webapp.hateoas.interfaces;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for ClassificationSystem
 */
public interface IClassificationSystemHateoasHandler extends IHateoasHandler {

    void addSeries(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassificationSystem(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSecondaryClassificationSystem(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSecondaryClassificationSystem(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassificationType(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
