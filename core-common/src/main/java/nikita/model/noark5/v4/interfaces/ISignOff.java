package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.SignOff;

import java.util.Set;

/**
 * Created by tsodring on 12/7/16.
 */
public interface ISignOff {
    void setReferenceSignOff(Set<SignOff> signOff);
    Set<SignOff> getReferenceSignOff();
}
