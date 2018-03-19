package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.BasicRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBasicRecordRepository extends
        PagingAndSortingRepository<BasicRecord, Long> {

    // -- All SAVE operations
    @Override
    BasicRecord save(BasicRecord BasicRecord);

    // -- All READ operations
    @Override
    List<BasicRecord> findAll();

    // id
    Optional<BasicRecord> findById(Long id);

    // systemId
    BasicRecord findBySystemId(String systemId);

    // ownedBy
    List<BasicRecord> findByOwnedBy(String ownedBy);
}
