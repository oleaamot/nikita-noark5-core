package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.DocumentType;

public interface IDocumentTypeService {

    MetadataHateoas createNewDocumentType(DocumentType documentType);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, DocumentType
            documentType);

    DocumentType generateDefaultDocumentType();
}
