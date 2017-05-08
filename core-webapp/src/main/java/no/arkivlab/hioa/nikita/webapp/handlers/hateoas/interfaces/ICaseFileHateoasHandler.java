package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface ICaseFileHateoasHandler extends IHateoasHandler {

    void addNewClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewPrecedence(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addPrecedence(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCaseParty(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCaseParty(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCaseStatus(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCaseStatus(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSecondaryClassification(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSecondaryClassification(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewRegistryEntry(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addRegistryEntry(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
