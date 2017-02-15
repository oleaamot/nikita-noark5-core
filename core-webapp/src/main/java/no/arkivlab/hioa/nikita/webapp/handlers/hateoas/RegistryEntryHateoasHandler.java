package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRegistryEntryHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add BasicRecordHateoas links with BasicRecord specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("registryEntryHateoasHandler")
public class RegistryEntryHateoasHandler extends BasicRecordHateoasHandler implements IRegistryEntryHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {

        super.addEntityLinks(entity, hateoasNoarkObject);
        addElectronicSignature(entity, hateoasNoarkObject);
        addNewElectronicSignature(entity, hateoasNoarkObject);
        addPrecedence(entity, hateoasNoarkObject);
        addNewPrecedence(entity, hateoasNoarkObject);
        addSignOff(entity, hateoasNoarkObject);
        addNewSignOff(entity, hateoasNoarkObject);
        addDocumentFlow(entity, hateoasNoarkObject);
        addNewDocumentFlow(entity, hateoasNoarkObject);
        addCorrespondencePart(entity, hateoasNoarkObject);
        addNewCorrespondencePart(entity, hateoasNoarkObject);
    }

    @Override
    public void addElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + ELECTRONIC_SIGNATURE + SLASH, REL_FONDS_STRUCTURE_ELECTRONIC_SIGNATURE, false));
    }

    @Override
    public void addNewElectronicSignature(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + NEW_ELECTRONIC_SIGNATURE + SLASH, REL_FONDS_STRUCTURE_NEW_ELECTRONIC_SIGNATURE, false));
    }

    @Override
    public void addPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + PRECEDENCE + SLASH, REL_FONDS_STRUCTURE_PRECEDENCE, false));
    }

    @Override
    public void addNewPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + NEW_PRECEDENCE + SLASH, REL_FONDS_STRUCTURE_NEW_PRECEDENCE, false));
    }

    @Override
    public void addSignOff(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + SIGN_OFF + SLASH, REL_FONDS_STRUCTURE_SIGN_OFF, false));
    }

    @Override
    public void addNewSignOff(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + NEW_SIGN_OFF + SLASH, REL_FONDS_STRUCTURE_NEW_SIGN_OFF, false));
    }

    @Override
    public void addDocumentFlow(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + DOCUMENT_FLOW + SLASH, REL_FONDS_STRUCTURE_DOCUMENT_FLOW, false));
    }

    @Override
    public void addNewDocumentFlow(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + NEW_DOCUMENT_FLOW + SLASH, REL_FONDS_STRUCTURE_NEW_DOCUMENT_FLOW, false));
    }

    @Override
    public void addCorrespondencePart(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + CORRESPONDENCE_PART + SLASH, REL_FONDS_STRUCTURE_CORRESPONDENCE_PART, false));
    }

    @Override
    public void addNewCorrespondencePart(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRY_ENTRY + SLASH +
                entity.getSystemId() + SLASH + NEW_CORRESPONDENCE_PART + SLASH, REL_FONDS_STRUCTURE_NEW_CORRESPONDENCE_PART, false));
    }
}
