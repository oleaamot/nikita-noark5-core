package nikita.model.noark5.v4.metadata;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.POSTAL_NUMBER;

// Noark 5v4 postnummer
@Entity
@Table(name = "zip")
// Enable soft delete
// @SQLDelete(sql = "UPDATE zip SET deleted = true WHERE pk_zip_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_zip_id"))
public class PostalCode extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return POSTAL_NUMBER;
    }
}
