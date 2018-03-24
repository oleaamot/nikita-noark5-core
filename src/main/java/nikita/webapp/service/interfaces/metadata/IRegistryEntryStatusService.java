package nikita.webapp.service.interfaces.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.RegistryEntryStatus;

/**
 * Created by tsodring on 12/02/18.
 */

public interface IRegistryEntryStatusService {

    MetadataHateoas createNewRegistryEntryStatus(
            RegistryEntryStatus RegistryEntryStatus);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version,
                                 RegistryEntryStatus RegistryEntryStatus);

    RegistryEntryStatus generateDefaultRegistryEntryStatus();
}
