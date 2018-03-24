package nikita.webapp.service.interfaces.metadata;

import nikita.common.model.noark5.v4.metadata.DocumentStatus;

import java.util.List;

/**
 * Created by tsodring on 31/1/18.
 */
public interface IDocumentStatusService {

    DocumentStatus createNewDocumentStatus(DocumentStatus documentStatus);

    Iterable<DocumentStatus> findAll();

    List<DocumentStatus> findAllAsList();

    DocumentStatus findBySystemId(String systemId);

    DocumentStatus update(DocumentStatus documentStatus);

    List<DocumentStatus> findByDescription(String description);

    List<DocumentStatus> findByCode(String code);
}
