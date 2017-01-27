package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.repository.n5v4.ICaseFileRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ICaseFileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class CaseFileService extends FileService implements ICaseFileService {

    private static final Logger logger = LoggerFactory.getLogger(CaseFileService.class);

    @Autowired
    IRegistryEntryService registryEntryService;

    @Autowired
    ICaseFileRepository caseFileRepository;

    public CaseFileService() {
    }

    @Override
    public CaseFile save(CaseFile caseFile) {
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(caseFile);
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(caseFile);
        return caseFileRepository.save(caseFile);
    }

    @Override
    public RegistryEntry createRegistryEntryAssociatedWithCaseFile(String fileSystemId, RegistryEntry registryEntry) {
        RegistryEntry persistedRecord = null;
        CaseFile file = caseFileRepository.findBySystemId(fileSystemId);
        if (file == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " CaseFile, using fileSystemId " + fileSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            registryEntry.setReferenceFile(file);
            persistedRecord = registryEntryService.save(registryEntry);
        }
        return persistedRecord;        
    }
}
