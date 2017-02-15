package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IRegistryEntryHateoasHandler extends IBasicRecordHateoasHandler {

    void addElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSignOff(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSignOff(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentFlow(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDocumentFlow(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCorrespondencePart(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCorrespondencePart(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
