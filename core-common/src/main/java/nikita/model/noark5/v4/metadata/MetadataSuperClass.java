package nikita.model.noark5.v4.metadata;

import nikita.model.noark5.v4.interfaces.entities.IMetadataEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        UniqueCodeMetadataSuperClass rhs = (UniqueCodeMetadataSuperClass) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(code, rhs.code)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(code)
                .toHashCode();
    }
}
