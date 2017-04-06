package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import nikita.repository.n5v4.IFondsCreatorRepository;
import nikita.repository.n5v4.IFondsRepository;
import nikita.util.exceptions.NikitaMalformedInputDataException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsCreatorService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;
import static nikita.config.N5ResourceMappings.STATUS_OPEN;

@Service
@Transactional
public class FondsCreatorService implements IFondsCreatorService {

    private static final Logger logger = LoggerFactory.getLogger(FondsCreatorService.class);
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);
    private IFondsCreatorRepository fondsCreatorRepository;
    private IFondsRepository fondsRepository;
    private SeriesService seriesService;
    private EntityManager entityManager;

    public FondsCreatorService(IFondsCreatorRepository fondsCreatorRepository,
                               IFondsRepository fondsRepository,
                               SeriesService seriesService,
                               EntityManager entityManager) {
        this.fondsCreatorRepository = fondsCreatorRepository;
        this.fondsRepository = fondsRepository;
        this.seriesService = seriesService;
        this.entityManager = entityManager;
    }

    // All CREATE operations

    /**
     * Persists a new fondsCreator object to the database. Some values are set in the incoming payload (e.g. title)
     * and some are set by the core. owner, createdBy, createdDate are automatically set by the core.
     *
     * @param fondsCreator fondsCreator object with some values set
     * @return the newly persisted fondsCreator object
     */
    @Override
    public FondsCreator createNewFondsCreator(FondsCreator fondsCreator) {
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(fondsCreator);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(fondsCreator);
        return fondsCreatorRepository.save(fondsCreator);
    }

    @Override
    public Fonds createFondsAssociatedWithFondsCreator(String fondsCreatorSystemId, Fonds fonds) {
        FondsCreator fondsCreator = getFondsCreatorOrThrow(fondsCreatorSystemId);
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(fonds);
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(fonds);
        fonds.setFondsStatus(STATUS_OPEN);
        NoarkUtils.NoarkEntity.Create.setFinaliseEntityValues(fonds);
        fonds.getReferenceFondsCreator().add(fondsCreator);
        fondsCreator.getReferenceFonds().add(fonds);
        fondsRepository.save(fonds);
        return fonds;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid FondsCreator back. If there is no valid
     * FondsCreator, an exception is thrown
     *
     * @param fondsCreatorSystemId
     * @return
     */
    protected FondsCreator getFondsCreatorOrThrow(String fondsCreatorSystemId) {
        FondsCreator fondsCreator = fondsCreatorRepository.findBySystemId(fondsCreatorSystemId);
        if (fondsCreator == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " FondsCreator, using systemId " + fondsCreatorSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return fondsCreator;
    }

    // All READ operations
    @Override
    public List<FondsCreator> findFondsCreatorByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FondsCreator> criteriaQuery = criteriaBuilder.createQuery(FondsCreator.class);
        Root<FondsCreator> from = criteriaQuery.from(FondsCreator.class);
        CriteriaQuery<FondsCreator> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<FondsCreator> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);

        return typedQuery.getResultList();
    }

    @Override
    public FondsCreator updateFondsCreator(@NotNull FondsCreator fondsCreatorUpdate) {
        if (fondsCreatorUpdate.getSystemId() != null) {
            FondsCreator existingFondsCreator = getFondsCreatorOrThrow(fondsCreatorUpdate.getSystemId());
            existingFondsCreator.setDescription(fondsCreatorUpdate.getDescription());
            existingFondsCreator.setFondsCreatorId(fondsCreatorUpdate.getFondsCreatorId());
            existingFondsCreator.setFondsCreatorName(fondsCreatorUpdate.getFondsCreatorName());
            return existingFondsCreator;
        }
        throw new NikitaMalformedInputDataException("Attempt to update arkivskaper, but systemID is missing");
    }

    @Override
    public Iterable<FondsCreator> findAll() {
        return fondsCreatorRepository.findAll();
    }


    @Override
    public FondsCreator findById(Long id) {
        return fondsCreatorRepository.findById(id);
    }

    @Override
    public FondsCreator findBySystemId(String systemId) {
        return fondsCreatorRepository.findBySystemId(systemId);
    }

}
