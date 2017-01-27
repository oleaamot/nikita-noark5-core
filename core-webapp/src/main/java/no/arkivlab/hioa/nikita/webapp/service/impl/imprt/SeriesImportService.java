package no.arkivlab.hioa.nikita.webapp.service.impl.imprt;

import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Series;
import nikita.repository.n5v4.ISeriesRepository;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.ICaseFileImportService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IFileImportService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.ISeriesImportService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityEditWhenClosedException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static nikita.config.Constants.INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT;
import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;
import static nikita.config.N5ResourceMappings.STATUS_CLOSED;

@Service
@Transactional
public class SeriesImportService implements ISeriesImportService {

    private static final Logger logger = LoggerFactory.getLogger(SeriesImportService.class);

    @Autowired
    IFileImportService fileImportService;

    @Autowired
    ICaseFileImportService caseFileImportService;

    @Autowired
    ISeriesRepository seriesRepository;

    // All CREATE operations

    @Override
    public CaseFile createCaseFileAssociatedWithSeries(String seriesSystemId, CaseFile caseFile) {
        CaseFile persistedFile = null;
        Series series = seriesRepository.findBySystemId(seriesSystemId);
        if (series == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Series, using seriesSystemId " + seriesSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        } else if (series.getSeriesStatus() != null && series.getSeriesStatus().equals(STATUS_CLOSED)) {
            String info = INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT + ". Series with seriesSystemId " + seriesSystemId +
                    "has status " + STATUS_CLOSED;
            logger.info(info);
            throw new NoarkEntityEditWhenClosedException(info);
        } else {
            caseFile.setReferenceSeries(series);
            persistedFile = caseFileImportService.save(caseFile);
        }
        return persistedFile;
    }
    
    @Override
    public File createFileAssociatedWithSeries(String seriesSystemId, File file) {
        File persistedFile = null;
        Series series = seriesRepository.findBySystemId(seriesSystemId);
        if (series == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Series, using seriesSystemId " + seriesSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else if (series.getSeriesStatus() != null && series.getSeriesStatus().equals(STATUS_CLOSED)) {
            String info = INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT + ". Series with seriesSystemId " + seriesSystemId +
                    "has status " + STATUS_CLOSED;
            logger.info(info) ;
            throw new NoarkEntityEditWhenClosedException(info);
        }
        else {
            file.setReferenceSeries(series);
            persistedFile = fileImportService.createFile(file);
        }
        return persistedFile;
    }

    public Series save(Series series){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null) {
            throw new NikitaException("Security context problem. username is null! Cannot continue with " +
                    "this request!");
        }
        if (series.getCreatedDate() == null) {
            series.setCreatedDate(new Date());
        }
        if (series.getCreatedBy() == null) {
            series.setCreatedBy(username);
        }
        if (series.getOwnedBy() == null) {
            series.setOwnedBy(username);
        }
        series.setDeleted(false);
        
        return seriesRepository.save(series);
    }
}
