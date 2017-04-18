package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.ACCESS_RESTRICTION;

// Noark 5v4 Tilgangsrestriksjon
@Entity
@Table(name = "access_restriction")
// Enable soft delete
@SQLDelete(sql = "UPDATE access_restriction SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_access_restriction_id"))
public class AccessRestriction extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return ACCESS_RESTRICTION;
    }
}
