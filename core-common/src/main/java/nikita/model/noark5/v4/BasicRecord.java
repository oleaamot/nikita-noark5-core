package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.*;
import nikita.model.noark5.v4.interfaces.entities.INoarkTitleDescriptionEntity;
import nikita.model.noark5.v4.secondary.*;
import nikita.util.deserialisers.BasicRecordDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.BASIC_RECORD;

@Entity
@Table(name = "basic_record")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of BasicRecord
// @SQLDelete(sql="UPDATE basic_record SET deleted = true WHERE pk_record_id = ? and version = ?")
// @Where(clause="deleted <> true")
//@Indexed(index = "basic_record")
@JsonDeserialize(using = BasicRecordDeserializer.class)
public class BasicRecord extends Record implements IDocumentMedium, INoarkTitleDescriptionEntity,
        IStorageLocation, IKeyword, IComment, ICrossReference, IAuthor {

    private static final long serialVersionUID = 1L;

    /**
     * M004 - registreringsID (xs:string)
     */
    @Column(name = "record_id")
    @Audited
    @Field
    protected String recordId;

    /**
     * M020 - tittel (xs:string)
     */
    @NotNull
    @Column(name = "title", nullable = false)
    @Audited
    @Field
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
    @Field
    protected String description;

    /**
     * M300 - dokumentmedium (xs:string)
     */
    @Column(name = "document_medium")
    @Audited
    @Field
    protected String documentMedium;

    @Column(name = "owned_by")
    @Audited
    @Field
    protected String ownedBy;

    // Link to StorageLocation
    @ManyToMany (cascade=CascadeType.PERSIST)
    @JoinTable(name = "basic_record_storage_location", joinColumns = @JoinColumn(name = "f_pk_basic_record_id",
            referencedColumnName = "pk_record_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_storage_location_id",
            referencedColumnName = "pk_storage_location_id"))
    protected Set<StorageLocation> referenceStorageLocation = new TreeSet<>();

    // Links to Keywords
    @ManyToMany
    @JoinTable(name = "basic_record_keyword", joinColumns = @JoinColumn(name = "f_pk_record_id",
            referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_keyword_id", referencedColumnName = "pk_keyword_id"))
    protected Set<Keyword> referenceKeyword = new TreeSet<>();
    // Links to Authors
    @ManyToMany
    @JoinTable(name = "basic_record_author", joinColumns = @JoinColumn(name = "f_pk_record_id",
            referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_author_id", referencedColumnName = "pk_author_id"))
    protected Set<Author> referenceAuthor = new TreeSet<>();
    // Links to Comments
    @ManyToMany
    @JoinTable(name = "basic_record_comment", joinColumns = @JoinColumn(name = "f_pk_record_id",
            referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_comment_id", referencedColumnName = "pk_comment_id"))
    protected Set<Comment> referenceComment = new TreeSet<>();
    // Links to CrossReference
    @OneToMany(mappedBy = "referenceBasicRecord")
    protected Set<CrossReference> referenceCrossReference;
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    @Field
    private Boolean deleted;

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

    @Override
    public String getBaseTypeName() {
        return BASIC_RECORD;
    }

    @Override
    public Set<StorageLocation> getReferenceStorageLocation() {return referenceStorageLocation;}

    public void setReferenceStorageLocation(Set<StorageLocation> referenceStorageLocation) {
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
    public Set<CrossReference> getReferenceCrossReference() {
        return referenceCrossReference;
    }

    public void setReferenceCrossReference(Set<CrossReference> referenceCrossReference) {
        this.referenceCrossReference = referenceCrossReference;
    }

    @Override
    public String toString() {
        return super.toString() + " BasicRecord{" +
                " documentMedium='" + documentMedium + '\'' +
                ", description='" + description + '\'' +
                ", officialTitle='" + officialTitle + '\'' +
                ", title='" + title + '\'' +
                ", recordId='" + recordId + '\'' +
                ", ownedBy='" + ownedBy + '\'' +
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
        BasicRecord rhs = (BasicRecord) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(recordId, rhs.recordId)
                .append(title, rhs.title)
                .append(officialTitle, rhs.officialTitle)
                .append(description, rhs.description)
                .append(documentMedium, rhs.documentMedium)
                .append(ownedBy, rhs.ownedBy)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(recordId)
                .append(title)
                .append(officialTitle)
                .append(description)
                .append(documentMedium)
                .append(ownedBy)
                .toHashCode();
    }
}
