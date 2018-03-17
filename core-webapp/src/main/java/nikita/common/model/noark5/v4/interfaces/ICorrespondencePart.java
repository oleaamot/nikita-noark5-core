package nikita.common.model.noark5.v4.interfaces;

import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;

import java.util.List;

/**
 * Created by tsodring on 12/7/16.
 */

public interface ICorrespondencePart {

    List<CorrespondencePartPerson> getReferenceCorrespondencePartPerson();

    void setReferenceCorrespondencePartPerson(List<CorrespondencePartPerson> referenceCorrespondencePartPerson);

    List<CorrespondencePartUnit> getReferenceCorrespondencePartUnit();

    void setReferenceCorrespondencePartUnit(List<CorrespondencePartUnit> referenceCorrespondencePartUnit);

    List<CorrespondencePartInternal> getReferenceCorrespondencePartInternal();

    void setReferenceCorrespondencePartInternal(List<CorrespondencePartInternal> referenceCorrespondencePartInternal);

}
