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

    void addNewReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
