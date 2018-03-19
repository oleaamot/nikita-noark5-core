package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 Sakspartrolle
@Entity
@Table(name = "case_party_role")
// Enable soft delete
// @SQLDelete(sql = "UPDATE case_party_role SET deleted = true WHERE pk_case_party_role_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_case_party_role_id"))
public class CasePartyRole extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.CASE_PARTY_ROLE;
    }
}
