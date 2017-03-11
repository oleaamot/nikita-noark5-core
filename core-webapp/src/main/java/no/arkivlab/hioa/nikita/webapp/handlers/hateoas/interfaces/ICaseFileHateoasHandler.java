package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface ICaseFileHateoasHandler extends IHateoasHandler {


    void addNewClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCaseParty(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCaseParty(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCaseStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCaseStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSecondaryClassification(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSecondaryClassification(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
