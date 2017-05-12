package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentDescriptionHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add DocumentDescriptionHateoas links with DocumentDescription specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("documentDescriptionHateoasHandler")
public class DocumentDescriptionHateoasHandler extends HateoasHandler implements IDocumentDescriptionHateoasHandler {

    @Override
    public void addEntityLinks(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {

        // links for primary entities
        addRecord(entity, hateoasNoarkObject);
        addDocumentObject(entity, hateoasNoarkObject);
        addNewDocumentObject(entity, hateoasNoarkObject);
        // links for secondary entities M:1
        addNewClassified(entity, hateoasNoarkObject);
        addNewDisposal(entity, hateoasNoarkObject);
        addNewDisposalUndertaken(entity, hateoasNoarkObject);
        addNewDeletion(entity, hateoasNoarkObject);
        addNewScreening(entity, hateoasNoarkObject);
        // links for secondary entities 1:M
        addStorageLocation(entity, hateoasNoarkObject);
        addNewStorageLocation(entity, hateoasNoarkObject);
        addComment(entity, hateoasNoarkObject);
        addNewComment(entity, hateoasNoarkObject);
        addAuthor(entity, hateoasNoarkObject);
        addNewAuthor(entity, hateoasNoarkObject);
        // links for metadata entities
        addDocumentMedium(entity, hateoasNoarkObject);
        addDocumentType(entity, hateoasNoarkObject);
        addDocumentStatus(entity, hateoasNoarkObject);
    }

    @Override
    public void addRecord(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH +
                REGISTRATION + SLASH, REL_FONDS_STRUCTURE_RECORD, false));
    }

    @Override
    public void addClassified(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + CLASSIFIED
                + SLASH, REL_FONDS_STRUCTURE_CLASSIFIED, false));
    }

    @Override
    public void addNewClassified(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH +
                NEW_CLASSIFIED + SLASH, REL_FONDS_STRUCTURE_NEW_CLASSIFIED, false));
    }

    @Override
    public void addDisposal(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + DISPOSAL
                + SLASH, REL_FONDS_STRUCTURE_DISPOSAL, false));
    }

    @Override
    public void addNewDisposal(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + NEW_DISPOSAL
                + SLASH, REL_FONDS_STRUCTURE_NEW_DISPOSAL, false));
    }

    @Override
    public void addDisposalUndertaken(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + DISPOSAL_UNDERTAKEN
                + SLASH, REL_FONDS_STRUCTURE_DISPOSAL_UNDERTAKEN, false));
    }

    @Override
    public void addNewDisposalUndertaken(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + NEW_DISPOSAL_UNDERTAKEN
                + SLASH, REL_FONDS_STRUCTURE_NEW_DISPOSAL_UNDERTAKEN, false));
    }

    @Override
    public void addDeletion(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + DELETION
                + SLASH, REL_FONDS_STRUCTURE_DELETION, false));
    }

    @Override
    public void addNewDeletion(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + NEW_DELETION
                + SLASH, REL_FONDS_STRUCTURE_NEW_DELETION, false));
    }

    @Override
    public void addScreening(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + SCREENING
                + SLASH, REL_FONDS_STRUCTURE_SCREENING, false));
    }

    @Override
    public void addNewScreening(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + NEW_SCREENING
                + SLASH, REL_FONDS_STRUCTURE_NEW_SCREENING, false));
    }

    @Override
    public void addAuthor(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + AUTHOR
                + SLASH, REL_FONDS_STRUCTURE_AUTHOR, false));
    }

    @Override
    public void addNewAuthor(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + NEW_AUTHOR
                + SLASH, REL_FONDS_STRUCTURE_NEW_AUTHOR, false));
    }

    @Override
    public void addComment(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + COMMENT
                + SLASH, REL_FONDS_STRUCTURE_COMMENT, false));
    }

    @Override
    public void addNewComment(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH + NEW_COMMENT
                + SLASH, REL_FONDS_STRUCTURE_NEW_COMMENT, false));
    }

    @Override
    public void addStorageLocation(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH +
                STORAGE_LOCATION + SLASH, REL_FONDS_STRUCTURE_STORAGE_LOCATION, false));
    }

    @Override
    public void addNewStorageLocation(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH +
                NEW_STORAGE_LOCATION + SLASH, REL_FONDS_STRUCTURE_NEW_STORAGE_LOCATION, false));
    }

    @Override
    public void addDocumentObject(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH +
                DOCUMENT_OBJECT + SLASH, REL_FONDS_STRUCTURE_DOCUMENT_OBJECT, false));
    }

    @Override
    public void addNewDocumentObject(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_DESCRIPTION + SLASH + entity.getSystemId() + SLASH +
                NEW_DOCUMENT_OBJECT + SLASH, REL_FONDS_STRUCTURE_NEW_DOCUMENT_OBJECT, false));
    }

    @Override
    public void addDocumentType(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + DOCUMENT_STATUS, REL_METADATA_DOCUMENT_STATUS, false));
    }

    @Override
    public void addDocumentStatus(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + DOCUMENT_TYPE, REL_METADATA_DOCUMENT_TYPE, false));
    }
}
