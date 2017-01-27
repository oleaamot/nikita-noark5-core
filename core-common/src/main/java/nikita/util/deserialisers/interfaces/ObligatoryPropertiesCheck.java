package nikita.util.deserialisers.interfaces;

import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;

/**
 * Created by tsodring on 1/9/17.
 */
public interface ObligatoryPropertiesCheck {

    void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity);
}
