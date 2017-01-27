package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;

public interface INikitaEntity extends Serializable {
    Long getId();
    void setId(Long id);
    String getOwnedBy();
    void setOwnedBy(String ownedBy);
    Boolean getDeleted();
    void setDeleted(Boolean deleted);
}
