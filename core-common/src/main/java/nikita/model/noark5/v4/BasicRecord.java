package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "basic_record")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of BasicRecord
@SQLDelete(sql="UPDATE basic_record SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class BasicRecord extends Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * M004 - registreringsID (xs:string)
     */
    @Column(name = "record_id")
    @Audited
    protected String recordId;

    /**
     * M020 - tittel (xs:string)
     */
    @Column(name = "title")
    @Audited
    protected String title;

    /**
     * M025 - offentligTittel (xs:string)
     */
    @Column(name = "official_title")
    @Audited
    protected String officialTitle;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    protected String description;

    /**
     * M300 - dokumentmedium (xs:string)
     */
    @Column(name = "document_medium")
    @Audited
    protected String documentMedium;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Link to StorageLocation
    @ManyToOne
    @JoinColumn(name = "basic_record_storage_location_id", referencedColumnName = "pk_storage_location_id")
    protected StorageLocation referenceStorageLocation;

    // Links to Keywords
    @ManyToMany
    @JoinTable(name = "basic_record_keyword", joinColumns = @JoinColumn(name = "f_pk_record_id", referencedColumnName = "pk_record_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_keyword_id", referencedColumnName = "pk_keyword_id"))
    protected Set<Keyword> referenceKeyword = new HashSet<Keyword>();

    // Links to Authors
    @ManyToMany
    @JoinTable(name = "basic_record_author", joinColumns = @JoinColumn(name = "f_pk_record_id", referencedColumnName = "pk_record_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_author_id", referencedColumnName = "pk_author_id"))
    protected Set<Author> referenceAuthor = new HashSet<Author>();

    // Links to Comments
    @ManyToMany
    @JoinTable(name = "basic_record_comment", joinColumns = @JoinColumn(name = "f_pk_record_id", referencedColumnName = "pk_record_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_comment_id", referencedColumnName = "pk_comment_id"))
    protected Set<Comment> referenceComment = new HashSet<Comment>();

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public StorageLocation getReferenceStorageLocation() {
        return referenceStorageLocation;
    }

    public void setReferenceStorageLocation(
            StorageLocation referenceStorageLocation) {
        this.referenceStorageLocation = referenceStorageLocation;
    }

    public Set<Keyword> getReferenceKeyword() {
        return referenceKeyword;
    }

    public void setReferenceKeyword(Set<Keyword> referenceKeyword) {
        this.referenceKeyword = referenceKeyword;
    }

    public Set<Author> getReferenceAuthor() {
        return referenceAuthor;
    }

    public void setReferenceAuthor(Set<Author> referenceAuthor) {
        this.referenceAuthor = referenceAuthor;
    }

    public Set<Comment> getReferenceComment() {
        return referenceComment;
    }

    public void setReferenceComment(Set<Comment> referenceComment) {
        this.referenceComment = referenceComment;
    }

    @Override
    public String toString() {
        return super.toString() + " BasicRecord{" +
                "documentMedium='" + documentMedium + '\'' +
                ", description='" + description + '\'' +
                ", officialTitle='" + officialTitle + '\'' +
                ", title='" + title + '\'' +
                ", recordId='" + recordId + '\'' +
                '}';
    }
}
