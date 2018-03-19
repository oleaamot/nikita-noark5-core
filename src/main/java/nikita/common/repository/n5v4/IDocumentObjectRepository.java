package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.DocumentObject;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDocumentObjectRepository extends
        PagingAndSortingRepository<DocumentObject, Long> {

    // -- All SAVE operations
    @Override
    DocumentObject save(DocumentObject documentObject);

    // -- All READ operations
    @Override
    List<DocumentObject> findAll();

    // id
    Optional<DocumentObject> findById(Long id);

    // systemId
    DocumentObject findBySystemId(String systemId);

    // ownedBy
    List<DocumentObject> findByOwnedBy(String ownedBy);
}
