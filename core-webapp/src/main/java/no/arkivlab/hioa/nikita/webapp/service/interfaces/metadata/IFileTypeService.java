package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.FileType;

/**
 * Created by tsodring on 03/03/18.
 */

public interface IFileTypeService {

    MetadataHateoas createNewFileType(FileType fileType);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, FileType
            fileType);

    FileType generateDefaultFileType();
}
