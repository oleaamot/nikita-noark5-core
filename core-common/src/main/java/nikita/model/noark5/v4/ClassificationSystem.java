package nikita.model.noark5.v4;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.CLASSIFICATION_SYSTEM;

@Entity
@Table(name = "classification_system")
// Enable soft delete of ClassificationSystem
// @SQLDelete(sql="UPDATE classification_system SET deleted = true WHERE pk_classification_system_id = ? and version = ?")
// @Where(clause="deleted <> true")
//@Indexed(index = "classification_system")
@AttributeOverride(name = "id", column = @Column(name = "pk_classification_system_id"))
public class ClassificationSystem extends NoarkGeneralEntity {

    private static final long serialVersionUID = 1L;

    /**
     * M086 - klassifikasjonstype (xs:string)
     */
    @Column(name = "classification_type")
    @Audited
    @Field
    private String classificationType;

    // Links to Series
    @OneToMany(mappedBy = "referenceClassificationSystem")
    private Set<Series> referenceSeries = new TreeSet<>();

    // Links to child Classes
    @OneToMany(mappedBy = "referenceClassificationSystem")
    private Set<Class> referenceClass = new TreeSet<>();

    public String getClassificationType() {
        return classificationType;
    }

    public void setClassificationType(String classificationType) {
        this.classificationType = classificationType;
    }

    @Override
    public String getBaseTypeName() {
        return CLASSIFICATION_SYSTEM;
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

    @Override
    public String toString() {
        return "ClassificationSystem{" + super.toString() +
                "classificationType='" + classificationType + '\'' +
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
        ClassificationSystem rhs = (ClassificationSystem) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(classificationType, rhs.classificationType)
                .isEquals();
    }
}
