package nikita.model.noark5.v4.metadata;

import nikita.model.noark5.v4.interfaces.entities.IMetadataEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by tsodring on 3/23/17.
 */
@MappedSuperclass
public class MetadataSuperClass extends MetadataSuperClassBase implements IMetadataEntity {

    /**
     * M -  (xs:string)
     */
    @Column(name = "code")
    @Audited
    protected String code;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", code='" + code + '\'' +
                '}';
    }
}
