package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Record;
import nikita.repository.n5v4.IFileRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRecordService;
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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class FileService implements IFileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    private IRecordService recordService;
    private IFileRepository fileRepository;
    private EntityManager entityManager;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = 10;

    public FileService(IRecordService recordService,
                       IFileRepository fileRepository,
                       EntityManager entityManager) {
        this.recordService = recordService;
        this.fileRepository = fileRepository;
        this.entityManager = entityManager;
    }

    @Override
    public File createFile(File file) {
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(file);
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(file);
        NoarkUtils.NoarkEntity.Create.setFinaliseEntityValues(file);
        return fileRepository.save(file);
    }

    @Override
    public Record createRecordAssociatedWithFile(String fileSystemId, Record record) {
        Record persistedRecord;
        File file = fileRepository.findBySystemIdOrderBySystemId(fileSystemId);
        if (file == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " File, using fileSystemId " + fileSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            record.setReferenceFile(file);
            persistedRecord = recordService.save(record);
        }
        return persistedRecord;        
    }

    @Override
    public BasicRecord createBasicRecordAssociatedWithFile(String fileSystemId, BasicRecord basicRecord) {
        BasicRecord persistedBasicRecord;
        File file = fileRepository.findBySystemIdOrderBySystemId(fileSystemId);
        if (file == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " File, using fileSystemId " + fileSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            basicRecord.setReferenceFile(file);
            persistedBasicRecord = (BasicRecord) recordService.save(basicRecord);
        }
        return persistedBasicRecord;
    }

    // All READ operations
    public List<File> findAll() {
        return fileRepository.findAll();
    }

    public List<File> findAll(Sort sort) {
        return fileRepository.findAll(sort);
    }

    public Page<File> findAll(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }

    // id
    public File findById(Long id) {
        return fileRepository.findById(id);
    }

    // ownedBy
    public List<File> findByOwnedBy(String ownedBy) {
        ownedBy = (ownedBy == null) ? SecurityContextHolder.getContext().getAuthentication().getName():ownedBy;
        return fileRepository.findByOwnedBy(ownedBy);
    }

    public List<File> findByOwnedBy(String ownedBy, Sort sort) {return fileRepository.findByOwnedBy(ownedBy, sort);}

    public Page<File> findByOwnedBy(String ownedBy, Pageable pageable) {return fileRepository.findByOwnedBy(ownedBy, pageable);}

    // All UPDATE operations
    public File update(File file){
        return fileRepository.save(file);
    }

    // All READ operations


    // systemId
    @Override
    public File findBySystemId(String systemId) {
        return getFileOrThrow(systemId);
    }

    @Override
    public List<File> findFileByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<File> criteriaQuery = criteriaBuilder.createQuery(File.class);
        Root<File> from = criteriaQuery.from(File.class);
        CriteriaQuery<File> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<File> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }


    // All UPDATE operations
    @Override
    public File handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull File incomingFile) {
        File existingFile = getFileOrThrow(systemId);
        // Here copy all the values you are allowed to copy ....
        if (null != existingFile.getDescription()) {
            existingFile.setDescription(incomingFile.getDescription());
        }
        if (null != existingFile.getTitle()) {
            existingFile.setTitle(incomingFile.getTitle());
        }
        if (null != existingFile.getDocumentMedium()) {
            existingFile.setDocumentMedium(existingFile.getDocumentMedium());
        }
        existingFile.setVersion(version);
        fileRepository.save(existingFile);
        return existingFile;
    }

    // All DELETE operations
    @Override
    public void deleteEntity(@NotNull String fileSystemId) {
        File file = getFileOrThrow(fileSystemId);
        fileRepository.delete(file);
    }

    // All HELPER operations
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid File back. If there is no valid
     * File, an exception is thrown
     *
     * @param fileSystemId systemId of the file object you are looking for
     * @return the newly found file object or null if it does not exist
     */
    private File getFileOrThrow(@NotNull String fileSystemId) {
        File file = fileRepository.findBySystemIdOrderBySystemId(fileSystemId);
        if (file == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " File, using systemId " + fileSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return file;
    }
}
