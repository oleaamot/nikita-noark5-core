package nikita.common.repository.n5v4;

import nikita.common.model.noark5.v4.FondsCreator;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFondsCreatorRepository extends
        PagingAndSortingRepository<FondsCreator, Long> {

    // -- All SAVE operations
    @Override
    FondsCreator save(FondsCreator fondsCreator);

    // -- All READ operations
    @Override
    List<FondsCreator> findAll();

    Optional<FondsCreator> findById(Long id);
    FondsCreator findBySystemId(String systemId);

    List<FondsCreator> findByOwnedBy(String ownedBy);
}
