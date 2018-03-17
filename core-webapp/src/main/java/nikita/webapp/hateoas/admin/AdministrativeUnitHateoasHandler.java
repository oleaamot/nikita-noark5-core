package nikita.webapp.handlers.hateoas.admin;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.hateoas.Link;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.webapp.hateoas.HateoasHandler;
import nikita.webapp.hateoas.interfaces.admin.IAdministrativeUnitHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.ADMINISTRATIVE_UNIT;
import static nikita.common.config.N5ResourceMappings.NEW_ADMINISTRATIVE_UNIT;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add AdministrativeUnitHateoas links with AdministrativeUnit specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("administrativeUnitHateoasHandler")
public class AdministrativeUnitHateoasHandler extends HateoasHandler implements IAdministrativeUnitHateoasHandler {

    @Override
    public void addEntityLinks(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {

    }

    @Override
    public void addEntityLinksOnCreate(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnRead(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }


    public void addChildAdministrativeUnit(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_ADMINISTRATION_PATH + SLASH + ADMINISTRATIVE_UNIT + SLASH + entity.getSystemId() + SLASH +
                NEW_ADMINISTRATIVE_UNIT + SLASH, REL_ADMIN_ADMINISTRATIVE_UNIT, false));
    }

}
