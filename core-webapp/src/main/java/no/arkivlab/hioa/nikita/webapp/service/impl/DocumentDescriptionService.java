package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.DocumentObject;
import nikita.repository.n5v4.IDocumentDescriptionRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentDescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class DocumentDescriptionService implements IDocumentDescriptionService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    private DocumentObjectService documentObjectService;
    private IDocumentDescriptionRepository documentDescriptionRepository;
    private EntityManager entityManager;
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    private Integer maxPageSize = new Integer(10);

    public DocumentDescriptionService(DocumentObjectService documentObjectService,
                                      IDocumentDescriptionRepository documentDescriptionRepository,
                                      EntityManager entityManager) {
        this.documentObjectService = documentObjectService;
        this.documentDescriptionRepository = documentDescriptionRepository;
        this.entityManager = entityManager;
    }

    // All CREATE operations
    @Override
    public DocumentObject createDocumentObjectAssociatedWithDocumentDescription(String documentDescriptionSystemId, DocumentObject documentObject) {
        DocumentObject persistedDocumentObject = null;
        DocumentDescription documentDescription = documentDescriptionRepository.findBySystemId(documentDescriptionSystemId);
        if (documentDescription == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " DocumentDescription, using documentDescriptionSystemId " + documentDescriptionSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            documentObject.setReferenceDocumentDescription(documentDescription);
            Set <DocumentObject> documentObjects = documentDescription.getReferenceDocumentObject();
            documentObjects.add(documentObject);
            persistedDocumentObject = documentObjectService.save(documentObject);
        }
        return persistedDocumentObject;
    }

    public DocumentDescription save(DocumentDescription documentDescription){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        documentDescription.setSystemId(UUID.randomUUID().toString());
        documentDescription.setCreatedDate(new Date());
        documentDescription.setOwnedBy(username);
        documentDescription.setCreatedBy(username);
        documentDescription.setDeleted(false);
        return documentDescriptionRepository.save(documentDescription);
    }

    // All READ operations
    public List<DocumentDescription> findAll() {
        return documentDescriptionRepository.findAll();
    }

    public List<DocumentDescription> findAll(Sort sort) {
        return documentDescriptionRepository.findAll(sort);
    }

    public Page<DocumentDescription> findAll(Pageable pageable) {
        return documentDescriptionRepository.findAll(pageable);
    }

    // id
    public DocumentDescription findById(Long id) {
        return documentDescriptionRepository.findById(id);
    }

    // systemId
    public DocumentDescription findBySystemId(String systemId) {
        return getDocumentDescriptionOrThrow(systemId);
    }

    // title
    public List<DocumentDescription> findByTitleAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleAndOwnedBy(title,  ownedBy);
    }

    public List<DocumentDescription> findByTitleContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleContainingAndOwnedBy(title, ownedBy);
    }

    public List<DocumentDescription> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy);
    }

    public List<DocumentDescription> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleAndOwnedBy(title, ownedBy, sort);
    }

    public List<DocumentDescription> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleContainingAndOwnedBy(title, ownedBy, sort);
    }

    public List<DocumentDescription> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, sort);
    }

    public Page<DocumentDescription> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<DocumentDescription> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleContainingAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<DocumentDescription> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, pageable);
    }

    // description
    public List<DocumentDescription> findByDescriptionAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionAndOwnedBy(description, ownedBy);
    }

    public List<DocumentDescription> findByDescriptionContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy);
    }

    public List<DocumentDescription> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy);
    }

    public List<DocumentDescription> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionAndOwnedBy(description, ownedBy, sort);
    }

    public List<DocumentDescription> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, sort);
    }

    public List<DocumentDescription> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, sort);
    }

    public Page<DocumentDescription> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<DocumentDescription> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<DocumentDescription> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, pageable);
    }

    // documentMedium
    public List<DocumentDescription> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy);
    }

    public List<DocumentDescription> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy, sort);
    }

    public Page<DocumentDescription> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy, pageable);
    }

    // createdDate
    public List<DocumentDescription> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy);
    }

    public List<DocumentDescription> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, sort);
    }

    public List<DocumentDescription> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<DocumentDescription> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, pageable);
    }

    public Page<DocumentDescription> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // createdBy
    public List<DocumentDescription> findByCreatedByAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentDescription> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentDescription> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentDescription> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<DocumentDescription> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<DocumentDescription> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public Page<DocumentDescription> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<DocumentDescription> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<DocumentDescription> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }


    // deleted
    public List<DocumentDescription> findByDeletedAndOwnedBy(String deleted, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDeletedAndOwnedBy(deleted, ownedBy);
    }

    public List<DocumentDescription> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDeletedAndOwnedBy(deleted, ownedBy, sort);
    }

    public Page<DocumentDescription> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByDeletedAndOwnedBy(deleted, ownedBy, pageable);
    }

    // ownedBy
    public List<DocumentDescription> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentDescriptionRepository.findByOwnedBy(ownedBy);
    }

    public List<DocumentDescription> findByOwnedBy(String ownedBy, Sort sort) {return documentDescriptionRepository.findByOwnedBy(ownedBy, sort);}

    public Page<DocumentDescription> findByOwnedBy(String ownedBy, Pageable pageable) {return documentDescriptionRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public DocumentDescription update(DocumentDescription documentDescription){
        return documentDescriptionRepository.save(documentDescription);
    }

    public DocumentDescription updateDocumentDescriptionSetFinalized(Long id){
        DocumentDescription documentDescription = documentDescriptionRepository.findById(id);

        if (documentDescription == null) {
            // throw Object not find
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return documentDescriptionRepository.save(documentDescription);
    }

    public DocumentDescription updateDocumentDescriptionSetTitle(Long id, String newTitle){

        DocumentDescription documentDescription = documentDescriptionRepository.findById(id);

        return documentDescriptionRepository.save(documentDescription);
    }


    // All READ operations
    @Override
    public List<DocumentDescription> findDocumentDescriptionByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDescription> criteriaQuery = criteriaBuilder.createQuery(DocumentDescription.class);
        Root<DocumentDescription> from = criteriaQuery.from(DocumentDescription.class);
        CriteriaQuery<DocumentDescription> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<DocumentDescription> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }

    // All UPDATE operations
    @Override
    public DocumentDescription handleUpdate(@NotNull String systemId, @NotNull Long version,
                                            @NotNull DocumentDescription incomingDocumentDescription) {
        DocumentDescription existingDocumentDescription = getDocumentDescriptionOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        if (null != incomingDocumentDescription.getDescription()) {
            existingDocumentDescription.setDescription(incomingDocumentDescription.getDescription());
        }
        if (null != incomingDocumentDescription.getTitle()) {
            existingDocumentDescription.setTitle(incomingDocumentDescription.getTitle());
        }
        if (null != incomingDocumentDescription.getDocumentMedium()) {
            existingDocumentDescription.setDocumentMedium(existingDocumentDescription.getDocumentMedium());
        }
        if (null != incomingDocumentDescription.getAssociatedWithRecordAs()) {
            existingDocumentDescription.setAssociatedWithRecordAs(incomingDocumentDescription.getAssociatedWithRecordAs());
        }
        if (null != incomingDocumentDescription.getDocumentNumber()) {
            existingDocumentDescription.setDocumentNumber(incomingDocumentDescription.getDocumentNumber());
        }

        existingDocumentDescription.setVersion(version);
        documentDescriptionRepository.save(existingDocumentDescription);
        return existingDocumentDescription;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String documentDescriptionSystemId) {
        // See issue for a description of why this code was written this way
        // https://github.com/HiOA-ABI/nikita-noark5-core/issues/82
        DocumentDescription documentDescription = getDocumentDescriptionOrThrow(documentDescriptionSystemId);
        Query q = entityManager.createNativeQuery("DELETE FROM record_document_description WHERE F_PK_DOCUMENT_DESCRIPTION_ID  = :id ;");
        q.setParameter("id", documentDescription.getId());
        q.executeUpdate();

        entityManager.remove(documentDescription);
        entityManager.flush();
        entityManager.clear();
    }

    // All HELPER operations
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid DocumentDescription back. If there is no valid
     * DocumentDescription, an exception is thrown
     *
     * @param documentDescriptionSystemId
     * @return
     */
    protected DocumentDescription getDocumentDescriptionOrThrow(@NotNull String documentDescriptionSystemId) {
        DocumentDescription documentDescription = documentDescriptionRepository.findBySystemId(documentDescriptionSystemId);
        if (documentDescription == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " DocumentDescription, using systemId " + documentDescriptionSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return documentDescription;
    }
}
