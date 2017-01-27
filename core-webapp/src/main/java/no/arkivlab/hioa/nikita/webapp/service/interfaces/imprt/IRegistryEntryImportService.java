package no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt;


import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.RegistryEntry;

public interface IRegistryEntryImportService {

	RegistryEntry save(RegistryEntry registryEntry);
	DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
			String recordSystemId, DocumentDescription documentDescription);
}
