package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 skjermingmetadata
@Entity
@Table(name = "screening_metadata")
// Enable soft delete
// @SQLDelete(sql = "UPDATE screening_metadata SET deleted = true WHERE pk_screening_metadata_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_screening_metadata_id"))
public class ScreeningMetadata extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.SCREENING_METADATA;
    }
}
