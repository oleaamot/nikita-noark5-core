package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.NoarkEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.KEYWORD;

@Entity
@Table(name = "keyword")
// Enable soft delete of Keyword
// @SQLDelete(sql="UPDATE keyword SET deleted = true WHERE pk_keyword_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_keyword_id"))
public class Keyword extends NoarkEntity {

    /**
     * M022 - noekkelord (xs:string)
     */
    @Column(name = "keyword")
    @Audited
    private String keyword;

    // Links to Class
    @ManyToMany(mappedBy = "referenceKeyword")
    private Set<Class> referenceClass = new TreeSet<>();

    // Links to File
    @ManyToMany(mappedBy = "referenceKeyword")
    private Set<File> referenceFile = new TreeSet<>();

    // Links to BasicRecord
    @ManyToMany(mappedBy = "referenceKeyword")
    private Set<BasicRecord> referenceBasicRecord = new TreeSet<>();

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getBaseTypeName() {
        return KEYWORD;
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

    public Set<BasicRecord> getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(Set<BasicRecord> referenceBasicRecord) {
        this.referenceBasicRecord = referenceBasicRecord;
    }

    @Override
    public String toString() {
        return "Keyword{" + super.toString() +
                "keyword='" + keyword + '\'' +
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
        Keyword rhs = (Keyword) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(keyword, rhs.keyword)
                .isEquals();
    }
}
