package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Series;
import nikita.repository.n5v4.ISeriesRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ICaseFileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityEditWhenClosedException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.List;

import static nikita.config.Constants.INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT;
import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;
import static nikita.config.N5ResourceMappings.STATUS_CLOSED;

@Service
@Transactional
public class SeriesService implements ISeriesService {

    private static final Logger logger = LoggerFactory.getLogger(SeriesService.class);

    @Autowired
    IFileService fileService;

    @Autowired
    EntityManager entityManager;
    
    @Autowired
    ICaseFileService caseFileService;

    @Autowired
    ISeriesRepository seriesRepository;


    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);

    public SeriesService() {
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
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }

    // NOTE: I am leaving these methods here for another while. They will probably be replaced
    // by a single CriteriaBuilder approach, but for the moment, they will be left here.

    public List<Series> findAll() {
        return seriesRepository.findAll();
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
        return seriesRepository.findBySystemId(systemId);
    }

    // title
    public List<Series> findByTitleAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleAndOwnedBy(title,  ownedBy);
    }

    public List<Series> findByTitleContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleContainingAndOwnedBy(title, ownedBy);
    }

    public List<Series> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy);
    }

    public List<Series> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleAndOwnedBy(title, ownedBy, sort);
    }

    public List<Series> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleContainingAndOwnedBy(title, ownedBy, sort);
    }

    public List<Series> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, sort);
    }

    public Page<Series> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<Series> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleContainingAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<Series> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, pageable);
    }

    // description
    public List<Series> findByDescriptionAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionAndOwnedBy(description, ownedBy);
    }

    public List<Series> findByDescriptionContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy);
    }

    public List<Series> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy);
    }

    public List<Series> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionAndOwnedBy(description, ownedBy, sort);
    }

    public List<Series> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, sort);
    }

    public List<Series> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, sort);
    }

    public Page<Series> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<Series> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<Series> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, pageable);
    }

    // fondStatus
    public List<Series> findBySeriesStatusAndOwnedBy(String seriesStatus, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findBySeriesStatusAndOwnedBy(seriesStatus, ownedBy);
    }

    public List<Series> findBySeriesStatusAndOwnedBy(String seriesStatus, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findBySeriesStatusAndOwnedBy(seriesStatus, ownedBy, sort);
    }

    public Page<Series> findBySeriesStatusAndOwnedBy(String seriesStatus, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findBySeriesStatusAndOwnedBy(seriesStatus, ownedBy, pageable);
    }

    // documentMedium
    public List<Series> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy);
    }

    public List<Series> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy, sort);
    }

    public Page<Series> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy, pageable);
    }

    // createdDate
    public List<Series> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy);
    }

    public List<Series> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, sort);
    }

    public List<Series> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Series> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, pageable);
    }

    public Page<Series> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // createdBy
    public List<Series> findByCreatedByAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy);
    }

    public List<Series> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Series> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Series> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Series> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Series> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public Page<Series> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Series> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Series> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    // finalisedDate
    public List<Series> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Series> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Series> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Series> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy, pageable);
    }

    public Page<Series> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // finalisedBy
    public List<Series> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Series> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Series> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Series> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Series> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Series> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public Page<Series> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Series> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Series> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    // deleted
    public List<Series> findByDeletedAndOwnedBy(String deleted, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDeletedAndOwnedBy(deleted, ownedBy);
    }

    public List<Series> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDeletedAndOwnedBy(deleted, ownedBy, sort);
    }

    public Page<Series> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByDeletedAndOwnedBy(deleted, ownedBy, pageable);
    }

    // ownedBy
    public List<Series> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return seriesRepository.findByOwnedBy(ownedBy);
    }

    public List<Series> findByOwnedBy(String ownedBy, Sort sort) {return seriesRepository.findByOwnedBy(ownedBy, sort);}

    public Page<Series> findByOwnedBy(String ownedBy, Pageable pageable) {return seriesRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public Series update(Series series){
        if (series.getSeriesStatus().equals(STATUS_CLOSED)) {
            //throw an exception back
        }
        return seriesRepository.save(series);
    }

    public Series updateSeriesSetFinalized(Long id){
        Series series = seriesRepository.findById(id);

        if (series == null) {
            // throw Object not find
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        series.setSeriesStatus(STATUS_CLOSED);
        series.setFinalisedDate(new Date());
        series.setFinalisedBy(username);

        return seriesRepository.save(series);
    }

    public Series updateSeriesSetTitle(Long id, String newTitle){

        Series series = seriesRepository.findById(id);

        if (series == null) {
            // throw Object not find
        } else if (series.getSeriesStatus().equals(STATUS_CLOSED)) {
            // throw Object finalises, cannot be edited
        }
        series.setTitle(newTitle);
        return seriesRepository.save(series);
    }


    // All DELETE operations


}
