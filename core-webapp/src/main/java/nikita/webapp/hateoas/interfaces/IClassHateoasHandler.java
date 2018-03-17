package nikita.webapp.hateoas.interfaces;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for Class
 */
public interface IClassHateoasHandler extends IHateoasHandler {

    void addRegistration(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewRegistration(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassificationSystem(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addParentClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSubClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSubClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addKeyword(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewKeyword(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassified(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposal(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposalUndertaken(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDeletion(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewScreening(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCrossReference(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCrossReference(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);


}
