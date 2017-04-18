package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.DISPOSAL_DECISION;

// Noark 5v4 Kassasjonsvedtak
@Entity
@Table(name = "disposal_decision")
// Enable soft delete
@SQLDelete(sql = "UPDATE disposal_decision SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_disposal_decision_id"))
public class DisposalDecision extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return DISPOSAL_DECISION;
    }
}
