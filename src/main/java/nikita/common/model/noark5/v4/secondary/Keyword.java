package nikita.common.model.noark5.v4.secondary;

import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.BasicRecord;
import nikita.common.model.noark5.v4.Class;
import nikita.common.model.noark5.v4.File;
import nikita.common.model.noark5.v4.NoarkEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Class> referenceClass = new ArrayList<>();

    // Links to File
    @ManyToMany(mappedBy = "referenceKeyword")
    private List<File> referenceFile = new ArrayList<>();

    // Links to BasicRecord
    @ManyToMany(mappedBy = "referenceKeyword")
    private List<BasicRecord> referenceBasicRecord = new ArrayList<>();

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.KEYWORD;
    }

    public List<Class> getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(List<Class> referenceClass) {
        this.referenceClass = referenceClass;
    }

    public List<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(List<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public List<BasicRecord> getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(List<BasicRecord> referenceBasicRecord) {
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(keyword)
                .toHashCode();
    }
}
