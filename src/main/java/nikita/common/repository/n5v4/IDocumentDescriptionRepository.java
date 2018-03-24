package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.DocumentDescription;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDocumentDescriptionRepository extends
        PagingAndSortingRepository<DocumentDescription, Long> {

    // -- All SAVE operations
    @Override
    DocumentDescription save(DocumentDescription documentDescription);

    // -- All READ operations
    @Override
    List<DocumentDescription> findAll();

    // id
    Optional<DocumentDescription> findById(Long id);

    // systemId
    DocumentDescription findBySystemId(String systemId);

    // ownedBy
    List<DocumentDescription> findByOwnedBy(String ownedBy);
}
