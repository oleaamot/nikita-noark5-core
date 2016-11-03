package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "record")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of Record
@SQLDelete(sql="UPDATE record SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_record_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id")
    @Audited
    protected String systemId;

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
     * M604 - arkivertDato (xs:dateTime)
     */
    @Column(name = "archived_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    protected Date archivedDate;

    /**
     * M605 - arkivertAv (xs:string)
     */
    @Column(name = "archived_by")
    @Audited
    protected String archivedBy;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Link to File
    @ManyToOne
    @JoinColumn(name = "record_file_id", referencedColumnName = "pk_file_id")
    protected File referenceFile;

    // Link to Series
    @ManyToOne
    @JoinColumn(name = "record_series_id", referencedColumnName = "pk_series_id")
    protected Series referenceSeries;

    // Link to Class
    @ManyToOne
    @JoinColumn(name = "record_class_id", referencedColumnName = "pk_class_id")
    protected nikita.model.noark5.v4.Class referenceClass;

    // Links to DocumentDescriptions
    @ManyToMany
    @JoinTable(name = "record_document_description", joinColumns = @JoinColumn(name = "f_pk_record_id", referencedColumnName = "pk_record_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_document_description_id", referencedColumnName = "pk_document_description_id"))
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<DocumentDescription>();

    // Links to DocumentObjects
    @OneToMany(mappedBy = "referenceRecord")
    protected Set<DocumentObject> referenceDocumentObject = new HashSet<DocumentObject>();

    // Links to Classified
    @ManyToOne
    @JoinColumn(name = "record_classified_id", referencedColumnName = "pk_classified_id")
    protected Classified referenceClassified;

    // Link to Disposal
    @ManyToOne
    @JoinColumn(name = "record_disposal_id", referencedColumnName = "pk_disposal_id")
    protected Disposal referenceDisposal;

    // Link to Screening
    @ManyToOne
    @JoinColumn(name = "record_screening_id", referencedColumnName = "pk_screening_id")
    protected Screening referenceScreening;


    @OneToOne(mappedBy = "referenceToRecord")
    protected CrossReference referenceToCrossReference;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public Date getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(Date archivedDate) {
        this.archivedDate = archivedDate;
    }

    public String getArchivedBy() {
        return archivedBy;
    }

    public void setArchivedBy(String archivedBy) {
        this.archivedBy = archivedBy;
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

    public File getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(File referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Series getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Series referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public nikita.model.noark5.v4.Class getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(nikita.model.noark5.v4.Class referenceClass) {
        this.referenceClass = referenceClass;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(
            Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    public Set<DocumentObject> getReferenceDocumentObject() {
        return referenceDocumentObject;
    }

    public void setReferenceDocumentObject(
            Set<DocumentObject> referenceDocumentObject) {
        this.referenceDocumentObject = referenceDocumentObject;
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

    public Screening getReferenceScreening() {
        return referenceScreening;
    }

    public void setReferenceScreening(Screening referenceScreening) {
        this.referenceScreening = referenceScreening;
    }

    public CrossReference getReferenceToCrossReference() {
        return referenceToCrossReference;
    }

    public void setReferenceToCrossReference(CrossReference referenceToCrossReference) {
        this.referenceToCrossReference = referenceToCrossReference;
    }

    @Override
    public String toString() {
        return "Record{" +
                "archivedBy='" + archivedBy + '\'' +
                ", archivedDate=" + archivedDate +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}
