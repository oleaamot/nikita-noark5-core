package nikita.webapp.service.interfaces;


import nikita.common.model.noark5.v4.DocumentObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface IDocumentObjectService {

    // -- File handling operations
    void init();

    void storeAndCalculateChecksum(InputStream inputStream, DocumentObject documentObject);

    Path load(String filename);

    Resource loadAsResource(DocumentObject documentObject);

	// -- All CREATE operations

	DocumentObject save(DocumentObject documentObject);

    // -- ALL UPDATE operations
    DocumentObject update(DocumentObject documentObject);

	// -- All READ operations

	List<DocumentObject> findDocumentObjectByAnyColumn(String column, String value);

    List<DocumentObject> findDocumentObjectByOwnerPaginated(Integer top, Integer skip);

    List<DocumentObject> findAll();

    List<DocumentObject> findAll(Sort sort);
	Page<DocumentObject> findAll(Pageable pageable);

	// id
	DocumentObject findById(Long id);

	// systemId
    DocumentObject findBySystemId(String systemId);

	// ownedBy
	List<DocumentObject> findByOwnedBy(String ownedBy);
	List<DocumentObject> findByOwnedBy(String ownedBy, Sort sort);
	Page<DocumentObject> findByOwnedBy(String ownedBy, Pageable pageable);

	// All UPDATE operations
	DocumentObject handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull DocumentObject documentObject);

	// All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
