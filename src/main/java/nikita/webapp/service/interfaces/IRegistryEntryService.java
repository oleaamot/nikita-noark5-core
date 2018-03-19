package nikita.webapp.service.interfaces;

import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.casehandling.Precedence;
import nikita.common.model.noark5.v4.casehandling.RegistryEntry;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IRegistryEntryService {

    // All save methods
    RegistryEntry save(RegistryEntry registryEntry);

    DocumentDescription createDocumentDescriptionAssociatedWithRegistryEntry(
            String systemID, DocumentDescription documentDescription);

    /*

    TODO: Temp disabled!
    CorrespondencePartPerson createCorrespondencePartPersonAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartPerson correspondencePart);

    CorrespondencePartUnit createCorrespondencePartUnitAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartUnit correspondencePart);

    CorrespondencePartInternal createCorrespondencePartInternalAssociatedWithRegistryEntry(
            String systemID, CorrespondencePartInternal correspondencePart);
*/
    Precedence createPrecedenceAssociatedWithRecord(String registryEntrysystemID, Precedence precedence);

    // All find methods
    RegistryEntry findBySystemId(String systemId);
    List<RegistryEntry> findRegistryEntryByOwnerPaginated(Integer top, Integer skip);

    /*
  TODO: Temp disabled!
    List<CorrespondencePartPerson> getCorrespondencePartPersonAssociatedWithRegistryEntry(String systemID);

    List<CorrespondencePartInternal> getCorrespondencePartInternalAssociatedWithRegistryEntry(String systemID);

    List<CorrespondencePartUnit> getCorrespondencePartUnitAssociatedWithRegistryEntry(String systemID);
*/
    // All UPDATE operations
    RegistryEntry handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull RegistryEntry incomingRegistryEntry);

    // All DELETE operations
    void deleteEntity(@NotNull String systemId);
}
