package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.REGISTRY_ENTRY_STATUS;

// Noark 5v4 journalposttype
@Entity
@Table(name = "registry_entry_status")
// Enable soft delete
@SQLDelete(sql = "UPDATE registry_entry_status SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_registry_entry_status_id"))
public class RegistryEntryStatus extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return REGISTRY_ENTRY_STATUS;
    }
}
