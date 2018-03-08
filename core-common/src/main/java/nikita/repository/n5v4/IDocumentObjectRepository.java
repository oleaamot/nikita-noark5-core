package nikita.repository.n5v4;

import nikita.model.noark5.v4.DocumentObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDocumentObjectRepository extends PagingAndSortingRepository<DocumentObject, Long> {

    // -- All SAVE operations
    @Override
    DocumentObject save(DocumentObject documentObject);

    // -- All READ operations
    @Override
    List<DocumentObject> findAll();

    @Override
    List<DocumentObject> findAll(Sort sort);

    @Override
    Page<DocumentObject> findAll(Pageable pageable);

    // id
    DocumentObject findById(Long id);

    // systemId
    DocumentObject findBySystemId(String systemId);
    // ownedBy
    List<DocumentObject> findByOwnedBy(String ownedBy);
    List<DocumentObject> findByOwnedBy(String ownedBy, Sort sort);
    Page<DocumentObject> findByOwnedBy(String ownedBy, Pageable pageable);
}
