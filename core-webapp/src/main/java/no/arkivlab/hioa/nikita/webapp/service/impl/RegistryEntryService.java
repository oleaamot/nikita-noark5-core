package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.casehandling.Precedence;
import nikita.model.noark5.v4.casehandling.RegistryEntry;
import nikita.model.noark5.v4.casehandling.secondary.*;
import nikita.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.repository.n5v4.IRegistryEntryRepository;
import nikita.repository.n5v4.metadata.ICorrespondencePartTypeRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.ICorrespondencePartService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.IPrecedenceService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class RegistryEntryService implements IRegistryEntryService {

    private static final Logger logger = LoggerFactory.getLogger(RegistryEntryService.class);
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);
    private DocumentDescriptionService documentDescriptionService;
    private ICorrespondencePartService correspondencePartService;
    private IPrecedenceService precedenceService;
    private IRegistryEntryRepository registryEntryRepository;
    private ICorrespondencePartTypeRepository correspondencePartTypeRepository;
    private EntityManager entityManager;

    public RegistryEntryService(DocumentDescriptionService documentDescriptionService,
                                ICorrespondencePartService correspondencePartService,
                                IPrecedenceService precedenceService,
                                IRegistryEntryRepository registryEntryRepository,
                                ICorrespondencePartTypeRepository correspondencePartTypeRepository,
                                EntityManager entityManager) {
        this.documentDescriptionService = documentDescriptionService;
        this.correspondencePartService = correspondencePartService;
        this.precedenceService = precedenceService;
        this.registryEntryRepository = registryEntryRepository;
        this.correspondencePartTypeRepository = correspondencePartTypeRepository;
        this.entityManager = entityManager;
    }

    @Override
    public RegistryEntry save(@NotNull RegistryEntry registryEntry) {
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(registryEntry);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(registryEntry);
        NoarkUtils.NoarkEntity.Create.setCreateEntityValues(registryEntry);
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(registryEntry);
        registryEntryRepository.save(registryEntry);
        return registryEntry;
    }

    private void associateCorrespondencePartTypeWithCorrespondencePart(@NotNull CorrespondencePart correspondencePart) {
        CorrespondencePartType incomingCorrespondencePartType = correspondencePart.getCorrespondencePartType();
        // It should never get this far with a null value
        // It should be rejected at controller level
        // The incoming CorrespondencePartType will not have @id field set. Therefore, we have to look it up
        // in the database and make sure the proper CorrespondencePartType is associated with the CorrespondencePart
        if (incomingCorrespondencePartType != null && incomingCorrespondencePartType.getCode() != null) {
            CorrespondencePartType actualCorrespondencePartType =
                    correspondencePartTypeRepository.findByCode(incomingCorrespondencePartType.getCode());
            if (actualCorrespondencePartType != null) {
                correspondencePart.setCorrespondencePartType(actualCorrespondencePartType);
            }
        }
    }

    @Override
    public List<CorrespondencePartPerson> getCorrespondencePartPersonAssociatedWithRegistryEntry(String systemID) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(systemID);
        return registryEntry.getReferenceCorrespondencePartPerson();
    }

    @Override
    public List<CorrespondencePartInternal> getCorrespondencePartInternalAssociatedWithRegistryEntry(String systemID) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(systemID);
        return registryEntry.getReferenceCorrespondencePartInternal();
    }

    @Override
    public List<CorrespondencePartUnit> getCorrespondencePartUnitAssociatedWithRegistryEntry(String systemID) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(systemID);
        return registryEntry.getReferenceCorrespondencePartUnit();
    }
    
    @Override
    public CorrespondencePartPerson createCorrespondencePartPersonAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartPerson correspondencePart) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(systemID);

        associateCorrespondencePartTypeWithCorrespondencePart(correspondencePart);

        ContactInformation contactInformation = correspondencePart.getContactInformation();
        if (null != contactInformation) {
            NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(contactInformation);
            NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(contactInformation);
        }

        SimpleAddress postalAddress = correspondencePart.getPostalAddress();
        if (null != postalAddress) {
            NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(postalAddress);
            NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(postalAddress);
        }

        SimpleAddress residingAddress = correspondencePart.getResidingAddress();
        if (null != residingAddress) {
            NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(residingAddress);
            NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(residingAddress);
        }

        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(correspondencePart);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(correspondencePart);
        // bidirectional relationship @ManyToMany, set both sides of relationship
        registryEntry.getReferenceCorrespondencePartPerson().add(correspondencePart);
        correspondencePart.getReferenceRegistryEntry().add(registryEntry);
        return correspondencePartService.createNewCorrespondencePartPerson(correspondencePart);
    }

    @Override
    public CorrespondencePartInternal createCorrespondencePartInternalAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartInternal correspondencePart) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(systemID);

        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(correspondencePart);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(correspondencePart);
        // bidirectional relationship @ManyToMany, set both sides of relationship
        registryEntry.getReferenceCorrespondencePartInternal().add(correspondencePart);
        correspondencePart.getReferenceRegistryEntry().add(registryEntry);
        return correspondencePartService.createNewCorrespondencePartInternal(correspondencePart);
    }

    @Override
    public CorrespondencePartUnit createCorrespondencePartUnitAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartUnit correspondencePart) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(systemID);

        associateCorrespondencePartTypeWithCorrespondencePart(correspondencePart);

        ContactInformation contactInformation = correspondencePart.getContactInformation();
        if (null != contactInformation) {
            NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(contactInformation);
            NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(contactInformation);
        }

        SimpleAddress postalAddress = correspondencePart.getPostalAddress();
        if (null != postalAddress) {
            NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(postalAddress);
            NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(postalAddress);
        }

        SimpleAddress businessAddress = correspondencePart.getBusinessAddress();
        if (null != businessAddress) {
            NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(businessAddress);
            NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(businessAddress);
        }

        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(correspondencePart);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(correspondencePart);
        // bidirectional relationship @ManyToMany, set both sides of relationship
        registryEntry.getReferenceCorrespondencePartUnit().add(correspondencePart);
        correspondencePart.getReferenceRegistryEntry().add(registryEntry);
        return correspondencePartService.createNewCorrespondencePartUnit(correspondencePart);
    }

    @Override
    public DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
            String systemID, DocumentDescription documentDescription) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(systemID);
        ArrayList<Record> records = (ArrayList<Record>) documentDescription.getReferenceRecord();

        // It should always be instaniated ... check this ...
        if (records == null) {
            records = new ArrayList<>();
            documentDescription.setReferenceRecord(records);
        }
        records.add(registryEntry);
        return documentDescriptionService.save(documentDescription);
    }

    // All READ operations
    public List<RegistryEntry> findRegistryEntryByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RegistryEntry> criteriaQuery = criteriaBuilder.createQuery(RegistryEntry.class);
        Root<RegistryEntry> from = criteriaQuery.from(RegistryEntry.class);
        CriteriaQuery<RegistryEntry> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<RegistryEntry> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(top);
        return typedQuery.getResultList();
    }

    // systemId
    public RegistryEntry findBySystemId(String systemId) {
        return getRegistryEntryOrThrow(systemId);
    }


    @Override
    public Precedence createPrecedenceAssociatedWithRecord(String registryEntrySystemID, Precedence precedence) {

        RegistryEntry registryEntry = getRegistryEntryOrThrow(registryEntrySystemID);
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(precedence);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(precedence);
        // bidirectional relationship @ManyToMany, set both sides of relationship
        registryEntry.getReferencePrecedence().add(precedence);
        precedence.getReferenceRegistryEntry().add(registryEntry);
        return precedenceService.createNewPrecedence(precedence);

    }

    // All UPDATE operations
    @Override
    public RegistryEntry handleUpdate(@NotNull String systemId, @NotNull Long version,
                                      @NotNull RegistryEntry incomingRegistryEntry) {
        RegistryEntry existingRegistryEntry = getRegistryEntryOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        if (null != incomingRegistryEntry.getDescription()) {
            existingRegistryEntry.setDescription(incomingRegistryEntry.getDescription());
        }
        if (null != incomingRegistryEntry.getTitle()) {
            existingRegistryEntry.setTitle(incomingRegistryEntry.getTitle());
        }
        if (null != incomingRegistryEntry.getDocumentMedium()) {
            existingRegistryEntry.setDocumentMedium(incomingRegistryEntry.getDocumentMedium());
        }
        if (null != incomingRegistryEntry.getDocumentDate()) {
            existingRegistryEntry.setDocumentDate(incomingRegistryEntry.getDocumentDate());
        }
        if (null != incomingRegistryEntry.getDueDate()) {
            existingRegistryEntry.setDueDate(incomingRegistryEntry.getDueDate() );
        }
        if (null != incomingRegistryEntry.getFreedomAssessmentDate()) {
            existingRegistryEntry.setFreedomAssessmentDate(incomingRegistryEntry.getFreedomAssessmentDate());
        }
        if (null != incomingRegistryEntry.getLoanedDate()) {
            existingRegistryEntry.setLoanedDate(incomingRegistryEntry.getLoanedDate() );
        }
        if (null != incomingRegistryEntry.getLoanedTo()) {
            existingRegistryEntry.setLoanedTo(incomingRegistryEntry.getLoanedTo());
        }
        existingRegistryEntry.setVersion(version);
        registryEntryRepository.save(existingRegistryEntry);
        return existingRegistryEntry;
    }
    
    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String registryEntrySystemId) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(registryEntrySystemId);
        registryEntryRepository.delete(registryEntry);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid RegistryEntry back. If there is no valid
     * RegistryEntry, an exception is thrown
     *
     * @param registryEntrySystemId
     * @return
     */
    protected RegistryEntry getRegistryEntryOrThrow(@NotNull String registryEntrySystemId) {
        RegistryEntry registryEntry = registryEntryRepository.findBySystemId(registryEntrySystemId);
        if (registryEntry == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " RegistryEntry, using systemId " + registryEntrySystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return registryEntry;
    }

}
