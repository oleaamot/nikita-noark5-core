package nikita.repository.n5v4;

import nikita.model.noark5.v4.FondsCreator;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFondsCreatorRepository extends PagingAndSortingRepository<FondsCreator, Long> {


    // -- All SAVE operations
    @Override
    FondsCreator save(FondsCreator fondsCreator);

    // -- All READ operations
    @Override
    List<FondsCreator> findAll();

    // id
    FondsCreator findById(Long id);


    // systemId
    FondsCreator findBySystemId(String systemId);
}
