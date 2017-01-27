package no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.DocumentObject;

public interface IDocumentDescriptionImportService {
	// -- All CREATE operations
	DocumentDescription save(DocumentDescription documentDescription);
}
