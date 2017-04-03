package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.metadata.DocumentMedium;
import nikita.repository.n5v4.metadata.IDocumentMediumRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IDocumentMediumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DocumentMediumService implements IDocumentMediumService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentMediumService.class);
    private IDocumentMediumRepository documentMediumRepository;

    public DocumentMediumService(IDocumentMediumRepository documentMediumRepository) {
        this.documentMediumRepository = documentMediumRepository;
    }

    // All CREATE operations
    /**
     * Persists a new documentMedium object to the database.
     *
     * @param documentMedium documentMedium object with values set
     * @return the newly persisted documentMedium object
     */
    @Override
    public DocumentMedium createNewDocumentMedium(DocumentMedium documentMedium) {
        documentMedium.setDeleted(false);
        documentMedium.setOwnedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return documentMediumRepository.save(documentMedium);
    }

    // All READ operations

    /**
     * retrieve all documentMedium
     *
     * @return
     */
    @Override
    public Iterable<DocumentMedium> findAll() {
        return documentMediumRepository.findAll();
    }

    @Override
    public List<DocumentMedium> findAll2() {
        return documentMediumRepository.findAll();
    }

    // find by systemId

    /**
     * retrieve a single documentMedium identified by systemId
     *
     * @param systemId
     * @return
     */
    @Override
    public DocumentMedium findBySystemId(String systemId) {
        return documentMediumRepository.findBySystemId(systemId);
    }

    /**
     * retrieve all documentMedium that have a particular description. <br>
     * This will be replaced by OData search.
     *
     * @param description
     * @return
     */
    @Override
    public List<DocumentMedium> findByDescription(String description) {
        return documentMediumRepository.findByDescription(description);
    }

    /**
     * retrieve all documentMedium that have a particular code. <br>
     * This will be replaced by OData search.

     * @param code
     * @return
     */
    @Override
    public List<DocumentMedium> findByCode(String code) {
        return documentMediumRepository.findByCode(code);
    }

    /**
     * retrieve all documentMedium that have a particular code. <br>
     * This will be replaced by OData search.
     *
     * @param code
     * @return
     */
    @Override
    public DocumentMedium update(DocumentMedium documentMedium) {
        return documentMediumRepository.save(documentMedium);
    }
}
