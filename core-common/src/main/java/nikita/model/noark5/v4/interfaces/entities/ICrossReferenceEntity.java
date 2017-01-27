package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;

/**
 * Created by tsodring on 1/16/17.
 */
public interface ICrossReferenceEntity extends Serializable {
    String getReferenceToFile();
    void setReferenceToFile(String referenceToFile);
    String getReferenceToRecord();
    void setReferenceToRecord(String referenceToRecord);
    String getReferenceToClass();
    void setReferenceToClass(String referenceToClass);
}
