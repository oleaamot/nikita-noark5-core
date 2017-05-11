package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.Record;
import nikita.repository.n5v4.IRecordRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRecordService;
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
import java.util.*;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class RecordService implements IRecordService {

    private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

    private DocumentDescriptionService documentDescriptionService;
    private IRecordRepository recordRepository;
    private EntityManager entityManager;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    private Integer maxPageSize = new Integer(10);

    public RecordService(DocumentDescriptionService documentDescriptionService,
                         IRecordRepository recordRepository,
                         EntityManager entityManager) {
        this.documentDescriptionService = documentDescriptionService;
        this.recordRepository = recordRepository;
        this.entityManager = entityManager;
    }

    // All CREATE operations
    public Record save(Record record){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        record.setSystemId(UUID.randomUUID().toString());
        record.setCreatedDate(new Date());
        record.setOwnedBy(username);
        record.setCreatedBy(username);
        record.setDeleted(false);

        return recordRepository.save(record);
    }

    @Override
    public DocumentDescription createDocumentDescriptionAssociatedWithRecord(String recordSystemId, DocumentDescription documentDescription) {
        DocumentDescription persistedDocumentDescription = null;
        Record record = recordRepository.findBySystemId(recordSystemId);
        if (record == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Record, using recordSystemId " + recordSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            TreeSet <Record> records = (TreeSet <Record>) documentDescription.getReferenceRecord();

            if (records == null) {
                records = new TreeSet<>();
                documentDescription.setReferenceRecord(records);
            }
            records.add(record);
            Set<DocumentDescription> documentDescriptions = record.getReferenceDocumentDescription();
            documentDescriptions.add(documentDescription);
            persistedDocumentDescription = documentDescriptionService.save(documentDescription);
        }
        return persistedDocumentDescription;
    }

    // All READ operations
    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    public List<Record> findAll(Sort sort) {
        return recordRepository.findAll(sort);
    }

    public Page<Record> findAll(Pageable pageable) {
        return recordRepository.findAll(pageable);
    }

    // id
    public Record findById(Long id) {
        return recordRepository.findById(id);
    }

    // systemId
    public Record findBySystemId(String systemId) {
        return getRecordOrThrow(systemId);
    }

    // createdDate
    public List<Record> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy);
    }

    public List<Record> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, sort);
    }

    public List<Record> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Record> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedDateAndOwnedBy(createdDate, ownedBy, pageable);
    }

    public Page<Record> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // createdBy
    public List<Record> findByCreatedByAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy);
    }

    public List<Record> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Record> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy);
    }

    public List<Record> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Record> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public List<Record> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, sort);
    }

    public Page<Record> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Record> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    public Page<Record> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByCreatedByIgnoreCaseContainingAndOwnedBy(createdBy, ownedBy, pageable);
    }

    // finalisedDate
    public List<Record> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Record> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedDateAndOwnedBy(finalisedDate, ownedBy);
    }

    public List<Record> findByArchivedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedDateBetweenAndOwnedBy(start, end, ownedBy);
    }

    public Page<Record> findByArchivedDateAndOwnedBy(Date finalisedDate, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedDateAndOwnedBy(finalisedDate, ownedBy, pageable);
    }

    public Page<Record> findByArchivedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedDateBetweenAndOwnedBy(start, end, ownedBy, pageable);
    }

    // finalisedBy
    public List<Record> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Record> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Record> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy);
    }

    public List<Record> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Record> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public List<Record> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, sort);
    }

    public Page<Record> findByArchivedByAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Record> findByArchivedByContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    public Page<Record> findByArchivedByIgnoreCaseContainingAndOwnedBy(String finalisedBy, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByArchivedByIgnoreCaseContainingAndOwnedBy(finalisedBy, ownedBy, pageable);
    }

    // deleted
    public List<Record> findByDeletedAndOwnedBy(String deleted, String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByDeletedAndOwnedBy(deleted, ownedBy);
    }

    public List<Record> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByDeletedAndOwnedBy(deleted, ownedBy, sort);
    }

    public Page<Record> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByDeletedAndOwnedBy(deleted, ownedBy, pageable);
    }

    // ownedBy
    public List<Record> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByOwnedBy(ownedBy);
    }

    public List<Record> findByOwnedBy(String ownedBy, Sort sort) {return recordRepository.findByOwnedBy(ownedBy, sort);}

    public Page<Record> findByOwnedBy(String ownedBy, Pageable pageable) {return recordRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public Record update(Record record){
        return recordRepository.save(record);
    }

    public Record updateRecordSetFinalized(Long id){
        Record record = recordRepository.findById(id);

        if (record == null) {
            // throw Object not find
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return recordRepository.save(record);
    }

    public Record updateRecordSetTitle(Long id, String newTitle){

        Record record = recordRepository.findById(id);

        return recordRepository.save(record);
    }


    // All READ operations

    public List<Record> findRecordByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Record> criteriaQuery = criteriaBuilder.createQuery(Record.class);
        Root<Record> from = criteriaQuery.from(Record.class);
        CriteriaQuery<Record> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<Record> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }

    // All UPDATE operations
    @Override
    public Record handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Record incomingRecord) {
        Record existingRecord = getRecordOrThrow(systemId);
        // Here copy all the values you are allowed to copy ....
        // TODO: FIND ALL VALUES
        // This might be a class that can only have values set via parameter values rather than request bodies
        existingRecord.setVersion(version);
        recordRepository.save(existingRecord);
        return existingRecord;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String recordSystemId) {
        Record record = getRecordOrThrow(recordSystemId);

        // See issue for a description of why this code was written this way
        // https://github.com/HiOA-ABI/nikita-noark5-core/issues/82
        //Query q = entityManager.createNativeQuery("DELETE FROM fonds_fonds_creator WHERE pk_fonds_creator_id  = :id ;");
        //q.setParameter("id", record.getId());
        //q.executeUpdate();
        entityManager.remove(record);
        entityManager.flush();
        entityManager.clear();
    }

    // All HELPER operations
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid Record back. If there is no valid
     * Record, an exception is thrown
     *
     * @param recordSystemId
     * @return
     */
    protected Record getRecordOrThrow(@NotNull String recordSystemId) {
        Record record = recordRepository.findBySystemId(recordSystemId);
        if (record == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Record, using systemId " + recordSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return record;
    }

}
