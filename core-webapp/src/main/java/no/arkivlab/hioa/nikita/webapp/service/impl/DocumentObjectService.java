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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static nikita.config.N5ResourceMappings.STATUS_OPEN;

@Service
@Transactional
public class DocumentObjectService implements IDocumentObjectService {

    @Autowired
    IDocumentObjectRepository documentObjectRepository;

    public DocumentObjectService() {
    }

    // All CREATE operations


    public DocumentObject save(DocumentObject documentObject){
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setCreateEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(documentObject);
        NoarkUtils.NoarkEntity.Create.setFinaliseEntityValues(documentObject);
        return documentObjectRepository.save(documentObject);
    }

    // All READ operations
    public Iterable<DocumentObject> findAll() {
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

    // All DELETE operations
}
