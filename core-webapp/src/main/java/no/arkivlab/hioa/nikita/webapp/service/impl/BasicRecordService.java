package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.BasicRecord;
import nikita.repository.n5v4.IBasicRecordRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IBasicRecordService;
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

@Service
@Transactional
public class BasicRecordService implements IBasicRecordService {

    private static final Logger logger = LoggerFactory.getLogger(BasicRecordService.class);

    private EntityManager entityManager;
    private IBasicRecordRepository basicRecordRepository;
    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    private Integer maxPageSize = new Integer(10);

    public BasicRecordService(EntityManager entityManager,
                              IBasicRecordRepository basicRecordRepository) {
        this.entityManager = entityManager;
        this.basicRecordRepository = basicRecordRepository;
    }

    // All READ operations
    @Override
    public List<BasicRecord> findBasicRecordByOwnerPaginated(Integer top, Integer skip) {

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

    // systemId
    public BasicRecord findBySystemIdOrderBySystemId(String systemId) {
        return getBasicRecordOrThrow(systemId);
    }


    // All UPDATE operations
    @Override
    public BasicRecord handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull BasicRecord incomingBasicRecord) {
        BasicRecord existingBasicRecord = getBasicRecordOrThrow(systemId);
        // Here copy all the values you are allowed to copy ....
        if (null != incomingBasicRecord.getDescription()) {
            existingBasicRecord.setDescription(incomingBasicRecord.getDescription());
        }
        if (null != incomingBasicRecord.getTitle()) {
            existingBasicRecord.setTitle(incomingBasicRecord.getTitle());
        }
        if (null != incomingBasicRecord.getDocumentMedium()) {
            existingBasicRecord.setDocumentMedium(existingBasicRecord.getDocumentMedium());
        }        existingBasicRecord.setVersion(version);
        basicRecordRepository.save(existingBasicRecord);
        return existingBasicRecord;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String basicRecordSystemId) {
        BasicRecord basicRecord = getBasicRecordOrThrow(basicRecordSystemId);
        basicRecordRepository.delete(basicRecord);
    }

    // All HELPER operations
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid BasicRecord back. If there is no valid
     * BasicRecord, an exception is thrown
     *
     * @param basicRecordSystemId
     * @return
     */
    protected BasicRecord getBasicRecordOrThrow(@NotNull String basicRecordSystemId) {
        BasicRecord basicRecord = basicRecordRepository.findBySystemIdOrderBySystemId(basicRecordSystemId);
        if (basicRecord == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " BasicRecord, using systemId " + basicRecordSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return basicRecord;
    }
}
