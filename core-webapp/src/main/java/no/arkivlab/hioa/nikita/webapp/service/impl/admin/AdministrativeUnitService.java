package no.arkivlab.hioa.nikita.webapp.service.impl.admin;

import nikita.model.noark5.v4.admin.AdministrativeUnit;
import nikita.repository.n5v4.admin.IAdministrativeUnitRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.admin.IAdministrativeUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class AdministrativeUnitService implements IAdministrativeUnitService {

    private static final Logger logger = LoggerFactory.getLogger(AdministrativeUnitService.class);
    private IAdministrativeUnitRepository administrativeUnitRepository;

    public AdministrativeUnitService(IAdministrativeUnitRepository administrativeUnitRepository) {
        this.administrativeUnitRepository = administrativeUnitRepository;
    }

    // All CREATE operations

    /**
     * Persists a new administrativeUnit object to the database.
     *
     * @param administrativeUnit administrativeUnit object with values set
     * @return the newly persisted administrativeUnit object
     */
    @Override
    public AdministrativeUnit createNewAdministrativeUnit(AdministrativeUnit administrativeUnit) {
        administrativeUnit.setDeleted(false);
        administrativeUnit.setOwnedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return administrativeUnitRepository.save(administrativeUnit);
    }

    // All READ operations

    /**
     * retrieve all administrativeUnit
     *
     * @return
     */
    @Override
    public List<AdministrativeUnit> findAll() {
        return administrativeUnitRepository.findAll();
    }

    // find by systemId

    /**
     * retrieve a single administrativeUnit identified by systemId
     *
     * @param systemId
     * @return
     */
    @Override
    public AdministrativeUnit findBySystemId(String systemId) {
        return administrativeUnitRepository.findBySystemId(systemId);
    }

    /**
     * update a particular administrativeUnit. <br>
     *
     * @param incomingAdministrativeUnit
     * @return the updated administrativeUnit
     */
    @Override
    public AdministrativeUnit update(String systemId, Long version,
                                     AdministrativeUnit incomingAdministrativeUnit) {
        AdministrativeUnit existingAdministrativeUnit = getAdministrativeUnitOrThrow(systemId);
        // Here copy all the values you are allowed to copy ....
        if (null != existingAdministrativeUnit.getAdministrativeUnitName()) {
            existingAdministrativeUnit.setAdministrativeUnitName(incomingAdministrativeUnit.getAdministrativeUnitName());
        }
        if (null != existingAdministrativeUnit.getShortName()) {
            existingAdministrativeUnit.setShortName(incomingAdministrativeUnit.getShortName());
        }
        existingAdministrativeUnit.setVersion(version);
        administrativeUnitRepository.save(existingAdministrativeUnit);
        return administrativeUnitRepository.save(incomingAdministrativeUnit);
    }

    // All HELPER operations

    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid AdministrativeUnit back. If there is no valid
     * AdministrativeUnit, an exception is thrown
     *
     * @param administrativeUnitSystemId
     * @return
     */
    protected AdministrativeUnit getAdministrativeUnitOrThrow(@NotNull String administrativeUnitSystemId) {
        AdministrativeUnit administrativeUnit = administrativeUnitRepository.findBySystemId(administrativeUnitSystemId);
        if (administrativeUnit == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " AdministrativeUnit, using systemId " + administrativeUnitSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return administrativeUnit;
    }
}
