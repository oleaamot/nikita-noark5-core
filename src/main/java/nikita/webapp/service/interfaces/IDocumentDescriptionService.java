package nikita.webapp.service.interfaces;

import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.DocumentObject;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IDocumentDescriptionService {

    // -- All CREATE operations
    DocumentDescription save(DocumentDescription documentDescription);

    DocumentObject
    createDocumentObjectAssociatedWithDocumentDescription(
            String documentDescriptionSystemId,
            DocumentObject documentObject);

    // -- All READ operations
    List<DocumentDescription> findAll();

    Optional<DocumentDescription> findById(Long id);

    DocumentDescription findBySystemId(String systemId);

    List<DocumentDescription> findByOwnedBy(String ownedBy);

    // -- All UPDATE operations
    DocumentDescription handleUpdate(@NotNull String systemId,
                                     @NotNull Long version,
                                     @NotNull DocumentDescription
                                             documentDescription);

    // -- All DELETE operations
    void deleteEntity(@NotNull String systemId);
}
