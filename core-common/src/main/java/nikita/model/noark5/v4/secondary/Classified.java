package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.*;
import nikita.model.noark5.v4.interfaces.entities.IClassifiedEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.CLASSIFIED;

/**
 * Created by tsodring on 4/10/16.
 */

@Entity
@Table(name = "classified")
// Enable soft delete of Classified
// @SQLDelete(sql="UPDATE classified SET deleted = true WHERE pk_classified_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_classified_id"))
public class Classified extends NoarkEntity implements IClassifiedEntity {

    private static final long serialVersionUID = 1L;
  
    /** M506 - gradering (xs:string) **/
    @Column(name="classification")
    @Audited
    private String classification;

    /** M624 - graderingsdato (xs:dateTime) **/
    @Column(name="classification_date")
    @Audited
    private Date classificationDate;

    /** M629 - gradertAv (xs:string) */
    @Column(name = "classification_by")
    @Audited
    private String classificationBy;

    /** M626 - nedgraderingsdato (xs:dateTime) **/
    @Column(name="classification_downgraded_date")
    @Audited
    private Date classificationDowngradedDate;

    /** M627 - nedgradertAv (xs:string) **/
    @Column(name = "classification_downgraded_by")
    @Audited
    private String classificationDowngradedBy;
    // Links to Series
    @OneToMany(mappedBy = "referenceClassified")
    private Set<Series> referenceSeries = new TreeSet<>();

    // Links to Klass
    @OneToMany(mappedBy = "referenceClassified")
    private Set<Class> referenceClass = new TreeSet<>();

    // Links to File
    @OneToMany(mappedBy = "referenceClassified")
    private Set<File> referenceFile = new TreeSet<>();

    // Links to Record
    @OneToMany(mappedBy = "referenceClassified")
    private Set<Record> referenceRecord = new TreeSet<>();

    // Links to DocumentDescription
    @OneToMany(mappedBy = "referenceClassified")
    private Set<DocumentDescription> referenceDocumentDescription = new TreeSet<>();

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Date getClassificationDate() {
        return classificationDate;
    }

    public void setClassificationDate(Date classificationDate) {
        this.classificationDate = classificationDate;
    }

    public String getClassificationBy() {
        return classificationBy;
    }

    public void setClassificationBy(String classificationBy) {
        this.classificationBy = classificationBy;
    }

    public Date getClassificationDowngradedDate() {
        return classificationDowngradedDate;
    }

    public void setClassificationDowngradedDate(Date classificationDowngradedDate) {
        this.classificationDowngradedDate = classificationDowngradedDate;
    }

    public String getClassificationDowngradedBy() {
        return classificationDowngradedBy;
    }

    public void setClassificationDowngradedBy(String classificationDowngradedBy) {
        this.classificationDowngradedBy = classificationDowngradedBy;
    }

    @Override
    public String getBaseTypeName() {
        return CLASSIFIED;
    }

    public Set<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Set<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
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

    public Set<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "Classified{"  + super.toString() +
                ", classification='" + classification + '\'' +
                ", classificationDate=" + classificationDate +
                ", classificationBy='" + classificationBy + '\'' +
                ", classificationDowngradedDate=" + classificationDowngradedDate +
                ", classificationDowngradedBy='" + classificationDowngradedBy + '\'' +
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
        Classified rhs = (Classified) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(classification, rhs.classification)
                .append(classificationDate, rhs.classificationDate)
                .append(classificationBy, rhs.classificationBy)
                .append(classificationDowngradedDate, rhs.classificationDowngradedDate)
                .append(classificationDowngradedBy, rhs.classificationDowngradedBy)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(classification)
                .append(classificationDate)
                .append(classificationBy)
                .append(classificationDowngradedDate)
                .append(classificationDowngradedBy)
                .toHashCode();
    }
}
