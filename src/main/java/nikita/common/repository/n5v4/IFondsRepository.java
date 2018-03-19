package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.Fonds;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFondsRepository extends
        PagingAndSortingRepository<Fonds, Long> {

    // -- All SAVE operations
    @Override
    Fonds save(Fonds fonds);

    // -- All READ operations
    @Override
    List<Fonds> findAll();

    Optional<Fonds> findById(Long id);
    Fonds findBySystemId(String systemId);
    List<Fonds> findByOwnedBy(String ownedBy);
}
