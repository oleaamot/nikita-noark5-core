package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.NoarkEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.AUTHOR;

@Entity
@Table(name = "author")
// Enable soft delete of Author
// @SQLDelete(sql="UPDATE author SET deleted = true WHERE pk_author_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_author_id"))
public class Author extends NoarkEntity {

    /**
     * M024 - forfatter (xs:string)
     */
    @Column(name = "author")
    @Audited
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Links to BasicRecords
    @ManyToMany(mappedBy = "referenceAuthor")
    private Set<BasicRecord> referenceBasicRecord = new TreeSet<>();

    // Links to DocumentDescriptions
    @ManyToMany(mappedBy = "referenceAuthor")
    private Set<DocumentDescription> referenceDocumentDescription = new TreeSet<>();

    @Override
    public String getBaseTypeName() {
        return AUTHOR;
    }

    public Set<BasicRecord> getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(Set<BasicRecord> referenceBasicRecord) {
        this.referenceBasicRecord = referenceBasicRecord;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "Author{" + super.toString() +
                "author='" + author + '\'' +
                '}';
    }
}
