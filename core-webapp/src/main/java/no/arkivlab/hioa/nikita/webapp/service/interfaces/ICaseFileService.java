package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.RegistryEntry;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface ICaseFileService {

	CaseFile save(CaseFile caseFile);

	RegistryEntry createRegistryEntryAssociatedWithCaseFile(String fileSystemId, RegistryEntry registryEntry);

    CaseFile findBySystemId(String systemId);

    List<CaseFile> findCaseFileByOwnerPaginated(Integer top, Integer skip);
    // All UPDATE operations
    CaseFile handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull CaseFile incomingCaseFile);

    // All DELETE operations
    void deleteEntity(@NotNull String systemId);
}
