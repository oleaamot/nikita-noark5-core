package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.IDeletionEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.DELETION;

@Entity
@Table(name = "deletion")
// Enable soft delete of Deletion
@SQLDelete(sql="UPDATE deletion SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Deletion implements IDeletionEntity, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_deletion_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /** M089 - slettingstype (xs:string) */
    @Column(name = "deletion_type")
    @Audited
    protected String deletionType;

    /** M614 - slettetAv (xs:string) */
    @Column(name = "deletion_by")
    @Audited
    protected String deletionBy;

    /** M613 slettetDato (xs:dateTime) */
    @Column(name = "deletion_date")
    @Audited
    protected Date deletionDate;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Links to Series
    @OneToMany(mappedBy = "referenceDeletion")
    protected Set<Series> referenceSeries = new HashSet<Series>();
    // Links to DocumentDescription
    @OneToMany(mappedBy = "referenceDeletion")
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<DocumentDescription>();
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getDeletionType() {
        return deletionType;
    }

    public void setDeletionType(String deletionType) {
        this.deletionType = deletionType;
    }

    public String getDeletionBy() {
        return deletionBy;
    }

    public void setDeletionBy(String deletionBy) {
        this.deletionBy = deletionBy;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
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
    public String getBaseTypeName() {
        return DELETION;
    }

    public Set<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Set<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "Deletion{" +
                "deletionDate=" + deletionDate +
                ", deletionBy='" + deletionBy + '\'' +
                ", deletionType='" + deletionType + '\'' +
                ", id=" + id +
                ", version='" + version + '\'' +
                '}';
    }
}