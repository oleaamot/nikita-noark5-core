package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.Precedence;

import java.util.Set;

/**
 * Created by tsodring on 12/7/16.
 */
public interface IPrecedence {
    void setReferencePrecedence(Set<Precedence> precedence);
    Set<Precedence> getReferencePrecedence();
}
