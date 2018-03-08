package nikita.repository.n5v4;

import nikita.model.noark5.v4.ClassificationSystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClassificationSystemRepository extends PagingAndSortingRepository<ClassificationSystem, Long> {

    // -- All SAVE operations
    @Override
    ClassificationSystem save(ClassificationSystem classificationSystem);

    // -- All READ operations
    @Override
    List<ClassificationSystem> findAll();

    @Override
    List<ClassificationSystem> findAll(Sort sort);

    @Override
    Page<ClassificationSystem> findAll(Pageable pageable);

    // id
    ClassificationSystem findById(Long id);

    // systemId
    ClassificationSystem findBySystemId(String systemId);

    // ownedBy
    List<ClassificationSystem> findByOwnedBy(String ownedBy);
    List<ClassificationSystem> findByOwnedBy(String ownedBy, Sort sort);
    Page<ClassificationSystem> findByOwnedBy(String ownedBy, Pageable pageable);
}
