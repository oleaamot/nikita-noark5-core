package nikita.model.noark5.v4.metadata;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.FONDS_STATUS;

// Noark 5v4 arkivstatus
@Entity
@Table(name = "fonds_status")
// Enable soft delete of FondsStatus
@SQLDelete(sql = "UPDATE fonds_status SET deleted = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_fonds_status_id"))
public class FondsStatus extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return FONDS_STATUS;
    }
}
