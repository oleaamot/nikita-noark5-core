package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.FlowStatus;
import nikita.repository.n5v4.metadata.IFlowStatusRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IFlowStatusService;
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
import static nikita.config.N5ResourceMappings.FLOW_STATUS;

/**
 * Created by tsodring on 17/02/18.
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class FlowStatusService
        implements IFlowStatusService {

    private static final Logger logger =
            LoggerFactory.getLogger(FlowStatusService.class);

    private IFlowStatusRepository formatRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public FlowStatusService(
            IFlowStatusRepository
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
     * Persists a new FlowStatus object to the database.
     *
     * @param format FlowStatus object with values set
     * @return the newly persisted FlowStatus object wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas createNewFlowStatus(
            FlowStatus flowStatus) {

        flowStatus.setDeleted(false);
        flowStatus.setOwnedBy(SecurityContextHolder.getContext().
                getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                formatRepository.save(flowStatus));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all FlowStatus objects
     *
     * @return list of FlowStatus objects wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        formatRepository.findAll(), FLOW_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single FlowStatus object identified by systemId
     *
     * @param systemId systemId of the FlowStatus you wish to retrieve
     * @return single FlowStatus object wrapped as a MetadataHateoas object
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
     * Retrieve all FlowStatus that have a given
     * description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description Description of object you wish to retrieve. The
     *                    whole text, this is an exact search.
     * @return A list of FlowStatus objects wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        formatRepository
                                .findByDescription(description),
                FLOW_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all FlowStatus that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code The code of the object you wish to retrieve
     * @return A list of FlowStatus objects wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        formatRepository.findByCode
                                (code),
                FLOW_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default FlowStatus object
     *
     * @return the FlowStatus object wrapped as a FlowStatusHateoas object
     */
    @Override
    public FlowStatus generateDefaultFlowStatus() {

        FlowStatus format = new FlowStatus();
        format.setCode(TEMPLATE_FLOW_STATUS_CODE);
        format.setDescription(TEMPLATE_FLOW_STATUS_DESCRIPTION);

        return format;
    }

    /**
     * Update a FlowStatus identified by its systemId
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
            version, FlowStatus format) {

        FlowStatus existingFlowStatus = getFlowStatusOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingFlowStatus.getCode()) {
            existingFlowStatus.setCode(existingFlowStatus.getCode());
        }
        if (null != existingFlowStatus.getDescription()) {
            existingFlowStatus.setDescription(existingFlowStatus.getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingFlowStatus.setVersion(version);

        MetadataHateoas formatHateoas = new MetadataHateoas(formatRepository
                .save(existingFlowStatus));

        metadataHateoasHandler.addLinks(formatHateoas, new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this, existingFlowStatus));
        return formatHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid FlowStatus object back. If there is no
     * FlowStatus object, a NoarkEntityNotFoundException exception is thrown
     *
     * @param systemId The systemId of the FlowStatus object to retrieve
     * @return the FlowStatus object
     */
    private FlowStatus
    getFlowStatusOrThrow(@NotNull String systemId) {
        FlowStatus format = formatRepository.findBySystemId(systemId);
        if (format == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " FlowStatus, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return format;
    }
}
