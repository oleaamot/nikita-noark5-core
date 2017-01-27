package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by tsodring on 12/7/16.
 *
 */
public interface IDisposalUndertakenEntity extends Serializable {
    String getDisposalBy();
    void setDisposalBy(String disposalBy);
    Date getDisposalDate();
    void setDisposalDate(Date disposalDate);
}
