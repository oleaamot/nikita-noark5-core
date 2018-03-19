package nikita.webapp.hateoas.interfaces.secondary;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.webapp.hateoas.interfaces.IHateoasHandler;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface ICorrespondencePartHateoasHandler extends IHateoasHandler {

    void addCorrespondencePartType(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
