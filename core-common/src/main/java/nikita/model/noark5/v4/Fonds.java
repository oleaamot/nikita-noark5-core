package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.IDocumentMedium;
import nikita.model.noark5.v4.interfaces.IFondsCreator;
import nikita.model.noark5.v4.interfaces.IStorageLocation;
import nikita.model.noark5.v4.secondary.StorageLocation;
import nikita.util.deserialisers.FondsDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.FONDS;

@Entity
@Table(name = "fonds")
// Enable soft delete of Fonds
// @SQLDelete(sql="UPDATE fonds SET deleted = true WHERE pk_fonds_id = ? and version = ?")
// @Where(clause="deleted <> true")
//@Indexed(index = "fonds")
@JsonDeserialize(using = FondsDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_fonds_id"))
public class Fonds extends NoarkGeneralEntity implements IStorageLocation, IDocumentMedium, IFondsCreator {

    private static final long serialVersionUID = 1L;

    /**
     * M050 - arkivstatus (xs:string)
     */
    @Column(name = "fonds_status")
    @Audited
    private String fondsStatus;

    /**
     * M300 - dokumentmedium (xs:string)
     */
    @Column(name = "document_medium")
    @Audited
    private String documentMedium;

    // Links to Series
    @OneToMany(mappedBy = "referenceFonds")
    @JsonIgnore
    private Set<Series> referenceSeries = new TreeSet<>();
    // Link to parent Fonds
    @ManyToOne(fetch = FetchType.LAZY)
    private Fonds referenceParentFonds;

    // Links to child Fonds
    @OneToMany(mappedBy = "referenceParentFonds", fetch = FetchType.LAZY)
    private Set<Fonds> referenceChildFonds = new TreeSet<>();

    // Links to StorageLocations
    @ManyToMany (cascade=CascadeType.PERSIST)
    @JoinTable(name = "fonds_storage_location", joinColumns = @JoinColumn(name = "f_pk_fonds_id",
            referencedColumnName = "pk_fonds_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_storage_location_id",
            referencedColumnName = "pk_storage_location_id"))
    private Set<StorageLocation> referenceStorageLocation = new TreeSet<>();

    // Links to FondsCreators
    @ManyToMany
    @JoinTable(name = "fonds_fonds_creator", joinColumns = @JoinColumn(name = "f_pk_fonds_id",
            referencedColumnName = "pk_fonds_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_fonds_creator_id",
            referencedColumnName = "pk_fonds_creator_id"))
    private Set<FondsCreator> referenceFondsCreator = new TreeSet<>();


    public String getFondsStatus() {
        return fondsStatus;
    }

    public void setFondsStatus(String fondsStatus) {
        this.fondsStatus = fondsStatus;
    }

    public String getDocumentMedium() {
        return documentMedium;
    }

    public void setDocumentMedium(String documentMedium) {
        this.documentMedium = documentMedium;
    }

    @Override
    public String getBaseTypeName() {
        return FONDS;
    }

    public Set<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Set<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Fonds getReferenceParentFonds() {
        return referenceParentFonds;
    }

    public void setReferenceParentFonds(Fonds referenceParentFonds) {
        this.referenceParentFonds = referenceParentFonds;
    }

    public Set<Fonds> getReferenceChildFonds() {
        return referenceChildFonds;
    }

    public void setReferenceChildFonds(Set<Fonds> referenceChildFonds) {
        this.referenceChildFonds = referenceChildFonds;
    }

    public Set<StorageLocation> getReferenceStorageLocation() {
        return referenceStorageLocation;
    }

    public void setReferenceStorageLocation(
            Set<StorageLocation> referenceStorageLocation) {
        this.referenceStorageLocation = referenceStorageLocation;
    }

    public Set<FondsCreator> getReferenceFondsCreator() {
        return referenceFondsCreator;
    }

    public void setReferenceFondsCreator(Set<FondsCreator> referenceFondsCreator) {
        this.referenceFondsCreator = referenceFondsCreator;
    }

    @Override
    public String toString() {
        return "Fonds{" + super.toString() +
                ", fondsStatus='" + fondsStatus + '\'' +
                ", documentMedium='" + documentMedium + '\'' +
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
        Fonds rhs = (Fonds) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(fondsStatus, rhs.fondsStatus)
                .append(documentMedium, rhs.documentMedium)
                .isEquals();
    }

}
