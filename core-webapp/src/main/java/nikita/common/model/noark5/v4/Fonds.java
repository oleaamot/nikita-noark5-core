package nikita.common.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.common.config.Constants;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.IDocumentMedium;
import nikita.common.model.noark5.v4.interfaces.IFondsCreator;
import nikita.common.model.noark5.v4.interfaces.IStorageLocation;
import nikita.common.model.noark5.v4.secondary.StorageLocation;
import nikita.common.util.deserialisers.FondsDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Constants.TABLE_FONDS)
//@Indexed(index = "fonds")
@JsonDeserialize(using = FondsDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = Constants.PRIMARY_KEY_FONDS))
public class Fonds extends NoarkGeneralEntity implements IStorageLocation,
        IDocumentMedium, IFondsCreator {

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
    private List<Series> referenceSeries = new ArrayList<>();

    // Link to parent Fonds
    @ManyToOne(fetch = FetchType.LAZY)
    private Fonds referenceParentFonds;

    // Links to child Fonds
    @OneToMany(mappedBy = "referenceParentFonds", fetch = FetchType.LAZY)
    private List<Fonds> referenceChildFonds = new ArrayList<>();

    // Links to StorageLocations
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = Constants.TABLE_FONDS_STORAGE_LOCATION,
            joinColumns = @JoinColumn(
                    name = Constants.FOREIGN_KEY_FONDS_PK,
                    referencedColumnName = Constants.PRIMARY_KEY_FONDS),
            inverseJoinColumns = @JoinColumn(
                    name = "f_pk_storage_location_id",
                    referencedColumnName = "pk_storage_location_id")
    )
    private List<StorageLocation> referenceStorageLocation = new ArrayList<>();

    // Links to FondsCreators
    @ManyToMany
    @JoinTable(name = Constants.TABLE_FONDS_FONDS_CREATOR,
            joinColumns = @JoinColumn(
                    name = Constants.FOREIGN_KEY_FONDS_PK,
                    referencedColumnName = Constants.PRIMARY_KEY_FONDS),
            inverseJoinColumns = @JoinColumn(
                    name = Constants.FOREIGN_KEY_FONDS_CREATOR_PK,
                    referencedColumnName = Constants.PRIMARY_KEY_FONDS_CREATOR)
    )
    private List<FondsCreator> referenceFondsCreator = new ArrayList<>();

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
        return N5ResourceMappings.FONDS;
    }

    public List<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(List<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Fonds getReferenceParentFonds() {
        return referenceParentFonds;
    }

    public void setReferenceParentFonds(Fonds referenceParentFonds) {
        this.referenceParentFonds = referenceParentFonds;
    }

    public List<Fonds> getReferenceChildFonds() {
        return referenceChildFonds;
    }

    public void setReferenceChildFonds(List<Fonds> referenceChildFonds) {
        this.referenceChildFonds = referenceChildFonds;
    }

    public List<StorageLocation> getReferenceStorageLocation() {
        return referenceStorageLocation;
    }

    public void setReferenceStorageLocation(
            List<StorageLocation> referenceStorageLocation) {
        this.referenceStorageLocation = referenceStorageLocation;
    }

    public List<FondsCreator> getReferenceFondsCreator() {
        return referenceFondsCreator;
    }

    public void setReferenceFondsCreator(List<FondsCreator> referenceFondsCreator) {
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(fondsStatus)
                .append(documentMedium)
                .toHashCode();
    }
}
