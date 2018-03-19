package nikita.webapp.service.interfaces;


import nikita.common.model.noark5.v4.Class;
import nikita.common.model.noark5.v4.ClassificationSystem;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IClassificationSystemService {

	// -- All CREATE operations
    ClassificationSystem createNewClassificationSystem(
            ClassificationSystem classificationSystem);

    Class createClassAssociatedWithClassificationSystem(
            String classificationSystemSystemId, Class klass);

	// -- All READ operations
    List<ClassificationSystem> findClassificationSystemByOwnerPaginated(
            Integer top, Integer skip);

	List<ClassificationSystem> findAll();

	// id
    Optional<ClassificationSystem> findById(Long id);

	// systemId
    ClassificationSystem findBySystemId(String systemId);

	// ownedBy
	List<ClassificationSystem> findByOwnedBy(String ownedBy);

    // All UPDATE operations
    ClassificationSystem handleUpdate(@NotNull String systemId,
                                      @NotNull Long version,
                                      @NotNull ClassificationSystem
                                              incomingClassificationSystem);

    // All DELETE operations
    void deleteEntity(@NotNull String classificationSystemSystemId);
}
