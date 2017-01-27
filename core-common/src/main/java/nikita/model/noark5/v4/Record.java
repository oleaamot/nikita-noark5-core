package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.IClassified;
import nikita.model.noark5.v4.interfaces.IDisposal;
import nikita.model.noark5.v4.interfaces.IScreening;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkCreateEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.deserialisers.RecordDeserializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.lang.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "record")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of Record
@SQLDelete(sql = "UPDATE record SET deleted = true WHERE id = ?")
@Where(clause = "deleted <> true")
@JsonDeserialize(using = RecordDeserializer.class)
public class Record implements INikitaEntity, INoarkSystemIdEntity, INoarkCreateEntity, IClassified, IScreening,
        IDisposal {

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_file_id", referencedColumnName = "pk_file_id")
    protected File referenceFile;

    // Link to Series
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_series_id", referencedColumnName = "pk_series_id")
    protected Series referenceSeries;

    // Link to Class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_class_id", referencedColumnName = "pk_class_id")
    protected Class referenceClass;

    // Links to DocumentDescriptions
    @ManyToMany
    @JoinTable(name = "record_document_description", joinColumns = @JoinColumn(name = "f_pk_record_id",
            referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_document_description_id",
                    referencedColumnName = "pk_document_description_id"))
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<DocumentDescription>();

    // Links to DocumentObjects
    @OneToMany(mappedBy = "referenceRecord", fetch = FetchType.LAZY)
    protected Set<DocumentObject> referenceDocumentObject = new HashSet<DocumentObject>();

    // Links to Classified
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_classified_id", referencedColumnName = "pk_classified_id")
    protected Classified referenceClassified;

    // Link to Disposal
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_disposal_id", referencedColumnName = "pk_disposal_id")
    protected Disposal referenceDisposal;

    // Link to Screening
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_screening_id", referencedColumnName = "pk_screening_id")
    protected Screening referenceScreening;

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

    public Class getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(Class referenceClass) {
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
    public void setReferenceScreening(Screening referenceScreening) {
        this.referenceScreening = referenceScreening;
    }

    @Override
    public Screening getReferenceScreening() {
        return referenceScreening;
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
