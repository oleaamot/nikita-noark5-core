package nikita.common.model.noark5.v4.interfaces.entities.casehandling;

import nikita.common.model.noark5.v4.admin.AdministrativeUnit;
import nikita.common.model.noark5.v4.admin.User;

/**
 * Created by tsodring on 5/22/17.
 */
public interface ICorrespondencePartInternalEntity extends ICorrespondencePartEntity {

    String getAdministrativeUnit();

    void setAdministrativeUnit(String administrativeUnit);

    AdministrativeUnit getReferenceAdministrativeUnit();

    void setReferenceAdministrativeUnit(AdministrativeUnit referenceAdministrativeUnit);

    String getCaseHandler();

    void setCaseHandler(String caseHandler);

    User getReferenceCaseHandler();

    void setReferenceCaseHandler(User referenceCaseHandler);
/*
  TODO: Temp disabled!
    List<RegistryEntry> getReferenceRegistryEntry();

    void setReferenceRegistryEntry(List<RegistryEntry> referenceRegistryEntry);
*/
}
