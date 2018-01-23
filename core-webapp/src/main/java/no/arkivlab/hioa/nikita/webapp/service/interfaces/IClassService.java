package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
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

	// ownedBy
	List<Class> findByOwnedBy(String ownedBy);
	List<Class> findByOwnedBy(String ownedBy, Sort sort);
	Page<Class> findByOwnedBy(String ownedBy, Pageable pageable);

	// All UPDATE operations
	Class handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Class klass);

	// All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
