package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by tsodring on 12/7/16.
 *
 *  */
public interface IScreeningEntity extends Serializable {
    String getAccessRestriction();
    void setAccessRestriction(String accessRestriction);
    String getScreeningAuthority() ;
    void setScreeningAuthority(String screeningAuthority);
    String getScreeningMetadata() ;
    void setScreeningMetadata(String screeningMetadata);
    String getScreeningDocument() ;
    void setScreeningDocument(String screeningDocument) ;
    Date getScreeningExpiresDate() ;
    void setScreeningExpiresDate(Date screeningExpiresDate) ;
    String getScreeningDuration() ;
    void setScreeningDuration(String screeningDuration);
}
