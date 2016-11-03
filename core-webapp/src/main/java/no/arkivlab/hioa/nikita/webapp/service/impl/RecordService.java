package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.Record;
import nikita.repository.n5v4.IRecordRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RecordService implements IRecordService {

    @Autowired
    IRecordRepository recordRepository;

    public RecordService() {
    }

    // All CREATE operations
    public Record save(Record record){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();


        record.setSystemId(UUID.randomUUID().toString());
        record.setCreatedDate(new Date());
        record.setOwnedBy(username);
        record.setCreatedBy(username);
        record.setDeleted(false);

        // Have to handle referenceToFonds. If it is not set do not allow persisit
        // throw illegalstructure exception

        // How do handle referenceToPrecusor? Update the entire object?? No patch?

        return recordRepository.save(record);
    }

    // All READ operations
    public Iterable<Record> findAll() {
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
        return recordRepository.findBySystemId(systemId);
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


    // All DELETE operations


}
