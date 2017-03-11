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

        // links for primary entities
        // link to record/documentdescription is one or the other
        addRecord(entity, hateoasNoarkObject);
        addDocumentDescription(entity, hateoasNoarkObject);
        // links for secondary entities
        addConversion(entity, hateoasNoarkObject);
        addNewConversion(entity, hateoasNoarkObject);
        addElectronicSignature(entity, hateoasNoarkObject);
        addReferenceDocumentFile(entity, hateoasNoarkObject);
        addNewElectronicSignature(entity, hateoasNoarkObject);
        // links for metadata entities
        addVariantFormat(entity, hateoasNoarkObject);
        addFormat(entity, hateoasNoarkObject);
    }

    @Override
    public void addRecord(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                REGISTRATION + SLASH, REL_FONDS_STRUCTURE_RECORD, false));
    }

    @Override
    public void addConversion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                CONVERSION + SLASH, REL_FONDS_STRUCTURE_CONVERSION, false));
    }

    @Override
    public void addNewConversion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                NEW_CONVERSION + SLASH, REL_FONDS_STRUCTURE_NEW_CONVERSION, false));
    }

    @Override
    public void addElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                ELECTRONIC_SIGNATURE + SLASH, REL_FONDS_STRUCTURE_ELECTRONIC_SIGNATURE, false));
    }

    @Override
    public void addNewElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                NEW_ELECTRONIC_SIGNATURE + SLASH, REL_FONDS_STRUCTURE_NEW_ELECTRONIC_SIGNATURE, false));
    }

    @Override
    public void addDocumentDescription(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                DOCUMENT_DESCRIPTION + SLASH, REL_FONDS_STRUCTURE_DOCUMENT_DESCRIPTION, false));
    }

    @Override
    public void addReferenceDocumentFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + DOCUMENT_OBJECT + SLASH + entity.getSystemId() + SLASH +
                REFERENCE_FILE + SLASH, REL_FONDS_STRUCTURE_DOCUMENT_FILE, false));
    }

    @Override
    public void addVariantFormat(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + VARIANT_FORMAT, REL_METADATA_VARIANT_FORMAT, false));
    }

    @Override
    public void addFormat(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + FORMAT, REL_METADATA_FORMAT, false));
    }
}
