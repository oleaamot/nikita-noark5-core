package no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary;


import nikita.model.noark5.v4.casehandling.Precedence;

public interface IPrecedenceService {

    Precedence updatePrecedence(String systemId, Long version,
                                                Precedence incomingPrecedence);

    Precedence createNewPrecedence(Precedence entity);

    Precedence findBySystemId(String precedenceSystemId);
}
