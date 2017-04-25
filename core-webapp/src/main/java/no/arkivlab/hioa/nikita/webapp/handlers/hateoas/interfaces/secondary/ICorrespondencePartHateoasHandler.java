package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.secondary;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRecordHateoasHandler;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface ICorrespondencePartHateoasHandler extends IHateoasHandler {

    void addCorrespondencePartType(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
