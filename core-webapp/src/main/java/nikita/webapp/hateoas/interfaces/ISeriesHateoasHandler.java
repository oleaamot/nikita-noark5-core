package nikita.webapp.hateoas.interfaces;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for Series
 */
public interface ISeriesHateoasHandler extends IHateoasHandler {

    void addNewRegistration(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCaseFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassificationSystem(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeriesSuccessor(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSeriesSuccessor(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeriesPrecursor(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSeriesPrecursor(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addRegistration(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCaseFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassificationSystem(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeriesStatus(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassified(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassified(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDisposal(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposal(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDisposalUndertaken(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposalUndertaken(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDeletion(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDeletion(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addScreening(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewScreening(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewStorageLocation(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addListStorageLocation(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewListStorageLocation(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
