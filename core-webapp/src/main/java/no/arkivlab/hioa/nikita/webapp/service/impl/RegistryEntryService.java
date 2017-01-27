package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.repository.n5v4.IRegistryEntryRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class RegistryEntryService extends BasicRecordService implements IRegistryEntryService {

    private static final Logger logger = LoggerFactory.getLogger(RegistryEntryService.class);

    @Autowired
    DocumentDescriptionService documentDescriptionService;

    @Autowired
    IRegistryEntryRepository registryEntryRepository;

    public RegistryEntryService() {
    }

    @Override
    public RegistryEntry save(RegistryEntry registryEntry) {
        NoarkUtils.NoarkEntity.Create.setNikitaEntityValues(registryEntry);
        NoarkUtils.NoarkEntity.Create.setSystemIdEntityValues(registryEntry);
        NoarkUtils.NoarkEntity.Create.setCreateEntityValues(registryEntry);
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(registryEntry);
        registryEntryRepository.save(registryEntry);
        return registryEntry;
    }

    @Override
    public DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
            String recordSystemId, DocumentDescription documentDescription){

        DocumentDescription persistedDocumentDescription = null;
        RegistryEntry registryEntry = registryEntryRepository.findBySystemId(recordSystemId);
        if (registryEntry == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " RegistryEntry, using registryEntrySystemId " + recordSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            HashSet <Record> records = (HashSet <Record>) documentDescription.getReferenceRecord();

            if (records == null) {
                records = new HashSet<>();
                documentDescription.setReferenceRecord(records);
            }
            records.add(registryEntry);
            persistedDocumentDescription = documentDescriptionService.save(documentDescription);
        }
        return persistedDocumentDescription;
    }

}
