package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.CASE_PARTY_ROLE;

// Noark 5v4 Sakspartrolle
@Entity
@Table(name = "case_party_role")
// Enable soft delete
@SQLDelete(sql = "UPDATE case_party_role SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_case_party_role_id"))
public class CasePartyRole extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return CASE_PARTY_ROLE;
    }
}
