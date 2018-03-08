package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.casehandling.CaseFile;
import nikita.repository.n5v4.ISeriesRepository;
import nikita.util.exceptions.NoarkEntityEditWhenClosedException;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ICaseFileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.config.Constants.INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT;
import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;
import static nikita.config.N5ResourceMappings.STATUS_CLOSED;

@Service
@Transactional
public class SeriesService implements ISeriesService {

    private static final Logger logger = LoggerFactory.getLogger(SeriesService.class);

    private IFileService fileService;
    private EntityManager entityManager;
    private ICaseFileService caseFileService;
    private ISeriesRepository seriesRepository;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);

    public SeriesService(IFileService fileService,
                         EntityManager entityManager,
                         ICaseFileService caseFileService,
                         ISeriesRepository seriesRepository) {
        this.fileService = fileService;
        this.entityManager = entityManager;
        this.caseFileService = caseFileService;
        this.seriesRepository = seriesRepository;
    }

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
            persistedFile = caseFileService.save(caseFile);
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
            persistedFile = fileService.createFile(file);
        }
        return persistedFile;
    }

    public Series save(Series series){
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(series);
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(series);
        return seriesRepository.save(series);
    }

    // All READ operations

    public List<Series> findSeriesByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Series> criteriaQuery = criteriaBuilder.createQuery(Series.class);
        Root<Series> from = criteriaQuery.from(Series.class);
        CriteriaQuery<Series> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<Series> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(top);
        return typedQuery.getResultList();
    }

    // NOTE: I am leaving these methods here for another while. They will probably be replaced
    // by a single CriteriaBuilder approach, but for the moment, they will be left here.

    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    public List<CaseFile> findAllCaseFileBySeries(String systemId) {
        Series series = getSeriesOrThrow(systemId);
        return caseFileService.findAllCaseFileBySeries(series);
    }


    public List<Series> findAll(Sort sort) {
        return seriesRepository.findAll(sort);
    }

    public Page<Series> findAll(Pageable pageable) {
        return seriesRepository.findAll(pageable);
    }

    // id
    public Series findById(Long id) {
        return seriesRepository.findById(id);
    }

    // systemId
    public Series findBySystemId(String systemId) {
        return getSeriesOrThrow(systemId);
    }


    // ownedBy
    public List<Series> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByOwnedBy(ownedBy);
    }

    public List<Series> findByOwnedBy(String ownedBy, Sort sort) {return seriesRepository.findByOwnedBy(ownedBy, sort);}

    public Page<Series> findByOwnedBy(String ownedBy, Pageable pageable) {return seriesRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    @Override
    public Series handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Series incomingSeries) {
        Series existingSeries = getSeriesOrThrow(systemId);
        // Here copy all the values you are allowed to copy ....
        if (null != existingSeries.getDescription()) {
            existingSeries.setDescription(incomingSeries.getDescription());
        }
        if (null != incomingSeries.getTitle()) {
            existingSeries.setTitle(incomingSeries.getTitle());
        }
        if (null != incomingSeries.getDocumentMedium()) {
            existingSeries.setDocumentMedium(existingSeries.getDocumentMedium());
        }
        existingSeries.setVersion(version);
        seriesRepository.save(existingSeries);
        return existingSeries;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String seriesSystemId) {
        Series series = getSeriesOrThrow(seriesSystemId);
        seriesRepository.delete(series);
    }

    // Helper methods
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid Series back. If there is no valid
     * Series, an exception is thrown
     *
     * @param seriesSystemId
     * @return
     */
    protected Series getSeriesOrThrow(@NotNull String seriesSystemId) {
        Series series = seriesRepository.findBySystemId(seriesSystemId);
        if (series == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Series, using systemId " + seriesSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return series;
    }
}
