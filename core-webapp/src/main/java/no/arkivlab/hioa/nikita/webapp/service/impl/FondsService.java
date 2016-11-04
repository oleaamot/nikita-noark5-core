package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.Fonds;
import nikita.repository.n5v4.IFondsRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import no.arkivlab.hioa.nikita.webapp.util.validation.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static nikita.config.N5ResourceMappings.*;

/**
 * Note with regard to update the following should be noted:
 * Normally one should use the save method for both create and update (CRUD Repository)
 * but seeing as create has some additional processing, e.g generateUUID, set createdBy etc
 * I think it's more logical to separate the update and save methods
 *
 * We are using String ownedBy to identify the user that owns a record so when a user logs
 * in we can easily pick out their records. A record could be created by another user and
 * assigned to another user. Currently this is implementable by changing ownedBy.
 *
 * But another user (records manager) may have to search for other records. That's why if
 * ownedBy is null, we use the logged in username.
 *
 * TODO: Also include  String ownedBy for unique identifiers so that a user cannot acces records for others
 */

@Service
@Transactional
public class FondsService implements IFondsService {

    @Autowired
    IFondsRepository fondsRepository;



    /**
     *
     * @param fonds
     *
     * Takes in a fonds object and will try to persist it to the database.
     *
     * The following types of situations are dealt with : <br>
     *     - A new fonds object with no children
     *     - A new fonds object where parent fonds object is identified
     *
     * If the client tries to create a fonds object that already has as series
     * object associated with it, the fonds object will not be persisted and a
     * IllegalStructureAttempt exception will be thrown.
     *
     * If required fields (title) are null a IncompleteObject is thrown back to
     * the Controller. Other fields will have values set automatically. These are
     * systemId (UUID), createdDate and createdBy. If documentMedium is not set
     * it is set to "Elektronisk arkiv", or if a non-standard value is used a
     * IncompleteObject exception is thrown. fondStatus is set to "Opprettet"
     * upon creation.
     *
     * @return The newly persisted fonds object
     */




    // All CREATE operations

    public Fonds saveWithOwner(Fonds fonds, String owner){

        if (Utils.checkDocumentMediumValid(fonds.getDocumentMedium())) {
            fonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);
        }
        else {
            // throw an error! Something is wrong. Either null or incorrect value
        }

        fonds.setSystemId(UUID.randomUUID().toString());
        fonds.setCreatedDate(new Date());
        fonds.setOwnedBy(owner);
        fonds.setCreatedBy(owner);
        fonds.setDeleted(false);

        // Can I get sent a fonds object via JSON that actually has children
        // that are not associated. Yes, you can if the JSON request is hand crafted
        // so you probably will want to double check it anyway. Never trust the client!!!

        // remember, a lot of times != null is not correct as the set <> objects are set and
        // have a size equal to 0. However, handcrafted JSON request may have them set to null
        // so you have to handle both != null && size == 0

        // Is date in JSON meant to be text readable or timestamp. Probably test so you will need a convertor
        if (fonds.getReferenceParentFonds() != null) {
            // check that parent exists
            Long parentId = 1L;

            // fonds.getReferenceParentFonds()
            Fonds parentFonds = fonds.getReferenceParentFonds();

            // check that no series are associated with the parent fonds

            // check that referenceSeries != null && referenceSeries.size == 0
            //Series series = seriesRepository.findBy
        }

