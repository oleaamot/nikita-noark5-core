package nikita.webapp.hateoas.interfaces.secondary;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.webapp.hateoas.interfaces.IHateoasHandler;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IPrecedenceHateoasHandler extends IHateoasHandler {

    void addPrecedenceStatus(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addNewCaseFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addCaseFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addNewPrecedence(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addPrecedence(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addNewRegistryEntry(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addRegistryEntry(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
