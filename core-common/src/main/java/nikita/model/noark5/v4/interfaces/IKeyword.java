package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.Keyword;
import java.util.Set;

/**
 * Created by tsodring on 12/7/16.
 */

public interface IKeyword {
    Set<Keyword> getReferenceKeyword();
    void setReferenceKeyword(Set<Keyword> keywords);
}
