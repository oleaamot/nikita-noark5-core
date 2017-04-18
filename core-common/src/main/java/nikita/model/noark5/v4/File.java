package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.*;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.deserialisers.FileDeserializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.FILE;

@Entity
@Table(name = "file")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of File
@SQLDelete(sql="UPDATE file SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@Indexed(index = "file")
@JsonDeserialize(using = FileDeserializer.class)
public class File implements INoarkGeneralEntity, IDocumentMedium, IStorageLocation, IKeyword, IClassified,
        IDisposal, IScreening, IComment, ICrossReference
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_file_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    @Field
    protected String systemId;

    /**
     * M003 - mappeID (xs:string)
     */
    @Column(name = "file_id")
    @Audited
    @Field
    protected String fileId;

    /**
     * M020 - tittel (xs:string)
     */
    @Column(name = "title")
    @Audited
    @Field
    protected String title;

    /**
     * M025 - offentligTittel (xs:string)
     */
    @Column(name = "official_title")
    @Audited
    @Field
    protected String officialTitle;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    @Field
    protected String description;

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
    @Field
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
    @Field
    protected Date finalisedDate;

    /**
     * M603 - avsluttetAv (xs:string)
     */
    @Column(name = "finalised_by")
    @Audited
    protected String finalisedBy;
    @Field
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Link to StorageLocation
    @ManyToMany (cascade=CascadeType.ALL)
    @JoinTable(name = "file_storage_location", joinColumns = @JoinColumn(name = "f_pk_file_id",
            referencedColumnName = "pk_file_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_storage_location_id",
            referencedColumnName = "pk_storage_location_id"))
    protected Set<StorageLocation> referenceStorageLocation = new HashSet<StorageLocation>();
    // Links to Keywords
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "file_keyword", joinColumns = @JoinColumn(name = "f_pk_file_id",
            referencedColumnName = "pk_file_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_keyword_id",
            referencedColumnName = "pk_keyword_id"))
    protected Set<Keyword> referenceKeyword = new HashSet<Keyword>();
    // Link to parent File
    @ManyToOne(fetch = FetchType.LAZY)
    protected File referenceParentFile;
    // Links to child Files
    @OneToMany(mappedBy = "referenceParentFile")
    protected Set<File> referenceChildFile = new HashSet<File>();
    // Link to Series
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_series_id", referencedColumnName = "pk_series_id")
    protected Series referenceSeries;
    // Link to Class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_class_id", referencedColumnName = "pk_class_id")
    protected Class referenceClass;
    // Links to Records
    @OneToMany(mappedBy = "referenceFile")
    protected Set<Record> referenceRecord = new HashSet<Record>();
    // Links to Comments
    @ManyToMany (cascade=CascadeType.PERSIST)
    @JoinTable(name = "file_comment", joinColumns = @JoinColumn(name = "f_pk_file_id",
            referencedColumnName = "pk_file_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_comment_id",
            referencedColumnName = "pk_comment_id"))
    protected Set<Comment> referenceComment = new HashSet<Comment>();
    // Links to Classified
    @ManyToOne (cascade=CascadeType.PERSIST)
    @JoinColumn(name = "file_classified_id", referencedColumnName = "pk_classified_id")
    @JsonIgnore
    protected Classified referenceClassified;
    // Link to Disposal
    @ManyToOne (cascade=CascadeType.PERSIST)
    @JoinColumn(name = "file_disposal_id", referencedColumnName = "pk_disposal_id")
    protected Disposal referenceDisposal;
    // Link to Screening
    @ManyToOne (cascade=CascadeType.PERSIST)
    @JoinColumn(name = "file_screening_id", referencedColumnName = "pk_screening_id")
    protected Screening referenceScreening;
    @OneToMany(mappedBy = "referenceFile")
    protected Set<CrossReference> referenceCrossReference;
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOfficialTitle() {
        return officialTitle;
    }

    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        this.version = version;
    }

    @Override
    public String getBaseTypeName() {
        return FILE;
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
    public Screening getReferenceScreening() {
        return referenceScreening;
    }

    @Override
    public void setReferenceScreening(Screening referenceScreening) {
        this.referenceScreening = referenceScreening;
    }

    @Override
    public Set<StorageLocation> getReferenceStorageLocation() {
        return null;
    }

    @Override
    public void setReferenceStorageLocation(Set<StorageLocation> storageLocations) {

    }

    public Set<Keyword> getReferenceKeyword() {
        return referenceKeyword;
    }

    public void setReferenceKeyword(Set<Keyword> referenceKeyword) {
        this.referenceKeyword = referenceKeyword;
    }

    public File getReferenceParentFile() {
        return referenceParentFile;
    }

    public void setReferenceParentFile(File referenceParentFile) {
        this.referenceParentFile = referenceParentFile;
    }

    public Set<File> getReferenceChildFile() {
        return referenceChildFile;
    }

    public void setReferenceChildFile(Set<File> referenceChildFile) {
        this.referenceChildFile = referenceChildFile;
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

    public Set<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }


    public Set<Comment> getReferenceComment() {
        return referenceComment;
    }

    public void setReferenceComment(Set<Comment> referenceComment) {
        this.referenceComment = referenceComment;
    }

    @Override
    public Set<CrossReference> getReferenceCrossReference() {
        return referenceCrossReference;
    }

    @Override
    public void setReferenceCrossReference(Set<CrossReference> referenceCrossReference) {
        this.referenceCrossReference = referenceCrossReference;
    }

    @Override
    public String toString() {
        return "File{" +
                "finalisedBy='" + finalisedBy + '\'' +
                ", finalisedDate=" + finalisedDate +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", documentMedium='" + documentMedium + '\'' +
                ", description='" + description + '\'' +
                ", officialTitle='" + officialTitle + '\'' +
                ", title='" + title + '\'' +
                ", fileId='" + fileId + '\'' +
                ", systemId='" + systemId + '\'' +
                ", version='" + version + '\'' +
                ", id=" + id +
                '}';
    }
}
