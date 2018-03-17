package nikita.webapp.service.impl.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.model.noark5.v4.metadata.FileType;
import nikita.common.repository.n5v4.metadata.IFileTypeRepository;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.interfaces.metadata.IFileTypeService;
import nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.FILE_TYPE;

/**
 * Created by tsodring on 03/03/18.
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class FileTypeService
        implements IFileTypeService {

    private static final Logger logger =
            LoggerFactory.getLogger(FileTypeService.class);

    private IFileTypeRepository fileTypeRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public FileTypeService(
            IFileTypeRepository
                    fileTypeRepository,
            IMetadataHateoasHandler metadataHateoasHandler,
            ApplicationEventPublisher applicationEventPublisher) {

        this.fileTypeRepository =
                fileTypeRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new FileType object to the database.
     *
     * @param fileType FileType object with values set
     * @return the newly persisted FileType object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewFileType(
            FileType fileType) {

        fileType.setDeleted(false);
        fileType.setOwnedBy(SecurityContextHolder.getContext().
                getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                fileTypeRepository.save(fileType));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all FileType objects
     *
     * @return list of FileType objects wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        fileTypeRepository.findAll(), FILE_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single FileType object identified by systemId
     *
     * @param systemId systemId of the FileType you wish to retrieve
     * @return single FileType object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                fileTypeRepository
                        .findBySystemId(systemId));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all FileType that have a given description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description Description of object you wish to retrieve. The
     *                    whole text, this is an exact search.
     * @return A list of FileType objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        fileTypeRepository
                                .findByDescription(description), FILE_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all FileType that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code The code of the object you wish to retrieve
     * @return A list of FileType objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        fileTypeRepository.findByCode(code), FILE_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default FileType object
     *
     * @return the FileType object wrapped as a FileTypeHateoas object
     */
    @Override
    public FileType generateDefaultFileType() {

        FileType fileType = new FileType();
        fileType.setCode(TEMPLATE_FILE_TYPE_CODE);
        fileType.setDescription(TEMPLATE_FILE_TYPE_DESCRIPTION);

        return fileType;
    }

    /**
     * Update a FileType identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param systemId The systemId of the fileType object you wish to
     *                 update
     * @param fileType The updated fileType object. Note the values
     *                 you are allowed to change are copied from this
     *                 object. This object is not persisted.
     * @return the updated fileType
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, FileType fileType) {

        FileType existingFileType = getFileTypeOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingFileType.getCode()) {
            existingFileType.setCode(existingFileType.getCode());
        }
        if (null != existingFileType.getDescription()) {
            existingFileType.setDescription(
                    existingFileType.getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingFileType.setVersion(version);

        MetadataHateoas fileTypeHateoas = new MetadataHateoas(
                fileTypeRepository.save(existingFileType));

        metadataHateoasHandler.addLinks(fileTypeHateoas,
                new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this,
                existingFileType));
        return fileTypeHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid FileType object back. If there
     * is no FileType object, a NoarkEntityNotFoundException exception
     * is thrown
     *
     * @param systemId The systemId of the FileType object to retrieve
     * @return the FileType object
     */
    private FileType getFileTypeOrThrow(@NotNull String systemId) {
        FileType fileType = fileTypeRepository.findBySystemId(systemId);
        if (fileType == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " FileType, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return fileType;
    }
}
