package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.casehandling.Precedence;
import nikita.model.noark5.v4.casehandling.RegistryEntry;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IRegistryEntryService {

    // All save methods
    RegistryEntry save(RegistryEntry registryEntry);

    DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
            String systemID, DocumentDescription documentDescription);

    CorrespondencePartPerson createCorrespondencePartPersonAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartPerson correspondencePart);

    CorrespondencePartUnit createCorrespondencePartUnitAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartUnit correspondencePart);

    CorrespondencePartInternal createCorrespondencePartInternalAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartInternal correspondencePart);

    Precedence createPrecedenceAssociatedWithRecord(String registryEntrysystemID, Precedence precedence);

    // All find methods
    RegistryEntry findBySystemId(String systemId);
    List<RegistryEntry> findRegistryEntryByOwnerPaginated(Integer top, Integer skip);

    // All UPDATE operations
    RegistryEntry handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull RegistryEntry incomingRegistryEntry);

    // All DELETE operations
    void deleteEntity(@NotNull String systemId);
}
