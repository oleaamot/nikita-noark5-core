package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.ClassificationSystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
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

	// ownedBy
	List<ClassificationSystem> findByOwnedBy(String ownedBy);
	List<ClassificationSystem> findByOwnedBy(String ownedBy, Sort sort);
	Page<ClassificationSystem> findByOwnedBy(String ownedBy, Pageable pageable);

    // All UPDATE operations
    ClassificationSystem handleUpdate(@NotNull String systemId,
                                      @NotNull Long version,
                                      @NotNull ClassificationSystem incomingClassificationSystem);

    // All DELETE operations
    void deleteEntity(@NotNull String systemId);
}
