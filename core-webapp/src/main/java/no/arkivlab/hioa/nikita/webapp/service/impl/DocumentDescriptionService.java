package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.repository.n5v4.IDocumentDescriptionRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentDescriptionService;
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

@Service
@Transactional
public class DocumentDescriptionService implements IDocumentDescriptionService {

    @Autowired
    IDocumentDescriptionRepository documentDescriptionRepository;

    public DocumentDescriptionService() {
    }

    // All CREATE operations
    public DocumentDescription save(DocumentDescription documentDescription){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();


        documentDescription.setSystemId(UUID.randomUUID().toString());
        documentDescription.setCreatedDate(new Date());
        documentDescription.setOwnedBy(username);
        documentDescription.setCreatedBy(username);
        documentDescription.setDeleted(false);

        // Have to handle referenceToFonds. If it is not set do not allow persisit
        // throw illegalstructure exception

        // How do handle referenceToPrecusor? Update the entire object?? No patch?

        return documentDescriptionRepository.save(documentDescription);
    }

    // All READ operations
    public Iterable<DocumentDescription> findAll() {
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
        return documentDescriptionRepository.findBySystemId(systemId);
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


    // All DELETE operations


}
