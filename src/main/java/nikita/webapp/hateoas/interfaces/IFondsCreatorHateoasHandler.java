package nikita.webapp.hateoas.interfaces;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler for FondsCreator
 */
public interface IFondsCreatorHateoasHandler extends IHateoasHandler {

    void addFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addNewFonds(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject);

}
