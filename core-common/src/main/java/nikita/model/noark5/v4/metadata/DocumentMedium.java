package nikita.model.noark5.v4.metadata;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM;

// Noark 5v4 dokumentmedium
@Entity
@Table(name = "document_medium")
// Enable soft delete of DocumentMedium
@SQLDelete(sql="UPDATE document_medium SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_document_medium_id"))
public class DocumentMedium extends MetadataSuperClass implements INoarkSystemIdEntity {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return DOCUMENT_MEDIUM;
    }
}
