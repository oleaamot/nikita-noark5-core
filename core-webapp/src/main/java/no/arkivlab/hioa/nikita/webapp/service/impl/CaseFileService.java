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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class CaseFileService implements ICaseFileService {

    private static final Logger logger = LoggerFactory.getLogger(CaseFileService.class);

    @Autowired
    IRegistryEntryService registryEntryService;

    @Autowired
    ICaseFileRepository caseFileRepository;

    @Autowired
    EntityManager entityManager;

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);

    public CaseFileService() {
    }

    @Override
    public CaseFile save(CaseFile caseFile) {
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(caseFile);
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(caseFile);
        return caseFileRepository.save(caseFile);
    }

    // systemId
    public CaseFile findBySystemId(String systemId) {
        return caseFileRepository.findBySystemId(systemId);
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

    // All READ operations
    @Override
    public List<CaseFile> findCaseFileByOwnerPaginated(Integer top, Integer skip) {
        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CaseFile> criteriaQuery = criteriaBuilder.createQuery(CaseFile.class);
        Root<CaseFile> from = criteriaQuery.from(CaseFile.class);
        CriteriaQuery<CaseFile> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<CaseFile> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(maxPageSize);
        return typedQuery.getResultList();
    }
}
