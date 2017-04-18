package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.StorageLocationSerializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.STORAGE_LOCATION;

@Entity
@Table(name = "storage_location")
// Enable soft delete of IStorageLocation
@SQLDelete(sql="UPDATE storage_location SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@JsonSerialize(using = StorageLocationSerializer.class)
public class StorageLocation implements Serializable, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;
    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    protected String systemId;
    /**
     * M301 - oppbevaringssted (xs:string)
     */
    @Column(name = "storage_location")
    @Audited
    protected String storageLocation;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Links to Fonds
    @ManyToMany(mappedBy = "referenceStorageLocation")
    @JsonIgnore
    protected Set<Fonds> referenceFonds = new HashSet<Fonds>();
    // Links to Series
    @ManyToMany(mappedBy = "referenceStorageLocation")
    protected Set<Series> referenceSeries = new HashSet<Series>();
    // Links to Files
    @OneToMany(mappedBy = "referenceStorageLocation")
    protected Set<File> referenceFile = new HashSet<File>();
    // Links to BasicRecords
    @ManyToMany(mappedBy = "referenceStorageLocation")
    @JsonIgnore
    protected Set<BasicRecord> referenceBasicRecord = new HashSet<>();
    @ManyToMany(mappedBy = "referenceStorageLocation")
    @JsonIgnore
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_storage_location_id", nullable = false, insertable = true, updatable = false)
    private long id;
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

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Set<Fonds> getReferenceFonds() {
        return referenceFonds;
    }

    public void setReferenceFonds(Set<Fonds> referenceFonds) {
        this.referenceFonds = referenceFonds;
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
        return STORAGE_LOCATION;
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

    public Set<BasicRecord> getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(Set<BasicRecord> referenceBasicRecord) {
        this.referenceBasicRecord = referenceBasicRecord;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "StorageLocation{" +
                "id=" + id +
                ", systemId='" + systemId + '\'' +
                ", storageLocation='" + storageLocation + '\'' +
                ", deleted=" + deleted +
                ", ownedBy='" + ownedBy + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
