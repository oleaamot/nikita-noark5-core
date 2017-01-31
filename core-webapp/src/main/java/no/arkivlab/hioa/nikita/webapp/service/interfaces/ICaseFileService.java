package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.RegistryEntry;


public interface ICaseFileService extends IFileService{

	CaseFile save(CaseFile caseFile);
	RegistryEntry createRegistryEntryAssociatedWithCaseFile(String fileSystemId, RegistryEntry registryEntry);

    Iterable<CaseFile> findCaseFileByOwnerPaginated(Integer top, Integer skip);
}
