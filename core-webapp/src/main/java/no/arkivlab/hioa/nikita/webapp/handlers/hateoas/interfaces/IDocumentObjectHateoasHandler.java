package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IDocumentObjectHateoasHandler extends IHateoasHandler {

    void addRecord(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentDescription(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addConversion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewConversion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceDocumentFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addVariantFormat(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFormat(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}

