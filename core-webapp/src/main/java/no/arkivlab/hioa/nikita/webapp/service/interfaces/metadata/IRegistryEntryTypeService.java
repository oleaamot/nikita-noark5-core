package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.RegistryEntryType;

/**
 * Created by tsodring on 18/02/18.
 */

public interface IRegistryEntryTypeService {

    MetadataHateoas createNewRegistryEntryType(RegistryEntryType
                                                       registryEntryType);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version,
                                 RegistryEntryType registryEntryType);

    RegistryEntryType generateDefaultRegistryEntryType();
}
