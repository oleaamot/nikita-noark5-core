package no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary;


import nikita.model.noark5.v4.casehandling.CorrespondencePart;

public interface ICorrespondencePartService {

    CorrespondencePart updateCorrespondencePart(String systemId, Long version,
                                                CorrespondencePart incomingCorrespondencePart);

    CorrespondencePart createNewCorrespondencePart(CorrespondencePart entity);

    CorrespondencePart findBySystemId(String correspondencePartSystemId);
}
