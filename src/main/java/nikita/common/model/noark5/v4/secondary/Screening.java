package nikita.common.model.noark5.v4.secondary;

import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.Class;
import nikita.common.model.noark5.v4.*;
import nikita.common.model.noark5.v4.interfaces.entities.IScreeningEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "screening")
// Enable soft delete of Screening
// @SQLDelete(sql="UPDATE screening SET deleted = true WHERE pk_screening_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_screening_id"))
public class Screening extends NoarkEntity implements IScreeningEntity {

    /**
     * M500 - tilgangsrestriksjon n4 (JP.TGKODE)
     */
    @Column(name = "access_restriction")
    @Audited
    private String accessRestriction;

    /**
     * M501 - skjermingshjemmel n4 (JP.UOFF)
     */
    @Column(name = "screening_authority")
    @Audited
    private String screeningAuthority;

    /**
     * M502 - skjermingMetadata should be 1-M
     */
    @Column(name = "screening_metadata")
    @Audited
    private String screeningMetadata;

    /**
     * M503 - skjermingDokument
     */
    @Column(name = "screening_document")
    @Audited
    private String screeningDocument;

    /**
     * M505 - skjermingOpphoererDato n4(JP.AGDATO)
     */
    @Column(name = "screening_expires")
    @Audited
    private Date screeningExpiresDate;

    /**
     * M504 - skjermingsvarighet
     * TODO: This should be an integer!!
     */
    @Column(name = "screening_duration")
    @Audited
    private String screeningDuration;

    // Links to Series
    @ManyToMany(mappedBy = "referenceScreening")
    private List<Series> referenceSeries = new ArrayList<>();

    // Links to Class
    @ManyToMany(mappedBy = "referenceScreening")
    private List<Class> referenceClass = new ArrayList<>();

    // Links to File
    @ManyToMany(mappedBy = "referenceScreening")
    private List<File> referenceFile = new ArrayList<>();

    // Links to Record
    @ManyToMany(mappedBy = "referenceScreening")
    private List<Record> referenceRecord = new ArrayList<>();

    // Links to DocumentDescription
    @ManyToMany(mappedBy = "referenceScreening")
    private List<DocumentDescription> referenceDocumentDescription = new ArrayList<>();

    public String getAccessRestriction() {
        return accessRestriction;
    }

    public void setAccessRestriction(String accessRestriction) {
        this.accessRestriction = accessRestriction;
    }

    public String getScreeningAuthority() {
        return screeningAuthority;
    }

    public void setScreeningAuthority(String screeningAuthority) {
        this.screeningAuthority = screeningAuthority;
    }

    public String getScreeningMetadata() {
        return screeningMetadata;
    }

    public void setScreeningMetadata(String screeningMetadata) {
        this.screeningMetadata = screeningMetadata;
    }

    public String getScreeningDocument() {
        return screeningDocument;
    }

    public void setScreeningDocument(String screeningDocument) {
        this.screeningDocument = screeningDocument;
    }

    public Date getScreeningExpiresDate() {
        return screeningExpiresDate;
    }

    public void setScreeningExpiresDate(Date screeningExpiresDate) {
        this.screeningExpiresDate = screeningExpiresDate;
    }

    public String getScreeningDuration() {
        return screeningDuration;
    }

    public void setScreeningDuration(String screeningDuration) {
        this.screeningDuration = screeningDuration;
    }

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.SCREENING;
    }

    public List<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(List<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public List<Class> getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(List<Class> referenceClass) {
        this.referenceClass = referenceClass;
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

    public List<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(List<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "Screening {" + super.toString() +
                "screeningDuration='" + screeningDuration + '\'' +
                ", screeningExpiresDate=" + screeningExpiresDate +
                ", screeningDocument='" + screeningDocument + '\'' +
                ", screeningMetadata='" + screeningMetadata + '\'' +
                ", screeningAuthority='" + screeningAuthority + '\'' +
                ", accessRestriction='" + accessRestriction + '\'' +
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
        Screening rhs = (Screening) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(screeningDuration, rhs.screeningDuration)
                .append(screeningExpiresDate, rhs.screeningExpiresDate)
                .append(screeningDocument, rhs.screeningDocument)
                .append(screeningMetadata, rhs.screeningMetadata)
                .append(screeningAuthority, rhs.screeningAuthority)
                .append(accessRestriction, rhs.accessRestriction)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(screeningDuration)
                .append(screeningExpiresDate)
                .append(screeningDocument)
                .append(screeningMetadata)
                .append(screeningAuthority)
                .append(accessRestriction)
                .toHashCode();
    }
}
