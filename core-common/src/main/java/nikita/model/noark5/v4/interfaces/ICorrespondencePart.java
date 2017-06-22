package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;

import java.util.Set;

/**
 *Created by tsodring on 12/7/16.
 */

public interface ICorrespondencePart {

    Set<CorrespondencePartPerson> getReferenceCorrespondencePartPerson();

    void setReferenceCorrespondencePartPerson(Set<CorrespondencePartPerson> referenceCorrespondencePartPerson);

    Set<CorrespondencePartUnit> getReferenceCorrespondencePartUnit();

    void setReferenceCorrespondencePartUnit(Set<CorrespondencePartUnit> referenceCorrespondencePartUnit);

    Set<CorrespondencePartInternal> getReferenceCorrespondencePartInternal();

    void setReferenceCorrespondencePartInternal(Set<CorrespondencePartInternal> referenceCorrespondencePartInternal);

}
