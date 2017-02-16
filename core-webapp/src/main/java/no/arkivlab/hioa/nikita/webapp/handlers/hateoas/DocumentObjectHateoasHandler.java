package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IDocumentObjectHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add DocumentObjectHateoas links with DocumentObject specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("documentObjectHateoasHandler")
public class DocumentObjectHateoasHandler extends HateoasHandler implements IDocumentObjectHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {

        addReferenceRecord(entity, hateoasNoarkObject);
        addReferenceDocumentDescription(entity, hateoasNoarkObject);
        addReferenceConversion(entity, hateoasNoarkObject);
        addNewReferenceConversion(entity, hateoasNoarkObject);
        addReferenceElectronicSignature(entity, hateoasNoarkObject);
        addNewReferenceElectronicSignature(entity, hateoasNoarkObject);
    }


    @Override
    public void addReferenceRecord(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                REGISTRATION + SLASH, REL_FONDS_STRUCTURE_RECORD, false));
    }

    @Override
    public void addReferenceConversion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                CONVERSION + SLASH, REL_FONDS_STRUCTURE_CONVERSION, false));
    }

    @Override
    public void addNewReferenceConversion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                NEW_CONVERSION + SLASH, REL_FONDS_STRUCTURE_NEW_CONVERSION, false));
    }

    @Override
    public void addReferenceElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                ELECTRONIC_SIGNATURE + SLASH, REL_FONDS_STRUCTURE_ELECTRONIC_SIGNATURE, false));
    }

    @Override
    public void addNewReferenceElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                NEW_ELECTRONIC_SIGNATURE + SLASH, REL_FONDS_STRUCTURE_NEW_ELECTRONIC_SIGNATURE, false));
    }

    @Override
    public void addReferenceDocumentDescription(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                DOCUMENT_DESCRIPTION + SLASH, REL_FONDS_STRUCTURE_DOCUMENT_DESCRIPTION, false));
    }
}
