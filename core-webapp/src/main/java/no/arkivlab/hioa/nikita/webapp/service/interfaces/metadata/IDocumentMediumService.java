package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.metadata.DocumentMedium;

import java.util.List;

/**
 * Created by tsodring on 3/9/17.
 */
public interface IDocumentMediumService {

    DocumentMedium createNewDocumentMedium(DocumentMedium documentMedium);

    Iterable<DocumentMedium> findAll();

    List<DocumentMedium> findAll2();

    DocumentMedium findBySystemId(String systemId);

    DocumentMedium update(DocumentMedium documentMedium);

    List<DocumentMedium> findByDescription(String description);

    List<DocumentMedium> findByCode(String code);
}
