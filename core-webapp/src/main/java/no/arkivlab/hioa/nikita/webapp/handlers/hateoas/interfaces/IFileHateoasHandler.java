package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IFileHateoasHandler extends IHateoasHandler {


    void addEndFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addExpandToCaseFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addExpandToMeetingFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addRegistration(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewRegistration(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addBasicRecord(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewBasicRecord(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addComment(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewComment(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addSubFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewSubFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCrossReference(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewCrossReference(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceSeries(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceSeries(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addReferenceSecondaryClassification(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewReferenceSecondaryClassification(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
