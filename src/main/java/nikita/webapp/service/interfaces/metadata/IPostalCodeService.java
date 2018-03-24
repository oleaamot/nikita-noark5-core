package nikita.webapp.service.interfaces.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.PostalCode;

/**
 * Created by tsodring on 16/03/18.
 */

public interface IPostalCodeService {

    MetadataHateoas createNewPostalCode(PostalCode PostalCode);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, PostalCode
            PostalCode);

    PostalCode generateDefaultPostalCode();
}
