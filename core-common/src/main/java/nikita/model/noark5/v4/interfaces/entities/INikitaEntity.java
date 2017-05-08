package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;

public interface INikitaEntity extends Serializable {
    Long getId();
    void setId(Long id);
    String getSystemId();
    void setSystemId(String systemId);
    String getOwnedBy();
    void setOwnedBy(String ownedBy);
    Boolean getDeleted();
    void setDeleted(Boolean deleted);
    void setVersion(Long version);
    Long getVersion();
    String getBaseTypeName();
    //boolean validateForUpdate(String description);
    //boolean validateForCreate(String description);
}
