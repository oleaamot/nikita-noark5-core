package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.Series;
import nikita.repository.n5v4.IFondsRepository;
import nikita.util.exceptions.NoarkEntityEditWhenClosedException;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import nikita.util.exceptions.NoarkInvalidStructureException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsCreatorService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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
    private IFondsCreatorService fondsCreatorService;
    private EntityManager entityManager;

    public FondsService(IFondsRepository fondsRepository,
                        SeriesService seriesService,
                        IFondsCreatorService fondsCreatorService,
                        EntityManager entityManager) {
        this.fondsRepository = fondsRepository;
        this.seriesService = seriesService;
        this.fondsCreatorService = fondsCreatorService;
        this.entityManager = entityManager;
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
        Fonds parentFonds = fondsRepository.findBySystemIdOrderBySystemId(parentFondsSystemId);

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
        Fonds fonds = fondsRepository.findBySystemIdOrderBySystemId(fondsSystemId);

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
        Fonds fonds = fondsRepository.findBySystemIdOrderBySystemId(fondsSystemId);

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
    @Override
    public Set<FondsCreator> findFondsCreatorAssociatedWithFonds(String systemId) {
        Fonds fonds = getFondsOrThrow(systemId);
        return fonds.getReferenceFondsCreator();
    }

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
        return getFondsOrThrow(systemId);
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
        typedQuery.setMaxResults(top);

        return typedQuery.getResultList();
    }

    // All UPDATE operations
    @Override
    public Fonds handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Fonds incomingFonds) {
        Fonds existingFonds = getFondsOrThrow(systemId);
        // Copy all the values you are allowed to copy ....
        if (null != incomingFonds.getDescription()) {
            existingFonds.setDescription(incomingFonds.getDescription());
        }
        if (null != incomingFonds.getTitle()) {
            existingFonds.setTitle(incomingFonds.getTitle());
        }
        if (null != incomingFonds.getDocumentMedium()) {
            existingFonds.setDocumentMedium(existingFonds.getDocumentMedium());
        }
        existingFonds.setVersion(version);
        fondsRepository.save(existingFonds);
        return existingFonds;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String fondsSystemId) {
        Fonds fonds = getFondsOrThrow(fondsSystemId);

        // Disassociate the link between Fonds and FondsCreator
        // https://github.com/HiOA-ABI/nikita-noark5-core/issues/82
        Query q = entityManager.createNativeQuery("DELETE FROM fonds_fonds_creator WHERE f_pk_fonds_id  = :id ;");
        q.setParameter("id", fonds.getId());
        q.executeUpdate();
        entityManager.remove(fonds);
        entityManager.flush();
        entityManager.clear();
        //fondsRepository.delete(fonds);
    }

    // All HELPER operations
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid Fonds back. If there is no valid
     * Fonds, an exception is thrown
     *
     * @param fondsSystemId
     * @return
     */
    protected Fonds getFondsOrThrow(@NotNull String fondsSystemId) {
        Fonds fonds = fondsRepository.findBySystemIdOrderBySystemId(fondsSystemId);
        if (fonds == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Fonds, using systemId " + fondsSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return fonds;
    }
}
