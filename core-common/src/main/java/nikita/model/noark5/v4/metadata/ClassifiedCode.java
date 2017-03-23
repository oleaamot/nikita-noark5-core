package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 graderingskode
@Entity
@Table(name = "classified_code")
// Enable soft delete
@SQLDelete(sql = "UPDATE classified_code SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_classified_code_id"))
public class ClassifiedCode extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;
}
