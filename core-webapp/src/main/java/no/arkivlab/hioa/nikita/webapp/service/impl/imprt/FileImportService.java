package no.arkivlab.hioa.nikita.webapp.service.impl.imprt;

import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Record;
import nikita.repository.n5v4.IFileRepository;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.impl.FileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IFileImportService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IRecordImportService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class FileImportService implements IFileImportService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    IRecordImportService recordImportService;

    @Autowired
    IFileRepository fileRepository;

    @Override
    public File createFile(File file) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null) {
            throw new NikitaException("Security context problem. username is null! Cannot continue with " +
                    "this request!");
        }
        if (file.getCreatedDate() == null) {
            file.setCreatedDate(new Date());
        }
        if (file.getCreatedBy() == null) {
            file.setCreatedBy(username);
        }
        if (file.getOwnedBy() == null) {
            file.setOwnedBy(username);
        }
        file.setDeleted(false);

        return fileRepository.save(file);
    }

    @Override
    public Record createRecordAssociatedWithFile(String fileSystemId, Record record) {
        Record persistedRecord = null;
        File file = fileRepository.findBySystemId(fileSystemId);
        if (file == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " File, using fileSystemId " + fileSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            record.setReferenceFile(file);
            persistedRecord = recordImportService.save(record);
        }
        return persistedRecord;
    }

    @Override
    public BasicRecord createBasicRecordAssociatedWithFile(String fileSystemId, BasicRecord basicRecord) {
        BasicRecord persistedBasicRecord = null;
        File file = fileRepository.findBySystemId(fileSystemId);
        if (file == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " File, using fileSystemId " + fileSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            basicRecord.setReferenceFile(file);
            persistedBasicRecord = (BasicRecord) recordImportService.save(basicRecord);
        }
        return persistedBasicRecord;
    }
}
