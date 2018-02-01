package nikita.repository.n5v4;

import nikita.model.noark5.v4.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClassRepository extends PagingAndSortingRepository<Class, Long> {

    // -- All SAVE operations
    @Override
    Class save(Class klass);

    // -- All READ operations
    @Override
    List<Class> findAll();

    @Override
    List<Class> findAll(Sort sort);

    @Override
    Page<Class> findAll(Pageable pageable);

    // id
    Class findById(Long id);


    // systemId
    Class findBySystemId(String systemId);

    // ownedBy
    List<Class> findByOwnedBy(String ownedBy);
    List<Class> findByOwnedBy(String ownedBy, Sort sort);
    Page<Class> findByOwnedBy(String ownedBy, Pageable pageable);
}
