package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IDocumentDescriptionHateoasHandler extends IHateoasHandler {

    void addRecord(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentObject(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDocumentObject(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClassified(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClassified(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDisposal(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposal(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDisposalUndertaken(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDisposalUndertaken(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDeletion(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewDeletion(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addScreening(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewScreening(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addStorageLocation(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewStorageLocation(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addComment(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewComment(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addAuthor(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewAuthor(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentType(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addDocumentStatus(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}

