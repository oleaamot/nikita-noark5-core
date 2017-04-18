package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.*;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkCreateEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkTitleDescriptionEntity;
import nikita.util.deserialisers.DocumentDescriptionDeserializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.DOCUMENT_DESCRIPTION;

@Entity
@Table(name = "document_description")
// Enable soft delete of DocumentDescription
@SQLDelete(sql="UPDATE document_description SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@Indexed(index = "document_description")
@JsonDeserialize(using = DocumentDescriptionDeserializer.class)
//TODO: Important! Should DocumentDescription have links to Series???
public class DocumentDescription implements INikitaEntity, INoarkSystemIdEntity, INoarkTitleDescriptionEntity,
        INoarkCreateEntity, IDocumentMedium, IStorageLocation, IDeletion, IScreening, IDisposal, IClassified,
        IDisposalUndertaken, IComment, IElectronicSignature, IAuthor {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_document_description_id", nullable = false, insertable = true, updatable = false)
    protected long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    @Field
    protected String systemId;

    /**
     * M083 - dokumenttype (xs:string)
     */
    @Column(name = "document_type")
    @Audited
    @Field
    protected String documentType;

    /**
     * M054 - dokumentstatus (xs:string)
     */
    @Column(name = "document_status")
    @Audited
    @Field
    protected String documentStatus;

    /**
     * M020 - tittel (xs:string)
     */
    @Column(name = "title")
    @Audited
    @Field
    protected String title;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    @Field
    protected String description;

    /**
     * M600 - opprettetDato (xs:dateTime)
     */
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    @Field
    protected Date createdDate;

    /**
     * M601 - opprettetAv (xs:string)
     */
    @Column(name = "created_by")
    @Audited
    @Field
    protected String createdBy;

    /**
     * M300 - dokumentmedium (xs:string)
     */
    @Column(name = "document_medium")
    @Audited
    @Field
    protected String documentMedium;

    /**
     * M217 - tilknyttetRegistreringSom (xs:string)
     */
    @Column(name = "associated_with_record_as")
    @Audited
    @Field
    protected String associatedWithRecordAs;

    /**
     * M007 - dokumentnummer (xs:integer)
     */
    @Column(name = "document_number")
    @Audited
    @Field
    protected Integer documentNumber;

    /**
     * M620 - tilknyttetDato (xs:date)
     */
    @Column(name = "association_date")
    @Temporal(TemporalType.DATE)
    @Audited
    @Field
    protected Date associationDate;

    /**
     * M621 - tilknyttetAv (xs:string)
     */
    @Column(name = "associated_by")
    @Audited
    @Field
    protected String associatedBy;
    @Column(name = "owned_by")
    @Audited
    @Field
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Links to Records
    @ManyToMany(mappedBy = "referenceDocumentDescription")
    protected Set<Record> referenceRecord = new HashSet<Record>();
    // Links to DocumentObjects
    @OneToMany(mappedBy = "referenceDocumentDescription")
    protected Set<DocumentObject> referenceDocumentObject = new HashSet<DocumentObject>();
    // Links to Comments
    @ManyToMany
    @JoinTable(name = "document_description_comment", joinColumns = @JoinColumn(name = "f_pk_document_description_id",
            referencedColumnName = "pk_document_description_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_comment_id", referencedColumnName = "pk_comment_id"))
    protected Set<Comment> referenceComment = new HashSet<Comment>();
    // Links to Authors
    @ManyToMany
    @JoinTable(name = "document_description_author", joinColumns = @JoinColumn(name = "f_pk_document_description_id",
            referencedColumnName = "pk_document_description_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_author_id", referencedColumnName = "pk_author_id"))
    protected Set<Author> referenceAuthor = new HashSet<Author>();
    // Link to StorageLocation
    @ManyToMany (cascade=CascadeType.ALL)
    @JoinTable(name = "document_description_storage_location", joinColumns = @JoinColumn(
            name = "f_pk_document_description_id",referencedColumnName = "pk_document_description_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_storage_location_id",
            referencedColumnName = "pk_storage_location_id"))
    protected Set<StorageLocation> referenceStorageLocation = new HashSet<StorageLocation>();
    // Link to Classified
    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name = "document_description_classified_id", referencedColumnName = "pk_classified_id")
    protected Classified referenceClassified;
    // Link to Disposal
    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name = "document_description_disposal_id", referencedColumnName = "pk_disposal_id")
    protected Disposal referenceDisposal;
    // Link to DisposalUndertaken
    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name = "document_description_disposal_undertaken_id",
            referencedColumnName = "pk_disposal_undertaken_id")
    protected DisposalUndertaken referenceDisposalUndertaken;
    // Link to Deletion
    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name = "document_description_deletion_id", referencedColumnName = "pk_deletion_id")
    protected Deletion referenceDeletion;
    // Link to Screening
    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name = "document_description_screening_id", referencedColumnName = "pk_screening_id")
    protected Screening referenceScreening;
    @OneToOne
    @JoinColumn(name="pk_electronic_signature_id")
    protected ElectronicSignature referenceElectronicSignature;
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    @Field
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String getBaseTypeName() {
        return DOCUMENT_DESCRIPTION;
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

    public Set<Comment> getReferenceComment() {
        return referenceComment;
    }

    public void setReferenceComment(Set<Comment> referenceComment) {
        this.referenceComment = referenceComment;
    }

    @Override
    public Set<Author> getReferenceAuthor() {
        return referenceAuthor;
    }

    @Override
    public void setReferenceAuthor(Set<Author> referenceAuthor) {
        this.referenceAuthor = referenceAuthor;
    }

    @Override
    public Set<StorageLocation> getReferenceStorageLocation() {
        return referenceStorageLocation;
    }

    @Override
    public void setReferenceStorageLocation(Set<StorageLocation> referenceStorageLocation) {
        this.referenceStorageLocation = referenceStorageLocation;
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
    public ElectronicSignature getReferenceElectronicSignature() {
        return referenceElectronicSignature;
    }

    @Override
    public void setReferenceElectronicSignature(ElectronicSignature referenceElectronicSignature) {
        this.referenceElectronicSignature = referenceElectronicSignature;
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
                ", version='" + version + '\'' +
                ", id=" + id +
                '}';
    }
}
