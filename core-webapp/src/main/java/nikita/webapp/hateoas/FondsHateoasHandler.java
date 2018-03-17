package nikita.webapp.hateoas;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.hateoas.Link;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.webapp.hateoas.interfaces.IFondsHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add FondsHateoas links with Fonds specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for
 * various CRUD operations so keeping themseparate calls at the moment.
 */
@Component
public class FondsHateoasHandler extends HateoasHandler
        implements IFondsHateoasHandler {

    @Override
    public void addEntityLinks(INikitaEntity entity,
                               IHateoasNoarkObject hateoasNoarkObject) {
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
    public void addEntityLinksOnCreate(INikitaEntity entity,
                                       IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnRead(INikitaEntity entity,
                                     IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnNew(INikitaEntity entity,
                                    IHateoasNoarkObject hateoasNoarkObject) {
        addDocumentMedium(entity, hateoasNoarkObject);
    }


    public void addFondsCreator(INikitaEntity entity,
                                IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath +
                HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH +
                SLASH + FONDS + SLASH + entity.getSystemId() + SLASH +
                FONDS_CREATOR + SLASH, REL_FONDS_STRUCTURE_FONDS_CREATOR, false));
    }

    public void addSeries(INikitaEntity entity,
                          IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath +
                HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH +
                FONDS + SLASH + entity.getSystemId() + SLASH + SERIES +
                SLASH, REL_FONDS_STRUCTURE_SERIES, false));
    }

    public void addFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                SLASH + entity.getSystemId() + SLASH + FONDS + SLASH, REL_FONDS_STRUCTURE_FONDS, false));
    }

    public void addNewFondsCreator(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateFonds()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                    SLASH + entity.getSystemId() + SLASH + NEW_FONDS_CREATOR + SLASH, REL_FONDS_STRUCTURE_NEW_FONDS_CREATOR,
                    false));
        }
    }

    public void addSubFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                SLASH + entity.getSystemId() + SLASH + SUB_FONDS + SLASH, REL_FONDS_STRUCTURE_SUB_FONDS, false));

    }

    public void addNewSubFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS + SLASH + entity.getSystemId() + SLASH + NEW_SUB_FONDS +
                SLASH, REL_FONDS_STRUCTURE_NEW_SUB_FONDS, false));

    }

    public void addFondsStatus(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH
                + FONDS_STATUS, REL_METADATA_FONDS_STATUS, false));
    }

    public void addNewFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateFonds()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                    SLASH + entity.getSystemId() + SLASH + NEW_FONDS + SLASH, REL_FONDS_STRUCTURE_NEW_FONDS, false));
        }
    }

    public void addNewSeries(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                    SLASH + entity.getSystemId() + SLASH + NEW_SERIES + SLASH, REL_FONDS_STRUCTURE_NEW_SERIES, false));
        }
    }
}
