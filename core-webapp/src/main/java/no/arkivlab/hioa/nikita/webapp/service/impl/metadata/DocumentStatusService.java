package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.metadata.DocumentStatus;
import nikita.repository.n5v4.metadata.IDocumentStatusRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IDocumentStatusService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tsodring on 31/1/18.
 */


@Service
@Transactional
public class DocumentStatusService implements IDocumentStatusService {

    private IDocumentStatusRepository documentStatusRepository;

    public DocumentStatusService(IDocumentStatusRepository documentStatusRepository) {
        this.documentStatusRepository = documentStatusRepository;
    }

    // All CREATE operations

    /**
     * Persists a new documentStatus object to the database.
     *
     * @param documentStatus documentStatus object with values set
     * @return the newly persisted documentStatus object
     */
    @Override
    public DocumentStatus createNewDocumentStatus(DocumentStatus documentStatus) {
        documentStatus.setDeleted(false);
        documentStatus.setOwnedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return documentStatusRepository.save(documentStatus);
    }

    // All READ operations

    /**
     * retrieve all documentStatus
     *
     * @return
     */
    @Override
    public Iterable<DocumentStatus> findAll() {
        return documentStatusRepository.findAll();
    }

    // find by systemId

    /**
     * retrieve a single documentStatus identified by systemId
     *
     * @param systemId
     * @return
     */
    @Override
    public DocumentStatus findBySystemId(String systemId) {
        return documentStatusRepository.findBySystemId(systemId);
    }

    /**
     * retrieve all documentStatus that have a particular description. <br>
     * This will be replaced by OData search.
     *
     * @param description
     * @return
     */
    @Override
    public List<DocumentStatus> findByDescription(String description) {
        return documentStatusRepository.findByDescription(description);
    }

    /**
     * retrieve all documentStatus that have a particular code. <br>
     * This will be replaced by OData search.
     *
     * @param code
     * @return
     */
    @Override
    public List<DocumentStatus> findByCode(String code) {
        return documentStatusRepository.findByCode(code);
    }

    /**
     * update a particular documentStatus. <br>
     *
     * @param documentStatus
     * @return the updated documentStatus
     */
    @Override
    public DocumentStatus update(DocumentStatus documentStatus) {
        return documentStatusRepository.save(documentStatus);
    }

    @Override
    public List<DocumentStatus> findAllAsList() {
        return documentStatusRepository.findAll();
    }

}
