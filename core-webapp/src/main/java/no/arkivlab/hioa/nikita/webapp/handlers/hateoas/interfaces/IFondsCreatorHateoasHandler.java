package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for FondsCreator
 */
public interface IFondsCreatorHateoasHandler extends IHateoasHandler {

    void addFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
