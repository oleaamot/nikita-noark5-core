package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 Tilgangsrestriksjon
@Entity
@Table(name = "access_restriction")
// Enable soft delete
// @SQLDelete(sql = "UPDATE access_restriction SET deleted = true WHERE pk_access_restriction_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_access_restriction_id"))
public class AccessRestriction extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.ACCESS_RESTRICTION;
    }
}
