package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.*;
import nikita.model.noark5.v4.interfaces.entities.IScreeningEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.SCREENING;

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
    private Set<Series> referenceSeries = new TreeSet<>();

    // Links to Class
    @ManyToMany(mappedBy = "referenceScreening")
    private Set<Class> referenceClass = new TreeSet<>();

    // Links to File
    @ManyToMany(mappedBy = "referenceScreening")
    private Set<File> referenceFile = new TreeSet<>();

    // Links to Record
    @ManyToMany(mappedBy = "referenceScreening")
    private Set<Record> referenceRecord = new TreeSet<>();

    // Links to DocumentDescription
    @ManyToMany(mappedBy = "referenceScreening")
    private Set<DocumentDescription> referenceDocumentDescription = new TreeSet<>();

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
        return SCREENING;
    }

    public Set<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Set<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Set<Class> getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(Set<Class> referenceClass) {
        this.referenceClass = referenceClass;
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

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "SignOff{" + super.toString() + 
                "screeningDuration='" + screeningDuration + '\'' +
                ", screeningExpiresDate=" + screeningExpiresDate +
                ", screeningDocument='" + screeningDocument + '\'' +
                ", screeningMetadata='" + screeningMetadata + '\'' +
                ", screeningAuthority='" + screeningAuthority + '\'' +
                ", accessRestriction='" + accessRestriction + '\'' +
                '}';
    }
}
