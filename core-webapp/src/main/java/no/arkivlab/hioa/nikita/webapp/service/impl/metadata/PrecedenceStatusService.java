package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.PrecedenceStatus;
import nikita.repository.n5v4.metadata.IPrecedenceStatusRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IPrecedenceStatusService;
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
import static nikita.config.N5ResourceMappings.PRECEDENCE_STATUS;

/**
 * Created by tsodring on 19/02/18.
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class PrecedenceStatusService
        implements IPrecedenceStatusService {

    private static final Logger logger =
            LoggerFactory.getLogger(PrecedenceStatusService.class);

    private IPrecedenceStatusRepository precedenceStatusRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public PrecedenceStatusService(
            IPrecedenceStatusRepository
                    precedenceStatusRepository,
            IMetadataHateoasHandler metadataHateoasHandler,
            ApplicationEventPublisher applicationEventPublisher) {

        this.precedenceStatusRepository =
                precedenceStatusRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new PrecedenceStatus object to the database.
     *
     * @param precedenceStatus PrecedenceStatus object with values set
     * @return the newly persisted PrecedenceStatus object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewPrecedenceStatus(
            PrecedenceStatus precedenceStatus) {

        precedenceStatus.setDeleted(false);
        precedenceStatus.setOwnedBy(SecurityContextHolder.getContext().
                getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                precedenceStatusRepository.save(precedenceStatus));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all PrecedenceStatus objects
     *
     * @return list of PrecedenceStatus objects wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        precedenceStatusRepository.findAll(), PRECEDENCE_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single PrecedenceStatus object identified by systemId
     *
     * @param systemId systemId of the PrecedenceStatus you wish to retrieve
     * @return single PrecedenceStatus object wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                precedenceStatusRepository
                        .findBySystemId(systemId));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all PrecedenceStatus that have a given
     * description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description Description of object you wish to retrieve. The
     *                    whole text, this is an exact search.
     * @return A list of PrecedenceStatus objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        precedenceStatusRepository
                                .findByDescription(description),
                PRECEDENCE_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all PrecedenceStatus that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code The code of the object you wish to retrieve
     * @return A list of PrecedenceStatus objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        precedenceStatusRepository.findByCode
                                (code), PRECEDENCE_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default PrecedenceStatus object
     *
     * @return the PrecedenceStatus object
     */
    @Override
    public PrecedenceStatus generateDefaultPrecedenceStatus() {

        PrecedenceStatus precedenceStatus = new PrecedenceStatus();
        precedenceStatus.setCode(TEMPLATE_PRECEDENCE_STATUS_CODE);
        precedenceStatus.setDescription(TEMPLATE_PRECEDENCE_STATUS_DESCRIPTION);

        return precedenceStatus;
    }

    /**
     * Update a PrecedenceStatus identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param systemId         The systemId of the precedenceStatus object you wish to
     *                         update
     * @param precedenceStatus The updated precedenceStatus object. Note the
     *                         values you are allowed to change are copied
     *                         from this object. This object is not persisted.
     * @return the updated precedenceStatus
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, PrecedenceStatus precedenceStatus) {

        PrecedenceStatus existingPrecedenceStatus =
                getPrecedenceStatusOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingPrecedenceStatus.getCode()) {
            existingPrecedenceStatus.setCode(existingPrecedenceStatus.getCode());
        }
        if (null != existingPrecedenceStatus.getDescription()) {
            existingPrecedenceStatus.setDescription(
                    existingPrecedenceStatus.getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingPrecedenceStatus.setVersion(version);

        MetadataHateoas precedenceStatusHateoas = new MetadataHateoas(precedenceStatusRepository
                .save(existingPrecedenceStatus));

        metadataHateoasHandler.addLinks(precedenceStatusHateoas, new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this,
                existingPrecedenceStatus));
        return precedenceStatusHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid PrecedenceStatus object back. If
     * there is no PrecedenceStatus object, a NoarkEntityNotFoundException
     * exception is thrown
     *
     * @param systemId The systemId of the PrecedenceStatus object to retrieve
     * @return the PrecedenceStatus object
     */
    private PrecedenceStatus
    getPrecedenceStatusOrThrow(@NotNull String systemId) {
        PrecedenceStatus precedenceStatus = precedenceStatusRepository.findBySystemId(systemId);
        if (precedenceStatus == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " PrecedenceStatus,  " +
                    "using systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return precedenceStatus;
    }
}
