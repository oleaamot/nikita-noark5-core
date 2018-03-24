package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.Series;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISeriesRepository extends PagingAndSortingRepository<Series, Long> {

    // -- All SAVE operations
    @Override
    Series save(Series series);

    // -- All READ operations
    @Override
    List<Series> findAll();

    // id
    Optional<Series> findById(Long id);

    // systemId
    Series findBySystemId(String systemId);

    // ownedBy
    List<Series> findByOwnedBy(String ownedBy);
}
