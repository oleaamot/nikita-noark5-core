package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 variantformat
@Entity
@Table(name = "variant_format")
// Enable soft delete
// @SQLDelete(sql = "UPDATE variant_format SET deleted = true WHERE pk_variant_format_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_variant_format_id"))
public class VariantFormat extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.VARIANT_FORMAT;
    }
}
