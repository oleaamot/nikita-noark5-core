package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFileHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add FileHateoas links with File specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("fileHateoasHandler")
public class FileHateoasHandler extends HateoasHandler implements IFileHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {

        addEndFile(entity, hateoasNoarkObject);
        addExpandToCaseFile(entity, hateoasNoarkObject);
        addExpandToMeetingFile(entity, hateoasNoarkObject);
        addRegistration(entity, hateoasNoarkObject);
        addNewRegistration(entity, hateoasNoarkObject);
        addBasicRecord(entity, hateoasNoarkObject);
        addNewBasicRecord(entity, hateoasNoarkObject);
        addComment(entity, hateoasNoarkObject);
        addNewComment(entity, hateoasNoarkObject);
        addSubFile(entity, hateoasNoarkObject);
        addNewSubFile(entity, hateoasNoarkObject);
        addCrossReference(entity, hateoasNoarkObject);
        addNewCrossReference(entity, hateoasNoarkObject);
        addClass(entity, hateoasNoarkObject);
        addNewClass(entity, hateoasNoarkObject);
        addReferenceSeries(entity, hateoasNoarkObject);
        addNewReferenceSeries(entity, hateoasNoarkObject);
        addReferenceSecondaryClassification(entity, hateoasNoarkObject);
        addNewReferenceSecondaryClassification(entity, hateoasNoarkObject);
    }

    @Override
    public void addEndFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + FILE_END
                + SLASH, REL_FONDS_STRUCTURE_END_FILE,
                false));
    }

    @Override
    public void addExpandToCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                FILE_EXPAND_TO_CASE_FILE + SLASH, REL_FONDS_STRUCTURE_EXPAND_TO_CASE_FILE, false));
    }

    @Override
    public void addExpandToMeetingFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                FILE_EXPAND_TO_MEETING_FILE + SLASH, REL_FONDS_STRUCTURE_EXPAND_TO_MEETING_FILE, false));
    }

    @Override
    public void addRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                REGISTRATION + SLASH, REL_FONDS_STRUCTURE_REGISTRATION, false));
    }

    @Override
    public void addNewRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_RECORD + SLASH, REL_FONDS_STRUCTURE_NEW_REGISTRATION, false));
    }

    @Override
    public void addBasicRecord(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + BASIC_RECORD
                + SLASH, REL_FONDS_STRUCTURE_BASIC_RECORD, false));
    }

    @Override
    public void addNewBasicRecord(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + NEW_BASIC_RECORD
                + SLASH, REL_FONDS_STRUCTURE_NEW_BASIC_RECORD, false));
    }

    @Override
    public void addComment(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                COMMENT + SLASH, REL_FONDS_STRUCTURE_COMMENT, false));
    }

    @Override
    public void addNewComment(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_COMMENT + SLASH, REL_FONDS_STRUCTURE_NEW_COMMENT, false));
    }

    @Override
    public void addSubFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                SUB_FILE + SLASH, REL_FONDS_STRUCTURE_SUB_FILE, false));
    }

    @Override
    public void addNewSubFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_SUB_FILE + SLASH, REL_FONDS_STRUCTURE_NEW_SUB_FILE, false));
    }

    @Override
    public void addCrossReference(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                CROSS_REFERENCE + SLASH, REL_FONDS_STRUCTURE_CROSS_REFERENCE, false));
    }

    @Override
    public void addNewCrossReference(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_CROSS_REFERENCE + SLASH, REL_FONDS_STRUCTURE_NEW_CROSS_REFERENCE, false));
    }

    @Override
    public void addClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + REFERENCE_CLASS
                + SLASH, REL_FONDS_STRUCTURE_CLASS, false));
    }

    @Override
    public void addNewClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + NEW_REFERENCE_CLASS
                + SLASH, REL_FONDS_STRUCTURE_NEW_CLASS, false));
    }

    @Override
    public void addReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + SERIES
                + SLASH, REL_FONDS_STRUCTURE_REFERENCE_SERIES, false));
    }

    @Override
    public void addNewReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_SERIES + SLASH, REL_FONDS_STRUCTURE_NEW_REFERENCE_SERIES, false));
    }

    @Override
    public void addReferenceSecondaryClassification(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                SECONDARY_CLASSIFICATION + SLASH, REL_FONDS_STRUCTURE_SECONDARY_CLASSIFICATION, false));
    }

    @Override
    public void addNewReferenceSecondaryClassification(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_SERIES + SLASH, REL_FONDS_STRUCTURE_NEW_SECONDARY_CLASSIFICATION, false));
    }
}
