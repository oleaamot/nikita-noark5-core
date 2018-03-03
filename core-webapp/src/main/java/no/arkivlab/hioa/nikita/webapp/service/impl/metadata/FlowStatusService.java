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

    private IFlowStatusRepository fileTypeRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public FlowStatusService(
            IFlowStatusRepository
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
     * Persists a new FlowStatus object to the database.
     *
     * @param flowStatus FlowStatus object with values set
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
                fileTypeRepository.save(flowStatus));
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
                        fileTypeRepository.findAll(), FLOW_STATUS);
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
                fileTypeRepository
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
                        fileTypeRepository
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
                        fileTypeRepository.findByCode
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

        FlowStatus fileType = new FlowStatus();
        fileType.setCode(TEMPLATE_FLOW_STATUS_CODE);
        fileType.setDescription(TEMPLATE_FLOW_STATUS_DESCRIPTION);

        return fileType;
    }

    /**
     * Update a FlowStatus identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param systemId The systemId of the fileType object you wish to update
     * @param fileType   The updated fileType object. Note the values you are
     *                 allowed to change are copied from this object. This
     *                 object is not persisted.
     * @return the updated fileType
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, FlowStatus fileType) {

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

        MetadataHateoas fileTypeHateoas = new MetadataHateoas(fileTypeRepository
                .save(existingFlowStatus));

        metadataHateoasHandler.addLinks(fileTypeHateoas, new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this, existingFlowStatus));
        return fileTypeHateoas;
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
        FlowStatus fileType = fileTypeRepository.findBySystemId(systemId);
        if (fileType == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " FlowStatus, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return fileType;
    }
}
