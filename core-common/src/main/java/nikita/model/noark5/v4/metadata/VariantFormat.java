package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.VARIANT_FORMAT;

// Noark 5v4 variantformat
@Entity
@Table(name = "variant_format")
// Enable soft delete
@SQLDelete(sql = "UPDATE variant_format SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_variant_format_id"))
public class VariantFormat extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return VARIANT_FORMAT;
    }
}
