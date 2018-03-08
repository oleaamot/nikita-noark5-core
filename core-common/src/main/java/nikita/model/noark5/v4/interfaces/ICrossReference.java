package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.secondary.CrossReference;

import java.util.List;

/**
 *Created by tsodring on 12/7/16.
 */

public interface ICrossReference {
    List<CrossReference> getReferenceCrossReference();

    void setReferenceCrossReference(List<CrossReference> crossReference);
}
