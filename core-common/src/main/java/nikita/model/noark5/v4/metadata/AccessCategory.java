package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.ACCESS_CATEGORY;

// Noark 5v4 Tilgangskategori
@Entity
@Table(name = "access_category")
// Enable soft delete
@SQLDelete(sql = "UPDATE access_category SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_access_category_id"))
public class AccessCategory extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return ACCESS_CATEGORY;
    }
}
