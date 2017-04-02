package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.metadata.FondsStatus;
import nikita.repository.n5v4.metadata.IFondsStatusRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IFondsStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class FondsStatusService implements IFondsStatusService {

    private static final Logger logger = LoggerFactory.getLogger(FondsStatusService.class);
    private IFondsStatusRepository fondsStatusRepository;

    public FondsStatusService(IFondsStatusRepository fondsStatusRepository) {
        this.fondsStatusRepository = fondsStatusRepository;
    }

    // All CREATE operations

    /**
     * Persists a new fondsStatus object to the database.
     *
     * @param fondsStatus fondsStatus object with values set
     * @return the newly persisted fondsStatus object
     */
    @Override
    public FondsStatus createNewFondsStatus(FondsStatus fondsStatus) {
        fondsStatus.setDeleted(false);
        fondsStatus.setOwnedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return fondsStatusRepository.save(fondsStatus);
    }

    // All READ operations

    /**
     * retrieve all fondsStatus
     *
     * @return
     */
    @Override
    public Iterable<FondsStatus> findAll() {
        return fondsStatusRepository.findAll();
    }

    // find by systemId

    /**
     * retrieve a single fondsStatus identified by systemId
     *
     * @param systemId
     * @return
     */
    @Override
    public FondsStatus findBySystemId(String systemId) {
        return fondsStatusRepository.findBySystemId(systemId);
    }

    /**
     * retrieve all fondsStatus that have a particular description. <br>
     * This will be replaced by OData search.
     *
     * @param description
     * @return
     */
    @Override
    public List<FondsStatus> findByDescription(String description) {
        return fondsStatusRepository.findByDescription(description);
    }

    /**
     * retrieve all fondsStatus that have a particular code. <br>
     * This will be replaced by OData search.
     *
     * @param code
     * @return
     */
    @Override
    public List<FondsStatus> findByCode(String code) {
        return fondsStatusRepository.findByCode(code);
    }

    /**
     * retrieve all fondsStatus that have a particular code. <br>
     * This will be replaced by OData search.
     *
     * @param fondsStatus
     * @return
     */
    @Override
    public FondsStatus update(FondsStatus fondsStatus) {
        return fondsStatusRepository.save(fondsStatus);
    }
}
