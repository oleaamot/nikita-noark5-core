package no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt;

import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.RegistryEntry;


public interface ICaseFileImportService extends IFileImportService{
	CaseFile save(CaseFile caseFile);
	RegistryEntry createRegistryEntryAssociatedWithCaseFile(String fileSystemId, RegistryEntry registryEntry);
}
