package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IHateoasHandler;

/**
 * Created by tsodring on 4/3/17.
 */
public interface IMetadataHateoasHandler extends IHateoasHandler {
    void addNewCode(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCode(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
