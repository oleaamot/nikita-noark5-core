package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.model.noark5.v4.interfaces.entities.IScreeningEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.SCREENING;

@Entity
@Table(name = "screening")
// Enable soft delete of Screening
@SQLDelete(sql="UPDATE screening SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Screening implements IScreeningEntity, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_screening_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /**
     * M500 - tilgangsrestriksjon n4 (JP.TGKODE)
     */
    @Column(name = "access_restriction")
    @Audited
    protected String accessRestriction;

    /**
     * M501 - skjermingshjemmel n4 (JP.UOFF)
     */
    @Column(name = "screening_authority")
    @Audited
    protected String screeningAuthority;

    /**
     * M502 - skjermingMetadata should be 1-M
     */
    @Column(name = "screening_metadata")
    @Audited
    protected String screeningMetadata;

    /**
     * M503 - skjermingDokument
     */
    @Column(name = "screening_document")
    @Audited
    protected String screeningDocument;

    /**
     * M505 - skjermingOpphoererDato n4(JP.AGDATO)
     */
    @Column(name = "screening_expires")
    @Audited
    protected Date screeningExpiresDate;

    /**
     * M504 - skjermingsvarighet
     * TODO: This should be an integer!!
     */
    @Column(name = "screening_duration")
    @Audited
    protected String screeningDuration;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Links to Series
    @ManyToMany(mappedBy = "referenceScreening")
    protected Set<Series> referenceSeries = new HashSet<Series>();
    // Links to Class
    @ManyToMany(mappedBy = "referenceScreening")
    protected Set<Class> referenceClass = new HashSet<Class>();
    // Links to File
    @ManyToMany(mappedBy = "referenceScreening")
    protected Set<File> referenceFile = new HashSet<File>();
    // Links to Record
    @ManyToMany(mappedBy = "referenceScreening")
    protected Set<Record> referenceRecord = new HashSet<Record>();
    // Links to DocumentDescription
    @ManyToMany(mappedBy = "referenceScreening")
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<DocumentDescription>();
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
        return "SignOff{" +
                "screeningDuration='" + screeningDuration + '\'' +
                ", screeningExpiresDate=" + screeningExpiresDate +
                ", screeningDocument='" + screeningDocument + '\'' +
                ", screeningMetadata='" + screeningMetadata + '\'' +
                ", screeningAuthority='" + screeningAuthority + '\'' +
                ", accessRestriction='" + accessRestriction + '\'' +
                ", version='" + version + '\'' +
                ", id=" + id +
                '}';
    }
}