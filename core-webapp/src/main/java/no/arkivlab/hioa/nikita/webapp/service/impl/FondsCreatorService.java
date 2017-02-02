package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.FondsCreator;
import nikita.repository.n5v4.IFondsCreatorRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsCreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
@Transactional
public class FondsCreatorService implements IFondsCreatorService {

    private static final Logger logger = LoggerFactory.getLogger(FondsCreatorService.class);

    @Autowired
    IFondsCreatorRepository fondsCreatorRepository;

    @Autowired
    SeriesService seriesService;

    @Autowired
    EntityManager entityManager;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);

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
        return fondsCreatorRepository.save(fondsCreator);
    }


    // All READ operations
    @Override
    public Iterable<FondsCreator> findFondsCreatorByOwnerPaginated(Integer top, Integer skip) {

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
