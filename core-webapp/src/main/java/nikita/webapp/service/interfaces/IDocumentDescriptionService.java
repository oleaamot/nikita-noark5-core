package nikita.webapp.service.interfaces;


import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.DocumentObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IDocumentDescriptionService {


	// -- All CREATE operations

	DocumentDescription save(DocumentDescription documentDescription);
	DocumentObject createDocumentObjectAssociatedWithDocumentDescription(String documentDescriptionSystemId,
														  DocumentObject documentObject);
	// -- All READ operations

	List<DocumentDescription> findDocumentDescriptionByOwnerPaginated(Integer top, Integer skip);

	List<DocumentDescription> findAll();
	List<DocumentDescription> findAll(Sort sort);
	Page<DocumentDescription> findAll(Pageable pageable);

	// id
	DocumentDescription findById(Long id);

	// systemId
	DocumentDescription findBySystemId(String systemId);

	// ownedBy
	List<DocumentDescription> findByOwnedBy(String ownedBy);
	List<DocumentDescription> findByOwnedBy(String ownedBy, Sort sort);

    List<DocumentDescription> findByOwnedBy(String ownedBy, Pageable pageable);
	
	// All UPDATE operations
	DocumentDescription handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull DocumentDescription documentDescription);

	// All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
