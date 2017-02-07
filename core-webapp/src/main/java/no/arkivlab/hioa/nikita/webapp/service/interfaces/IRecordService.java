package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface IRecordService {


	// -- All CREATE operations

	Record save(Record record);
	DocumentDescription createDocumentDescriptionAssociatedWithRecord(String recordSystemId,
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

	// createdDate
	List<Record> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
	List<Record> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
	List<Record> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<Record> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
	Page<Record> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// createdBy
	List<Record> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
	List<Record> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
	List<Record> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
	List<Record> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<Record> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<Record> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

	Page<Record> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<Record> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<Record> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);


	// finalisedDate
	List<Record> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy);
	List<Record> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort);
	List<Record> findByArchivedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<Record> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable);
	Page<Record> findByArchivedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// finalisedBy
	List<Record> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy);
	List<Record> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy);
	List<Record> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy);
	List<Record> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
	List<Record> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
	List<Record> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

	Page<Record> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
	Page<Record> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
	Page<Record> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

	// deleted
	List<Record> findByDeletedAndOwnedBy(String deleted, String ownedBy);
	List<Record> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
	Page<Record> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

	// ownedBy
	List<Record> findByOwnedBy(String ownedBy);
	List<Record> findByOwnedBy(String ownedBy, Sort sort);
	Page<Record> findByOwnedBy(String ownedBy, Pageable pageable);

}