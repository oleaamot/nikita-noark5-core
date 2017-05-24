package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.metadata.SeriesStatus;

import java.util.List;

/**
 * Created by tsodring on 3/9/17.
 */
public interface ISeriesStatusService {

    SeriesStatus createNewSeriesStatus(SeriesStatus seriesStatus);

    Iterable<SeriesStatus> findAll();

    SeriesStatus findBySystemIdOrderBySystemId(String systemId);

    SeriesStatus update(SeriesStatus seriesStatus);

    List<SeriesStatus> findByDescription(String description);

    List<SeriesStatus> findByCode(String code);
}
