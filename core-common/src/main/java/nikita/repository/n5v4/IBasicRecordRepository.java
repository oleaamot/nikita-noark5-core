package nikita.repository.n5v4;

import nikita.model.noark5.v4.BasicRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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

    // ownedBy
    //List<BasicRecord> findByOwnedBy();
    List<BasicRecord> findByOwnedBy(String ownedBy);
    List<BasicRecord> findByOwnedBy(String ownedBy, Sort sort);
    Page<BasicRecord> findByOwnedBy(String ownedBy, Pageable pageable);
}
