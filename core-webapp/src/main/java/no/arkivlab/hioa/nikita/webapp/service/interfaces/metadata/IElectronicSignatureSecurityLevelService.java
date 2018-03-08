package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.ElectronicSignatureSecurityLevel;

/**
 * Created by tsodring on 13/02/18.
 */

public interface IElectronicSignatureSecurityLevelService {

    MetadataHateoas createNewElectronicSignatureSecurityLevel(
            ElectronicSignatureSecurityLevel electronicSignatureSecurityLevel);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version,
                                 ElectronicSignatureSecurityLevel
                                         electronicSignatureSecurityLevel);

    ElectronicSignatureSecurityLevel
    generateDefaultElectronicSignatureSecurityLevel();
}
