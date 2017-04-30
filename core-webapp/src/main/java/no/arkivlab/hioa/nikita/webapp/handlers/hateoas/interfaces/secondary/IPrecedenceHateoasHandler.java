package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.secondary;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IHateoasHandler;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface IPrecedenceHateoasHandler extends IHateoasHandler {

    void addPrecedenceStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addNewCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addNewPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addNewRegistryEntry(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
    void addRegistryEntry(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
