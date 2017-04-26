package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.POSTAL_ADDRESS;

@Entity
@Table(name = "postal_address")
// Enable soft delete of PostalAddress
@SQLDelete(sql="UPDATE postalAddress SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class PostalAddress implements INikitaEntity, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_postal_address_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    protected String systemId;

    /**
     * M024 - forfatter (xs:string)
     */
    @Column(name = "postal_address")
    @Audited
    protected String postalAddress;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    @Version
    @Column(name = "version")
    protected Long version;

    // Links to CorrespondencePart
    @ManyToMany(mappedBy = "postalAddress")
    protected Set<CorrespondencePart> referenceCorrespondencePart = new HashSet<CorrespondencePart>();

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getBaseTypeName() {
        return POSTAL_ADDRESS;
    }

    @Override
    public String toString() {
        return "PostalAddress{" +
                "postalAddress='" + postalAddress + '\'' +
                ", version='" + version + '\'' +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}
