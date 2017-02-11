package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentObject;
import nikita.repository.n5v4.IDocumentObjectRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentObjectService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DocumentObjectService implements IDocumentObjectService {

    @Autowired
    IDocumentObjectRepository documentObjectRepository;

    @Autowired
    EntityManager entityManager;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);


    public DocumentObjectService() {
    }

    // All CREATE operations

    public DocumentObject save(DocumentObject documentObject){
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setCreateEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(documentObject);
        return documentObjectRepository.save(documentObject);
    }

    // All READ operations
    public List<DocumentObject> findAll() {
        return documentObjectRepository.findAll();
    }

    public List<DocumentObject> findAll(Sort sort) {
        return documentObjectRepository.findAll(sort);
    }

    public Page<DocumentObject> findAll(Pageable pageable) {
        return documentObjectRepository.findAll(pageable);
    }

    // id
    public DocumentObject findById(Long id) {
        return documentObjectRepository.findById(id);
    }

    // systemId
    public DocumentObject findBySystemId(String systemId) {
        return documentObjectRepository.findBySystemId(systemId);
    }

    // createdDate
    public List<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy);
    }

    public List<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, sort);
    }

    public List<DocumentObject> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, pageable);
    }

    public Page<DocumentObject> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // createdBy
    public List<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public Page<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    // deleted
    public List<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByDeletedAndOwnedBy(deleted, ownedBy);
    }

    public List<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByDeletedAndOwnedBy(deleted, ownedBy, sort);
    }

    public Page<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByDeletedAndOwnedBy(deleted, ownedBy, pageable);
    }

    // ownedBy
    public List<DocumentObject> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return documentObjectRepository.findByOwnedBy(ownedBy);
    }

    public List<DocumentObject> findByOwnedBy(String ownedBy, Sort sort) {return documentObjectRepository.findByOwnedBy(ownedBy, sort);}

    public Page<DocumentObject> findByOwnedBy(String ownedBy, Pageable pageable) {return documentObjectRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public DocumentObject update(DocumentObject documentObject){
        return documentObjectRepository.save(documentObject);
    }

    // All READ operations
    @Override
    public List<DocumentObject> findDocumentObjectByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentObject> criteriaQuery = criteriaBuilder.createQuery(DocumentObject.class);
        Root<DocumentObject> from = criteriaQuery.from(DocumentObject.class);
        CriteriaQuery<DocumentObject> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<DocumentObject> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }
}
