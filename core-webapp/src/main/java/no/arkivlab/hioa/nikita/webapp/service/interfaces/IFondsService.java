package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface IFondsService  {

    Fonds handleUpdate(String systemId, Long version, Fonds incomingFonds);

    // -- All CREATE operations
    Fonds createNewFonds(Fonds fonds);
    Series createSeriesAssociatedWithFonds(String fondsSystemId, Series series);
    Fonds createFondsAssociatedWithFonds(String parentFondsSystemId, Fonds childFonds);

    FondsCreator createFondsCreatorAssociatedWithFonds(String fondsSystemId, FondsCreator fondsCreator);

    List<Fonds> findFondsByOwnerPaginated(Integer top, Integer skip);

    // -- All READ operations

    List<Fonds> findAll();
    List<Fonds> findAll(Sort sort);
    Page<Fonds> findAll(Pageable pageable);

    // id
    Fonds findById(Long id);

    // systemId
    Fonds findBySystemId(String systemId);

    // title
    List<Fonds> findByTitleAndOwnedBy(String title, String ownedBy);
    List<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy);
    List<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy);
    List<Fonds> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort);
    List<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort);
    List<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort);

    Page<Fonds> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable);
    Page<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);
    Page<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);

    // description
    List<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy);
    List<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy);
    List<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy);
    List<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort);
    List<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort);
    List<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort);

    Page<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable);
    Page<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);
    Page<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);

    // fondStatus
    List<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy);
    List<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy, Sort sort);
    Page<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy, Pageable pageable);

    // documentMedium
    List<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy);
    List<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Sort sort);
    Page<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Pageable pageable);

    // createdDate
    List<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
    List<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
    List<Fonds> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

    Page<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
    Page<Fonds> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

    // createdBy
    List<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
    List<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
    List<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
    List<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
    List<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
    List<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

    Page<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    Page<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    Page<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);


    // finalisedDate
    List<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy);
    List<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort);
    List<Fonds> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

    Page<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable);
    Page<Fonds> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

    // finalisedBy
    List<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy);
    List<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy);
    List<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy);
    List<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
    List<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
    List<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

    Page<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
    Page<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
    Page<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

    // deleted
    List<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy);
    List<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
    Page<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

    // ownedBy
    List<Fonds> findByOwnedBy(String ownedBy);
    List<Fonds> findByOwnedBy(String ownedBy, Sort sort);
    Page<Fonds> findByOwnedBy(String ownedBy, Pageable pageable);

    // -- All UPDATE operations
    Fonds updateFonds(Fonds fonds);
}
