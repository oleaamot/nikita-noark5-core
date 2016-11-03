package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document_description")
// Enable soft delete of DocumentDescription
@SQLDelete(sql="UPDATE document_description SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class DocumentDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_document_description_id", nullable = false, insertable = true, updatable = false)
    protected long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id")
    @Audited
    protected String systemId;

    /**
     * M083 - dokumenttype (xs:string)
     */
    @Column(name = "document_type")
    @Audited
    protected String documentType;

    /**
     * M054 - dokumentstatus (xs:string)
     */
    @Column(name = "document_status")
    @Audited
    protected String documentStatus;

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
     * M300 - dokumentmedium (xs:string)
     */
    @Column(name = "document_medium")
    @Audited
    protected String documentMedium;

    /**
     * M217 - tilknyttetRegistreringSom (xs:string)
     */
    @Column(name = "associated_with_record_as")
    @Audited
    protected String associatedWithRecordAs;

    /**
     * M007 - dokumentnummer (xs:integer)
     */
    @Column(name = "document_number")
    @Audited
    protected Integer documentNumber;

    /**
     * M620 - tilknyttetDato (xs:date)
     */
    @Column(name = "association_date")
    @Temporal(TemporalType.DATE)
    @Audited
    protected Date associationDate;

    /**
     * M621 - tilknyttetAv (xs:string)
     */
    @Column(name = "associated_by")
    @Audited
    protected String associatedBy;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Links to Records
    @ManyToMany(mappedBy = "referenceDocumentDescription")
    protected Set<Record> referenceRecord = new HashSet<Record>();

    // Links to DocumentObjects
    @OneToMany(mappedBy = "referenceDocumentDescription")
    protected Set<DocumentObject> referenceDocumentObject = new HashSet<DocumentObject>();

    // Links to Classified
    @ManyToOne
    @JoinColumn(name = "document_description_classified_id", referencedColumnName = "pk_classified_id")
    protected Classified referenceClassified;

    // Links to Comments
    @ManyToMany
    @JoinTable(name = "document_description_comment", joinColumns = @JoinColumn(name = "f_pk_document_description_id", referencedColumnName = "pk_document_description_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_comment_id", referencedColumnName = "pk_comment_id"))
    protected Set<Comment> referenceComment = new HashSet<Comment>();

    // Link to Disposal
    @ManyToOne
    @JoinColumn(name = "document_description_disposal_id", referencedColumnName = "pk_disposal_id")
    protected Disposal referenceDisposal;

    // Link to DisposalUndertaken
    @ManyToOne
    @JoinColumn(name = "document_description_disposal_undertaken_id", referencedColumnName = "pk_disposal_undertaken_id")
    protected DisposalUndertaken referenceDisposalUndertaken;

    // Link to Deletion
    @ManyToOne
    @JoinColumn(name = "document_description_deletion_id", referencedColumnName = "pk_deletion_id")
    protected Deletion referenceDeletion;

    // Link to Screening
    @ManyToOne
    @JoinColumn(name = "document_description_screening_id", referencedColumnName = "pk_screening_id")
    protected Screening referenceScreening;

    public Long getId() {
        return id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
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

    public String getDocumentMedium() {
        return documentMedium;
    }

    public void setDocumentMedium(String documentMedium) {
        this.documentMedium = documentMedium;
    }

    public String getAssociatedWithRecordAs() {
        return associatedWithRecordAs;
    }

    public void setAssociatedWithRecordAs(String associatedWithRecordAs) {
        this.associatedWithRecordAs = associatedWithRecordAs;
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

    public Integer getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Integer documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Date getAssociationDate() {
        return associationDate;
    }

    public void setAssociationDate(Date associationDate) {
        this.associationDate = associationDate;
    }

    public String getAssociatedBy() {
        return associatedBy;
    }

    public void setAssociatedBy(String associatedBy) {
        this.associatedBy = associatedBy;
    }

    public Set<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    public void addReferenceRecord(Record record) {
        this.referenceRecord.add(record);
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

    public Set<Comment> getReferenceComment() {
        return referenceComment;
    }

    public void setReferenceComment(Set<Comment> referenceComment) {
        this.referenceComment = referenceComment;
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
        return "DocumentDescription{" +
                "associatedBy='" + associatedBy + '\'' +
                ", associationDate=" + associationDate +
                ", documentNumber=" + documentNumber +
                ", associatedWithRecordAs='" + associatedWithRecordAs + '\'' +
                ", documentMedium='" + documentMedium + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", documentStatus='" + documentStatus + '\'' +
                ", documentType='" + documentType + '\'' +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}
