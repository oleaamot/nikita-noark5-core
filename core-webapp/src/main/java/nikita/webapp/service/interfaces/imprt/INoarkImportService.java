package nikita.webapp.service.interfaces.imprt;

import nikita.common.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;

/**
 * Created by tsodring on 12/7/16.
 */
public interface INoarkImportService {
    void checkAndSetDefaultValues(INoarkGeneralEntity entity) throws Exception;
}
