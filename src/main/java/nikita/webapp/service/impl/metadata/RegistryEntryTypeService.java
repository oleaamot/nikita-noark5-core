package nikita.webapp.service.impl.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.model.noark5.v4.metadata.RegistryEntryType;
import nikita.common.repository.n5v4.metadata.IRegistryEntryTypeRepository;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.interfaces.metadata.IRegistryEntryTypeService;
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
import static nikita.common.config.N5ResourceMappings.REGISTRY_ENTRY_TYPE;

/**
 * Created by tsodring on 18/02/18.
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class RegistryEntryTypeService
        implements IRegistryEntryTypeService {

    private static final Logger logger =
            LoggerFactory.getLogger(RegistryEntryTypeService.class);

    private IRegistryEntryTypeRepository formatRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public RegistryEntryTypeService(
            IRegistryEntryTypeRepository
                    formatRepository,
            IMetadataHateoasHandler metadataHateoasHandler,
            ApplicationEventPublisher applicationEventPublisher) {

        this.formatRepository =
                formatRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new RegistryEntryType object to the database.
     *
     * @param registryEntryType RegistryEntryType object with values set
     * @return the newly persisted RegistryEntryType object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewRegistryEntryType(
            RegistryEntryType registryEntryType) {

        registryEntryType.setDeleted(false);
        registryEntryType.setOwnedBy(SecurityContextHolder.getContext().
                getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                formatRepository.save(registryEntryType));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all RegistryEntryType objects
     *
     * @return list of RegistryEntryType objects wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        formatRepository.findAll(), REGISTRY_ENTRY_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single RegistryEntryType object identified by systemId
     *
     * @param systemId systemId of the RegistryEntryType you wish to retrieve
     * @return single RegistryEntryType object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                formatRepository
                        .findBySystemId(systemId));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all RegistryEntryType that have a given
     * description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description Description of object you wish to retrieve. The
     *                    whole text, this is an exact search.
     * @return A list of RegistryEntryType objects wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        formatRepository
                                .findByDescription(description),
                REGISTRY_ENTRY_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all RegistryEntryType that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code The code of the object you wish to retrieve
     * @return A list of RegistryEntryType objects wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        formatRepository.findByCode
                                (code),
                REGISTRY_ENTRY_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default RegistryEntryType object
     *
     * @return the RegistryEntryType object
     */
    @Override
    public RegistryEntryType generateDefaultRegistryEntryType() {

        RegistryEntryType format = new RegistryEntryType();
        format.setCode(TEMPLATE_REGISTRY_ENTRY_TYPE_CODE);
        format.setDescription(TEMPLATE_REGISTRY_ENTRY_TYPE_DESCRIPTION);

        return format;
    }

    /**
     * Update a RegistryEntryType identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param systemId The systemId of the format object you wish to update
     * @param format   The updated format object. Note the values you are
     *                 allowed to change are copied from this object. This
     *                 object is not persisted.
     * @return the updated format
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, RegistryEntryType format) {

        RegistryEntryType existingRegistryEntryType = getRegistryEntryTypeOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingRegistryEntryType.getCode()) {
            existingRegistryEntryType.setCode(existingRegistryEntryType.getCode());
        }
        if (null != existingRegistryEntryType.getDescription()) {
            existingRegistryEntryType.setDescription(existingRegistryEntryType.getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingRegistryEntryType.setVersion(version);

        MetadataHateoas formatHateoas = new MetadataHateoas(formatRepository
                .save(existingRegistryEntryType));

        metadataHateoasHandler.addLinks(formatHateoas, new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this, existingRegistryEntryType));
        return formatHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid RegistryEntryType object back. If
     * there is no RegistryEntryType object, a NoarkEntityNotFoundException
     * exception is thrown
     *
     * @param systemId The systemId of the RegistryEntryType object to retrieve
     * @return the RegistryEntryType object
     */
    private RegistryEntryType
    getRegistryEntryTypeOrThrow(@NotNull String systemId) {
        RegistryEntryType format = formatRepository.findBySystemId(systemId);
        if (format == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " RegistryEntryType,  " +
                    "using systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return format;
    }
}
