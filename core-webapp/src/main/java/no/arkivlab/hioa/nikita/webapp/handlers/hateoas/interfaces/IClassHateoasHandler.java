package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for Class
 */
public interface IClassHateoasHandler extends IHateoasHandler {

    void addRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addParentClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSubClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSubClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addKeyword(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewKeyword(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCrossReference(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCrossReference(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);


}
