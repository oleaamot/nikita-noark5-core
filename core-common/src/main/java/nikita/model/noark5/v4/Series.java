package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "series")
// Enable soft delete of Series
@SQLDelete(sql="UPDATE series SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Series {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_series_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id")
    @Audited
    protected String systemId;

    /**
     * M020 - tittel (xs:string)
     */
    @Column(name = "title")
    @Audited
    protected String title;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    protected String description;

    /**
     * M051 - arkivdelstatus (xs:string)
     */
    @Column(name = "series_status")
    @Audited
    protected String seriesStatus;

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

    /**
     * M107 - arkivperiodeStartDato (xs:date)
     */
    @Column(name = "series_start_date")
    @Temporal(TemporalType.DATE)
    @Audited
    protected Date seriesStartDate;

    /**
     * M108 - arkivperiodeSluttDato (xs:date)
     */
    @Column(name = "series_end_date")
    @Temporal(TemporalType.DATE)
    @Audited
    protected Date seriesEndDate;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Links to StorageLocations
    @ManyToMany
    @JoinTable(name = "series_storage_location", joinColumns = @JoinColumn(name = "f_pk_series_id", referencedColumnName = "pk_series_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_storage_location_id", referencedColumnName = "pk_storage_location_id"))
    protected Set<StorageLocation> referenceStorageLocation = new HashSet<StorageLocation>();

    // Link to Fonds
    @ManyToOne
    @JoinColumn(name = "series_fonds_id", referencedColumnName = "pk_fonds_id")
    protected Fonds referenceFonds;

    // Link to precursor Series
    @OneToOne
    protected Series referencePrecursor;

    // Link to successor Series
    @OneToOne(mappedBy = "referencePrecursor")
    protected Series referenceSuccessor;

    // Link to ClassificationSystem
    @ManyToOne
    @JoinColumn(name = "series_classification_system_id", referencedColumnName = "pk_classification_system_id")
    protected ClassificationSystem referenceClassificationSystem;

    // Links to Files
    @JsonIgnore
    @OneToMany(mappedBy = "referenceSeries")
    protected Set<File> referenceFile = new HashSet<File>();

    // Links to Records
    @OneToMany(mappedBy = "referenceSeries")
    protected Set<Record> referenceRecord = new HashSet<Record>();

    // Links to Classified
    @ManyToOne
    @JoinColumn(name = "series_classified_id", referencedColumnName = "pk_classified_id")
    protected Classified referenceClassified;

    // Link to Disposal
    @ManyToOne
    @JoinColumn(name = "series_disposal_id", referencedColumnName = "pk_disposal_id")
    protected Disposal referenceDisposal;

    // Link to DisposalUndertaken
    @ManyToOne
    @JoinColumn(name = "series_disposal_undertaken_id", referencedColumnName = "pk_disposal_undertaken_id")
    protected DisposalUndertaken referenceDisposalUndertaken;

    // Link to Deletion
    @ManyToOne
    @JoinColumn(name = "series_deletion_id", referencedColumnName = "pk_deletion_id")
    protected Deletion referenceDeletion;

    // Link to Screening
    @ManyToOne
    @JoinColumn(name = "series_screening_id", referencedColumnName = "pk_screening_id")
    protected Screening referenceScreening;


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

    public String getSeriesStatus() {
        return seriesStatus;
    }

    public void setSeriesStatus(String seriesStatus) {
        this.seriesStatus = seriesStatus;
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

    public Date getSeriesStartDate() {
        return seriesStartDate;
    }

    public void setSeriesStartDate(Date seriesStartDate) {
        this.seriesStartDate = seriesStartDate;
    }

    public Date getSeriesEndDate() {
        return seriesEndDate;
    }

    public void setSeriesEndDate(Date seriesEndDate) {
        this.seriesEndDate = seriesEndDate;
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

    public Set<StorageLocation> getReferenceStorageLocation() {
        return referenceStorageLocation;
    }

    public void setReferenceStorageLocation(
            Set<StorageLocation> referenceStorageLocation) {
        this.referenceStorageLocation = referenceStorageLocation;
    }

    public Fonds getReferenceFonds() {
        return referenceFonds;
    }

    public void setReferenceFonds(Fonds referenceFonds) {
        this.referenceFonds = referenceFonds;
    }

    public Series getReferencePrecursor() {
        return referencePrecursor;
    }

    public void setReferencePrecursor(Series referencePrecursor) {
        this.referencePrecursor = referencePrecursor;
    }

    public Series getReferenceSuccessor() {
        return referenceSuccessor;
    }

    public void setReferenceSuccessor(Series referenceSuccessor) {
        this.referenceSuccessor = referenceSuccessor;
    }

    public ClassificationSystem getReferenceClassificationSystem() {
        return referenceClassificationSystem;
    }

    public void setReferenceClassificationSystem(
            ClassificationSystem referenceClassificationSystem) {
        this.referenceClassificationSystem = referenceClassificationSystem;
    }

    public Set<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(Set<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Set<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    public Classified getReferenceClassified() {
        return referenceClassified;
    }

    public void setReferenceClassified(Classified referenceClassified) {
        this.referenceClassified = referenceClassified;
    }

    public Disposal getReferenceDisposal() {
        return referenceDisposal;
    }

    public void setReferenceDisposal(Disposal referenceDisposal) {
        this.referenceDisposal = referenceDisposal;
    }

    public DisposalUndertaken getReferenceDisposalUndertaken() {
        return referenceDisposalUndertaken;
    }

    public void setReferenceDisposalUndertaken(DisposalUndertaken referenceDisposalUndertaken) {
        this.referenceDisposalUndertaken = referenceDisposalUndertaken;
    }

    public Deletion getReferenceDeletion() {
        return referenceDeletion;
    }

    public void setReferenceDeletion(Deletion referenceDeletion) {
        this.referenceDeletion = referenceDeletion;
    }

    public Screening getReferenceScreening() {
        return referenceScreening;
    }

    public void setReferenceScreening(Screening referenceScreening) {
        this.referenceScreening = referenceScreening;
    }

    @Override
    public String toString() {
        return "Series{" +
                "seriesEndDate=" + seriesEndDate +
                ", seriesStartDate=" + seriesStartDate +
                ", finalisedBy='" + finalisedBy + '\'' +
                ", finalisedDate=" + finalisedDate +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", documentMedium='" + documentMedium + '\'' +
                ", seriesStatus='" + seriesStatus + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}
