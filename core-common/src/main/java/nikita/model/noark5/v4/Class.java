package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.IClassified;
import nikita.model.noark5.v4.interfaces.ICrossReference;
import nikita.model.noark5.v4.interfaces.IDisposal;
import nikita.model.noark5.v4.interfaces.IScreening;
import nikita.model.noark5.v4.secondary.*;
import nikita.util.deserialisers.ClassDeserializer;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static nikita.config.N5ResourceMappings.CLASS;

@Entity
@Table(name = "class")
// Enable soft delete of Class
// @SQLDelete(sql="UPDATE class SET deleted = true WHERE pk_class_id = ? and version = ?")
// @Where(clause="deleted <> true")
//@Indexed(index = "class")
@JsonDeserialize(using = ClassDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_class_id"))
public class Class extends NoarkGeneralEntity implements IDisposal, IScreening, IClassified, ICrossReference {

    /**
     * M002 - klasseID (xs:string)
     */
    @Column(name = "class_id")
    @Audited
    @Field
    private String classId;

    // Links to Keywords
    @ManyToMany
    @JoinTable(name = "class_keyword", joinColumns = @JoinColumn(name = "f_pk_class_id",
            referencedColumnName = "pk_class_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_keyword_id",
            referencedColumnName = "pk_keyword_id"))
    private List<Keyword> referenceKeyword = new ArrayList<>();

    // Link to ClassificationSystem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_classification_system_id", referencedColumnName = "pk_classification_system_id")
    private ClassificationSystem referenceClassificationSystem;

    // Link to parent Class
    @ManyToOne(fetch = FetchType.LAZY)
    private Class referenceParentClass;

    // Links to child Classes
    @OneToMany(mappedBy = "referenceParentClass")
    private List<Class> referenceChildClass = new ArrayList<>();

    // Links to Files
    @OneToMany(mappedBy = "referenceClass")
    private List<File> referenceFile = new ArrayList<>();

    // Links to Records
    @OneToMany(mappedBy = "referenceClass")
    private List<Record> referenceRecord = new ArrayList<>();

    // Links to Classified
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_classified_id", referencedColumnName = "pk_classified_id")
    private Classified referenceClassified;

    // Link to Disposal
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_disposal_id", referencedColumnName = "pk_disposal_id")
    private Disposal referenceDisposal;

    // Link to Screening
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_screening_id", referencedColumnName = "pk_screening_id")
    private Screening referenceScreening;

    @OneToMany(mappedBy = "referenceClass")
    private List<CrossReference> referenceCrossReference;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public String getBaseTypeName() {
        return CLASS;
    }

    public List<Keyword> getReferenceKeyword() {
        return referenceKeyword;
    }

    public void setReferenceKeyword(List<Keyword> referenceKeyword) {
        this.referenceKeyword = referenceKeyword;
    }

    public ClassificationSystem getReferenceClassificationSystem() {
        return referenceClassificationSystem;
    }

    public void setReferenceClassificationSystem(
            ClassificationSystem referenceClassificationSystem) {
        this.referenceClassificationSystem = referenceClassificationSystem;
    }

    public Class getReferenceParentClass() {
        return referenceParentClass;
    }

    public void setReferenceParentClass(Class referenceParentClass) {
        this.referenceParentClass = referenceParentClass;
    }

    public List<Class> getReferenceChildClass() {
        return referenceChildClass;
    }

    public void setReferenceChildClass(List<Class> referenceChildClass) {
        this.referenceChildClass = referenceChildClass;
    }

    public List<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(List<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public List<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(List<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
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
    public void setReferenceDisposal(Disposal disposal) {
        this.referenceDisposal = disposal;
    }

    @Override
    public Screening getReferenceScreening() {
        return referenceScreening;
    }

    @Override
    public void setReferenceScreening(Screening screening) {
        this.referenceScreening = screening;
    }

    @Override
    public List<CrossReference> getReferenceCrossReference() {
        return referenceCrossReference;
    }

    @Override
    public void setReferenceCrossReference(List<CrossReference> referenceCrossReference) {
        this.referenceCrossReference = referenceCrossReference;
    }

    public NoarkEntity chooseParent() {
        if (null != referenceParentClass) {
            return referenceParentClass;
        }
        else if (null != referenceClassificationSystem) {
            return referenceClassificationSystem;
        }
        else { // This should be impossible, a class cannot exist without a parent
            throw new NoarkEntityNotFoundException("Could not find parent object for " + this.toString());
        }
    }

    @Override
    public String toString() {
        return "Class{" + super.toString() +
                ", classId='" + classId + '\'' +
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
        Class rhs = (Class) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(classId, rhs.classId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(classId)
                .toHashCode();
    }
}
