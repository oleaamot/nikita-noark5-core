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
public class CaseFileService implements ICaseFileService {

    private static final Logger logger = LoggerFactory.getLogger(CaseFileService.class);
    private IRegistryEntryService registryEntryService;
    private ICaseFileRepository caseFileRepository;
    private EntityManager entityManager;

    public CaseFileService(IRegistryEntryService registryEntryService, ICaseFileRepository caseFileRepository, EntityManager entityManager) {
        this.registryEntryService = registryEntryService;
        this.caseFileRepository = caseFileRepository;
        this.entityManager = entityManager;
    }

    //@Value("${nikita-noark5-core.pagination.maxPageSize}")
    Integer maxPageSize = new Integer(10);

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
    public RegistryEntry createRegistryEntryAssociatedWithCaseFile(@NotNull String fileSystemId, @NotNull RegistryEntry registryEntry) {
        CaseFile caseFile = getCaseFileOrThrow(fileSystemId);
        // bidirectional relationship @OneToMany and @ManyToOne, set both sides of relationship
        registryEntry.setReferenceFile(caseFile);
        caseFile.getReferenceRecord().add(registryEntry);
        return registryEntryService.save(registryEntry);
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
    /**
     * Internal helper method. Rather than having a find and try catch in multiple methods, we have it here once.
     * If you call this, be aware that you will only ever get a valid CaseFile back. If there is no valid
     * CaseFile, an exception is thrown
     *
     * @param caseFileSystemId
     * @return
     */
    protected CaseFile getCaseFileOrThrow(@NotNull String caseFileSystemId) {
        CaseFile caseFile = caseFileRepository.findBySystemId(caseFileSystemId);
        if (caseFile == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " CaseFile, using systemId " + caseFileSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return caseFile;
    }
}
