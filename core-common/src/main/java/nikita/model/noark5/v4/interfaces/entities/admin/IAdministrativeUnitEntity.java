package nikita.model.noark5.v4.interfaces.entities.admin;

import nikita.model.noark5.v4.admin.AdministrativeUnit;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkCreateEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkFinaliseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tsodring on 1/16/17.
 */
public interface IAdministrativeUnitEntity extends INikitaEntity, INoarkCreateEntity, INoarkFinaliseEntity, Serializable {

    String getShortName();

    void setShortName(String shortName);

    String getAdministrativeUnitName();

    void setAdministrativeUnitName(String administrativeUnitName);

    String getAdministrativeUnitStatus();

    void setAdministrativeUnitStatus(String administrativeUnitStatus);

    AdministrativeUnit getParentAdministrativeUnit();

    void setParentAdministrativeUnit(AdministrativeUnit referenceParentAdministrativeUnit);

    List<AdministrativeUnit> getReferenceChildAdministrativeUnit();

    void setReferenceChildAdministrativeUnit(List<AdministrativeUnit> referenceChildAdministrativeUnit);
}
