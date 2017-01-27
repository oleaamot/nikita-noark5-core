package no.arkivlab.hioa.nikita.webapp.service.impl.imprt;

import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.Record;
import nikita.repository.n5v4.IFileRepository;
import no.arkivlab.hioa.nikita.webapp.service.impl.FileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IFileImportService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IRecordImportService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            persistedBasicRecord = (BasicRecord)recordImportService.save((Record)basicRecord);
        }
        return persistedBasicRecord;
    }
}
