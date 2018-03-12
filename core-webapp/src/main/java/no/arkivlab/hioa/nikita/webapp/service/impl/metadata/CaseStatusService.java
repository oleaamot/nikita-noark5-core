package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.CaseStatus;
import nikita.repository.n5v4.metadata.ICaseStatusRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ICaseStatusService;
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
import static nikita.config.N5ResourceMappings.CASE_STATUS;

/**
 * Created by tsodring on 13/03/18.
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class CaseStatusService
        implements ICaseStatusService {

    private static final Logger logger =
            LoggerFactory.getLogger(CaseStatusService.class);

    private ICaseStatusRepository caseStatusRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public CaseStatusService(
            ICaseStatusRepository
                    caseStatusRepository,
            IMetadataHateoasHandler metadataHateoasHandler,
            ApplicationEventPublisher applicationEventPublisher) {

        this.caseStatusRepository =
                caseStatusRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new CaseStatus object to the database.
     *
     * @param caseStatus CaseStatus object with values set
     * @return the newly persisted CaseStatus object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewCaseStatus(
            CaseStatus caseStatus) {

        caseStatus.setDeleted(false);
        caseStatus.setOwnedBy(SecurityContextHolder.getContext().
                getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                caseStatusRepository.save(caseStatus));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all CaseStatus objects
     *
     * @return list of CaseStatus objects wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        caseStatusRepository.findAll(), CASE_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single CaseStatus object identified by systemId
     *
     * @param systemId systemId of the CaseStatus you wish to retrieve
     * @return single CaseStatus object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                caseStatusRepository
                        .findBySystemId(systemId));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all CaseStatus that have a given description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description Description of object you wish to retrieve. The
     *                    whole text, this is an exact search.
     * @return A list of CaseStatus objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        caseStatusRepository
                                .findByDescription(description), CASE_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all CaseStatus that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code The code of the object you wish to retrieve
     * @return A list of CaseStatus objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        caseStatusRepository.findByCode(code), CASE_STATUS);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default CaseStatus object
     *
     * @return the CaseStatus object wrapped as a CaseStatusHateoas object
     */
    @Override
    public CaseStatus generateDefaultCaseStatus() {

        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setCode(TEMPLATE_CASE_STATUS_CODE);
        caseStatus.setDescription(TEMPLATE_CASE_STATUS_DESCRIPTION);

        return caseStatus;
    }

    /**
     * Update a CaseStatus identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param systemId   The systemId of the caseStatus object you wish to
     *                   update
     * @param caseStatus The updated caseStatus object. Note the values
     *                   you are allowed to change are copied from this
     *                   object. This object is not persisted.
     * @return the updated caseStatus
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, CaseStatus caseStatus) {

        CaseStatus existingCaseStatus = getCaseStatusOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingCaseStatus.getCode()) {
            existingCaseStatus.setCode(existingCaseStatus.getCode());
        }
        if (null != existingCaseStatus.getDescription()) {
            existingCaseStatus.setDescription(
                    existingCaseStatus.getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingCaseStatus.setVersion(version);

        MetadataHateoas caseStatusHateoas = new MetadataHateoas(
                caseStatusRepository.save(existingCaseStatus));

        metadataHateoasHandler.addLinks(caseStatusHateoas,
                new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this,
                existingCaseStatus));
        return caseStatusHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid CaseStatus object back. If there
     * is no CaseStatus object, a NoarkEntityNotFoundException exception
     * is thrown
     *
     * @param systemId The systemId of the CaseStatus object to retrieve
     * @return the CaseStatus object
     */
    private CaseStatus getCaseStatusOrThrow(@NotNull String systemId) {
        CaseStatus caseStatus = caseStatusRepository.
                findBySystemId(systemId);
        if (caseStatus == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " CaseStatus, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return caseStatus;
    }
}
