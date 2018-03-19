package nikita.common.model.noark5.v4.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.*;
import nikita.common.util.serializers.noark5v4.StorageLocationSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Fonds> referenceFonds = new ArrayList<>();

    // Links to Series
    @ManyToMany(mappedBy = "referenceStorageLocation")
    private List<Series> referenceSeries = new ArrayList<>();

    // Links to Files
    @OneToMany(mappedBy = "referenceStorageLocation")
    private List<File> referenceFile = new ArrayList<>();

    // Links to BasicRecords
    @ManyToMany(mappedBy = "referenceStorageLocation")
    @JsonIgnore
    private List<BasicRecord> referenceBasicRecord = new ArrayList<>();


    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public List<Fonds> getReferenceFonds() {
        return referenceFonds;
    }

    public void setReferenceFonds(List<Fonds> referenceFonds) {
        this.referenceFonds = referenceFonds;
    }

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.STORAGE_LOCATION;
    }

    public List<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(List<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public List<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(List<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public List<BasicRecord> getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(List<BasicRecord> referenceBasicRecord) {
        this.referenceBasicRecord = referenceBasicRecord;
    }

    @Override
    public String toString() {
        return "StorageLocation{" + super.toString() +
                ", storageLocation='" + storageLocation + '\'' +
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
        StorageLocation rhs = (StorageLocation) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(storageLocation, rhs.storageLocation)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(storageLocation)
                .toHashCode();
    }
}
