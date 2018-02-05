package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.interfaces.entities.ICommentEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static nikita.config.N5ResourceMappings.COMMENT;

@Entity
@Table(name = "comment")
// Enable soft delete of Comment
// @SQLDelete(sql="UPDATE comment SET deleted = true WHERE pk_comment_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_comment_id"))
public class Comment extends NoarkEntity implements ICommentEntity {

    private static final long serialVersionUID = 1L;

    /**
     * M310 - merknadstekst (xs:string)
     */
    @Column(name = "comment_text")
    @Audited
    private String commentText;

    /**
     * M084 - merknadstype (xs:string)
     */
    @Column(name = "comment_type")
    @Audited
    private String commentType;

    /**
     * M611 - merknadsdato (xs:dateTime)
     */
    @Column(name = "comment_time")
    @Audited
    private Date commentDate;

    /**
     * M612 - merknadRegistrertAv (xs:string)
     */
    @Column(name = "comment_registered_by")
    @Audited
    private String commentRegisteredBy;

    // Link to File
    @ManyToMany(mappedBy = "referenceComment")
    private List<File> referenceFile = new ArrayList<>();

    // Links to BasicRecord
    @ManyToMany(mappedBy = "referenceComment")
    private List<BasicRecord> referenceRecord = new ArrayList<>();

    // Link to DocumentDescription
    @ManyToMany(mappedBy = "referenceComment")
    private List<DocumentDescription> referenceDocumentDescription = new ArrayList<>();

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentRegisteredBy() {
        return commentRegisteredBy;
    }

    public void setCommentRegisteredBy(String commentRegisteredBy) {
        this.commentRegisteredBy = commentRegisteredBy;
    }

    @Override
    public String getBaseTypeName() {
        return COMMENT;
    }

    public List<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(List<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public List<BasicRecord> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(List<BasicRecord> referenceRecord) {
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
        return "Comment{" + super.toString() +
                ", commentText='" + commentText + '\'' +
                ", commentType='" + commentType + '\'' +
                ", commentDate=" + commentDate +
                ", commentRegisteredBy='" + commentRegisteredBy + '\'' +
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
        Comment rhs = (Comment) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(commentText, rhs.commentText)
                .append(commentType, rhs.commentType)
                .append(commentDate, rhs.commentDate)
                .append(commentRegisteredBy, rhs.commentRegisteredBy)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(commentText)
                .append(commentType)
                .append(commentDate)
                .append(commentRegisteredBy)
                .toHashCode();
    }
}
