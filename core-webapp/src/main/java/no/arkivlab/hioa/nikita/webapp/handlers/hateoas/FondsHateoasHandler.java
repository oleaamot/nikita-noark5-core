package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add FondsHateoas links with Fonds specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("fondsHateoasHandler")
public class FondsHateoasHandler extends HateoasHandler implements IFondsHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addDocumentMedium(entity, hateoasNoarkObject);
        addFondsCreator(entity, hateoasNoarkObject);
        addSeries(entity, hateoasNoarkObject);
        // It's not clear why addFonds would be part of this
        //addFonds(entity, hateoasNoarkObject);
        addNewFondsCreator(entity, hateoasNoarkObject);
        addSubFonds(entity, hateoasNoarkObject);
        addNewSubFonds(entity, hateoasNoarkObject);
        addFondsStatus(entity, hateoasNoarkObject);
        addNewSeries(entity, hateoasNoarkObject);
        //addNewFonds(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnCreate(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnRead(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnNew(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addDocumentMedium(entity, hateoasNoarkObject);
    }


    public void addFondsCreator(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                SLASH + entity.getSystemId() + SLASH + FONDS_CREATOR + SLASH, REL_FONDS_STRUCTURE_FONDS_CREATOR,
                false));
    }

    public void addSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                SLASH + entity.getSystemId() + SLASH + SERIES + SLASH, REL_FONDS_STRUCTURE_SERIES, false));
    }

    public void addFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                SLASH + entity.getSystemId() + SLASH + FONDS + SLASH, REL_FONDS_STRUCTURE_FONDS, false));
    }

    public void addNewFondsCreator(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateFonds()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                    SLASH + entity.getSystemId() + SLASH + NEW_FONDS_CREATOR + SLASH, REL_FONDS_STRUCTURE_NEW_FONDS_CREATOR,
                    false));
        }
    }

    public void addSubFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                SLASH + entity.getSystemId() + SLASH + SUB_FONDS + SLASH, REL_FONDS_STRUCTURE_SUB_FONDS, false));

    }

    public void addNewSubFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS + SLASH + entity.getSystemId() + SLASH + NEW_SUB_FONDS +
                SLASH, REL_FONDS_STRUCTURE_NEW_SUB_FONDS, false));

    }

    public void addFondsStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH
                + FONDS_STATUS + SLASH, REL_METADATA_FONDS_STATUS, false));
    }

    public void addNewFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateFonds()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                    SLASH + entity.getSystemId() + SLASH + NEW_FONDS + SLASH, REL_FONDS_STRUCTURE_NEW_FONDS, false));
        }
    }

    public void addNewSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                    SLASH + entity.getSystemId() + SLASH + NEW_SERIES + SLASH, REL_FONDS_STRUCTURE_NEW_SERIES, false));
        }
    }
}
