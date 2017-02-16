package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IBasicRecordHateoasHandler extends IRecordHateoasHandler {

    void addStorageLocation(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewStorageLocation(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addComment(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewComment(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addAuthor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewAuthor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCrossReference(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCrossReference(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addKeyword(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewKeyword(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
