package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsCreatorHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FONDS;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add FondsCreatorHateoas links with FondsCreator specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("fondsCreatorHateoasHandler")
public class FondsCreatorHateoasHandler extends HateoasHandler implements IFondsCreatorHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addFonds(entity, hateoasNoarkObject);
    }

    @Override
    public void addFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                SLASH + entity.getSystemId() + SLASH + FONDS + SLASH,
                REL_FONDS_STRUCTURE_FONDS, false));
    }
}
