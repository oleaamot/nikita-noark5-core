package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.REGISTRY_ENTRY_TYPE;

// Noark 5v4 journaltype
@Entity
@Table(name = "registry_entry_type")
// Enable soft delete
@SQLDelete(sql = "UPDATE registry_entry_type SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_registry_entry_type_id"))
public class RegistryEntryType extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return REGISTRY_ENTRY_TYPE;
    }
}
