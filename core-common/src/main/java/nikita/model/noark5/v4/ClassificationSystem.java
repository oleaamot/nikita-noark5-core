package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.TreeSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.CLASSIFICATION_SYSTEM;

@Entity
@Table(name = "classification_system")
// Enable soft delete of ClassificationSystem
// @SQLDelete(sql="UPDATE classification_system SET deleted = true WHERE pk_classification_system_id = ? and version = ?")
// @Where(clause="deleted <> true")
@Indexed(index = "classification_system")
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

}
