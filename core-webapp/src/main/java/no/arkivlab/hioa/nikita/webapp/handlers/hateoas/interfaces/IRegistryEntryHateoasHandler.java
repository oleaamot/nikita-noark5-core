package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IRegistryEntryHateoasHandler extends IBasicRecordHateoasHandler {

    void addElectronicSignature(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewElectronicSignature(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addPrecedence(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewPrecedence(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSignOff(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSignOff(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentFlow(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDocumentFlow(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCorrespondencePart(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCorrespondencePart(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
