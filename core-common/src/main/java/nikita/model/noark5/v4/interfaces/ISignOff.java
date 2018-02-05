package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.secondary.SignOff;

import java.util.List;

/**
 * Created by tsodring on 12/7/16.
 */
public interface ISignOff {
    List<SignOff> getReferenceSignOff();

    void setReferenceSignOff(List<SignOff> signOff);
}
