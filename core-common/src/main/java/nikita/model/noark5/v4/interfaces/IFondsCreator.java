package nikita.model.noark5.v4.interfaces;


import nikita.model.noark5.v4.FondsCreator;

import java.util.List;

/**
 *Created by tsodring on 12/7/16.
 */

public interface IFondsCreator {
    List<FondsCreator> getReferenceFondsCreator();

    void setReferenceFondsCreator(List<FondsCreator> referenceFondsCreator);
}
