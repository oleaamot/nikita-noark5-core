package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.SCREENING_METADATA;

// Noark 5v4 Skjermingmetadata
@Entity
@Table(name = "screening_metadata")
// Enable soft delete
@SQLDelete(sql = "UPDATE screening_metadata SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_screening_metadata_id"))
public class ScreeningMetadata extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return SCREENING_METADATA;
    }
}
