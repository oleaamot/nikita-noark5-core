package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IFileHateoasHandler extends IHateoasHandler {


    void addEndFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addExpandToCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addExpandToMeetingFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addBasicRecord(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewBasicRecord(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addComment(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewComment(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSubFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSubFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCrossReference(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCrossReference(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceSecondaryClassification(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceSecondaryClassification(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
