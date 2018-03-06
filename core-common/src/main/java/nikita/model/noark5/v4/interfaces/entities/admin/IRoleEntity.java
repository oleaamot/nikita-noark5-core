package nikita.model.noark5.v4.interfaces.entities.admin;

import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 5/23/17.
 */
public interface IRoleEntity extends INikitaEntity {
    String getRole();

    void setRole(String role);

    String getAccessCategory();

    void setAccessCategory(String accessCategory);

    String getReferenceEntity();

    void setReferenceEntity(String referenceEntity);

    String getAccessRestriction();

    void setAccessRestriction(String accessRestriction);

    Boolean getRead();

    void setRead(Boolean read);

    Boolean getCreate();

    void setCreate(Boolean create);

    Boolean getUpdate();

    void setUpdate(Boolean update);

    Boolean getDelete();

    void setDelete(Boolean delete);

}
