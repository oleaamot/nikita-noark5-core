package nikita.model.noark5.v4.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.*;
import nikita.util.serializers.noark5v4.StorageLocationSerializer;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.STORAGE_LOCATION;

@Entity
@Table(name = "storage_location")
// Enable soft delete of IStorageLocation
// @SQLDelete(sql="UPDATE storage_location SET deleted = true WHERE pk_storage_location_id = ? and version = ?")
// @Where(clause="deleted <> true")
@JsonSerialize(using = StorageLocationSerializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_storage_location_id"))
public class StorageLocation extends NoarkEntity {

    /**
     * M301 - oppbevaringssted (xs:string)
     */
    @Column(name = "storage_location")
    @Audited
    private String storageLocation;

    // Links to Fonds
    @ManyToMany(mappedBy = "referenceStorageLocation")
    @JsonIgnore
    private Set<Fonds> referenceFonds = new TreeSet<>();

    // Links to Series
    @ManyToMany(mappedBy = "referenceStorageLocation")
    private Set<Series> referenceSeries = new TreeSet<>();

    // Links to Files
    @OneToMany(mappedBy = "referenceStorageLocation")
    private Set<File> referenceFile = new TreeSet<>();

    // Links to BasicRecords
    @ManyToMany(mappedBy = "referenceStorageLocation")
    @JsonIgnore
    private Set<BasicRecord> referenceBasicRecord = new TreeSet<>();


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

    @Override
    public String toString() {
        return "StorageLocation{" + super.toString() + 
                ", storageLocation='" + storageLocation + '\'' +
                '}';
    }
}
