package nikita.model.noark5.v4.interfaces.entities.casehandling;

import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.CorrespondencePartType;

import java.io.Serializable;

/**
 * Created by tsodring on 1/16/17.
 */
public interface ICorrespondencePartEntity extends INikitaEntity, Serializable {


    CorrespondencePartType getCorrespondencePartType();

    void setCorrespondencePartType(CorrespondencePartType correspondencePartType);


}
