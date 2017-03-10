package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IRecordHateoasHandler extends IHateoasHandler {

    void addReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDocumentDescription(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentDescription(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDocumentObject(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentObject(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

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
}
