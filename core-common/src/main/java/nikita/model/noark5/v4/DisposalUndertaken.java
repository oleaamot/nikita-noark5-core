package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.IDisposalUndertakenEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.DISPOSAL_UNDERTAKEN;

@Entity
@Table(name = "disposal_undertaken")
// Enable soft delete of DisposalUndertaken
@SQLDelete(sql="UPDATE disposal_undertaken SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class DisposalUndertaken implements IDisposalUndertakenEntity, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_disposal_undertaken_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /** M631 - kassertAv (xs:string) */
    @Column(name = "disposal_by")
    @Audited
    protected String disposalBy;

    /** M630 - kassertDato (xs:dateTime) */
    @Column(name = "disposal_date")
    @Audited
    protected Date disposalDate;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Links to Series
    @ManyToMany(mappedBy = "referenceDisposalUndertaken")
    protected Set<Series> referenceSeries = new HashSet<Series>();
    // Links to DocumentDescription
    @ManyToMany(mappedBy = "referenceDisposalUndertaken")
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

    public String getDisposalBy() {
        return disposalBy;
    }

    public void setDisposalBy(String disposalBy) {
        this.disposalBy = disposalBy;
    }

    public Date getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
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
        return DISPOSAL_UNDERTAKEN;
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
        return "DisposalUndertaken{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", disposalBy='" + disposalBy + '\'' +
                ", disposalDate=" + disposalDate +
                '}';
    }
}