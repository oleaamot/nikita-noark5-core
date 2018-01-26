package nikita.repository.n5v4;

import nikita.model.noark5.v4.BasicRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IBasicRecordRepository extends PagingAndSortingRepository<BasicRecord, Long> {


    // -- All SAVE operations
    @Override
    BasicRecord save(BasicRecord BasicRecord);

    // -- All READ operations
    @Override
    List<BasicRecord> findAll();

    @Override
    List<BasicRecord> findAll(Sort sort);

    @Override
    Page<BasicRecord> findAll(Pageable pageable);

    // id
    BasicRecord findById(Long id);

    // systemId
    BasicRecord findBySystemId(String systemId);

    // createdDate
    List<BasicRecord> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);

    List<BasicRecord> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);

    List<BasicRecord> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

    Page<BasicRecord> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);

    Page<BasicRecord> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

    // createdBy
    List<BasicRecord> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);

    List<BasicRecord> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);

    List<BasicRecord> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);

    List<BasicRecord> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);

    List<BasicRecord> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

    List<BasicRecord> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

    Page<BasicRecord> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);

    Page<BasicRecord> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);

    Page<BasicRecord> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);

    // finalisedDate
    List<BasicRecord> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy);

    List<BasicRecord> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort);

    List<BasicRecord> findByArchivedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

    Page<BasicRecord> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable);

    Page<BasicRecord> findByArchivedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

    // finalisedBy
    List<BasicRecord> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy);

    List<BasicRecord> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy);

    List<BasicRecord> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy);

    List<BasicRecord> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

    List<BasicRecord> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

    List<BasicRecord> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

    Page<BasicRecord> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

    Page<BasicRecord> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

    Page<BasicRecord> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

    // deleted
    List<BasicRecord> findByDeletedAndOwnedBy(String deleted, String ownedBy);

    List<BasicRecord> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);

    Page<BasicRecord> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

    // ownedBy
    //List<BasicRecord> findByOwnedBy();
    List<BasicRecord> findByOwnedBy(String ownedBy);

    List<BasicRecord> findByOwnedBy(String ownedBy, Sort sort);

    Page<BasicRecord> findByOwnedBy(String ownedBy, Pageable pageable);

}
