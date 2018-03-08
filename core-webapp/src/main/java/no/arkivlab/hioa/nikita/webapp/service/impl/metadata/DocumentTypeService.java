package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.DocumentType;
import nikita.repository.n5v4.metadata.IDocumentTypeRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.IDocumentTypeService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.DOCUMENT_TYPE;

/**
 * Created by tsodring on 07/02/18.
 */


@Service
@Transactional
public class DocumentTypeService
        implements IDocumentTypeService {

    private static final Logger logger =
            LoggerFactory.getLogger(DocumentTypeService.class);

    private IDocumentTypeRepository documentTypeRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public DocumentTypeService(IDocumentTypeRepository documentTypeRepository,
                               IMetadataHateoasHandler metadataHateoasHandler,
                               ApplicationEventPublisher applicationEventPublisher) {
        this.documentTypeRepository = documentTypeRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new documentType object to the database.
     *
     * @param documentType documentType object with values set
     * @return the newly persisted documentType object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewDocumentType(DocumentType documentType) {

        documentType.setDeleted(false);
        documentType.setOwnedBy(
                SecurityContextHolder.getContext().
                        getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                documentTypeRepository.save(documentType));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all documentType objects
     *
     * @return list of documentType objects wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        documentTypeRepository.findAll(), DOCUMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single documentType object identified by systemId
     *
     * @param systemId
     * @return single documentType object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                documentTypeRepository.save(
                        documentTypeRepository.findBySystemId(systemId)));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all documentType that have a given description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description
     * @return A list of documentType objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        documentTypeRepository.findByDescription(description),
                DOCUMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all documentType that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code
     * @return A list of documentType objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        documentTypeRepository.findByCode(code),
                DOCUMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default DocumentType object
     *
     * @return the DocumentType object wrapped as a DocumentTypeHateoas object
     */
    @Override
    public DocumentType generateDefaultDocumentType() {

        DocumentType documentType = new DocumentType();
        documentType.setCode(TEMPLATE_DOCUMENT_TYPE_CODE);
        documentType.setDescription(TEMPLATE_DOCUMENT_TYPE_DESCRIPTION);

        return documentType;
    }

    /**
     * Update a documentType identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param documentType
     * @return the updated documentType
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, DocumentType documentType) {

        DocumentType existingDocumentType = getDocumentTypeOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingDocumentType.getCode()) {
            existingDocumentType.setCode(existingDocumentType.getCode());
        }
        if (null != existingDocumentType.getDescription()) {
            existingDocumentType.setDescription(existingDocumentType.
                    getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingDocumentType.setVersion(version);

        MetadataHateoas documentTypeHateoas = new MetadataHateoas(
                documentTypeRepository.save(existingDocumentType));
        metadataHateoasHandler.addLinks(documentTypeHateoas,
                new Authorisation());
        applicationEventPublisher.publishEvent(
                new AfterNoarkEntityUpdatedEvent(this,
                        existingDocumentType));
        return documentTypeHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid DocumentType object back. If there
     * is no DocumentType object, a NoarkEntityNotFoundException exception
     * is thrown
     *
     * @param systemId The systemId of the DocumentType object to retrieve
     * @return the DocumentType object
     */
    private DocumentType getDocumentTypeOrThrow(@NotNull String systemId) {
        DocumentType documentType = documentTypeRepository.findBySystemId
                (systemId);
        if (documentType == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " DocumentType, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return documentType;
    }

}
