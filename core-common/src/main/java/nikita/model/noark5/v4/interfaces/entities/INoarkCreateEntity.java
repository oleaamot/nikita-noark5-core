package nikita.model.noark5.v4.interfaces.entities;

import java.util.Date;

public interface INoarkCreateEntity {

    Date getCreatedDate();
    void setCreatedDate(Date createdDate);
    String getCreatedBy();
    void setCreatedBy(String createdBy);
}
