package no.arkivlab.hioa.nikita.webapp.service.impl.imprt;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.DocumentObject;
import nikita.repository.n5v4.IDocumentDescriptionRepository;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.impl.DocumentObjectService;
import no.arkivlab.hioa.nikita.webapp.service.impl.FileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IDocumentDescriptionService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IDocumentDescriptionImportService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class DocumentDescriptionImportService implements IDocumentDescriptionImportService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentDescriptionImportService.class);


    @Autowired
    IDocumentDescriptionRepository documentDescriptionRepository;

    public DocumentDescriptionImportService() {
    }

    // All CREATE operations

    public DocumentDescription save(DocumentDescription documentDescription){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null) {
            throw new NikitaException("Security context problem. username is null! Cannot continue with " +
                    "this request!");
        }
        if (documentDescription.getCreatedDate() == null) {
            documentDescription.setCreatedDate(new Date());
        }
        if (documentDescription.getCreatedBy() == null) {
            documentDescription.setCreatedBy(username);
        }
        if (documentDescription.getOwnedBy() == null) {
            documentDescription.setOwnedBy(username);
        }
        documentDescription.setDeleted(false);
        return documentDescriptionRepository.save(documentDescription);
    }
}
