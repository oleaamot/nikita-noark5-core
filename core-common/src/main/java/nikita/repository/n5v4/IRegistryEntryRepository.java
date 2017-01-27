package nikita.repository.n5v4;

import nikita.model.noark5.v4.RegistryEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistryEntryRepository extends PagingAndSortingRepository<RegistryEntry, Long> {


    // -- All SAVE operations
    @Override
    RegistryEntry save(RegistryEntry registryEntry);

    RegistryEntry findBySystemId(String systemId);

}
