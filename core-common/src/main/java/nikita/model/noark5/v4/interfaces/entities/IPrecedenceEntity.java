package nikita.model.noark5.v4.interfaces.entities;

import java.util.Date;

/**
 * Created by tsodring on 1/16/17.
 */
public interface IPrecedenceEntity extends  INoarkCreateEntity, INoarkTitleDescriptionEntity, INoarkFinaliseEntity
{
    Date getPrecedenceDate();
    void setPrecedenceDate(Date precedenceDate);
    String getPrecedenceAuthority();
    void setPrecedenceAuthority(String precedenceAuthority);
    String getSourceOfLaw();
    void setSourceOfLaw(String sourceOfLaw);
    Date getPrecedenceApprovedDate();
    void setPrecedenceApprovedDate(Date precedenceApprovedDate);
    String getPrecedenceApprovedBy();
    void setPrecedenceApprovedBy(String precedenceApprovedBy);
    String getPrecedenceStatus();
    void setPrecedenceStatus(String precedenceStatus);
}
