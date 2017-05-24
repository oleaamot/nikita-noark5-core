package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.admin;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IHateoasHandler;

/**
 * Created by tsodring on 5/18/17.
 */
public interface IAdministrativeUnitHateoasHandler extends IHateoasHandler {
    void addChildAdministrativeUnit(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
