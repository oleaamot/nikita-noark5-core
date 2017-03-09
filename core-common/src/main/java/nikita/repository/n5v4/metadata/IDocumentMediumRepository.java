package nikita.repository.n5v4.metadata;

import nikita.model.noark5.v4.metadata.DocumentMedium;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDocumentMediumRepository extends CrudRepository<DocumentMedium, Long> {

    // -- All SAVE operations
    @Override
    DocumentMedium save(DocumentMedium documentMedium);

    // -- All READ operations
    @Override
    Iterable<DocumentMedium> findAll();

    DocumentMedium findBySystemId(String systemId);

    List<DocumentMedium> findByDescription(String description);

    List<DocumentMedium> findByCode(String code);
}
