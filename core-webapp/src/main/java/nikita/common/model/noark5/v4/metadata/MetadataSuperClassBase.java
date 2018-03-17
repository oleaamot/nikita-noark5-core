package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.Constants;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.exceptions.NikitaException;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;

/**
 * Created by tsodring on 3/23/17.
 */
@MappedSuperclass
public class MetadataSuperClassBase implements INikitaEntity, Comparable<MetadataSuperClassBase> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    @Field
    protected String systemId;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    protected String description;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    @Version
    @Column(name = "version")
    protected Long version;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String getOwnedBy() {
        return ownedBy;
    }

    @Override
    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    // This method should never be called. This exception can only occur if you add a new
    // metadata entity and forget to override this method
    public String getBaseTypeName() {
        throw new NikitaException("MetadataSuperClass exception. Internal exception. " +
                "This exception can only occur if a new metadata entity is introduced and you forgot to override " +
                "getBaseTypeName. Check logfile to see what caused this");
    }

    @Override
    // All Metadata entities belong to "metadata". These entities pick the value up here
    public String getFunctionalTypeName() {
        return Constants.NOARK_METADATA_PATH;
    }


    @Override
    public int compareTo(MetadataSuperClassBase otherEntity) {
        if (null == otherEntity) {
            return -1;
        }
        return new CompareToBuilder()
                .append(this.systemId, otherEntity.systemId)
                .toComparison();
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
                .append(systemId, rhs.systemId)
                .append(deleted, rhs.getDeleted())
                .append(version, rhs.getVersion())
                .append(ownedBy, rhs.getOwnedBy())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(systemId)
                .append(deleted)
                .append(version)
                .append(ownedBy)
                .toHashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " {" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ", version=" + version +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
