package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.PRECEDENCE_STATUS;

// Noark 5v4 Presedensstatus
@Entity
@Table(name = "precedence_status")
// Enable soft delete
@SQLDelete(sql = "UPDATE precedence_status SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_precedence_status_id"))
public class PrecedenceStatus extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return PRECEDENCE_STATUS;
    }
}
