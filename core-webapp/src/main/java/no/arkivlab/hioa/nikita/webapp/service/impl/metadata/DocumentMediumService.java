package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.metadata.DocumentMedium;
import nikita.repository.n5v4.metadata.IDocumentMediumRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IDocumentMediumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DocumentMediumService implements IDocumentMediumService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentMediumService.class);
    private IDocumentMediumRepository documentMediumRepository;

    @Autowired
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
        return documentMediumRepository.save(documentMedium);
    }

    // All READ operations

    @Override
    public Iterable<DocumentMedium> findAll() {
        return documentMediumRepository.findAll();
    }

    // systemId
    @Override
    public DocumentMedium findBySystemId(String systemId) {
        return documentMediumRepository.findBySystemId(systemId);
    }

    // description
    @Override
    public List<DocumentMedium> findByDescription(String description) {
        return documentMediumRepository.findByDescription(description);
    }

    // code
    @Override
    public List<DocumentMedium> findByCode(String code) {
        return documentMediumRepository.findByCode(code);
    }
}
