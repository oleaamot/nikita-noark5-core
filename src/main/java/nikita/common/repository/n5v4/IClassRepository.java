package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.Class;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClassRepository
        extends PagingAndSortingRepository<Class, Long> {

    // -- All SAVE operations
    @Override
    Class save(Class klass);

    // -- All READ operations
    @Override
    List<Class> findAll();

    // id
    Optional<Class> findById(Long id);

    // systemId
    Class findBySystemId(String systemId);

    // ownedBy
    List<Class> findByOwnedBy(String ownedBy);
}
