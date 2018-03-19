package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 journalposttype
@Entity
@Table(name = "registry_entry_status")
// Enable soft delete
// @SQLDelete(sql = "UPDATE registry_entry_status SET deleted = true WHERE pk_registry_entry_status_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_registry_entry_status_id"))
public class RegistryEntryStatus extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.REGISTRY_ENTRY_STATUS;
    }
}
