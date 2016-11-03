package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "storage_location")
// Enable soft delete of StorageLocation
@SQLDelete(sql="UPDATE storage_location SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class StorageLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_storage_location_id", nullable = false, insertable = true, updatable = false)
    private long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id")
    @Audited
    protected String systemId;

    /**
     * M301 - oppbevaringssted (xs:string)
     */
    @Column(name = "storage_location")
    @Audited
    protected String storageLocation;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Links to Fonds
    @ManyToMany(mappedBy = "referenceStorageLocation")
    protected Set<Fonds> referenceFonds = new HashSet<Fonds>();

    // Links to Series
    @ManyToMany(mappedBy = "referenceStorageLocation")
    protected Set<Series> referenceSeries = new HashSet<Series>();

    // Links to Files
    @OneToMany(mappedBy = "referenceStorageLocation")
    protected Set<File> referenceFile = new HashSet<File>();

    // Links to BasicRecords
    @OneToMany(mappedBy = "referenceStorageLocation")
    protected Set<BasicRecord> referenceRecord = new HashSet<BasicRecord>();


    // TODO: Fix this!!
    // Links to BasicRecords
    /* @ManyToMany(mappedBy = "referenceStorageLocation", targetEntity="BasicRecord", fetch="EXTRA_LAZY")
    protected $referenceRecord;

    // Links to DocumentDescription
     @OneToMany(mappedBy = "referenceStorageLocation", targetEntity="DocumentDescription", fetch="EXTRA_LAZY")
    protected $referenceDocumentDescription;
    */

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Set<Fonds> getReferenceFonds() {
        return referenceFonds;
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

    public void setReferenceFonds(Set<Fonds> referenceFonds) {
        this.referenceFonds = referenceFonds;
    }

    public Set<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Set<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Set<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(Set<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Set<BasicRecord> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<BasicRecord> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    @Override
    public String toString() {
        return "StorageLocation [id=" + id + ", systemId=" + systemId
                + ", storageLocation=" + storageLocation + ", referenceFonds="
                + referenceFonds + ", referenceSeries=" + referenceSeries
                + ", referenceFile=" + referenceFile + ", referenceRecord="
                + referenceRecord + "]";
    }

}
