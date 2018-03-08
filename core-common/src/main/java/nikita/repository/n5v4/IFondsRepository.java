package nikita.repository.n5v4;

import nikita.model.noark5.v4.Fonds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFondsRepository extends PagingAndSortingRepository<Fonds, Long> {

    // -- All SAVE operations
    @Override
    Fonds save(Fonds fonds);

    // -- All READ operations
    @Override
    List<Fonds> findAll();

    @Override
    List<Fonds> findAll(Sort sort);

    @Override
    Page<Fonds> findAll(Pageable pageable);

    // id
    Fonds findById(Long id);

    // systemId
    Fonds findBySystemId(String systemId);

    // ownedBy
    List<Fonds> findByOwnedBy(String ownedBy);
    List<Fonds> findByOwnedBy(String ownedBy, Sort sort);
    Page<Fonds> findByOwnedBy(String ownedBy, Pageable pageable);

    // All DELETE operation
    void delete(Fonds fonds);
}
