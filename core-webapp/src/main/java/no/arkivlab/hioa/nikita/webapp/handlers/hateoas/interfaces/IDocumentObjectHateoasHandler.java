package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IDocumentObjectHateoasHandler extends IHateoasHandler {

    void addReferenceRecord(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceDocumentDescription(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceConversion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceConversion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceDocumentFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}

