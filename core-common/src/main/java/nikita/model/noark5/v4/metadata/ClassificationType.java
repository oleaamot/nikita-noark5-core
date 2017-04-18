package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.CLASSIFICATION_TYPE;

// Noark 5v4 Klassifikasjonstype
@Entity
@Table(name = "classification_type")
// Enable soft delete
@SQLDelete(sql = "UPDATE classification_type SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_classification_type_id"))
public class ClassificationType extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return CLASSIFICATION_TYPE;
    }
}
