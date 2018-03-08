package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.*;
import nikita.model.noark5.v4.secondary.*;
import nikita.util.deserialisers.SeriesDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static nikita.config.Constants.PRIMARY_KEY_FONDS;
import static nikita.config.N5ResourceMappings.SERIES;

@Entity
@Table(name = "series")
// Enable soft delete of Series
// @SQLDelete(sql="UPDATE series SET deleted = true WHERE pk_series_id = ? and version = ?")
// @Where(clause="deleted <> true")
//@Indexed(index = "series")
@JsonDeserialize(using = SeriesDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_series_id"))
public class Series extends NoarkGeneralEntity implements IStorageLocation, IDocumentMedium, IClassified, IScreening,
        IDisposal, IDisposalUndertaken, IDeletion {

    private static final long serialVersionUID = 1L;
    /**
     * M051 - arkivdelstatus (xs:string)
     */
    @NotNull
    @Column(name = "series_status", nullable = false)
    @Audited
    private String seriesStatus;

    /**
     * M107 - arkivperiodeStartDato (xs:date)
     */
    @Column(name = "series_start_date")
    @Temporal(TemporalType.DATE)
    @Audited
    private Date seriesStartDate;

    /**
     * M108 - arkivperiodeSluttDato (xs:date)
     */
    @Column(name = "series_end_date")
    @Temporal(TemporalType.DATE)
    @Audited
    private Date seriesEndDate;

    /**
     * M300 - dokumentmedium (xs:string)
     */
    @Column(name = "document_medium")
    @Audited
    private String documentMedium;

    // Links to StorageLocations
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "series_storage_location", joinColumns = @JoinColumn(name = "f_pk_series_id",
            referencedColumnName = "pk_series_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_storage_location_id",
            referencedColumnName = "pk_storage_location_id"))
    private List<StorageLocation> referenceStorageLocation = new ArrayList<>();

    // Link to Fonds
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_fonds_id",
            referencedColumnName = PRIMARY_KEY_FONDS)
    @JsonIgnore
    private Fonds referenceFonds;

    // Link to precursor Series
    @OneToOne(fetch = FetchType.LAZY)
    private Series referencePrecursor;

    // Link to successor Series
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "referencePrecursor")
    private Series referenceSuccessor;

    // Link to ClassificationSystem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_classification_system_id", referencedColumnName = "pk_classification_system_id")
    private ClassificationSystem referenceClassificationSystem;

    // Links to Files
    @JsonIgnore
    @OneToMany(mappedBy = "referenceSeries")
    private List<File> referenceFile = new ArrayList<>();

    // Links to Records
    @OneToMany(mappedBy = "referenceSeries")
    @JsonIgnore
    private List<Record> referenceRecord = new ArrayList<>();

    // Links to Classified
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "series_classified_id", referencedColumnName = "pk_classified_id")
    @JsonIgnore
    private Classified referenceClassified;

    // Link to Disposal
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "series_disposal_id", referencedColumnName = "pk_disposal_id")
    @JsonIgnore
    private Disposal referenceDisposal;

    // Link to Screening
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "series_screening_id", referencedColumnName = "pk_screening_id")
    @JsonIgnore
    private Screening referenceScreening;

    // Link to DisposalUndertaken
    @ManyToOne (cascade=CascadeType.PERSIST)
    @JoinColumn(name = "document_description_disposal_undertaken_id",
            referencedColumnName = "pk_disposal_undertaken_id")
    @JsonIgnore
    private DisposalUndertaken referenceDisposalUndertaken;

    // Link to Deletion
    @ManyToOne (cascade=CascadeType.PERSIST)
    @JoinColumn(name = "document_description_deletion_id", referencedColumnName = "pk_deletion_id")
    @JsonIgnore
    private Deletion referenceDeletion;


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

    @Override
    public String getBaseTypeName() {
        return SERIES;
    }

    @Override
    public List<StorageLocation> getReferenceStorageLocation() {
        return referenceStorageLocation;
    }

    @Override
    public void setReferenceStorageLocation(
            List<StorageLocation> referenceStorageLocation) {
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

    public List<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(List<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public List<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(List<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    @Override
    public Classified getReferenceClassified() {
        return referenceClassified;
    }

    @Override
    public void setReferenceClassified(Classified referenceClassified) {
        this.referenceClassified = referenceClassified;
    }

    @Override
    public Disposal getReferenceDisposal() {
        return referenceDisposal;
    }

    @Override
    public void setReferenceDisposal(Disposal referenceDisposal) {
        this.referenceDisposal = referenceDisposal;
    }

    @Override
    public DisposalUndertaken getReferenceDisposalUndertaken() {
        return referenceDisposalUndertaken;
    }

    @Override
    public void setReferenceDisposalUndertaken(DisposalUndertaken referenceDisposalUndertaken) {
        this.referenceDisposalUndertaken = referenceDisposalUndertaken;
    }

    @Override
    public Screening getReferenceScreening() {
        return referenceScreening;
    }

    @Override
    public void setReferenceScreening(Screening referenceScreening) {
        this.referenceScreening = referenceScreening;
    }
    @Override
    public Deletion getReferenceDeletion() {
        return referenceDeletion;
    }

    @Override
    public void setReferenceDeletion(Deletion referenceDeletion) {
        this.referenceDeletion = referenceDeletion;
    }

    @Override
    public String toString() {
        return "Series{" + super.toString() +
                ", seriesEndDate=" + seriesEndDate +
                ", seriesStartDate=" + seriesStartDate +
                ", documentMedium='" + documentMedium + '\'' +
                ", seriesStatus='" + seriesStatus + '\'' +
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
        Series rhs = (Series) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(seriesEndDate, rhs.seriesEndDate)
                .append(seriesStartDate, rhs.seriesStartDate)
                .append(documentMedium, rhs.documentMedium)
                .append(seriesStatus, rhs.seriesStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(seriesEndDate)
                .append(seriesStartDate)
                .append(documentMedium)
                .append(seriesStatus)
                .toHashCode();
    }
}
