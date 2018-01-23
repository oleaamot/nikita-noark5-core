package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IFileService {


	// -- All CREATE operations
    File createFile(File file);
	Record createRecordAssociatedWithFile(String fileSystemId, Record record);
	BasicRecord createBasicRecordAssociatedWithFile(String fileSystemId, BasicRecord basicRecord);

    List<File> findFileByOwnerPaginated(Integer top, Integer skip);

	// -- All READ operations
    List<File> findAll();

    List<File> findAll(Sort sort);
	Page<File> findAll(Pageable pageable);

	// id
	File findById(Long id);

	// ownedBy
	List<File> findByOwnedBy(String ownedBy);
	List<File> findByOwnedBy(String ownedBy, Sort sort);
	Page<File> findByOwnedBy(String ownedBy, Pageable pageable);

	// All UPDATE operations
	File handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull File file);

	// All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
