package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.secondary.CrossReference;

import java.util.Set;

/**
 *Created by tsodring on 12/7/16.
 */

public interface ICrossReference {
    Set <CrossReference> getReferenceCrossReference();
    void setReferenceCrossReference(Set<CrossReference> crossReference);
}
