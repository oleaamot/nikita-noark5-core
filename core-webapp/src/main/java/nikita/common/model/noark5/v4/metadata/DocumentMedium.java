package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 dokumentmedium
@Entity
@Table(name = "document_medium")
// Enable soft delete of DocumentMedium
// @SQLDelete(sql="UPDATE document_medium SET deleted = true WHERE pk_document_medium_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_document_medium_id"))
public class DocumentMedium extends MetadataSuperClass implements INikitaEntity {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.DOCUMENT_MEDIUM;
    }
}
