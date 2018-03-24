package nikita.webapp.hateoas.interfaces.metadata;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.webapp.hateoas.interfaces.IHateoasHandler;

/**
 * Created by tsodring on 4/3/17.
 */
public interface IMetadataHateoasHandler extends IHateoasHandler {
    void addNewCode(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCode(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);
}
