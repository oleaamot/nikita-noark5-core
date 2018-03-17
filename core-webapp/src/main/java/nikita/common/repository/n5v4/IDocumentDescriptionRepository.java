package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.DocumentDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDocumentDescriptionRepository extends PagingAndSortingRepository<DocumentDescription, Long> {

    // -- All SAVE operations
    @Override
    DocumentDescription save(DocumentDescription documentDescription);

    // -- All READ operations
    @Override
    List<DocumentDescription> findAll();

    @Override
    List<DocumentDescription> findAll(Sort sort);

    @Override
    Page<DocumentDescription> findAll(Pageable pageable);

    // id
    DocumentDescription findById(Long id);

    // systemId
    DocumentDescription findBySystemId(String systemId);

    // ownedBy
    List<DocumentDescription> findByOwnedBy(String ownedBy);

    List<DocumentDescription> findByOwnedBy(String ownedBy, Sort sort);

    List<DocumentDescription> findByOwnedBy(String ownedBy, Pageable pageable);
}
