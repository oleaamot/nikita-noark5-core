package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 graderingskode
@Entity
@Table(name = "classified_code")
// Enable soft delete
// @SQLDelete(sql = "UPDATE classified_code SET deleted = true WHERE pk_classified_code_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_classified_code_id"))
public class ClassifiedCode extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.CLASSIFIED_CODE;
    }
}
