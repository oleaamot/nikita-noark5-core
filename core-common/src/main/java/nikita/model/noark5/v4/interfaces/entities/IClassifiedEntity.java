package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tsodring on 12/7/16.
 */
public interface IClassifiedEntity extends Serializable {
    String getClassification();
    void setClassification(String classification);
    Date getClassificationDate();
    void setClassificationDate(Date classificationDate);
    String getClassificationBy();
    void setClassificationBy(String classificationBy);
    Date getClassificationDowngradedDate();
    void setClassificationDowngradedDate(Date classificationDowngradedDate);
    String getClassificationDowngradedBy();
    void setClassificationDowngradedBy(String classificationDowngradedBy);

}
