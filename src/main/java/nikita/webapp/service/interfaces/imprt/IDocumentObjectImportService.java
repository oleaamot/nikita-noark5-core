package nikita.webapp.service.interfaces.imprt;


import nikita.common.model.noark5.v4.DocumentObject;

/**
 * This service only supports the importing of documentObjects
 * All other requests should be via other services
 */
public interface IDocumentObjectImportService {
	// -- All CREATE operations
	DocumentObject save(DocumentObject documentObject);
}
