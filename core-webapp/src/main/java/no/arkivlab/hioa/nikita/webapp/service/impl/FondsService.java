package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.Series;
import nikita.repository.n5v4.IFondsRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityEditWhenClosedException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkInvalidStructureException;
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
import java.util.Date;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.STATUS_CLOSED;
import static nikita.config.N5ResourceMappings.STATUS_OPEN;

@Service
@Transactional
public class FondsService implements IFondsService {

    private static final Logger logger = LoggerFactory.getLogger(FondsService.class);
    // TODO: Trying to pick up property from yaml file, but not working ...
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);
    private IFondsRepository fondsRepository;
    private SeriesService seriesService;
    private FondsCreatorService fondsCreatorService;
    private EntityManager entityManager;

    public FondsService(IFondsRepository fondsRepository,
                        SeriesService seriesService,
                        FondsCreatorService fondsCreatorService,
                        EntityManager entityManager) {
        this.fondsRepository = fondsRepository;
        this.seriesService = seriesService;
        this.fondsCreatorService = fondsCreatorService;
        this.entityManager = entityManager;
    }

    @Override
    public Fonds handleUpdate(String systemId, Long version, Fonds incomingFonds) {
        Fonds existingFonds = fondsRepository.findBySystemId(systemId);
        // Here copy all the values you are allowed to copy ....
        existingFonds.setDescription(incomingFonds.getDescription());
        existingFonds.setTitle(incomingFonds.getTitle());
        existingFonds.setVersion(version);
        fondsRepository.save(existingFonds);
        return existingFonds;
    }


    // All CREATE operations

    /**
     * Persists a new fonds object to the database. Some values are set in the incoming payload (e.g. title)
     * and some are set by the core. owner, createdBy, createdDate are automatically set by the core.
     *
     * @param fonds fonds object with some values set
     * @return the newly persisted fonds object
     */
    @Override
    public Fonds createNewFonds(Fonds fonds){
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(fonds);
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(fonds);
        fonds.setFondsStatus(STATUS_OPEN);
        NoarkUtils.NoarkEntity.Create.setFinaliseEntityValues(fonds);
        return fondsRepository.save(fonds);
    }

    /**
     *
     ** Persists a new fonds object to the database, that is associated with a parent fonds object. Some values are set
     * in the incoming payload (e.g. title) and some are set by the core. owner, createdBy, createdDate are
     * automatically set by the core.
     *
     * First we try to locate the parent. If the parent does not exist a NoarkEntityNotFoundException exception is
     * thrown
     *
     * @param childFonds incoming fonds object with some values set
     * @param parentFondsSystemId The systemId of the parent fonds
     * @return the newly persisted fonds object
     */
    @Override
    public Fonds createFondsAssociatedWithFonds(String parentFondsSystemId, Fonds childFonds) {
        Fonds persistedChildFonds = null;
        Fonds parentFonds = fondsRepository.findBySystemId(parentFondsSystemId);

        if (parentFonds == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Fonds, using fondsSystemId " + parentFondsSystemId + " when " +
                    "trying to associate a child fonds with a parent fonds";
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else if (parentFonds.getReferenceSeries() != null) {
            String info = INFO_INVALID_STRUCTURE + " Cannot associate a new child fonds with a fonds that has " +
                    "one or more series " + parentFondsSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            childFonds.setReferenceParentFonds(parentFonds);
            persistedChildFonds = this.createNewFonds(childFonds);
        }
        return persistedChildFonds;
    }

    /**
     *
     * Persists a new series object to the database. Some values are set in the incoming payload (e.g. title)
     * and some are set by the core. owner, createdBy, createdDate are automatically set by the core.
     *
     * First we try to locate the parent fonds. If the parent fonds does not exist a NoarkEntityNotFoundException
     * exception is thrown. Next  we check that the fonds does not have children fonds. If it does an
     * exception is thrown.
     *
     * @param fondsSystemId
     * @param series
     * @return the newly persisted series object
     */
    @Override
    public Series createSeriesAssociatedWithFonds(String fondsSystemId, Series series) {
        Series persistedSeries = null;
        Fonds fonds = fondsRepository.findBySystemId(fondsSystemId);

        if (fonds == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Fonds, using fondsSystemId " + fondsSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else if (fonds.getReferenceChildFonds() != null && fonds.getReferenceChildFonds().size() > 0) {
            String info = INFO_INVALID_STRUCTURE + " Cannot associate series with a fonds that has" +
                    "children fonds " + fondsSystemId;
            logger.info(info) ;
            throw new NoarkInvalidStructureException(info, "Fonds", "Series");
        }
        else if (fonds.getFondsStatus() != null && fonds.getFondsStatus().equals(STATUS_CLOSED)) {
            String info = INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT + ". Fonds with fondsSystemId " + fondsSystemId +
                    "has status " + STATUS_CLOSED;
            logger.info(info) ;
            throw new NoarkEntityEditWhenClosedException(info);
        }
        else {
            series.setReferenceFonds(fonds);
            persistedSeries = seriesService.save(series);
        }
        return persistedSeries;
    }

    public FondsCreator createFondsCreatorAssociatedWithFonds(String fondsSystemId, FondsCreator fondsCreator) {
        Fonds fonds = fondsRepository.findBySystemId(fondsSystemId);

        if (fonds == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Fonds, using fondsSystemId " + fondsSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }

        fondsCreatorService.createNewFondsCreator(fondsCreator);
        fondsCreator.addFonds(fonds);
        fonds.getReferenceFondsCreator().add(fondsCreator);
        return fondsCreator;
    }

    // All READ operations
    public List<Fonds> findAll() {
        return fondsRepository.findAll();
    }

    public List<Fonds> findAll(Sort sort) {
        return fondsRepository.findAll(sort);
    }

    public Page<Fonds> findAll(Pageable pageable) {
        return fondsRepository.findAll(pageable);
    }

    // id
    public Fonds findById(Long id) {
        return fondsRepository.findById(id);
    }

    // systemId
    public Fonds findBySystemId(String systemId) {
        return fondsRepository.findBySystemId(systemId);
    }

    // title
    public List<Fonds> findByTitleAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleAndOwnedBy(title,  ownedBy);
    }

    public List<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleContainingAndOwnedBy(title, ownedBy);
    }

    public List<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy);
    }

    public List<Fonds> findByTitleAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleAndOwnedBy(title, ownedBy, sort);
    }

    public List<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleContainingAndOwnedBy(title, ownedBy, sort);
    }

    public List<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, sort);
    }

    public Page<Fonds> findByTitleAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<Fonds> findByTitleContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleContainingAndOwnedBy(title, ownedBy, pageable);
    }

    public Page<Fonds> findByTitleIgnoreCaseContainingAndOwnedBy(String title, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByTitleIgnoreCaseContainingAndOwnedBy(title, ownedBy, pageable);
    }

    // description
    public List<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionAndOwnedBy(description, ownedBy);
    }

    public List<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy);
    }

    public List<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy);
    }

    public List<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionAndOwnedBy(description, ownedBy, sort);
    }

    public List<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, sort);
    }

    public List<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, sort);
    }

    public Page<Fonds> findByDescriptionAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<Fonds> findByDescriptionContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionContainingAndOwnedBy(description, ownedBy, pageable);
    }

    public Page<Fonds> findByDescriptionIgnoreCaseContainingAndOwnedBy(String description, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDescriptionIgnoreCaseContainingAndOwnedBy(description, ownedBy, pageable);
    }

    // fondStatus
    public List<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFondsStatusAndOwnedBy(fondsStatus, ownedBy);
    }

    public List<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFondsStatusAndOwnedBy(fondsStatus, ownedBy, sort);
    }

    public Page<Fonds> findByFondsStatusAndOwnedBy(String fondsStatus, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFondsStatusAndOwnedBy(fondsStatus, ownedBy, pageable);
    }

    // documentMedium
    public List<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy);
    }

    public List<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy, sort);
    }

    public Page<Fonds> findByDocumentMediumAndOwnedBy(String documentMedium, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDocumentMediumAndOwnedBy(documentMedium, ownedBy, pageable);
    }

    // createdDate
    public List<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy);
    }

    public List<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, sort);
    }

    public List<Fonds> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Fonds> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, pageable);
    }

    public Page<Fonds> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // createdBy
    public List<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy);
    }

    public List<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public Page<Fonds> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Fonds> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Fonds> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    // finalisedDate
    public List<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Fonds> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Fonds> findByFinalisedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateAndOwnedBy(finalisedDate, ownedBy, pageable);
    }

    public Page<Fonds> findByFinalisedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // finalisedBy
    public List<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public Page<Fonds> findByFinalisedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Fonds> findByFinalisedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Fonds> findByFinalisedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByFinalisedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    // deleted
    public List<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDeletedAndOwnedBy(deleted, ownedBy);
    }

    public List<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDeletedAndOwnedBy(deleted, ownedBy, sort);
    }

    public Page<Fonds> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByDeletedAndOwnedBy(deleted, ownedBy, pageable);
    }

    // ownedBy
    public List<Fonds> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fondsRepository.findByOwnedBy(ownedBy);
    }

    public List<Fonds> findByOwnedBy(String ownedBy, Sort sort) {return fondsRepository.findByOwnedBy(ownedBy, sort);}

    public Page<Fonds> findByOwnedBy(String ownedBy, Pageable pageable) {return fondsRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public Fonds update(Fonds fonds){
        if (fonds.getFondsStatus().equals(STATUS_CLOSED)) {
            //throw an exception back
        }
        return fondsRepository.save(fonds);
    }

    public Fonds updateFondsSetFinalized(Long id){
        Fonds fonds = fondsRepository.findById(id);

        if (fonds == null) {
            // throw Object not find
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        fonds.setFondsStatus(STATUS_CLOSED);
        fonds.setFinalisedDate(new Date());
        fonds.setFinalisedBy(username);

        return fondsRepository.save(fonds);
    }

    public Fonds updateFondsSetTitle(Long id, String newTitle){

        Fonds fonds = fondsRepository.findById(id);

        if (fonds == null) {
            // throw Object not find
        } else if (fonds.getFondsStatus().equals(STATUS_CLOSED)) {
            // throw Object finalises, cannot be edited
        }
        fonds.setTitle(newTitle);
        return fondsRepository.save(fonds);
    }


    // All READ operations

    public List<Fonds> findFondsByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fonds> criteriaQuery = criteriaBuilder.createQuery(Fonds.class);
        Root<Fonds> from = criteriaQuery.from(Fonds.class);
        CriteriaQuery<Fonds> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<Fonds> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);

        return typedQuery.getResultList();
    }

    /**
     * Persists a updated fonds to the database.
     *
     * @param fonds fonds object with some values set
     * @return the newly persisted fonds object
     */
    @Override
    public Fonds updateFonds(Fonds fonds) {
        return fondsRepository.save(fonds);
    }
}
