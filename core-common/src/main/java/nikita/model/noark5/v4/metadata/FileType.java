package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 mappetype
@Entity
@Table(name = "file_type")
// Enable soft delete
@SQLDelete(sql = "UPDATE file_type SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_file_type_id"))
public class FileType extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;
}
