package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
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

	// systemId
	File findBySystemId(String systemId);

	// title
	List<File> findByTitleAndOwnedBy(String title, String ownedBy);
	List<File> findByTitleContainingAndOwnedBy(String title, String ownedBy);
	List<File> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy);
	List<File> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort);
	List<File> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort);
	List<File> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort);

	Page<File> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable);
	Page<File> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);
	Page<File> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);

	// description
	List<File> findByDescriptionAndOwnedBy(String description, String ownedBy);
	List<File> findByDescriptionContainingAndOwnedBy(String description, String ownedBy);
	List<File> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy);
	List<File> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort);
	List<File> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort);
	List<File> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort);

	Page<File> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable);
	Page<File> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);
	Page<File> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);

	// documentMedium
	List<File> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy);
	List<File> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Sort sort);
	Page<File> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Pageable pageable);

	// createdDate
	List<File> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
	List<File> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
	List<File> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<File> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
	Page<File> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// createdBy
	List<File> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
	List<File> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
	List<File> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
	List<File> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<File> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<File> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

	Page<File> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<File> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<File> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);


	// finalisedDate
	List<File> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy);
	List<File> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort);
	List<File> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<File> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable);
	Page<File> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// finalisedBy
	List<File> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy);
	List<File> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy);
	List<File> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy);
	List<File> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
	List<File> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
	List<File> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

	Page<File> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
	Page<File> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
	Page<File> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

	// deleted
	List<File> findByDeletedAndOwnedBy(String deleted, String ownedBy);
	List<File> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
	Page<File> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

	// ownedBy
	List<File> findByOwnedBy(String ownedBy);
	List<File> findByOwnedBy(String ownedBy, Sort sort);
	Page<File> findByOwnedBy(String ownedBy, Pageable pageable);

}
