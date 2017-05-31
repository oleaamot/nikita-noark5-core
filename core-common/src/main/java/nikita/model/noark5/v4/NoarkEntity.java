package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;

import static nikita.config.Constants.NOARK_FONDS_STRUCTURE_PATH;

/**
 * Created by tsodring on 5/8/17.
 */
@MappedSuperclass
public class NoarkEntity implements INikitaEntity, Comparable<NoarkEntity>   {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    @Field
    private String systemId;

    @Column(name = "owned_by")
    @Audited
    private String ownedBy;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Version
    @Column(name = "version")
    private Long version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(String systemId) {
        this.systemId = systemId;
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
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    // You should not use, me. All subclasses must implement this themselves
    public String getBaseTypeName() {
        return null;
    }

    @Override
    // Most entities belong to arkivstruktur. These entities pick the value up here
    public String getFunctionalTypeName() {
        return NOARK_FONDS_STRUCTURE_PATH;
    }

    @Override
    public void setVersion(Long version) {

        if (!this.version.equals(version)) {
            throw new RuntimeException("Concurrency Exception. Old version [" + this.version + "], new version "
                    + "[" + version + "]");
        }
        this.version = version;
    }

    //@Override
    // Sub classes must implement this or validation will always be false
    public boolean validateForUpdate(String errorDescription) {
        /*if (description == null) {
            errorDescription += "beskrivelse field is empty. ";
            return false;
        }
        if (description.contains (ONLY_WHITESPACE)) {
            errorDescription += "beskrivelse field contains only whitespace. ";
            return false;
        }
        if (code == null) {
            errorDescription += "kode field is empty. ";
            return false;
        }
        if (description.contains (ONLY_WHITESPACE)) {
            errorDescription += "kode field contains only whitespace. ";
            return false;
        }*/
        return false;
    }

    //@Override
    // Sub classes must implement this or validation will always be false
    public boolean validateForCreate(String errorDescription) {
        /*if (description == null) {
            errorDescription += "beskrivelse field is empty. ";
            return false;
        }
        if (description.contains (ONLY_WHITESPACE)) {
            errorDescription += "beskrivelse field contains only whitespace. ";
            return false;
        }
        if (code == null) {
            errorDescription += "kode field is empty. ";
            return false;
        }
        if (description.contains (ONLY_WHITESPACE)) {
            errorDescription += "kode field contains only whitespace. ";
            return false;
        }*/
        return false;
    }

    @Override
    public int compareTo(NoarkEntity otherEntity) {
        if (null != otherEntity && otherEntity.getId() != null) {
            if (id < otherEntity.getId()) {
                return -1;
            } else if (id > otherEntity.getId()) {
                return 1;
            } else {
                return 0;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object other) {
	if (null == other) {
	    return false;
	}
	NoarkEntity otherEntity = (NoarkEntity)other;
	if (this == other) {
	    return true;
	}
	Long otherId = otherEntity.getId();
        if (otherId != null) {
            return id == otherId;
        }
        return false;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", systemId='" + systemId + '\'' +
                ", deleted=" + deleted +
                ", ownedBy='" + ownedBy + '\'' +
                ", version='" + version + '\'';
    }
}
