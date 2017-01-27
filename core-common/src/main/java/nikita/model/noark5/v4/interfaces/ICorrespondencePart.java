package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.CorrespondencePart;

import java.util.Set;

/**
 *Created by tsodring on 12/7/16.
 */

public interface ICorrespondencePart {
    Set<CorrespondencePart> getReferenceCorrespondencePart();
    void setReferenceCorrespondencePart(Set<CorrespondencePart> correspondenceParts);
}
