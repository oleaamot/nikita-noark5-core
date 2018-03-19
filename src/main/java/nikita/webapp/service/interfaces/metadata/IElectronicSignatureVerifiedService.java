package nikita.webapp.service.interfaces.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.ElectronicSignatureVerified;

/**
 * Created by tsodring on 19/02/18.
 */

public interface IElectronicSignatureVerifiedService {

    MetadataHateoas createNewElectronicSignatureVerified(
            ElectronicSignatureVerified electronicSignatureVerified);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version,
                                 ElectronicSignatureVerified
                                         electronicSignatureVerified);

    ElectronicSignatureVerified generateDefaultElectronicSignatureVerified();
}
