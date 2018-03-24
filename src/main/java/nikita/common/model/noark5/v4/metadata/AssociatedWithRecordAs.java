package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 Tilknyttet registrering som
@Entity
@Table(name = "associated_with_record_as")
// Enable soft delete
// @SQLDelete(sql = "UPDATE associated_with_record_as SET deleted = true WHERE pk_associated_with_record_as_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_associated_with_record_as_id"))
public class AssociatedWithRecordAs extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.ASSOCIATED_WITH_RECORD_AS;
    }
}
