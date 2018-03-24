package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 Kassasjonsvedtak
@Entity
@Table(name = "disposal_decision")
// Enable soft delete
// @SQLDelete(sql = "UPDATE disposal_decision SET deleted = true WHERE pk_disposal_decision_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_disposal_decision_id"))
public class DisposalDecision extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.DISPOSAL_DECISION;
    }
}
