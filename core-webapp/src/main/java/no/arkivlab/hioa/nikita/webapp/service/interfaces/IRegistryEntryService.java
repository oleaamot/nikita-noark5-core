package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.CorrespondencePart;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.RegistryEntry;

import java.util.List;

public interface IRegistryEntryService {

    // All save methods
    RegistryEntry save(RegistryEntry registryEntry);
    DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
            String recordSystemId, DocumentDescription documentDescription);
    CorrespondencePart createCorrespondencePartAssociatedWithRegistryEntry(
            String recordSystemId, CorrespondencePart correspondencePart);

    // All find methods
    RegistryEntry findBySystemId(String systemId);
    List<RegistryEntry> findRegistryEntryByOwnerPaginated(Integer top, Integer skip);
}