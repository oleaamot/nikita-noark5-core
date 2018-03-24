package nikita.webapp.service.interfaces.metadata;

import nikita.common.model.noark5.v4.metadata.SeriesStatus;

import java.util.List;

/**
 * Created by tsodring on 3/9/17.
 */
public interface ISeriesStatusService {

    SeriesStatus createNewSeriesStatus(SeriesStatus seriesStatus);

    Iterable<SeriesStatus> findAll();

    SeriesStatus findBySystemId(String systemId);

    SeriesStatus update(SeriesStatus seriesStatus);

    List<SeriesStatus> findByDescription(String description);

    List<SeriesStatus> findByCode(String code);
}
