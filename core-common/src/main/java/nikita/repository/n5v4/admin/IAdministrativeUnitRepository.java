package nikita.repository.n5v4.admin;

import nikita.model.noark5.v4.admin.AdministrativeUnit;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IAdministrativeUnitRepository extends PagingAndSortingRepository<AdministrativeUnit, Long> {


    // -- All SAVE operations
    @Override
    AdministrativeUnit save(AdministrativeUnit administrativeUnit);

    // -- All READ operations
    @Override
    List<AdministrativeUnit> findAll();

    // id
    AdministrativeUnit findById(Long id);

    // systemId
    AdministrativeUnit findBySystemId(String systemId);

    // administrativeUnitName
    AdministrativeUnit findByAdministrativeUnitName(String administrativeUnitName);

    // shortName
    AdministrativeUnit findByShortName(String shortName);
}
