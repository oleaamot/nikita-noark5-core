package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.BasicRecord;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IBasicRecordService;
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
public class BasicRecordService extends RecordService implements IBasicRecordService {

    private static final Logger logger = LoggerFactory.getLogger(BasicRecordService.class);

    @Autowired
    EntityManager entityManager;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);

    // All READ operations
    @Override
    public Iterable<BasicRecord> findBasicRecordByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BasicRecord> criteriaQuery = criteriaBuilder.createQuery(BasicRecord.class);
        Root<BasicRecord> from = criteriaQuery.from(BasicRecord.class);
        CriteriaQuery<BasicRecord> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<BasicRecord> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }

}
