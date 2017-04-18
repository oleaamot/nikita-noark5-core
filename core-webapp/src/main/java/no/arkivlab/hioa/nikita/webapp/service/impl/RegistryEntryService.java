package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.repository.n5v4.IRegistryEntryRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
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

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class RegistryEntryService implements IRegistryEntryService {

    private static final Logger logger = LoggerFactory.getLogger(RegistryEntryService.class);
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);
    private DocumentDescriptionService documentDescriptionService;
    private IRegistryEntryRepository registryEntryRepository;
    private EntityManager entityManager;

    public RegistryEntryService(DocumentDescriptionService documentDescriptionService,
                                IRegistryEntryRepository registryEntryRepository,
                                EntityManager entityManager) {
        this.documentDescriptionService = documentDescriptionService;
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
    public DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
            String recordSystemId, DocumentDescription documentDescription){

        DocumentDescription persistedDocumentDescription = null;
        RegistryEntry registryEntry = registryEntryRepository.findBySystemId(recordSystemId);
        if (registryEntry == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " RegistryEntry, using registryEntrySystemId " + recordSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            HashSet <Record> records = (HashSet <Record>) documentDescription.getReferenceRecord();

            if (records == null) {
                records = new HashSet<>();
                documentDescription.setReferenceRecord(records);
            }
            records.add(registryEntry);
            persistedDocumentDescription = documentDescriptionService.save(documentDescription);
        }
        return persistedDocumentDescription;
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
}
