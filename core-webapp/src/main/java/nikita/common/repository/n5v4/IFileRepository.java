package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFileRepository extends PagingAndSortingRepository<File, Long> {

    // -- All SAVE operations
    @Override
    File save(File file);

    // -- All READ operations
    @Override
    List<File> findAll();

    @Override
    List<File> findAll(Sort sort);

    @Override
    Page<File> findAll(Pageable pageable);

    // id
    File findById(Long id);

    // systemId
    File findBySystemId(String systemId);

    // ownedBy
    List<File> findByOwnedBy(String ownedBy);

    List<File> findByOwnedBy(String ownedBy, Sort sort);

    Page<File> findByOwnedBy(String ownedBy, Pageable pageable);
}
