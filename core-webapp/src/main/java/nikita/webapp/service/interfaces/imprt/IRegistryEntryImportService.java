package nikita.webapp.service.interfaces.imprt;


import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.casehandling.RegistryEntry;

public interface IRegistryEntryImportService {

	RegistryEntry save(RegistryEntry registryEntry);
	DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
            String systemID, DocumentDescription documentDescription);
}
