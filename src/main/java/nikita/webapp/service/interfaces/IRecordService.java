package nikita.webapp.service.interfaces;


import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.Record;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IRecordService {

	// -- All CREATE operations
	Record save(Record record);

    DocumentDescription
    createDocumentDescriptionAssociatedWithRecord(String systemID,
                                                  DocumentDescription
                                                          documentDescription);
	// -- All READ operations
    List<Record> findAll();

    Optional<Record> findById(Long id);
    Record findBySystemId(String systemId);
	List<Record> findByOwnedBy(String ownedBy);

    // -- All UPDATE operations
	Record handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Record record);

    // -- All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
