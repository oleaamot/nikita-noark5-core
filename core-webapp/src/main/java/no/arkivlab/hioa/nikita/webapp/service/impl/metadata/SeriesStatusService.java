package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.metadata.SeriesStatus;
import nikita.repository.n5v4.metadata.ISeriesStatusRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ISeriesStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class SeriesStatusService implements ISeriesStatusService {

    private static final Logger logger = LoggerFactory.getLogger(SeriesStatusService.class);
    private ISeriesStatusRepository seriesStatusRepository;

    public SeriesStatusService(ISeriesStatusRepository seriesStatusRepository) {
        this.seriesStatusRepository = seriesStatusRepository;
    }

    // All CREATE operations

    /**
     * Persists a new seriesStatus object to the database.
     *
     * @param seriesStatus seriesStatus object with values set
     * @return the newly persisted seriesStatus object
     */
    @Override
    public SeriesStatus createNewSeriesStatus(SeriesStatus seriesStatus) {
        seriesStatus.setDeleted(false);
        seriesStatus.setOwnedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return seriesStatusRepository.save(seriesStatus);
    }

    // All READ operations

    /**
     * retrieve all seriesStatus
     *
     * @return
     */
    @Override
    public Iterable<SeriesStatus> findAll() {
        return seriesStatusRepository.findAll();
    }

    // find by systemId

    /**
     * retrieve a single seriesStatus identified by systemId
     *
     * @param systemId
     * @return
     */
    @Override
    public SeriesStatus findBySystemIdOrderBySystemId(String systemId) {
        return seriesStatusRepository.findBySystemIdOrderBySystemId(systemId);
    }

    /**
     * retrieve all seriesStatus that have a particular description. <br>
     * This will be replaced by OData search.
     *
     * @param description
     * @return
     */
    @Override
    public List<SeriesStatus> findByDescription(String description) {
        return seriesStatusRepository.findByDescription(description);
    }

    /**
     * retrieve all seriesStatus that have a particular code. <br>
     * This will be replaced by OData search.
     *
     * @param code
     * @return
     */
    @Override
    public List<SeriesStatus> findByCode(String code) {
        return seriesStatusRepository.findByCode(code);
    }

    /**
     * update a particular seriesStatus. <br>
     *
     * @param seriesStatus
     * @return the updated seriesStatus
     */
    @Override
    public SeriesStatus update(SeriesStatus seriesStatus) {
        return seriesStatusRepository.save(seriesStatus);
    }
}
