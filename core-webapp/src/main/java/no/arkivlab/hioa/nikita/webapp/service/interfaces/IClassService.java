package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface IClassService {


	// -- All CREATE operations

	Class save(Class klass);
	Class createClassAssociatedWithClass(String classSystemId, Class klass);

	// -- All READ operations
	List<Class> findClassByOwnerPaginated(Integer top, Integer skip);

	List<Class> findAll();
	List<Class> findAll(Sort sort);
	Page<Class> findAll(Pageable pageable);

	// id
	Class findById(Long id);

	// systemId
	Class findBySystemId(String systemId);

	// title
	List<Class> findByTitleAndOwnedBy(String title, String ownedBy);
	List<Class> findByTitleContainingAndOwnedBy(String title, String ownedBy);
	List<Class> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy);
	List<Class> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort);
	List<Class> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort);
	List<Class> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort);

	Page<Class> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable);
	Page<Class> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);
	Page<Class> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable);

	// description
	List<Class> findByDescriptionAndOwnedBy(String description, String ownedBy);
	List<Class> findByDescriptionContainingAndOwnedBy(String description, String ownedBy);
	List<Class> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy);
	List<Class> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort);
	List<Class> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort);
	List<Class> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort);

	Page<Class> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable);
	Page<Class> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);
	Page<Class> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable);

	// createdDate
	List<Class> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
	List<Class> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
	List<Class> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<Class> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
	Page<Class> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// createdBy
	List<Class> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
	List<Class> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
	List<Class> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
	List<Class> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<Class> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<Class> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

	Page<Class> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<Class> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<Class> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);


	// finalisedDate
	List<Class> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy);
	List<Class> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort);
	List<Class> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<Class> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable);
	Page<Class> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// finalisedBy
	List<Class> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy);
	List<Class> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy);
	List<Class> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy);
	List<Class> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
	List<Class> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);
	List<Class> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort);

	Page<Class> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
	Page<Class> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);
	Page<Class> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable);

	// deleted
	List<Class> findByDeletedAndOwnedBy(String deleted, String ownedBy);
	List<Class> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
	Page<Class> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

	// ownedBy
	List<Class> findByOwnedBy(String ownedBy);
	List<Class> findByOwnedBy(String ownedBy, Sort sort);
	Page<Class> findByOwnedBy(String ownedBy, Pageable pageable);

}