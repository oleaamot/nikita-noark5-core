package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.IDocumentMedium;
import nikita.model.noark5.v4.interfaces.IFondsCreator;
import nikita.model.noark5.v4.interfaces.IStorageLocation;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.deserialisers.FondsDeserializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.FONDS;

@Entity
@Table(name = "fonds")
// Enable soft delete of Fonds
@SQLDelete(sql="UPDATE fonds SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@Indexed(index = "fonds")
@JsonDeserialize(using = FondsDeserializer.class)
public class Fonds implements INoarkGeneralEntity, IStorageLocation, IDocumentMedium, IFondsCreator {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_fonds_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    @Field
    protected String systemId;

    /**
     * M020 - tittel (xs:string)
     */

    @Column(name = "title")
    @Audited
    @Field
    @Boost (1.3f)
    @JsonProperty("tittel")
    protected String title;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    @Field
    @Boost (1.2f)
    protected String description;

    /**
     * M050 - arkivstatus (xs:string)
     */
    @Column(name = "fonds_status")
    @Audited
    protected String fondsStatus;

    /**
     * M300 - dokumentmedium (xs:string)
     */
    @Column(name = "document_medium")
    @Audited
    protected String documentMedium;

    /**
     * M600 - opprettetDato (xs:dateTime)
     */
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    protected Date createdDate;

    /**
     * M601 - opprettetAv (xs:string)
     */
    @Column(name = "created_by")
    @Audited
    protected String createdBy;

    /**
     * M602 - avsluttetDato (xs:dateTime)
     */
    @Column(name = "finalised_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    protected Date finalisedDate;

    /**
     * M603 - avsluttetAv (xs:string)
     */
    @Column(name = "finalised_by")
    @Audited
    protected String finalisedBy;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    @Version
    @Column(name = "version")
    protected Long version;
    // Links to Series
    @OneToMany(mappedBy = "referenceFonds")
    @JsonIgnore
    protected Set<Series> referenceSeries = new HashSet<Series>();
    // Link to parent Fonds
    @ManyToOne(fetch = FetchType.LAZY)
    protected Fonds referenceParentFonds;
    // Links to child Fonds
    @OneToMany(mappedBy = "referenceParentFonds", fetch = FetchType.LAZY)
    protected Set<Fonds> referenceChildFonds = new HashSet<Fonds>();
    // Links to StorageLocations
    @ManyToMany (cascade=CascadeType.ALL)
    @JoinTable(name = "fonds_storage_location", joinColumns = @JoinColumn(name = "f_pk_fonds_id",
            referencedColumnName = "pk_fonds_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_storage_location_id",
            referencedColumnName = "pk_storage_location_id"))
    protected Set<StorageLocation> referenceStorageLocation = new HashSet<StorageLocation>();
    // Links to FondsCreators
    @ManyToMany
    @JoinTable(name = "fonds_fonds_creator", joinColumns = @JoinColumn(name = "f_pk_fonds_id",
            referencedColumnName = "pk_fonds_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_fonds_creator_id",
            referencedColumnName = "pk_fonds_creator_id"))
    protected Set<FondsCreator> referenceFondsCreator = new HashSet<FondsCreator>();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getFinalisedDate() {
        return finalisedDate;
    }

    public void setFinalisedDate(Date finalisedDate) {
        this.finalisedDate = finalisedDate;
    }

    public String getFinalisedBy() {
        return finalisedBy;
    }

    public void setFinalisedBy(String finalisedBy) {
        this.finalisedBy = finalisedBy;
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

        if (this.version != version) {
            throw new RuntimeException("Concurrency Exception. Old version [" + this.version + "], new version "
            + "[" + version + "]");
        }
        this.version = version;
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
        return "Fonds{" +
                "id=" + id +
                ", systemId='" + systemId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", fondsStatus='" + fondsStatus + '\'' +
                ", documentMedium='" + documentMedium + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", finalisedDate=" + finalisedDate +
                ", finalisedBy='" + finalisedBy + '\'' +
                ", deleted=" + deleted +
                ", ownedBy='" + ownedBy + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
