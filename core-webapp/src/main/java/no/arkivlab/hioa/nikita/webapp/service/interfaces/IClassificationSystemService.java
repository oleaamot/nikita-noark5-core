package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.ClassificationSystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface IClassificationSystemService {


	// -- All CREATE operations

	ClassificationSystem createNewClassificationSystem(ClassificationSystem classificationSystem);
	Class createClassAssociatedWithClassificationSystem(String classificationSystemSystemId, Class klass);

	// -- All READ operations
	List<ClassificationSystem> findClassificationSystemByOwnerPaginated(Integer top, Integer skip);

	List<ClassificationSystem> findAll();
	List<ClassificationSystem> findAll(Sort sort);
	Page<ClassificationSystem> findAll(Pageable pageable);

	// id
	ClassificationSystem findById(Long id);

	// systemId
	ClassificationSystem findBySystemId(String systemId);

	// title
	List<ClassificationSystem> findByTitleAndOwnedBy(String title, String ownedBy);
	List<ClassificationSystem> findByTitleContainingAndOwnedBy(String title, String ownedBy);
	List<ClassificationSystem> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy);
	List<ClassificationSystem> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort);
	List<ClassificationSystem> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort);
	List<ClassificationSystem> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort);

	Page<ClassificationSystem> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);

	// description
	List<ClassificationSystem> findByDescriptionAndOwnedBy(String description, String ownedBy);
	List<ClassificationSystem> findByDescriptionContainingAndOwnedBy(String description, String ownedBy);
	List<ClassificationSystem> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy);
	List<ClassificationSystem> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort);
	List<ClassificationSystem> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort);
	List<ClassificationSystem> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort);

	Page<ClassificationSystem> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);

	// createdDate
	List<ClassificationSystem> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
	List<ClassificationSystem> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
	List<ClassificationSystem> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<ClassificationSystem> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// createdBy
	List<ClassificationSystem> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
	List<ClassificationSystem> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
	List<ClassificationSystem> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
	List<ClassificationSystem> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<ClassificationSystem> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<ClassificationSystem> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

	Page<ClassificationSystem> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);


	// finalisedDate
	List<ClassificationSystem> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy);
	List<ClassificationSystem> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort);
	List<ClassificationSystem> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<ClassificationSystem> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// finalisedBy
	List<ClassificationSystem> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy);
	List<ClassificationSystem> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy);
	List<ClassificationSystem> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy);
	List<ClassificationSystem> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
	List<ClassificationSystem> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
	List<ClassificationSystem> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

	Page<ClassificationSystem> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
	Page<ClassificationSystem> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

	// deleted
	List<ClassificationSystem> findByDeletedAndOwnedBy(String deleted, String ownedBy);
	List<ClassificationSystem> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
	Page<ClassificationSystem> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

	// ownedBy
	List<ClassificationSystem> findByOwnedBy(String ownedBy);
	List<ClassificationSystem> findByOwnedBy(String ownedBy, Sort sort);
	Page<ClassificationSystem> findByOwnedBy(String ownedBy, Pageable pageable);

}