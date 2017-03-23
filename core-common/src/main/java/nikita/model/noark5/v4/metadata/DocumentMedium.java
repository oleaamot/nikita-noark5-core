package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 dokumentmedium
@Entity
@Table(name = "document_medium")
// Enable soft delete of DocumentMedium
@SQLDelete(sql="UPDATE document_medium SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_document_medium_id"))
public class DocumentMedium extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;
}
