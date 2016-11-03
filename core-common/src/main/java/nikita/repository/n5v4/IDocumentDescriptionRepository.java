package nikita.repository.n5v4;

import nikita.model.noark5.v4.DocumentDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IDocumentDescriptionRepository extends PagingAndSortingRepository<DocumentDescription, Long> {


    // -- All SAVE operations
    @Override
    DocumentDescription save(DocumentDescription documentDescription);

    // -- All READ operations
    @Override
    List<DocumentDescription> findAll();

    @Override
    List<DocumentDescription> findAll(Sort sort);

    @Override
    Page<DocumentDescription> findAll(Pageable pageable);

    // id
    DocumentDescription findById(Long id);


    // systemId
    DocumentDescription findBySystemId(String systemId);

    // title
    List<DocumentDescription> findByTitleAndOwnedBy(String title, String ownedBy);
    List<DocumentDescription> findByTitleContainingAndOwnedBy(String title, String ownedBy);
    List<DocumentDescription> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy);
    List<DocumentDescription> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort);
    List<DocumentDescription> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort);
    List<DocumentDescription> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort);

    Page<DocumentDescription> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable);
    Page<DocumentDescription> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);
    Page<DocumentDescription> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);

    // description
    List<DocumentDescription> findByDescriptionAndOwnedBy(String description, String ownedBy);
    List<DocumentDescription> findByDescriptionContainingAndOwnedBy(String description, String ownedBy);
    List<DocumentDescription> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy);
    List<DocumentDescription> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort);
    List<DocumentDescription> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort);
    List<DocumentDescription> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort);

    Page<DocumentDescription> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable);
    Page<DocumentDescription> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);
    Page<DocumentDescription> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);

    // documentMedium
    List<DocumentDescription> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy);
    List<DocumentDescription> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Sort sort);
    Page<DocumentDescription> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Pageable pageable);

    // createdDate
    List<DocumentDescription> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
    List<DocumentDescription> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
    List<DocumentDescription> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

    Page<DocumentDescription> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
    Page<DocumentDescription> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

    // createdBy
    List<DocumentDescription> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
    List<DocumentDescription> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
    List<DocumentDescription> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
    List<DocumentDescription> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
    List<DocumentDescription> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
    List<DocumentDescription> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

    Page<DocumentDescription> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    Page<DocumentDescription> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    Page<DocumentDescription> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);

    // deleted
    List<DocumentDescription> findByDeletedAndOwnedBy(String deleted, String ownedBy);
    List<DocumentDescription> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
    Page<DocumentDescription> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

    // ownedBy
    //List<DocumentDescription> findByOwnedBy();
    List<DocumentDescription> findByOwnedBy(String ownedBy);
    List<DocumentDescription> findByOwnedBy(String ownedBy, Sort sort);
    Page<DocumentDescription> findByOwnedBy(String ownedBy, Pageable pageable);

}
