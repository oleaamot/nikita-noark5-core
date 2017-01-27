package nikita.repository.n5v4;

import nikita.model.noark5.v4.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ISeriesRepository extends PagingAndSortingRepository<Series, Long> {


    // -- All SAVE operations
    @Override
    Series save(Series series);


    // -- All READ operations
    @Override
    List<Series> findAll();

    @Override
    List<Series> findAll(Sort sort);

    @Override
    Page<Series> findAll(Pageable pageable);

    // id
    Series findById(Long id);


    // systemId
    Series findBySystemId(String systemId);

    // title
    List<Series> findByTitleAndOwnedBy(String title, String ownedBy);
    List<Series> findByTitleContainingAndOwnedBy(String title, String ownedBy);
    List<Series> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy);
    List<Series> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort);
    List<Series> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort);
    List<Series> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort);

    Page<Series> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable);
    Page<Series> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);
    Page<Series> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);

    // description
    List<Series> findByDescriptionAndOwnedBy(String description, String ownedBy);
    List<Series> findByDescriptionContainingAndOwnedBy(String description, String ownedBy);
    List<Series> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy);
    List<Series> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort);
    List<Series> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort);
    List<Series> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort);

    Page<Series> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable);
    Page<Series> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);
    Page<Series> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);

    // fondStatus
    List<Series> findBySeriesStatusAndOwnedBy(String seriesStatus, String ownedBy);
    List<Series> findBySeriesStatusAndOwnedBy(String seriesStatus, String ownedBy, Sort sort);
    Page<Series> findBySeriesStatusAndOwnedBy(String seriesStatus, String ownedBy, Pageable pageable);

    // documentMedium
    List<Series> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy);
    List<Series> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Sort sort);
    Page<Series> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Pageable pageable);

    // createdDate
    List<Series> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
    List<Series> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
    List<Series> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

    Page<Series> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
    Page<Series> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

    // createdBy
    List<Series> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
    List<Series> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
    List<Series> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
    List<Series> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
    List<Series> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
    List<Series> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

    Page<Series> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    Page<Series> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    Page<Series> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
    
    // finalisedDate
    List<Series> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy);
    List<Series> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort);
    List<Series> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

    Page<Series> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable);
    Page<Series> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

    // finalisedBy
    List<Series> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy);
    List<Series> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy);
    List<Series> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy);
    List<Series> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
    List<Series> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
    List<Series> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

    Page<Series> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
    Page<Series> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
    Page<Series> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

    // deleted
    List<Series> findByDeletedAndOwnedBy(String deleted, String ownedBy);
    List<Series> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
    Page<Series> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

    // ownedBy
    //List<Series> findByOwnedBy();
    List<Series> findByOwnedBy(String ownedBy);
    List<Series> findByOwnedBy(String ownedBy, Sort sort);
    Page<Series> findByOwnedBy(String ownedBy, Pageable pageable);

}
