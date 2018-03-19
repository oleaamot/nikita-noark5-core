package nikita.webapp.service.impl;

import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.Record;
import nikita.common.repository.n5v4.IRecordRepository;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.service.interfaces.IRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.*;

import static nikita.common.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class RecordService implements IRecordService {

    private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

    private DocumentDescriptionService documentDescriptionService;
    private IRecordRepository recordRepository;
    private EntityManager entityManager;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    private Integer maxPageSize = 10;

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
    public DocumentDescription createDocumentDescriptionAssociatedWithRecord(String systemID, DocumentDescription documentDescription) {
        DocumentDescription persistedDocumentDescription = null;
        Record record = recordRepository.findBySystemId(systemID);
        if (record == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Record, using systemID " + systemID;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            ArrayList<Record> records = (ArrayList<Record>) documentDescription.getReferenceRecord();

            if (records == null) {
                records = new ArrayList<>();
                documentDescription.setReferenceRecord(records);
            }
            records.add(record);
            List<DocumentDescription> documentDescriptions = record.getReferenceDocumentDescription();
            documentDescriptions.add(documentDescription);
            persistedDocumentDescription = documentDescriptionService.save(documentDescription);
        }
        return persistedDocumentDescription;
    }

    // All READ operations
    public List<Record> findAll() {
        return recordRepository.findAll();
    }


    // id
    public Optional<Record> findById(Long id) {
        return recordRepository.findById(id);
    }

    // systemId
    public Record findBySystemId(String systemId) {
        return getRecordOrThrow(systemId);
    }


    // ownedBy
    public List<Record> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return recordRepository.findByOwnedBy(ownedBy);
    }

    // All UPDATE operations
    public Record update(Record record){
        return recordRepository.save(record);
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
    public void deleteEntity(@NotNull String systemID) {
        Record record = getRecordOrThrow(systemID);

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
     * @param systemID
     * @return
     */
    protected Record getRecordOrThrow(@NotNull String systemID) {
        Record record = recordRepository.findBySystemId(systemID);
        if (record == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Record, using systemId " + systemID;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return record;
    }

}
