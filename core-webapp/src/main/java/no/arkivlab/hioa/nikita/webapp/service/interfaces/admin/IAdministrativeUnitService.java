package no.arkivlab.hioa.nikita.webapp.service.interfaces.admin;


import nikita.model.noark5.v4.admin.AdministrativeUnit;

import java.util.List;

public interface IAdministrativeUnitService {

    AdministrativeUnit update(String systemId, Long version,
                              AdministrativeUnit incomingAdministrativeUnit);

    AdministrativeUnit createNewAdministrativeUnit(AdministrativeUnit entity);

    AdministrativeUnit findBySystemIdOrderBySystemId(String administrativeUnitSystemId);

    List<AdministrativeUnit> findAll();
}
