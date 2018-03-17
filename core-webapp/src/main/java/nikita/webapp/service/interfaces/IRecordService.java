package nikita.webapp.service.interfaces;


import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IRecordService {


	// -- All CREATE operations

	Record save(Record record);

    DocumentDescription createDocumentDescriptionAssociatedWithRecord(String systemID,
                                                                      DocumentDescription documentDescription);

    List<Record> findRecordByOwnerPaginated(Integer top, Integer skip);

	// -- All READ operations

    List<Record> findAll();

    List<Record> findAll(Sort sort);
	Page<Record> findAll(Pageable pageable);

	// id
	Record findById(Long id);

	// systemId
    Record findBySystemId(String systemId);


	// ownedBy
	List<Record> findByOwnedBy(String ownedBy);
	List<Record> findByOwnedBy(String ownedBy, Sort sort);
	Page<Record> findByOwnedBy(String ownedBy, Pageable pageable);

	// All UPDATE operations
	Record handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Record record);

	// All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
