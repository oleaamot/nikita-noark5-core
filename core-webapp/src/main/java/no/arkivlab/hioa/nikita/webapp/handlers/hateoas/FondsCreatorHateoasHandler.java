package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsCreatorHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FONDS;
import static nikita.config.N5ResourceMappings.FONDS_CREATOR;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add FondsCreatorHateoas links with FondsCreator specific information
 */
@Component("fondsCreatorHateoasHandler")
public class FondsCreatorHateoasHandler extends HateoasHandler implements IFondsCreatorHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addFonds(entity, hateoasNoarkObject);
        addNewFonds(entity, hateoasNoarkObject);
    }

    @Override
    public void addFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS + SLASH + entity.getSystemId() + SLASH +
                FONDS + SLASH, REL_FONDS_STRUCTURE_FONDS, false));
    }

    @Override
    public void addNewFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS_CREATOR + SLASH + entity.getSystemId() + SLASH +
                NEW_FONDS + SLASH, REL_FONDS_STRUCTURE_NEW_FONDS, false));
    }
}
