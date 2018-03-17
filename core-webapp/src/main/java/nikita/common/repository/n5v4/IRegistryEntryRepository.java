package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.casehandling.RegistryEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistryEntryRepository extends PagingAndSortingRepository<RegistryEntry, Long> {

    // -- All SAVE operations
    @Override
    RegistryEntry save(RegistryEntry registryEntry);

    RegistryEntry findBySystemId(String systemId);
}
