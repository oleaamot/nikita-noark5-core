package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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

    // ownedBy
    List<Series> findByOwnedBy(String ownedBy);

    List<Series> findByOwnedBy(String ownedBy, Sort sort);

    Page<Series> findByOwnedBy(String ownedBy, Pageable pageable);
}
