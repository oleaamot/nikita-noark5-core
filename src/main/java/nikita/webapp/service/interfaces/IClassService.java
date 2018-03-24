package nikita.webapp.service.interfaces;


import nikita.common.model.noark5.v4.Class;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IClassService {


	// -- All CREATE operations

	Class save(Class klass);
	Class createClassAssociatedWithClass(String classSystemId, Class klass);

	// -- All READ operations
	List<Class> findClassByOwnerPaginated(Integer top, Integer skip);

	List<Class> findAll();

	// id
    Optional<Class> findById(Long id);

	// systemId
    Class findBySystemId(String systemId);

	// ownedBy
	List<Class> findByOwnedBy(String ownedBy);

	// All UPDATE operations
	Class handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Class klass);

	// All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
