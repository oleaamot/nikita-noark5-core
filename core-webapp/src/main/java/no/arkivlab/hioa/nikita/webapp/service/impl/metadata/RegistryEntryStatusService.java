package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.RegistryEntryStatus;
import nikita.repository.n5v4.metadata.IRegistryEntryStatusRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IRegistryEntryStatusService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_TYPE;

/**
 * Created by tsodring on 12/02/18.
 */


@Service
@Transactional
public class RegistryEntryStatusService
        implements IRegistryEntryStatusService {

    private static final Logger logger =
            LoggerFactory.getLogger(RegistryEntryStatusService.class);

    private IRegistryEntryStatusRepository RegistryEntryStatusRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public RegistryEntryStatusService(IRegistryEntryStatusRepository RegistryEntryStatusRepository,
                                      IMetadataHateoasHandler metadataHateoasHandler,
                                      ApplicationEventPublisher applicationEventPublisher) {
        this.RegistryEntryStatusRepository = RegistryEntryStatusRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new RegistryEntryStatus object to the database.
     *
     * @param RegistryEntryStatus RegistryEntryStatus object with values set
     * @return the newly persisted RegistryEntryStatus object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewRegistryEntryStatus(
            RegistryEntryStatus RegistryEntryStatus) {

        RegistryEntryStatus.setDeleted(false);
        RegistryEntryStatus.setOwnedBy(
                SecurityContextHolder.getContext().
                        getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                RegistryEntryStatusRepository.save(RegistryEntryStatus));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all RegistryEntryStatus objects
     *
     * @return list of RegistryEntryStatus objects wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        RegistryEntryStatusRepository.findAll(), DOCUMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single RegistryEntryStatus object identified by systemId
     *
     * @param systemId
     * @return single RegistryEntryStatus object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                RegistryEntryStatusRepository.save(
                        RegistryEntryStatusRepository.findBySystemId(systemId)));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all RegistryEntryStatus that have a given description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description
     * @return A list of RegistryEntryStatus objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        RegistryEntryStatusRepository.findByDescription(description),
                DOCUMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all RegistryEntryStatus that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code
     * @return A list of RegistryEntryStatus objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        RegistryEntryStatusRepository.findByCode(code),
                DOCUMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default RegistryEntryStatus object
     *
     * @return the RegistryEntryStatus object wrapped as a RegistryEntryStatusHateoas object
     */
    @Override
    public RegistryEntryStatus generateDefaultRegistryEntryStatus() {

        RegistryEntryStatus RegistryEntryStatus = new RegistryEntryStatus();
        RegistryEntryStatus.setCode(TEMPLATE_DOCUMENT_TYPE_CODE);
        RegistryEntryStatus.setDescription(TEMPLATE_DOCUMENT_TYPE_DESCRIPTION);

        return RegistryEntryStatus;
    }

    /**
     * Update a RegistryEntryStatus identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param RegistryEntryStatus
     * @return the updated RegistryEntryStatus
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, RegistryEntryStatus RegistryEntryStatus) {

        RegistryEntryStatus existingRegistryEntryStatus = getRegistryEntryStatusOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingRegistryEntryStatus.getCode()) {
            existingRegistryEntryStatus.setCode(existingRegistryEntryStatus.getCode());
        }
        if (null != existingRegistryEntryStatus.getDescription()) {
            existingRegistryEntryStatus.setDescription(existingRegistryEntryStatus.
                    getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingRegistryEntryStatus.setVersion(version);

        MetadataHateoas RegistryEntryStatusHateoas = new MetadataHateoas(
                RegistryEntryStatusRepository.save(existingRegistryEntryStatus));
        metadataHateoasHandler.addLinks(RegistryEntryStatusHateoas,
                new Authorisation());
        applicationEventPublisher.publishEvent(
                new AfterNoarkEntityUpdatedEvent(this,
                        existingRegistryEntryStatus));
        return RegistryEntryStatusHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid RegistryEntryStatus object back. If there
     * is no RegistryEntryStatus object, a NoarkEntityNotFoundException exception
     * is thrown
     *
     * @param systemId The systemId of the RegistryEntryStatus object to retrieve
     * @return the RegistryEntryStatus object
     */
    private RegistryEntryStatus getRegistryEntryStatusOrThrow(@NotNull String systemId) {
        RegistryEntryStatus RegistryEntryStatus = RegistryEntryStatusRepository.findBySystemId
                (systemId);
        if (RegistryEntryStatus == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " RegistryEntryStatus, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return RegistryEntryStatus;
    }

}
