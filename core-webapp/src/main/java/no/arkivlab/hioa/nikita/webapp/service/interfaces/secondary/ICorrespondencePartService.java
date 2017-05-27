package no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary;


import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePart;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;

import javax.validation.constraints.NotNull;

public interface ICorrespondencePartService {

    CorrespondencePartPerson updateCorrespondencePartPerson(String systemId, Long version,
                                                            CorrespondencePartPerson incomingCorrespondencePart);

    CorrespondencePartUnit updateCorrespondencePartUnit(String systemId, Long version,
                                                        CorrespondencePartUnit incomingCorrespondencePart);

    CorrespondencePartInternal updateCorrespondencePartInternal(String systemId, Long version,
                                                                CorrespondencePartInternal incomingCorrespondencePart);

    CorrespondencePartUnit createNewCorrespondencePartUnit(CorrespondencePartUnit correspondencePartUnit);

    CorrespondencePartInternal createNewCorrespondencePartInternal(CorrespondencePartInternal correspondencePartUnit);

    CorrespondencePartPerson createNewCorrespondencePartPerson(CorrespondencePartPerson correspondencePartPerson);

    CorrespondencePart findBySystemIdOrderBySystemId(String correspondencePartSystemId);

    void deleteCorrespondencePartUnit(@NotNull String code);

    void deleteCorrespondencePartPerson(@NotNull String code);

    void deleteCorrespondencePartInternal(@NotNull String code);

}
