package nikita.webapp.service.interfaces.secondary;


import nikita.common.model.noark5.v4.casehandling.Precedence;

public interface IPrecedenceService {

    Precedence updatePrecedence(String systemId, Long version,
                                                Precedence incomingPrecedence);

    Precedence createNewPrecedence(Precedence entity);

    Precedence findBySystemId(String precedenceSystemId);
}
