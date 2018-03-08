package nikita.repository.n5v4;

import nikita.model.noark5.v4.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecordRepository extends PagingAndSortingRepository<Record, Long> {

    // -- All SAVE operations
    @Override
    Record save(Record record);

    // -- All READ operations
    @Override
    List<Record> findAll();

    @Override
    List<Record> findAll(Sort sort);

    @Override
    Page<Record> findAll(Pageable pageable);

    // id
    Record findById(Long id);

    // systemId
    Record findBySystemId(String systemId);

    // ownedBy
    List<Record> findByOwnedBy(String ownedBy);
    List<Record> findByOwnedBy(String ownedBy, Sort sort);
    Page<Record> findByOwnedBy(String ownedBy, Pageable pageable);
}
