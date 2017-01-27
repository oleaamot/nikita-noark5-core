package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.Conversion;

import java.util.Set;

/**
 *Created by tsodring on 12/7/16.
 */
public interface IConversion {
    Set<Conversion> getReferenceConversion();
    void setReferenceConversion(Set<Conversion> conversion);
}
