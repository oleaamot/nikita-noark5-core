package nikita.model.noark5.v4.interfaces.entities;


import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by tsodring on 12/7/16.
 *
 */
 public interface IDisposalEntity extends Serializable {
    String getDisposalDecision();
    void setDisposalDecision(String disposalDecision);
    String getDisposalAuthority();
    void setDisposalAuthority(String disposalAuthority);
    Integer getPreservationTime();
    void setPreservationTime(Integer preservationTime);
    Date getDisposalDate();
    void setDisposalDate(Date disposalDate);
}