        return fondsRepository.save(fonds);

    }

    public Fonds save(Fonds fonds){
        SecurityContext securityContext  = SecurityContextHolder.getContext();
        Authentication authentication = securityContext .getAuthentication();

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();

        if (Utils.checkDocumentMediumValid(fonds.getDocumentMedium())) {
            fonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);
        }
        else {
                // throw an error! Something is wrong. Either null or incorrect value
        }

        fonds.setSystemId(UUID.randomUUID().toString());
        fonds.setCreatedDate(new Date());
        fonds.setOwnedBy(username);
        fonds.setCreatedBy(username);
        fonds.setDeleted(false);

        // Can I get sent a fonds object via JSON that actually has children
        // that are not associated. Yes, you can if the JSON request is hand crafted
        // so you probably will want to double check it anyway. Never trust the client!!!

        // remember, a lot of times != null is not correct as the set <> objects are set and
        // have a size equal to 0. However, handcrafted JSON request may have them set to null
        // so you have to handle both != null && size == 0

        // Is date in JSON meant to be text readable or timestamp. Probably test so you will need a convertor
        if (fonds.getReferenceParentFonds() != null) {
            // check that parent exists
            Long parentId = 1L;

            // fonds.getReferenceParentFonds()
            Fonds parentFonds = fonds.getReferenceParentFonds();

            // check that no series are associated with the parent fonds

            // check that referenceSeries != null && referenceSeries.size == 0
            //Series series = seriesRepository.findBy
        }

        return fondsRepository.save(fonds);
    }

    // All READ operations
    public Iterable<Fonds> findAll() {

        return fondsRepository.findAll();
    }

    public List<Fonds> findAll(Sort sort) {
        return fondsRepository.findAll(sort);
    }

    public Page<Fonds> findAll(Pageable pageable) {
        return fondsRepository.findAll(pageable);
    }

    // id
    public Fonds findById(Long id) {
        return fondsRepository.findById(id);
    }

    // systemId
    public Fonds findBySystemId(String systemId) {
        return fondsRepository.findBySystemId(systemId);
    }

    // title
    public List<Fonds> findByTitleAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleAndOwnedBy(title,  ownedBy);
    }

    public List<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleContainingAndOwnedBy(title, ownedBy);
    }

    public List<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy);
    }

    public List<Fonds> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleAndOwnedBy(title, ownedBy, sort);
    }

    public List<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleContainingAndOwnedBy(title, ownedBy, sort);
    }

    public List<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, sort);
    }

    public Page<Fonds> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleContainingAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, pageable);
    }

    // description
    public List<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionAndOwnedBy(description, ownedBy);
    }

    public List<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy);
    }

    public List<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy);
    }

    public List<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionAndOwnedBy(description, ownedBy, sort);
    }

    public List<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, sort);
    }

    public List<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, sort);
    }

    public Page<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, pageable);
    }

    // fondStatus
    public List<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFondsStatusAndOwnedBy(fondsStatus, ownedBy);
    }

    public List<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFondsStatusAndOwnedBy(fondsStatus, ownedBy, sort);
    }

    public Page<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFondsStatusAndOwnedBy(fondsStatus, ownedBy, pageable);
    }

    // documentMedium
    public List<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy);
    }

    public List<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy, sort);
    }

    public Page<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy, pageable);
    }

    // createdDate
    public List<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy);
    }

    public List<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, sort);
    }

    public List<Fonds> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, pageable);
    }

    public Page<Fonds> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // createdBy
    public List<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy);
    }

    public List<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public Page<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    // finalisedDate
    public List<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Fonds> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy, pageable);
    }

    public Page<Fonds> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // finalisedBy
    public List<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public Page<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    // deleted
    public List<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDeletedAndOwnedBy(deleted, ownedBy);
    }

    public List<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDeletedAndOwnedBy(deleted, ownedBy, sort);
    }

    public Page<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDeletedAndOwnedBy(deleted, ownedBy, pageable);
    }

    // ownedBy
    public List<Fonds> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByOwnedBy(ownedBy);
    }

    public List<Fonds> findByOwnedBy(String ownedBy, Sort sort) {return fondsRepository.findByOwnedBy(ownedBy, sort);}

    public Page<Fonds> findByOwnedBy(String ownedBy, Pageable pageable) {return fondsRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public Fonds update(Fonds fonds){
        if (fonds.getFondsStatus() == STATUS_CLOSED) {
            //throw an exception back
        }
        return fondsRepository.save(fonds);
    }

    public Fonds updateFondsSetFinalized(Long id){
        Fonds fonds = fondsRepository.findById(id);

        if (fonds == null) {
            // throw Object not find
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        fonds.setFondsStatus(STATUS_CLOSED);
        fonds.setFinalisedDate(new Date());
        fonds.setFinalisedBy(username);

        return fondsRepository.save(fonds);
    }

    public Fonds updateFondsSetTitle(Long id, String newTitle){

        Fonds fonds = fondsRepository.findById(id);

        if (fonds == null) {
            // throw Object not find
        } else if (fonds.getFondsStatus().equals(STATUS_CLOSED)) {
            // throw Object finalises, cannot be edited
        }
        fonds.setTitle(newTitle);
        return fondsRepository.save(fonds);
    }


    // All DELETE operations

}
