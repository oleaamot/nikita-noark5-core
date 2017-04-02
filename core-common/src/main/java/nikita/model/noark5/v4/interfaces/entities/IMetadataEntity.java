package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;

/**
 *
 */
public interface IMetadataEntity extends INoarkSystemIdEntity, Serializable {

    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

}
