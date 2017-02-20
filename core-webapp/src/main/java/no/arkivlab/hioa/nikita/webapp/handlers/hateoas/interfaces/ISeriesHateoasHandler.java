package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for Series
 */
public interface ISeriesHateoasHandler extends IHateoasHandler {

    void addNewRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeriesSuccessor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSeriesSuccessor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeriesPrecursor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSeriesPrecursor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSeriesStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewStorageLocation(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addListStorageLocation(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewListStorageLocation(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
