package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.ClassificationType;

/**
 * Created by tsodring on 11/03/18.
 */

public interface IClassificationTypeService {

    MetadataHateoas createNewClassificationType(ClassificationType classificationType);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, ClassificationType
            classificationType);

    ClassificationType generateDefaultClassificationType();
}
