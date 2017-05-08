package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IClassHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add ClassHateoas links with Class specific information
 *
 */
@Component("classHateoasHandler")
public class ClassHateoasHandler extends HateoasHandler implements IClassHateoasHandler {

    @Override
    public void addEntityLinks(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {

        // links for primary entities
        addClass(entity, hateoasNoarkObject);
        addNewClass(entity, hateoasNoarkObject);
        addRegistration(entity, hateoasNoarkObject);
        addNewRegistration(entity, hateoasNoarkObject);
        addFile(entity, hateoasNoarkObject);
        addNewFile(entity, hateoasNoarkObject);
        addClassificationSystem(entity, hateoasNoarkObject);
        addParentClass(entity, hateoasNoarkObject);
        addSubClass(entity, hateoasNoarkObject);
        addNewSubClass(entity, hateoasNoarkObject);
        // links for secondary entities (non-embeddable)
        addKeyword(entity, hateoasNoarkObject);
        addNewKeyword(entity, hateoasNoarkObject);
        addCrossReference(entity, hateoasNoarkObject);
        addNewCrossReference(entity, hateoasNoarkObject);
        // links for secondary entities (embeddable)
        addNewClassified(entity, hateoasNoarkObject);
        addNewDisposal(entity, hateoasNoarkObject);
        addNewDisposalUndertaken(entity, hateoasNoarkObject);
        addNewDeletion(entity, hateoasNoarkObject);
        addNewScreening(entity, hateoasNoarkObject);
        // links for metadata entities
        // Class has no metadata entities
    }

    @Override
    public void addClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + PARENT_CLASS +
                SLASH, REL_FONDS_STRUCTURE_CLASS, false));
    }

    @Override
    public void addNewClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + SUB_CLASS +
                SLASH, REL_FONDS_STRUCTURE_CLASS, false));
    }

    @Override
    public void addParentClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + PARENT_CLASS +
                SLASH, REL_FONDS_STRUCTURE_CLASS, false));
    }

    @Override
    public void addSubClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + SUB_CLASS +
                SLASH, REL_FONDS_STRUCTURE_SUB_CLASS, false));
    }

    @Override
    public void addNewSubClass(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + SUB_CLASS +
                SLASH, REL_FONDS_STRUCTURE_NEW_SUB_CLASS, false));
    }

    @Override
    public void addClassificationSystem(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS +
                SLASH + entity.getSystemId() + SLASH + CLASSIFICATION_SYSTEM + SLASH,
                REL_FONDS_STRUCTURE_CLASSIFICATION_SYSTEM, false));
    }

    @Override
    public void addRegistration(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + REGISTRATION
                + SLASH, REL_FONDS_STRUCTURE_REGISTRATION,
                false));
    }

    @Override
    public void addNewRegistration(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + NEW_RECORD
                + SLASH, REL_FONDS_STRUCTURE_NEW_RECORD,
                false));
    }

    @Override
    public void addFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + FILE +
                SLASH, REL_FONDS_STRUCTURE_FILE,
                false));
    }

    @Override
    public void addNewFile(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + NEW_FILE +
                SLASH, REL_FONDS_STRUCTURE_NEW_FILE,
                false));
    }

    @Override
    public void addNewClassified(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + NEW_CLASSIFIED
                + SLASH, REL_FONDS_STRUCTURE_NEW_CLASSIFIED, false));
    }

    @Override
    public void addNewDisposal(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + NEW_DISPOSAL
                + SLASH, REL_FONDS_STRUCTURE_NEW_DISPOSAL, false));
    }

    @Override
    public void addNewDisposalUndertaken(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH +
                NEW_DISPOSAL_UNDERTAKEN + SLASH, REL_FONDS_STRUCTURE_NEW_DISPOSAL_UNDERTAKEN, false));
    }

    @Override
    public void addNewDeletion(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + NEW_DELETION
                + SLASH, REL_FONDS_STRUCTURE_NEW_DELETION, false));
    }


    @Override
    public void addNewScreening(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + NEW_SCREENING
                + SLASH, REL_FONDS_STRUCTURE_NEW_SCREENING, false));
    }

    @Override
    public void addKeyword(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + KEYWORD
                + SLASH, REL_FONDS_STRUCTURE_KEYWORD, false));
    }

    @Override
    public void addNewKeyword(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH + NEW_KEYWORD
                + SLASH, REL_FONDS_STRUCTURE_NEW_KEYWORD, false));
    }

    @Override
    public void addCrossReference(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH +
                CROSS_REFERENCE + SLASH, REL_FONDS_STRUCTURE_CROSS_REFERENCE, false));
    }

    @Override
    public void addNewCrossReference(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASS + SLASH + entity.getSystemId() + SLASH +
                NEW_CROSS_REFERENCE + SLASH, REL_FONDS_STRUCTURE_NEW_CROSS_REFERENCE, false));
    }
}
