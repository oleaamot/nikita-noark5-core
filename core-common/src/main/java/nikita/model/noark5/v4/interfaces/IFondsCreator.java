package nikita.model.noark5.v4.interfaces;


import nikita.model.noark5.v4.FondsCreator;

import java.util.Set;

/**
 *Created by tsodring on 12/7/16.
 */

public interface IFondsCreator {
    Set<FondsCreator> getReferenceFondsCreator();
    void setReferenceFondsCreator(Set<FondsCreator> referenceFondsCreator) ;
}
