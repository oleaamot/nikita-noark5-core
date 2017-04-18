package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.ASSOCIATED_WITH_RECORD_AS;

// Noark 5v4 Tilknyttet registrering som
@Entity
@Table(name = "associated_with_record_as")
// Enable soft delete
@SQLDelete(sql = "UPDATE associated_with_record_as SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_associated_with_record_as_id"))
public class AssociatedWithRecordAs extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return ASSOCIATED_WITH_RECORD_AS;
    }
}
