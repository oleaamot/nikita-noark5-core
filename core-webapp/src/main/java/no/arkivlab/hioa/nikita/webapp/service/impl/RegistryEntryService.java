package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.secondary.CorrespondencePart;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.secondary.PostalAddress;
import nikita.model.noark5.v4.secondary.Precedence;
import nikita.repository.n5v4.IRegistryEntryRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.ICorrespondencePartService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary.IPrecedenceService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private EntityManager entityManager;

    public RegistryEntryService(DocumentDescriptionService documentDescriptionService,
                                ICorrespondencePartService correspondencePartService,
                                IRegistryEntryRepository registryEntryRepository,
                                EntityManager entityManager) {
        this.documentDescriptionService = documentDescriptionService;
        this.correspondencePartService = correspondencePartService;
        this.registryEntryRepository = registryEntryRepository;
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


    @Override
    public CorrespondencePart createCorrespondencePartAssociatedWithRegistryEntry(
            String recordSystemId, CorrespondencePart correspondencePart) {
        RegistryEntry registryEntry = getRegistryEntryOrThrow(recordSystemId);
        Set <PostalAddress> postalAddresss = correspondencePart.getPostalAddress();

        for (PostalAddress postalAddress : postalAddresss) {
            NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(postalAddress);
            NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(postalAddress);
        }

        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(correspondencePart);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(correspondencePart);
        // bidirectional relationship @ManyToMany, set both sides of relationship
        registryEntry.getReferenceCorrespondencePart().add(correspondencePart);
        correspondencePart.getReferenceRegistryEntry().add(registryEntry);
        return (CorrespondencePart) correspondencePartService.createNewCorrespondencePart(correspondencePart);
    }

    @Override
    public DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
            String recordSystemId, DocumentDescription documentDescription){
        RegistryEntry registryEntry = getRegistryEntryOrThrow(recordSystemId);
        HashSet <Record> records = (HashSet <Record>) documentDescription.getReferenceRecord();

        // It should always be instaniated ... check this ...
        if (records == null) {
            records = new HashSet<>();
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
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }

    // systemId
    public RegistryEntry findBySystemId(String systemId) {
        return registryEntryRepository.findBySystemId(systemId);
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

    @Override
    public Precedence createPrecedenceAssociatedWithRecord(String registryEntrySystemID, Precedence precedence) {

        RegistryEntry registryEntry = getRegistryEntryOrThrow(registryEntrySystemID);
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(precedence);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(precedence);
        // bidirectional relationship @ManyToMany, set both sides of relationship
        registryEntry.getReferencePrecedence().add(precedence);
        precedence.getReferenceRegistryEntry().add(registryEntry);
        return (Precedence) precedenceService.createNewPrecedence(precedence);

    }
}
