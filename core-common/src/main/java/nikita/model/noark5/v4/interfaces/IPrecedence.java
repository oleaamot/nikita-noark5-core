package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.casehandling.Precedence;

import java.util.List;

/**
 * Created by tsodring on 12/7/16.
 */
public interface IPrecedence {
    List<Precedence> getReferencePrecedence();

    void setReferencePrecedence(List<Precedence> precedence);
}
