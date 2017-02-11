package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.ICaseFileHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add CaseFileHateoas links with CaseFile specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("caseFileHateoasHandler")
public class CaseFileHateoasHandler extends HateoasHandler implements ICaseFileHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addNewClass(entity, hateoasNoarkObject);
        addClass(entity, hateoasNoarkObject);
        addNewPrecedence(entity, hateoasNoarkObject);
        addPrecedence(entity, hateoasNoarkObject);
        addNewCaseParty(entity, hateoasNoarkObject);
        addCaseParty(entity, hateoasNoarkObject);
        addCaseStatus(entity, hateoasNoarkObject);
        addSecondaryClassification(entity, hateoasNoarkObject);
    }

    public void addNewClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH + entity.getSystemId() + SLASH + NEW_CLASS
                + SLASH, REL_CASE_HANDLING_NEW_CLASS,
                false));
    }

    public void addClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH + entity.getSystemId() + SLASH + CLASS +
                SLASH, REL_CASE_HANDLING_CLASS, false));
    }

    public void addNewPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH + entity.getSystemId() + SLASH + NEW_PRECEDENCE
                + SLASH, REL_CASE_HANDLING_NEW_PRECEDENCE, false));
    }

    public void addPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH + entity.getSystemId() + SLASH +
                PRECEDENCE + SLASH, REL_CASE_HANDLING_PRECEDENCE, false));
    }

    public void addNewCaseParty(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH + entity.getSystemId() + SLASH + NEW_CASE_PARTY
                + SLASH, REL_CASE_HANDLING_NEW_CASE_PARTY, false));
    }

    public void addCaseParty(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH + entity.getSystemId() + SLASH + CASE_PARTY
                + SLASH, REL_CASE_HANDLING_CASE_PARTY, false));
    }

    public void addCaseStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH + entity.getSystemId() + SLASH + CASE_STATUS
                + SLASH, REL_METADATA_CASE_STATUS, false));
    }

    public void addSecondaryClassification(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_CASE_HANDLING_PATH + SLASH + CASE_FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_SECONDARY_CLASSIFICATION + SLASH, REL_CASE_HANDLING_SECONDARY_CLASSIFICATION, false));
    }
}
